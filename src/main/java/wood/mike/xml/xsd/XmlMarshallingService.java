package wood.mike.xml.xsd;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.Optional;

public class XmlMarshallingService {

    public <T> Optional<T> unmarshall(String xmlFile, Class<T> c) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(xmlFile)) {

            if (is == null) {
                System.err.println("File not found: " + xmlFile);
                return Optional.empty();
            }

            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            @SuppressWarnings("unchecked")
            T result = (T) unmarshaller.unmarshal(is);

            return Optional.ofNullable(result);

        } catch (Exception e) {
            System.err.println("Failed to unmarshall " + xmlFile + ": " + e.getMessage());
            return Optional.empty();
        }
    }

}
