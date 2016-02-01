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

import isve.webchat.util.comm.HttpsURLHelper;
import isve.webchat.util.constants.WeixinConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author yuwang881@gmail.com
 */
@WebServlet(name = "CreateMenu", urlPatterns = {"/CreateMenu"})
public class CreateMenu extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String menuString = getMenuJson();
            String token = getAccessToken(WeixinConstants.Access_TokenURL);
            if (token !=null) {
                String result = createMenu(menuString,token);
                out.println(result);
            }
            
            out.flush();
        }
    }

    private String getAccessToken(String tokenurl) throws Exception {
        HttpsURLHelper helper = new HttpsURLHelper(true);
        String resp =helper.get(tokenurl);
        helper.close();
        
        JSONObject json = new JSONObject(resp);
        String token=null;
        try {
            token = json.getString("access_token");
        } catch (JSONException JE) {
            log("cannot get access token, error: "+ resp);
        }
        return token;
    }
    
    private String createMenu(String menu, String token) throws Exception {
        HttpsURLHelper helper = new HttpsURLHelper(true);
        helper.setEncoding("GBK");
        String result = helper.Post(WeixinConstants.MenuCreateURL+token, menu);
        helper.close();
        return result;
    }
    
    private String getMenuJson() {
        StringBuffer menuJson = new StringBuffer();
        InputStreamReader isr = null;
        BufferedReader reader = null;
        String filename = "/WEB-INF/menu.json";
        try {
            InputStream is = getServletContext().getResourceAsStream(filename);
            if (is != null) {
                isr = new InputStreamReader(is);
                reader = new BufferedReader(isr);
                String text = "";
                while ((text = reader.readLine()) != null) {
                    menuJson.append(text);
                }
                reader.close();
            } else {
                return "nofile";
            }
          

        } catch (IOException e) {
            log("IOException: "+e.getMessage());
        } 
        return menuJson.toString();
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CreateMenu.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CreateMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
