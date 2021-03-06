# line-bot-spring-boot

This is a Spring Boot auto-configuration for the LINE Messaging API.

## Synopsis

```java
package com.example.bot.spring.echo;

import static java.util.Collections.singletonList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
public class EchoApplication {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        System.out.println("event: " + event);
        final BotApiResponse apiResponse = lineMessagingClient
                .replyMessage(new ReplyMessage(event.getReplyToken(),
                                               singletonList(new TextMessage(event.getMessage().getText()))))
                .get();
        System.out.println("Sent messages: " + apiResponse);
    }

    @EventMapping
    public void defaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
```

## Usage

Add this library as a dependency of your project.

You can then get parsed messages like the following:

```java
@LineMessageHandler
public class EchoApplication {
    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        System.out.println("event: " + event);
        final BotApiResponse apiResponse = lineMessagingClient
                .replyMessage(new ReplyMessage(event.getReplyToken(),
                                               singletonList(new TextMessage(event.getMessage().getText()))))
                .get();
        System.out.println("Sent messages: " + apiResponse);
    }
}
```

Method with `@EventMapping` on `@LineMessageHandler` annotated class is treated as message event handler.

The event handler method should be only one argument with some type implements `Event`.

When webhook is reached, SDK call decide which method should be called based on the arguments type.

All methods detected by SDK is logged into start up time as follows.

```
c.l.b.s.b.s.LineMessageHandlerSupport    : Mapped "[MessageEvent<TextMessageContent>]" onto public java.util.List<com.linecorp.bot.model.message.TextMessage> com.example.bot.spring.echo.EchoApplication.handleTextMessageEvent(com.linecorp.bot.model.event.MessageEvent<com.linecorp.bot.model.event.message.TextMessageContent>) throws java.lang.Exception
c.l.b.s.b.s.LineMessageHandlerSupport    : Mapped "[Event]" onto public void com.example.bot.spring.echo.EchoApplication.handleDefaultMessageEvent(com.linecorp.bot.model.event.Event)
```

## Configuration

The Messaging API SDK is automatically configured by the system properties. The parameters are shown below.

| Parameter | Description |
| ----- | ------ |
| line.bot.channelToken | Channel access token for the server |
| line.bot.channelSecret | Channel secret for the server |
| line.bot.connectTimeout | Connection timeout in milliseconds |
| line.bot.readTimeout | Read timeout in milliseconds |
| line.bot.writeTimeout | Write timeout in milliseconds |
| line.bot.handler.enabled| Enable @EventMapping mechanism. (default: true)|
| line.bot.handler.path| Path to waiting webhook. (default: `/callback`)|
