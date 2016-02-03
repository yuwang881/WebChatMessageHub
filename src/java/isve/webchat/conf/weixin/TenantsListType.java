
package isve.webchat.conf.weixin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenantsListType", propOrder = {
    "tenant"
})
public class TenantsListType {

    @XmlElement(required = true)
    protected List<TenantType> tenant;


    public List<TenantType> getTenant() {
        if (tenant == null) {
            tenant = new ArrayList<TenantType>();
        }
        return this.tenant;
    }

}
