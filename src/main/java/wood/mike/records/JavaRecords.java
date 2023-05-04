package wood.mike.records;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

public class JavaRecords {
    public static void main(String[] args) {
        new JavaRecords().runAll();
    }

    private void runAll() {
        implementInterfaceNaming();
        createRecords();
    }

    private void implementInterfaceNaming() {
        new DefinitionDto(UUID.randomUUID(), "Bob", "Active");
    }

    private void createRecords() {
        new Human("Jon", LocalDate.now());

        new Pet("Alf", LocalDate.now());
        new Pet("Bernie");
        System.out.println(Pet.getUnknownDobPetCount());

        Item.Price p = new Item.Price(0, 100);
        Item.Description d = new Item().new Description("ABC");
    }

    /**
     * Class with inner record and class
     */
    public class Item {
        record Price(int min, int max) {}
        class Description { Description(String d) {}}
    }

    record Human(String name, LocalDate dob) {}
    record Pet(String name, LocalDate dob) {
        static int unknownDobPetCount;    // can have static fields
        public Pet(String name) {
            this(name, LocalDate.of(2020, Month.JANUARY, 1));
            unknownDobPetCount++;
        }

        public static int getUnknownDobPetCount() {
            return unknownDobPetCount;
        }
    }

    /**
     * To allow a Record to implement an interface and use sensible variable names we have to use the same name for the
     * method name in the interface i.e. no get prefix
     */
    interface Definition {
        UUID id();

        String name();

        /**
         * Here we're using getStatus rather than just status unlike the other 2 methods above
         */
        String getStatus();
    }

    record DefinitionDto( UUID id, String name, String status ) implements Definition {
        /**
         * Because we've named the method getStatus in the interface we have to add a method of the same name here, or
         * name the status varialbe getStatus which seems daft
         */
        @Override
        public String getStatus() {
            return status;
        }
    }
}
