package wood.mike.records;

import java.util.UUID;

public class JavaRecords {
    public static void main(String[] args) {
        new JavaRecords().runAll();
    }

    private void runAll() {
        implementInterfaceNaming();
    }

    private void implementInterfaceNaming() {
        new DefinitionDto(UUID.randomUUID(), "Bob", "Active");
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
