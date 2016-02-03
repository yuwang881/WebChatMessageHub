
package isve.webchat.conf.weixin;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the isve.webchat.conf.weixin package. 
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

    private final static QName _Tenants_QNAME = new QName("", "tenants");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: isve.webchat.conf.weixin
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TenantsListType }
     * 
     */
    public TenantsListType createTenantsListType() {
        return new TenantsListType();
    }

    /**
     * Create an instance of {@link TenantType }
     * 
     */
    public TenantType createTenantType() {
        return new TenantType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenantsListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tenants")
    public JAXBElement<TenantsListType> createTenants(TenantsListType value) {
        return new JAXBElement<TenantsListType>(_Tenants_QNAME, TenantsListType.class, null, value);
    }

}
