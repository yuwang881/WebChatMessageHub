package isve.webchat.util.services;

import isve.webchat.util.comm.HttpsURLHelper;
import isve.webchat.util.constants.WeixinConstants;



/**
 * 菜单创建
 * 
 * @author caspar.chen
 * @version 1.1
 * 
 */
public class MenuService {
    static public String createMenu(String menuContent, String tenant) throws Exception {
         
        //get Access_Token
        String access_token = TokenService.getAccessToken(tenant);
        String result = null;
        
        if (access_token != null) {
            //get https helper
            HttpsURLHelper helper = new HttpsURLHelper(true);
            helper.setEncoding("GBK");
            result = helper.Post(WeixinConstants.MenuCreateURL+access_token, menuContent);
            helper.close();
        } 
        return result;
    }
    
    static public String createConditionMenu(String menuContent, String tenant) throws Exception {
         
        //get Access_Token
        String access_token = TokenService.getAccessToken(tenant);
        String result = null;
        
        if (access_token != null) {
            //get https helper
            HttpsURLHelper helper = new HttpsURLHelper(true);
            helper.setEncoding("GBK");
            result = helper.Post(WeixinConstants.MenuConditionCreateURL+access_token, menuContent);
            helper.close();
        } 
        return result;
    }
    
    static public String deleteMenu(String tenant) throws Exception {
         
        //get Access_Token
        String access_token = TokenService.getAccessToken(tenant);
        String result = null;
        
        if (access_token != null) {
            //get https helper
            HttpsURLHelper helper = new HttpsURLHelper(true);
            helper.setEncoding("GBK");
            result = helper.get(WeixinConstants.MenuDeleteURL+access_token);
            helper.close();
        } 
        return result;
    }

    static public String getMenu(String tenant) throws Exception {
        //get Access_Token
        String access_token = TokenService.getAccessToken(tenant);
        String result = null;
        
        if (access_token != null) {
            //get https helper
            HttpsURLHelper helper = new HttpsURLHelper(true);
            helper.setEncoding("GBK");
            result = helper.get(WeixinConstants.MenuGetURL+access_token);
            helper.close();
        } 
        return result;
        
        
    }
}
