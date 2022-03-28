package wood.mike.versions.java16;

import java.util.Random;

public class Java16Features {
    public static void main(String[] args) {
        records();
    }

    /**
     * Records are immutable, implicitly final, cannot be abstract
     * Fields are also final
     * Cannot extend and superclass is Record
     * Cannot declare instance fields
     * hashCode, toString and equals are implicitly declared
     */
    record Person( String forename, String surname ) {
        // Constructor is implicitly declared based on header fields as are getters

        // explicit constructor must call implicit
        public Person( String aForename ) {
            this( aForename, "everyman" );
        }

        // explicit constructor must call implicit
        public Person( String aForename, String aSurname, boolean likesCheese ) {
            this( aForename, aSurname + (likesCheese ? " likes cheese": "" ) );
        }

        // instance methods are fine
        public String getFullName() {
            return forename + " " + surname;
        }

        // instance method creating nested record
        public String getSecretAgentId() {
            return new Secret( surname ).secretAgentId();
        }

        // nested record is implicitly static
        record Secret( String surname ) {
            public String secretAgentId() {
                return String.format( "00%s - %s", new Random().nextInt( 10, 20 ), surname );
            }
        }
    }

    /**
     * Testing the record above
     */
    private static void records() {
        var roger = new Person("Roger", "Bannister" );
        // p.forename = "Bob"; can't do this, immutable
        System.out.println( roger.toString() );
        System.out.println( roger.getSecretAgentId() );

        var dave = new Person( "Dave" );
        System.out.println( dave.getFullName() );

        System.out.println( new Person( "My", "mouse", true ) );
    }

}
