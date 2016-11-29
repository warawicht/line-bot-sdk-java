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

        if (inputText == null || inputText.length() > 100) {
            return;
        }

        Source source = event.getSource();
        boolean isDevGroup = false;
        if (source instanceof GroupSource) {
            //source: GroupSource(groupId=C4e0426cb20f0e72e671eb49d5a510d52, userId=null)
            GroupSource groupSource = (GroupSource) source;
            if ("C4e0426cb20f0e72e671eb49d5a510d52".equals(groupSource.getGroupId())) {
                isDevGroup = true;
            }
        }

        String outputText = null;

        if (inputText.indexOf("กลับ") > -1 && inputText.indexOf("บ้าน") > -1) {

            int value = getRandomNumber(64);
            int modValue = value % 4;

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
            int modValue = value % 6;

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

        }

        if (isDevGroup) {
            if (inputText.indexOf("ช่วย") > -1 || (inputText.indexOf("ทำ") > -1 && (inputText.indexOf("งาน") > -1))) {
                outputText = "ไม่ทำโว้ย";
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
