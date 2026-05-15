package wood.mike.xml.xsd;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.ToString;

@ToString
@XmlRootElement(name = "User")
public class User {
    private int id;
    private String firstName;
    private int age;

    @XmlAttribute
    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    @XmlElement(name = "FirstName")
    public void setFirstName(String name) { this.firstName = name; }
    public String getFirstName() { return firstName; }

    @XmlElement(name = "Age")
    public void setAge(int age) { this.age = age; }
    public int getAge() { return age; }
}
