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
package isve.webchat.util.constants;

import isve.webchat.util.comm.ConfigUtil;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yuwang881@gmail.com
 */
public class WeixinConstants {

    public static Map<String, Map<String,String>> multiTenants = null;
    public static int tenantNum = ConfigUtil.getInt("tenantnum");
    
   
    public static String MenuCreateURL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    public static String Access_TokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%1$s&secret=%2$s";

    public static String encryptedMessageTemplate = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
    public static String textMessageTemplate = "<xml>\n"
            + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
            + "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
            + "<CreateTime>%3$s</CreateTime>\n"
            + "<MsgType><![CDATA[text]]></MsgType>\n"
            + "<Content><![CDATA[%4$s]]></Content>\n"
            + "</xml>";
    
    static {
        multiTenants = new HashMap();
        for (int i=1; i<=tenantNum; i++){
            String tenantName = null;
            if (i<10) tenantName = "tenant00"+i;
            else if (i<100) tenantName = "tenant0"+i;
            else tenantName = "tenant"+i;
            
            Map<String,String> config = new HashMap();
            config.put("TOKEN",ConfigUtil.get(tenantName+".Token") );
            config.put("APPID",ConfigUtil.get(tenantName+".appID") );
            config.put("APPSECRET",ConfigUtil.get(tenantName+".appsecret") );
            config.put("UID",ConfigUtil.get(tenantName+".uid") );
            config.put("ENCRYPTION",ConfigUtil.get(tenantName+".EncryptionType","raw") );
            config.put("AESKEY",ConfigUtil.get(tenantName+".AESKey","0x00") );
            
            multiTenants.put(tenantName, config);
        }
    }
    
    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 返回消息类型：语音
     */
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 返回消息类型：视频
     */
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：事件
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(关注)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消关注)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：SCAN(二维码扫描事件)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";

    /**
     * OAUTH scope
     */
    public static final String SCOPE_SNSAPI_BASE = "snsapi_base";
    public static final String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";

}
