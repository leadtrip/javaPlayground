package wood.mike.xml.xsd;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.net.URL;

public class XmlValidator {

    public boolean validateXMLSchema(String xsdResourceName, String xmlResourceName) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            URL xsdUrl = XmlValidator.class.getClassLoader().getResource(xsdResourceName);
            URL xmlUrl = XmlValidator.class.getClassLoader().getResource(xmlResourceName);

            if (xsdUrl == null || xmlUrl == null) {
                System.out.println("Could not find files on classpath!");
                return false;
            }

            Schema schema = factory.newSchema(xsdUrl);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlUrl.openStream()));

            return true;
        } catch (Exception e) {
            System.out.println(STR."Validation Error: \{e.getMessage()}");
            return false;
        }
    }
}