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

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author yuwang881@gmail.com
 */
public interface StateHandler {
    //from tenant, get openid, comparing with db, forward to a return URL
    //maybe do not need openid, just return a URL to forward or redirect
    public String stateProcess(HttpServletRequest request, String tenant,String code,String configUrl);
}
