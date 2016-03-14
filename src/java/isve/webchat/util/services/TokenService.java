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

import isve.webchat.util.comm.HttpsURLHelper;
import isve.webchat.util.constants.WeixinConstants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author yuwang881@gmail.com
 */
public class TokenService {
    public static String getAccessToken(String tenant) throws Exception {
        HttpsURLHelper helper = new HttpsURLHelper(true);
        String appid= WeixinConstants.multiTenants.get(tenant).get("APPID");
        String secret = WeixinConstants.multiTenants.get(tenant).get("APPSECRET");
        String getTokenURL =  String.format(WeixinConstants.Access_TokenURL,appid,secret);
        String resp =helper.get(getTokenURL);
        helper.close();
        JSONObject json = new JSONObject(resp);
        String token=null;
        try {
            token = json.getString("access_token");
        } catch (JSONException JE) {
            System.out.println("cannot get access token, error: "+ resp);
        }
        return token;
    }
    
    
}
