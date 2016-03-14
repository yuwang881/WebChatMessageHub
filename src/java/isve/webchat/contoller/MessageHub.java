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
package isve.webchat.contoller;

import isve.webchat.util.aes.AesException;
import isve.webchat.util.comm.MessageUtil;
import isve.webchat.util.constants.WeixinConstants;
import isve.webchat.util.services.MessageCrypt;
import isve.webchat.util.services.MessageHandler;
import isve.webchat.util.services.SignService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yuwang881@gmail.com
 */
@WebServlet(name = "MessageHub", urlPatterns = {"/MessageHub/*"})
public class MessageHub extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String echostr = request.getParameter("echostr");
        
         // get tenantid from request PathInfo
        String tenant = getTenant(request);
      
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (checkSignature(request,tenant)) {
                log("Siganature is valid!");
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
  

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {  }
        response.setCharacterEncoding("UTF-8");
        
//        String uri = request.getScheme() + "://" +
//             request.getServerName() + 
//             ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
//             request.getRequestURI() +
//            (request.getQueryString() != null ? "?" + request.getQueryString() : "");
//        
//        log("The full URL is: "+uri);
        
        // get tenantid from request PathInfo
        String tenant = getTenant(request);
        
        //判断租户是否启用了加密消息。主要是看httprequest中，encrypt_type是否为aes
        //如果是，则读出url中的msg_signature
        boolean isCrypted = false;
        String encrypt_type = request.getParameter("encrypt_type");
        String msg_signature = null;
        if (encrypt_type != null) {
            if (encrypt_type.equals("aes")) {
                isCrypted = true;
                msg_signature = request.getParameter("msg_signature");
            }
        }

        
         // get POST raw data from httprequest
        StringBuffer message = new StringBuffer();
        String XmlMessage = null;
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                message.append(line);
            }
        } catch (Exception e) {
            log("Read Post data error: "+ e.getMessage());
        } 
        //log("get message from the weinxin server:   "+message.toString());
        
        if (isCrypted) {  //是加密的消息
            Map<String, String> parsedMessage = MessageUtil.parseXML(message.toString().getBytes("UTF-8"));
            String encryptedString = parsedMessage.get("Encrypt");
            //log("encryptedString: "+encryptedString);
            if (!checkCryptedSignature(request, tenant,encryptedString)) {
                log("signature validation failed!");
                return;
            }      
            //log("crypted message signature validation success!!");
            try {
                XmlMessage = decrypt(tenant,encryptedString);
            } catch (AesException ex) {
                log("Decrypt message error! ");
                log(ex.getMessage());
                return;
            }
        } else //不是加密的消息
        {
            if (!checkSignature(request, tenant)) {
                log("signature validation failed!");
                return;
            }
            XmlMessage = message.toString();
        }

        
        Map<String, String> parsedMessage = MessageUtil.parseXML(XmlMessage.getBytes("UTF-8"));
        String responseMessage = MessageHandler.handleMessage(parsedMessage,tenant);
     
        if(isCrypted){
            try {
                responseMessage = encrypt(tenant, responseMessage);
            } catch (AesException ex) {
                log("AES encrypt exception!");
            } catch (UnsupportedEncodingException ex) {
                log("UnsupportedEncodingException !");
            }
        }
        
        OutputStream outs =null;
        //PrintWriter out = null;
        try {
//            out = response.getWriter();
//            out.print(responseMessage);
//            out.flush();
            
            outs = response.getOutputStream();
            outs.write(responseMessage.getBytes("UTF-8"));
            outs.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "weixin message hub";
    }
    
    private String getTenant(HttpServletRequest request) {
        //存在不需要tenant的请求情况
        String tenant = null;
        String pathInfo = request.getPathInfo();
     
        if (pathInfo != null){
            char a = '/';
            pathInfo = pathInfo.substring(1);
            int index1 = pathInfo.indexOf(a);
            if (index1 <0){
                char b ='?';
                index1 = pathInfo.indexOf(b);
                if (index1<0) index1 = pathInfo.length();
            }
            tenant=pathInfo.substring(0, index1);
        }
  
        return tenant;
        
   }
    
    private boolean checkSignature(HttpServletRequest request, String tenant) {
        String msg_signature = request.getParameter("msg_signature");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        if (msg_signature != null )   signature = msg_signature;
        return SignService.checkSignature(tenant, signature, timestamp, nonce);
      
    }
    
    private boolean checkCryptedSignature(HttpServletRequest request, String tenant, String encryptedString ) {
         String msg_signature =  request.getParameter("msg_signature");
         String timestamp = request.getParameter("timestamp");
         String nonce = request.getParameter("nonce");
         return SignService.checkSignature(tenant, msg_signature, timestamp, nonce, encryptedString);
        
    }

    private String decrypt(String tenant, String encryptedString) throws AesException {
        String appId = WeixinConstants.multiTenants.get(tenant).get("APPID");
        String aesKey = WeixinConstants.multiTenants.get(tenant).get("AESKEY");
        //log("APPID:  "+appId+"  AESKEY: "+aesKey);
        return MessageCrypt.decrypt(appId, aesKey, encryptedString);
    }
    
    private String encrypt(String tenant, String responseMessage) throws AesException, UnsupportedEncodingException{
        String appId = WeixinConstants.multiTenants.get(tenant).get("APPID");
        String token = WeixinConstants.multiTenants.get(tenant).get("TOKEN");
        String aesKey = WeixinConstants.multiTenants.get(tenant).get("AESKEY");
        String timestamp = MessageCrypt.getTimestamp();
        String nonce = MessageCrypt.genNonce(10);
        String encrypt = MessageCrypt.encrypt(appId, aesKey, responseMessage);
        String[] arr = new String[] {token,timestamp,nonce,encrypt};
        String signature = SignService.SHA1(arr);	
        String result = MessageHandler.generateCryptedResponse(encrypt, signature.toLowerCase(), timestamp, nonce);
        return result;
    }

}






