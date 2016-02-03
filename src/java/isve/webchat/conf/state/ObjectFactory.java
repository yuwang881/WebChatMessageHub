
package isve.webchat.conf.state;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the isve.webchat.conf.state package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Configurations_QNAME = new QName("", "configurations");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: isve.webchat.conf.state
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConfigurationsListType }
     * 
     */
    public ConfigurationsListType createConfigurationsListType() {
        return new ConfigurationsListType();
    }

    /**
     * Create an instance of {@link StateCodeType }
     * 
     */
    public StateCodeType createStateCodeType() {
        return new StateCodeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationsListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "configurations")
    public JAXBElement<ConfigurationsListType> createConfigurations(ConfigurationsListType value) {
        return new JAXBElement<ConfigurationsListType>(_Configurations_QNAME, ConfigurationsListType.class, null, value);
    }

}
