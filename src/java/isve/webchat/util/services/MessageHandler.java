/*
 * Copyright 2016 yuwang881@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package isve.webchat.util.services;

import isve.webchat.util.constants.WeixinConstants;
import java.util.Map;

/**
 *
 * @author yuwang881@gmail.com
 */
public class MessageHandler {
    
    public static String handleMessage(Map<String,String> message, String tenant){
        String MsgType = message.get("MsgType");
        if (MsgType.equals("event")) return handleEventMessage(message);
        if (MsgType.equals("text")) return handleTextMessage(message);
        if (MsgType.equals("image")) return handleImageMessage(message);
        
        return handleOtherMessage(message); 
    }
    
    public static String generateTextResponse(String toUser,String fromUser,String content) {
        String timeStamp = Long.toString(System.currentTimeMillis());
        return String.format((WeixinConstants.textMessageTemplate),toUser,fromUser,timeStamp,content);
    }
    
    public static String generateCryptedResponse(String crypt,String signature,String timestamp,String nonce) {
        return String.format(WeixinConstants.encryptedMessageTemplate,crypt,signature,timestamp,nonce);
    }
    
    

    private static String handleEventMessage(Map<String, String> message) {
       String event = message.get("Event");
       String toUser =message.get("ToUserName");
       String fromUser =message.get("FromUserName");
       String content= null;       
       if(event == null) event ="other";
       if (event.trim().equals("subscribe")) {
           content= "Welcome to our Test Group!  Li Jian& Wang Yu";
       } else {
           content ="We cannot handle this kind of event!";
       }
       
       return generateTextResponse(fromUser,toUser,content);
    }
    
    
    private static String handleOtherMessage(Map<String, String> message) {
       String toUser =message.get("ToUserName");
       String fromUser =message.get("FromUserName");
       String content= "Hello, How are you!";
       return generateTextResponse(fromUser,toUser,content);
    }
    
    private static String handleImageMessage(Map<String, String> message) {
    
       String toUser =message.get("ToUserName");
       String fromUser =message.get("FromUserName");
       String content= "请提供照片场景名称！";       
   
       return generateTextResponse(fromUser,toUser,content);
    }
    
     private static String handleTextMessage(Map<String, String> message) {
       String toUser =message.get("ToUserName");
       String fromUser =message.get("FromUserName");
       String content =message.get("Content");
       content= "场景名： "+content +"已保存！";       
       return generateTextResponse(fromUser,toUser,content);
    }
}
