package wood.mike.xml.xsd;

import java.util.List;
import java.util.Optional;

public class XsdDriver {

    private XmlValidator xmlValidator;
    private XmlMarshallingService userService;

    private final List<String> userFiles = List.of("xml/good_user.xml", "xml/bad_user.xml");

    static void main(String[] args) {
        new XsdDriver().run();
    }

    private void run() {
        xmlValidator = new XmlValidator();
        userService = new XmlMarshallingService();
        userFiles.forEach(f -> {
            boolean isValid = xmlValidator.validateXMLSchema("xml/schema.xsd", f);
            System.out.printf("Is XML valid for %s? %s%n", f, isValid);
            Optional<User> user = userService.unmarshall(f, User.class);
            user.ifPresentOrElse(System.out::println, () -> System.out.println("User not available"));
        });
    }


}
