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
package isve.webchat.util.comm;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yuwang881@gmail.com
 */
public class MessageUtil {
    
    //utf8 encoding
    public static Map<String, String> parseXML(byte[] xmlString) throws UnsupportedEncodingException{ 
        
        Map<String, String> map = new HashMap<String, String>();
        
        StringBM bm1 = new StringBM("<xml>");
        StringBM bm2 = new StringBM("<");
        StringBM bm3 = new StringBM(">");
        StringBM bm4 = new StringBM("[CDATA[");
        StringBM bm5 = new StringBM("]");
        StringBM bm6 = new StringBM("SendPicsInfo");
        
        bm1.init();
        bm2.init();
        bm3.init();
        bm4.init();
        bm5.init();
        bm6.init();
        
        //parse the raw xml data to a HashMap
        int index =0;
        int tmp=0;
        String name=null;
        String value=null;
        
        //to find a valid <xml> tag
        index= bm1.indexOf(xmlString, 0);
        if (index < 0) {
            return null;
        }

        while (index >= 0) {
            //  get name
            index = bm2.indexOf(xmlString, index + 4);
            tmp = index;
            index = bm3.indexOf(xmlString, index);
            if (index < 0) {
                break;
            }
            name = new String(xmlString, tmp + 1, index - tmp - 1, "UTF-8");

            // if name equal SendPicsInfo node
            if (name.equals("SendPicsInfo")) {
                tmp = index;
                index = bm6.indexOf(xmlString, index);
                value = new String(xmlString,tmp+1,index-tmp-3,"UTF8");
            } else {     // get other single layer value
                tmp = bm4.indexOf(xmlString, index);

                if (tmp - index < 8 && tmp - index > 0) {
                    // has CDATA
                    index = bm5.indexOf(xmlString, tmp);
                    if (index < 0) {
                        break;
                    }
                    value = new String(xmlString, tmp + 7, index - tmp - 7, "UTF-8");
                    index = index + 6;
                } else {
                    // has no CDATA
                    tmp = bm2.indexOf(xmlString, index);
                    if (tmp < 0) {
                        break;
                    }
                    value = new String(xmlString, index + 1, tmp - index - 1, "UTF-8");
                    index = tmp + 2;
                }
            }
            System.out.println(name + "                  :                                 " + value);
            map.putIfAbsent(name, value);
            
        }
        
        return map;
    }
    
   public static void main(String[] args) throws UnsupportedEncodingException {
       //String xml ="<xml><ToUserName><![CDATA[gh_e162768bd940]]></ToUserName><FromUserName><![CDATA[ohI7AuOM3UUkyp8oJZDsuZ8xdKX8]]></FromUserName><CreateTime>1452321889</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[pic_photo_or_album]]></Event><EventKey><![CDATA[V1002_COLLECT]]></EventKey><SendPicsInfo><Count>1</Count><PicList><item><PicMd5Sum><![CDATA[465961e409c299dc6937b73b12efec1e]]></PicMd5Sum></item></PicList></SendPicsInfo></xml>";
       String xml=" <xml><ToUserName><![CDATA[gh_e162768bd940]]></ToUserName><FromUserName><![CDATA[ohI7AuOM3UUkyp8oJZDsuZ8xdKX8]]></FromUserName><CreateTime>1452322856</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[]]></EventKey></xml>";
       MessageUtil.parseXML(xml.getBytes());
   }
    

}
