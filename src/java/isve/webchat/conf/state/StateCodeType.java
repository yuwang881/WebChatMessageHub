
package isve.webchat.conf.state;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import isve.webchat.stateHandlers.StateHandler;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateCodeType", propOrder = {
    "statePattern",
    "tenant",
    "stateHandler",
    "action",
    "pageUri"
})
public class StateCodeType {

    @XmlElement(required = true)
    protected String statePattern;
    @XmlElement(required = true)
    protected String tenant;
    @XmlElement(required = true)
    protected String stateHandler;
    @XmlElement(required = true)
    protected String action;
    @XmlElement(required = true)
    protected String pageUri;
    
    @XmlTransient
    protected StateHandler handler;
    
    public void initHandler() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        handler = (StateHandler) Class.forName(this.getAction()).newInstance();
    }
    
    public StateHandler getHandler(){
        return handler;
    }


    public String getStatePattern() {
        return statePattern;
    }


    public void setStatePattern(String value) {
        this.statePattern = value;
    }

  
    public String getTenant() {
        return tenant;
    }

    public void setTenant(String value) {
        this.tenant = value;
    }

  
    public String getStateHandler() {
        return stateHandler;
    }

   
    public void setStateHandler(String value) {
        this.stateHandler = value;
    }

    
    public String getAction() {
        return action;
    }


    public void setAction(String value) {
        this.action = value;
    }

   
    public String getPageUri() {
        return pageUri;
    }


    public void setPageUri(String value) {
        this.pageUri = value;
    }

}
