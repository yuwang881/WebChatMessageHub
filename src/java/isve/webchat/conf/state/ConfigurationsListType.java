

package isve.webchat.conf.state;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurationsListType", propOrder = {
    "stateCode"
})
public class ConfigurationsListType {

    @XmlElement(required = true)
    protected List<StateCodeType> stateCode;

    /**
     * Gets the value of the stateCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StateCodeType }
     * 
     * 
     */
    public List<StateCodeType> getStateCode() {
        if (stateCode == null) {
            stateCode = new ArrayList<StateCodeType>();
        }
        return this.stateCode;
    }

}
