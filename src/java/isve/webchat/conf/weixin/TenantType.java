

package isve.webchat.conf.weixin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenantType", propOrder = {
    "tenantId",
    "des",
    "appId",
    "appSecret",
    "encryptionType",
    "token",
    "uid",
    "aesKey"
})
public class TenantType {

    @XmlElement(required = true)
    protected String tenantId;
    @XmlElement(required = true)
    protected String des;
    @XmlElement(required = true)
    protected String appId;
    @XmlElement(required = true)
    protected String appSecret;
    @XmlElement(required = true)
    protected String encryptionType;
    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String uid;
    @XmlElement(required = true)
    protected String aesKey;


    public String getTenantId() {
        return tenantId;
    }

  
    public void setTenantId(String value) {
        this.tenantId = value;
    }


    public String getDes() {
        return des;
    }


    public void setDes(String value) {
        this.des = value;
    }


    public String getAppId() {
        return appId;
    }

  
    public void setAppId(String value) {
        this.appId = value;
    }

  
    public String getAppSecret() {
        return appSecret;
    }

 
    public void setAppSecret(String value) {
        this.appSecret = value;
    }

 
    public String getEncryptionType() {
        return encryptionType;
    }

 
    public void setEncryptionType(String value) {
        this.encryptionType = value;
    }

  
    public String getToken() {
        return token;
    }

    
    public void setToken(String value) {
        this.token = value;
    }

 
    public String getUid() {
        return uid;
    }

 
    public void setUid(String value) {
        this.uid = value;
    }

 
    public String getAesKey() {
        return aesKey;
    }


    public void setAesKey(String value) {
        this.aesKey = value;
    }

}
