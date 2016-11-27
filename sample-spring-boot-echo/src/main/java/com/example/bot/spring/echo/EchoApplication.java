/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring.echo;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

import static java.util.Collections.singletonList;

@SpringBootApplication
@LineMessageHandler
@Slf4j
public class EchoApplication {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        //log.info("event: {}", event);

        String inputText = event.getMessage().getText();

        String outputText = null;

        if (inputText.indexOf("กลับ") > -1 && inputText.indexOf("บ้าน") > -1) {

            int value = getRandomNumber(64);
            int modValue = value % 3;

            switch (modValue) {
                case 0:
                    outputText = "งานเสร็จแล้วเหรอจะกลับบ้านอ่ะ";
                    break;
                case 1:
                    outputText = "ได้งานได้การยังวันนี้";
                    break;
                default:
                    outputText = "กลับด้วยๆ";
                    break;
            }
        }

        if (inputText.indexOf("กิน") > -1 && inputText.indexOf("ข้าว") > -1) {

            int value = getRandomNumber(32);
            int modValue = value % 3;

            switch (modValue) {
                case 0:
                    outputText = "ป่ะๆ กินไรกันดี";
                    break;
                case 1:
                    outputText = "กินอยู่นั้น งานการไม่เดินสักที";
                    break;
                default:
                    outputText = "ไม่หิวอ่ะ";
                    break;
            }
        }

        if (((inputText.indexOf("สวัสดี") > -1 || inputText.indexOf("หวัดดี") > -1))
                || ((inputText.indexOf("ใคร") > -1) && ((inputText.indexOf("Tbot") > -1) || (inputText.indexOf("tbot") > -1)))) {

            int value = getRandomNumber(12);
            int modValue = value % 3;

            switch (modValue) {
                case 0:
                    outputText = "ดีจ๊ะ";
                    break;
                case 1:
                    outputText = "ไม่บอก";
                    break;
                default:
                    outputText = "หวัดดีจ้า...เรา Tbot เอง";
                    break;
            }
        }

        if (inputText.indexOf("pantip.com") > -1) {

            int value = getRandomNumber(12);
            int modValue = value % 3;

            switch (modValue) {
                case 0:
                    outputText = "ชอบเผือกกันจริง";
                    break;
                case 1:
                    outputText = "อ่านแต่ พันทิป งานเสร็จแล้วเหรอ";
                    break;
                default:
                    outputText = "ขอตัวไปเผือก...ที่พันดิปแป๊รบบ...";
                    break;
            }

        }

        if (inputText.indexOf("facebook") > -1) {

            int value = getRandomNumber(12);
            int modValue = value % 3;

            switch (modValue) {
                case 0:
                    outputText = "ชอบส่องกันจริง";
                    break;
                case 1:
                    outputText = "เล่นแต่ เฟส งานการไม่ทำ";
                    break;
                default:
                    outputText = "ขอตัวไปส่องเฟสสเปปปป";
                    break;
            }
        }

        if (inputText.indexOf("วี") > -1 && inputText.indexOf("ไม่") > -1 && (inputText.indexOf("มา") > -1 || inputText.indexOf("อยู่") > -1)) {

            int value = getRandomNumber(12);
            int modValue = value % 3;

            switch (modValue) {
                case 0:
                    outputText = "ว่างเลยดิ";
                    break;
                case 1:
                    outputText = "หัวหน้าไม่อยู่ เราร่างเริง";
                    break;
                default:
                    outputText = "สบายละ";
                    break;
            }
        }


        if (outputText != null && outputText.length() > 0) {
            final BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(event.getReplyToken(),
                            singletonList(new TextMessage(outputText))))
                    .get();
            //log.info("Sent messages: {}", apiResponse);
        }
    }

    @EventMapping
    public void defaultMessageEvent(Event event) {
        //log.info("event: {}", event);
    }

    private int getRandomNumber(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

}
