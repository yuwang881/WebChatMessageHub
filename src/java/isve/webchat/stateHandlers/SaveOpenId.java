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
package isve.webchat.stateHandlers;

import isve.webchat.util.comm.HttpsURLHelper;
import isve.webchat.util.constants.WeixinConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author yuwang881@gmail.com
 */
public class SaveOpenId implements StateHandler{

    @Override
    public String stateProcess(HttpServletRequest request, String tenant, String code, String configUrl) {
        String appId= WeixinConstants.multiTenants.get(tenant).get("APPID");
        String secret = WeixinConstants.multiTenants.get(tenant).get("APPSECRET");
        String getOAuthTokenURL = String.format(WeixinConstants.OAuth_TokenURL, appId,secret,code);
        String jsonResult = null;
        HttpsURLHelper helper;
        try {
            helper = new HttpsURLHelper(true);
            helper.setEncoding("GBK");
            jsonResult = helper.get(getOAuthTokenURL);
        } catch (Exception ex) {
            request.getServletContext().log(ex.getMessage());
        }
        
        String openid = null;
                
         JSONObject json = new JSONObject(jsonResult);
       
        try {
            openid = json.getString("openid");
        } catch (JSONException JE) {
            request.getServletContext().log("cannot get access openid, error: "+ JE.getMessage());
        }   
        
        //save openid to th session
        request.getSession(true).setAttribute("openid", openid);
       
        //return the config url passed in
        return configUrl;
    }
    
}
