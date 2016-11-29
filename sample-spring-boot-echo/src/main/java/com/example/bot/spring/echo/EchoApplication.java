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
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
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

        if (inputText == null || inputText.length() > 50) {
            return;
        }

        Source source = event.getSource();
        log.info("SenderId:{}, source:{}", source.getSenderId(), source);
        boolean isEnableAllMessage = false;
        if (source instanceof GroupSource) {
            //source: GroupSource(groupId=C4e0426cb20f0e72e671eb49d5a510d52, userId=null)
            GroupSource groupSource = (GroupSource) source;
            if ("C4e0426cb20f0e72e671eb49d5a510d52".equals(groupSource.getGroupId())) {
                isEnableAllMessage = true;
            }
        } else if (source instanceof UserSource) {
            //source: UserSource(userId=U6de4a4a739436c670a60d28961da2123)
            UserSource userSource = (UserSource) source;
            if ("U6de4a4a739436c670a60d28961da2123".equals(userSource.getUserId())) {
                isEnableAllMessage = true;
            }
        }

        String outputText = null;

        if (inputText.indexOf("กลับ") > -1 && inputText.indexOf("บ้าน") > -1) {

            int value = getRandomNumber(64);
            int modValue = value % 5;

            switch (modValue) {
                case 0:
                    outputText = "งานเสร็จแล้วเหรอจะกลับบ้านอ่ะ";
                    break;
                case 1:
                    outputText = "ได้งานได้การยังวันนี้";
                    break;
                case 2:
                    outputText = "ดีๆ จะได้ประหยัดไฟ";
                    break;
                case 3:
                    outputText = "หล็อบเริน";
                    break;
                default:
                    outputText = "กลับด้วยๆ";
                    break;
            }
        } else if (inputText.indexOf("ง่วง") > -1) {

            int value = getRandomNumber(64);
            int modValue = value % 4;

            switch (modValue) {
                case 0:
                    outputText = "ง่วงเหมือนกัน";
                    break;
                case 1:
                    outputText = "วิ่งรอบชั้น25 สัก 3 รอบรับรองหาย";
                    break;
                case 2:
                    outputText = "กลับบ้านไปพักผ่อนได้แล้ว";
                    break;
                default:
                    outputText = "ไม่ไหวก็กลับไปพักก่อนเถอะ";
                    break;
            }
        } else if (inputText.indexOf("กิน") > -1 && inputText.indexOf("ข้าว") > -1) {

            int value = getRandomNumber(32);
            int modValue = value % 5;

            switch (modValue) {
                case 0:
                    outputText = "ป่ะๆ กินไรกันดี";
                    break;
                case 1:
                    outputText = "กินอยู่นั้น งานการไม่เดินสักที";
                    break;
                case 2:
                    outputText = "รอแป๊บ ขออีก 5 นาที";
                    break;
                case 3:
                    outputText = "รอข้างล่างนะ";
                    break;
                case 4:
                    outputText = "ไปก่อนเลยเดียวตามไป";
                    break;
                default:
                    outputText = "ไม่หิวอ่ะ";
                    break;
            }
        } else if (((inputText.indexOf("สวัสดี") > -1 || inputText.indexOf("หวัดดี") > -1))
                || (((inputText.indexOf("ใคร") > -1) || (inputText.indexOf("ครัย") > -1))
                && ((inputText.indexOf("Tbot") > -1) || (inputText.indexOf("tbot") > -1)))) {

            int value = getRandomNumber(12);
            int modValue = value % 4;

            switch (modValue) {
                case 0:
                    outputText = "ดีจ๊ะ";
                    break;
                case 1:
                    outputText = "รู้จักเราป่าว";
                    break;
                case 2:
                    outputText = "ใครเรียกเราป่าว";
                    break;
                default:
                    outputText = "หวัดดีจ้า...เรา Tbot เอง";
                    break;
            }
        } else if (inputText.indexOf("วี") > -1 && ((inputText.indexOf("ไม่") > -1 && (inputText.indexOf("มา") > -1 || inputText.indexOf("อยู่") > -1))
                || (inputText.indexOf("ลา") > -1))) {

            int value = getRandomNumber(12);
            int modValue = value % 5;

            switch (modValue) {
                case 0:
                    outputText = "ว่างเลยดิ";
                    break;
                case 1:
                    outputText = "หัวหน้าไม่อยู่ เราร่างเริง";
                    break;
                case 2:
                    outputText = "มีอะไรโทรเบอร์ 5000 เลย";
                    break;
                case 3:
                    outputText = "พี่วี...แอ้อู้งานครับ...";
                    break;
                case 4:
                    outputText = "ให้พี่แกพักบ้างจิ";
                    break;
                case 5:
                    outputText = "หนีไปสัมภาษณ์ป่าว";
                    break;
                default:
                    outputText = "สบายละ";
                    break;
            }
        } else if (inputText.indexOf("แอ้") > -1 && inputText.indexOf("สวย") > -1) {

            int value = getRandomNumber(12);
            int modValue = value % 8;

            switch (modValue) {
                case 0:
                    outputText = "สุดสวย";
                    break;
                case 1:
                    outputText = "มีใครจะสวยเท่าอีก";
                    break;
                case 2:
                    outputText = "สุดๆ";
                    break;
                case 3:
                    outputText = "คิดดีๆ";
                    break;
                case 4:
                    outputText = "คุยเรื่องอื่นดีกว่า";
                    break;
                case 5:
                    outputText = "หน้าตาดีไม่ช่วยอะไรเลย...";
                    break;
                case 6:
                    outputText = "บางครั้ง การอธิบายก็ไม่ได้ช่วยให้ดีขึ้น แต่อาจจะทำให้แย่ลง เงียบและเย็นเข้าไว้จะดีกว่า";
                    break;
                default:
                    outputText = "เปร่งประกายมีออร่ามาตั้งแต่หน้าประตูเลยทีเดียว";
                    break;
            }
        } else if (inputText.indexOf("แอ้") > -1 && inputText.indexOf("อ้วน") > -1) {

            int value = getRandomNumber(12);
            int modValue = value % 5;

            switch (modValue) {
                case 0:
                    outputText = "ว่า สุดสวย ได้ไง";
                    break;
                case 1:
                    outputText = "ไม่จริงหรอก แค่เสื้อมันตัวเล็กลงแค่นั้นเอง";
                    break;
                case 2:
                    outputText = "แบ่งให้คุณสามีบ้างนะ";
                    break;
                case 3:
                    outputText = "ไม่จริงหรอก";
                    break;
                default:
                    outputText = "ออกกำลังกายกันเถอะก่อนที่จะ...เหมือน...";
                    break;
            }
        } else if (inputText.indexOf("ด่วย") > -1 || inputText.indexOf("เคด่วย") > -1) {

            int value = getRandomNumber(12);
            int modValue = value % 10;

            switch (modValue) {
                case 0:
                    outputText = "เค้าคือ...ชายผู้มีอนาคต";
                    break;
                case 1:
                    outputText = "buddy ปีนี้ร่วมด้วยช่วยกัน take พี่ด่วย กันด้วยนะจ๊ะ";
                    break;
                case 2:
                    outputText = "มีสัมมาคาราเต้ กันหน่อยนะ";
                    break;
                case 3:
                    outputText = "ซุปปรามแอ้หน่อยนะ";
                    break;
                case 4:
                    outputText = "มีลูกมีหลาน จะตั้งชื่อ คิดเยอะๆกันหน่อยนะ";
                    break;
                case 5:
                    outputText = "ห้ามผวน";
                    break;
                case 6:
                    outputText = "คุณพี่target";
                    break;
                case 7:
                    outputText = "เคารพหัวหน้ากันมาก";
                    break;
                case 8:
                    outputText = "อย่าให้อุ้มได้ด่า";
                    break;
                default:
                    outputText = "ทำอะไรพังอีกแล้ว!!!";
                    break;
            }
        } else if (inputText.indexOf("buddy") > -1) {

            int value = getRandomNumber(12);
            int modValue = value % 4;

            switch (modValue) {
                case 0:
                    outputText = "รักใครชอบใคร จัดเต็ม";
                    break;
                case 1:
                    outputText = "ฝาก take พี่ด่วย หน่อย";
                    break;
                default:
                    outputText = "จัดเต็มแน่นอน";
                    break;
            }

        } else if (inputText.indexOf("เตะ") > -1) {
            int value = getRandomNumber(12);
            int modValue = value % 10;

            switch (modValue) {
                case 0:
                    outputText = "สงสารบอทตาดำๆเถอะ";
                    break;
                case 1:
                    outputText = "เค้าขอโต๊ด";
                    break;
                case 2:
                    outputText = "คนเราเกิดมาตายเพียงครั้งเดียวจะกลัวทำไม";
                    break;
                case 3:
                    outputText = "บอทก็มีหัวใจนะ";
                    break;
                case 4:
                    outputText = "ได้โปรด";
                    break;
                default:
                    outputText = "จิตใจนายทำด้วยอะไร ขนาดบอทยังรังแก";
                    break;
            }
        } else if (inputText.indexOf("คำแก้ว") > -1) {
            outputText = "อีคำแก้ว ลูกอีคำปองมันเป็นงูผี";
        } else if (inputText.length() == 4 && inputText.toLowerCase().indexOf("tbot") > -1) {
            int value = getRandomNumber(256);
            int modValue = value % 30;

            switch (modValue) {
                case 0:
                    outputText = "เบ้าหน้าดี สติ๊กเกอร์อันเดียวเค้าก็ตอบ...";
                    break;
                case 1:
                    outputText = "รักดีดี.ใครก็ต้องการ..รักนานนาน...ใครก็อยากมี";
                    break;
                case 2:
                    outputText = "เงยหน้าสนใจคนข้างๆบ้างนะ";
                    break;
                case 3:
                    outputText = "หัวมีไว้คั่นหูหรอคะ?";
                    break;
                case 4:
                    outputText = "หน้าตาดีไม่ช่วยอะไรเลย...";
                    break;
                case 5:
                    outputText = "ความเชื่อใจมีให้กันครั้งเดียวเนอะ...";
                    break;
                case 6:
                    outputText = "คนแบบนี้ตายไปเป็นปุ๋ยต้นไม่ยังไม่อยากดูดซึมเลยค่ะ";
                    break;
                case 7:
                    outputText = "นอกจากรักคุณแล้วผมก็ไม่เก่งอะไรเลย";
                    break;
                case 8:
                    outputText = "สิ่งสำคัญของการเดินทางไม่ใช่จุดหมาย แต่อยู่ที่ระหว่างทางต่างหาก";
                    break;
                case 9:
                    outputText = "ทำไมคนเราถึงล้ม เพื่อที่เราจะได้เรียนรู้การลุกขึ้นมาใหม่";
                    break;
                case 10:
                    outputText = "Happiness is only real, when shared";
                    break;
                case 11:
                    outputText = "You make me want to be a better man";
                    break;
                case 12:
                    outputText = "ถ้าเราผ่านสิ่งที่เรากลัวที่สุดไปได้....ต่อไปนี้เราจะไม่กลัวอะไรอีก";
                    break;
                case 13:
                    outputText = "ต้องใช้ความแกร่งกล้าอย่างมากที่จะยืนหยัดต่อสู้ศัตรู แต่ต้องมีความกล้ามากกว่านั้นที่จะยืนหยัดต่อเพื่อนของเรา";
                    break;
                case 14:
                    outputText = "ความกลัวเป็นหนทางสู่ด้านมืด ความกลัวทำให้โกรธ โกรธทำให้เกลียด เกลียดทำให้เกิดทุกข์";
                    break;
                case 15:
                    outputText = "คุณไม่อาจเปลี่ยนแปลงคนอื่นได้ โดยไม่ทำลายตัวตนเดิมของเขา";
                    break;
                case 16:
                    outputText = "หากก้าวเท้าไปบนถนน แล้วไม่ก้าวต่อ เราจะไม่มีวันรุ้ว่าถนนไปสิ้นสุดที่ตรงไหน";
                    break;
                case 17:
                    outputText = "นายก็ต้องตาย ฉันก็ต้องตาย เราทุกคนก็ต้องตาย.....แต่ไม่ใช่วันนี้";
                    break;
                case 18:
                    outputText = "ผมมองไม่เห็นตัวตนของคุณตอนคุณอยู่ที่นี่ ตอนนี้คุณไม่อยู่แล้ว ผมกลับเห็นคุณทุกหนทุกแห่ง";
                    break;
                case 19:
                    outputText = "การฉลองและการยินดีกับความสำเร็จเป็นเรื่องที่ดี แต่สิ่งสำคัญกว่านั้นคือการเรียนรู้จากความผิดพลาด";
                    break;
                case 20:
                    outputText = "ลูกค้าที่แสดงความไม่พอใจ นั่นล่ะคือแหล่งเรียนรู้ที่ดีที่สุด";
                    break;
                case 21:
                    outputText = "คนทุกคนจำเป็นต้องมี “โค้ช” ไม่ว่าคุณจะเป็น นักเล่นบาสเกตบอล นักเทนนิส นักกีฬายิมนาสติก และอื่นๆ";
                    break;
                case 22:
                    outputText = "ถ้าทำให้มันดีไม่ได้ อย่างน้อยก็ทำให้มันออกมาดูดีสิ";
                    break;
                case 23:
                    outputText = "เราต้องการผู้คนที่พร้อมจะแสดงความคิดเห็น และนั่นคือวิธีเรียนรู้ของเรา";
                    break;
                case 24:
                    outputText = "ถ้าคุณอยากให้ลูกคุณเก่ง คุณต้องหาทางให้ลูกได้เจอ ครูที่สุดยอด “ไม่ใช่โรงเรียนที่สุดยอด”";
                    break;
                case 25:
                    outputText = "ผมเชื่อในนวัตกรรม และหนทางที่คุณจะได้ นวัตกรรม นั้นได้มาจากการศึกษาในเรื่องเบสิคพื้นฐาน";
                    break;
                case 26:
                    outputText = "ชีวิตมันไม่มีความยุติธรรม จงยอมรับและเคยชินกับมันซะ";
                    break;
                case 27:
                    outputText = "ถ้าคุณไม่ชอบเทคโนโลยี ชีวิตคุณมีปัญหาแน่!";
                    break;
                case 28:
                    outputText = "ไม่ว่าผมจะอยู่ที่ไหน ไม่ว่าในสำนักงาน บ้าน หรือบนถนน ผมไม่พลาดที่จะพกหนังสือไปอ่านด้วยเสมอ";
                    break;
                case 29:
                    outputText = "มันต้องใช้ 20 ปีในการสร้างชื่อเสียง แต่สามารถใช้แค่ 5 นาที ในการทำลาย";
                    break;
                default:
                    outputText = "";
                    break;
            }
        } else if (inputText.length() < 20) {
            String lowercase = inputText.toLowerCase();

            if (lowercase.indexOf("order") > -1 && lowercase.indexOf("type") > -1) {
                String stringOrderType = inputText.replace("order", "").replace("type", "");
                outputText = "ไม่รู้จัก order type " + stringOrderType + "อ่ะ";
                try {
                    int intOrderType = Integer.parseInt(stringOrderType.trim());
                    switch (intOrderType) {
                        case 1:
                            outputText = "1 : Activation : extId=ACTIVATION";
                            break;
                        case 2:
                            outputText = "2 : ChangePP(Agreement/Subscriber) : extId=CHANGE_PP ";
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {
                    outputText = ex.getMessage();
                }
            }
        }

        //

        else if (isEnableAllMessage) {
            if (inputText.indexOf("ช่วย") > -1 || (inputText.indexOf("ทำ") > -1 && (inputText.indexOf("งาน") > -1))) {

                int value = getRandomNumber(12);
                int modValue = value % 5;

                switch (modValue) {
                    case 0:
                        outputText = "ไม่ทำโว้ยย";
                        break;
                    case 1:
                        outputText = "ไม่ทำโว้ยยย";
                        break;
                    case 2:
                        outputText = "ไม่ทำโว้ยยยย";
                        break;
                    case 3:
                        outputText = "ไม่ทำโว้ยย..";
                        break;
                    default:
                        outputText = "ไม่ทำโว้ย";
                        break;
                }
            } else if (inputText.indexOf("แก้") > -1 && inputText.indexOf("ด้วย") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 5;

                switch (modValue) {
                    case 0:
                        outputText = "ไม่ทำโว้ยย";
                        break;
                    case 1:
                        outputText = "ไม่ทำโว้ยยย";
                        break;
                    case 2:
                        outputText = "ไม่ทำโว้ยยยย";
                        break;
                    case 3:
                        outputText = "ไม่ทำโว้ยยยย";
                        break;
                    default:
                        outputText = "ไม่ทำโว้ย";
                        break;
                }
            } else if (inputText.indexOf("เอา") > -1 && inputText.indexOf("ที่") > -1 && inputText.indexOf("สบาย") > -1 && inputText.indexOf("ใจ") > -1) {
                outputText = "เรื่องของมรึง";
            } else if (inputText.indexOf("ล้ง") > -1 || inputText.indexOf("ล่งล้ง") > -1) {

                int value = getRandomNumber(12);
                int modValue = value % 10;

                switch (modValue) {
                    case 0:
                        outputText = "ยังไม่ว่าง";
                        break;
                    case 1:
                        outputText = "อย่าพึ่งกวน";
                        break;
                    case 2:
                        outputText = "เล่นเกมอยู่";
                        break;
                    case 3:
                        outputText = "ไม่";
                        break;
                    case 4:
                        outputText = "อาไหยหย๋อ";
                        break;
                    case 5:
                        outputText = "พี่วีครับ";
                        break;
                    default:
                        outputText = "กวนตีนหย๋อ";
                        break;
                }
            } else if (inputText.indexOf("คุย") > -1 && inputText.indexOf("งาน") > -1) {

                int value = getRandomNumber(32);
                int modValue = value % 6;

                switch (modValue) {
                    case 0:
                        outputText = "สร้างห้องใหม่นะ ถ้าจะคุยงาน";
                        break;
                    case 1:
                        outputText = "ไปเล่นกันตรงโน้น นะ";
                        break;
                    case 2:
                        outputText = "พรุ่งนี้ค่อยคุย";
                        break;
                    case 3:
                        outputText = "ไม่ทำโว้ย";
                        break;
                    case 4:
                        outputText = "ไม่คุยโว้ย";
                        break;
                    case 5:
                        outputText = "เกร็งใจอุ้มบ้างนะ";
                        break;
                    default:
                        outputText = "เออ ไม่รู้จักเวลาเลย";
                        break;
                }
            } else if (inputText.indexOf("พี่") > -1 && inputText.indexOf("บอล") > -1) {
                outputText = "เรียกให้ถูก ด้วยนะ ต้องเป็น พี่บอนด์";
            } else if (inputText.indexOf("สกรัม") > -1 || inputText.indexOf("scrum") > -1) {

                int value = getRandomNumber(12);
                int modValue = value % 4;

                switch (modValue) {
                    case 0:
                        outputText = "มาๆ มาคุยกันหน่อย";
                        break;
                    case 1:
                        outputText = "วันนี้งดนะ ไม่มีอะไรอัพเดท";
                        break;
                    default:
                        outputText = "เรียกแอ้มาเลย";
                        break;
                }
            } else if (inputText.indexOf("pantip.com") > -1) {

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

            } else if (inputText.indexOf("facebook") > -1) {

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
                        outputText = "ขอตัวไปส่องเฟสกันน๊า";
                        break;
                }
            } else if ((inputText.indexOf("เล่น") > -1 && inputText.indexOf("พ่อ") > -1)
                    || (inputText.indexOf("หยาบ") > -1 && inputText.indexOf("คาย") > -1)
                    || (inputText.indexOf("ด่า") > -1)
                    || (inputText.indexOf("ควาย") > -1)
                    || (inputText.indexOf("สาด") > -1)
                    || (inputText.indexOf("แสด") > -1)
                    || (inputText.indexOf("สัด") > -1)
                    || (inputText.indexOf("แม่ง") > -1)
                    || (inputText.indexOf("เหี้ย") > -1)
                    || (inputText.indexOf("โว้ย") > -1)
                    || (inputText.indexOf("มึง") > -1)
                    || (inputText.indexOf("มรึง") > -1)
                    || (inputText.indexOf("กู") > -1)
                    ) {

                int value = getRandomNumber(12);
                int modValue = value % 10;

                switch (modValue) {
                    case 0:
                        outputText = "อย่าหยาบคาย";
                        break;
                    case 1:
                        outputText = "การศึกษาไม่ช่วยอะไร";
                        break;
                    case 2:
                        outputText = "หยาบแบบนี้ไปต่อไม่ถูกเลย";
                        break;
                    case 3:
                        outputText = "พูดกันดีๆก็ได้นะ";
                        break;
                    case 4:
                        outputText = "ได้ยินแบบนี้ขึ้นเลย";
                        break;
                    case 5:
                        outputText = "หยาบจริง";
                        break;
                    case 6:
                        outputText = "ดูคำพูดคำจาสิ";
                        break;
                    case 7:
                        outputText = "บวกกันไหม";
                        break;
                    case 8:
                        outputText = "สนุกปากเลยนะ";
                        break;
                    case 9:
                        outputText = "ครับ คะ ใช่เป็นไหม";
                        break;
                    default:
                        outputText = "สุภาพกันหน่อย";
                        break;
                }
            } else if (inputText.indexOf("แฟ") > -1) {

                int value = getRandomNumber(12);
                int modValue = value % 3;

                switch (modValue) {
                    case 0:
                        outputText = "แฟๆ";
                        break;
                    case 1:
                        outputText = "บัคกันไหม";
                        break;
                    default:
                        outputText = "เลี้ยงแฟหน่อย";
                        break;
                }
            } else if ((inputText.indexOf("ว่าง") > -1 && inputText.indexOf("งาน") > -1)
                    || (inputText.indexOf("ไม่") > -1 && inputText.indexOf("มี") > -1 && inputText.indexOf("ทำ") > -1)
                    || (inputText.indexOf("เบื่อ") > -1 && inputText.indexOf("จัง") > -1)
                    ) {

                int value = getRandomNumber(12);
                int modValue = value % 3;

                switch (modValue) {
                    case 0:
                        outputText = "ว่างก็ไปช่วยคนอื่นทำงานสิ";
                        break;
                    case 1:
                        outputText = "เค้าจ้างมาทำงานนะ";
                        break;
                    default:
                        outputText = "งานการมีไม่ทำ";
                        break;
                }
            } else if (inputText.indexOf("ไม่ค่อยสบายตัว") > -1) {
                outputText = inputText.replace("ไม่ค่อยสบายตัว", "เฉียวฉาวเชียวชาว");
            } else if (inputText.indexOf("พูดจาโกหกกลับไปกลับมา") > -1) {
                outputText = "สับปลับ";
            } else if (inputText.indexOf("โกหก") > -1) {
                outputText = inputText.replace("โกหก", "ขี้หก");
            } else if (inputText.indexOf("อิจฉา") > -1) {
                outputText = inputText.replace("อิจฉา", "หึงสา");
            } else if (inputText.indexOf("รถถีบ") > -1) {
                outputText = inputText.replace("รถถีบ", "รถจักรยาน");
            } else if (inputText.indexOf("แหลง") > -1) {
                outputText = "พี่แหลงชัดคำเดียวว่ารั้กเธอ";
            } else if (inputText.indexOf("แลหวัน") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 2;
                switch (modValue) {
                    case 0:
                        outputText = inputText.replace("แลหวัน", "มอง");
                        break;
                    default:
                        outputText = inputText.replace("แลหวัน", "ดู");
                        break;
                }
            } else if (inputText.indexOf("ทำไม") > -1) {
                outputText = inputText.replace("ทำไม", "ไซร์");
            } else if (inputText.indexOf("เป็นยังไงอีก") > -1) {
                outputText = inputText.replace("เป็นยังไงอีก", "พันพรือหล้าว");
            } else if (inputText.indexOf("ทุกวันนี้") > -1) {
                outputText = inputText.replace("ทุกวันนี้", "คุมวัน");
            } else if (inputText.indexOf("เวทนา") > -1) {
                outputText = inputText.replace("เวทนา", "เวดนา");
            } else if (inputText.indexOf("เอียง") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 2;
                switch (modValue) {
                    case 0:
                        outputText = inputText.replace("เอียง", "แคงควน");
                        break;
                    default:
                        outputText = inputText.replace("เอียง", "เซน");
                        break;
                }
            } else if (inputText.indexOf("ไม่ตรง") > -1) {
                outputText = inputText.replace("ไม่ตรง", "แคงควน");
            } else if (inputText.indexOf("ปัจุบัน") > -1) {
                outputText = inputText.replace("ปัจุบัน", "ในโย");
            } else if (inputText.indexOf("โมโห") > -1) {
                outputText = inputText.replace("โมโห", "หวิบ");
            } else if (inputText.indexOf("ไม่รู้เรือง") > -1) {
                outputText = inputText.replace("ไม่รู้เรือง", "ไม่รู้หวัน");
            } else if (inputText.indexOf("รองเท้า") > -1) {
                outputText = inputText.replace("รองเท้า", "เกือก");
            } else if (inputText.indexOf("หยอก") > -1 && inputText.indexOf("เล่น") > -1) {
                outputText = "บ่าวหยอกนิน้อง";
            } else if (inputText.indexOf("โง่") > -1) {
                outputText = "อีโหว้งวก";
            } else if (inputText.indexOf("เมื่อวานนี้") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 2;
                switch (modValue) {
                    case 0:
                        outputText = inputText.replace("เมื่อวานนี้", "แต่วา");
                        break;
                    default:
                        outputText = inputText.replace("เมื่อวานนี้", "แรกแต่วา");
                        break;
                }
            } else if (inputText.indexOf("พรุ่งนี้") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 2;
                switch (modValue) {
                    case 0:
                        outputText = inputText.replace("พรุ่งนี้", "ต่อเช้า");
                        break;
                    default:
                        outputText = inputText.replace("พรุ่งนี้", "ต่อโพรก");
                        break;
                }
            } else if (inputText.indexOf("วันมะรืน") > -1) {
                outputText = inputText.replace("วันมะรืน", "ต่อรือ");
            } else if (inputText.indexOf("ไม่") > -1 && inputText.indexOf("เต็ม") > -1 && (inputText.indexOf("เต็ง") > -1 || inputText.indexOf("บาท") > -1)) {
                int value = getRandomNumber(12);
                int modValue = value % 3;
                switch (modValue) {
                    case 0:
                        outputText = "ข้าดหุ๊น";
                        break;
                    case 1:
                        outputText = "เบา";
                        break;
                    default:
                        outputText = "ฉ็อมฉ็อม";
                        break;
                }
            } else if (inputText.indexOf("ทั้งหมด") > -1) {
                outputText = inputText.replace("ทั้งหมด", "ทั้งเพ");
            } else if (inputText.indexOf("ทั้งสิ้น") > -1) {
                outputText = inputText.replace("ทั้งสิ้น", "ทั้งเพ");
            } else if (inputText.indexOf("ใจ") > -1 && (inputText.indexOf("สะ") > -1 || inputText.indexOf("ถูก") > -1)) {
                outputText = "ด่ายแหร่งอ็อก";
            } else if (inputText.indexOf("ดุ") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 3;
                switch (modValue) {
                    case 0:
                        outputText = "แง็ง";
                        break;
                    case 1:
                        outputText = "ด้น";
                        break;
                    default:
                        outputText = "ด่อน";
                        break;
                }
            } else if (inputText.indexOf("ยาก") > -1 && inputText.indexOf("จน") > -1) {
                int value = getRandomNumber(12);
                int modValue = value % 4;
                switch (modValue) {
                    case 0:
                        outputText = "หม้ายไหร ";
                        break;
                    case 1:
                        outputText = "ม้ายหรั้ย";
                        break;
                    case 2:
                        outputText = "ลอกอ";
                        break;
                    default:
                        outputText = "เปลาๆ";
                        break;
                }
            } else if (inputText.indexOf("ไม่มี") > -1) {
                outputText = inputText.replace("ไม่มี", "หาม้าย");
            } else if (inputText.indexOf("ยุ่ง") > -1 || inputText.indexOf("เสือก") > -1) {
                outputText = "ทำถ้าว";
            } else if (inputText.indexOf("เละ") > -1) {
                outputText = "เนียนแจ็กแจ๊ก";
            } else if (inputText.indexOf("เมื่อไหร่") > -1) {
                outputText = inputText.replace("เมื่อไหร่", "ตอใด");
            }

        } //isEnableAllMessage

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
