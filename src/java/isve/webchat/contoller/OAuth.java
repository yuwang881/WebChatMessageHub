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

import isve.webchat.conf.state.ConfigurationsListType;
import isve.webchat.conf.state.StateCodeType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author yuwang881@gmail.com
 */
public class OAuth extends HttpServlet {
    Map<String,StateCodeType> statecodes = new HashMap(); 
    

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
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        
        if (code == null) {
            log("Cannot get CODE from weixin server!");
            return;
        }
        
               
        // get tenant, StateHandler by state mapping
        StateCodeType mystatecode = statecodes.get(state);
        if (mystatecode == null) {
            log("cannot got state handler from the config file!");
            return;
        }
        String returnURL =  mystatecode.getHandler().stateProcess(request, mystatecode.getTenant(), code, mystatecode.getPageUri());
               
        if (mystatecode.getAction().equals("forward"))  
            getServletContext().getRequestDispatcher(returnURL).forward(request, response);
        else 
            response.sendRedirect(returnURL);
    }

    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "OAuth servlet for Webchat";
    }
    
    @Override
    public void init()  {
        try {
            JAXBContext jc = JAXBContext.newInstance("isve.webchat.conf.state");
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement e = (JAXBElement) u.unmarshal(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("conf/state.xml"));
            ConfigurationsListType stateConfig = (ConfigurationsListType) e.getValue();
            for (StateCodeType statecode : stateConfig.getStateCode()){
                statecode.initHandler();
                statecodes.put(statecode.getStatePattern(), statecode);
            }
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JAXBException  ex) {
            log(ex.getMessage());
        }
    }

}
