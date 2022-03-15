package wood.mike.misc;

import lombok.Data;
import lombok.ToString;

/**
 * Don't provide "setter" methods — methods that modify fields or objects referred to by fields.
 * Make all fields final and private.
 * Don't allow subclasses to override methods. The simplest way to do this is to declare the class as final.
   A more sophisticated approach is to make the constructor private and construct instances in factory methods.
 * If the instance fields include references to mutable objects, don't allow those objects to be changed:
        * Don't provide methods that modify the mutable objects.
        * Don't share references to the mutable objects. Never store references to external, mutable objects passed to the constructor;
          if necessary, create copies, and store references to the copies. Similarly, create copies of your internal mutable objects when
          necessary to avoid returning the originals in your methods.
 */
public class Immutability {

    public static void main(String[] args) {
        Clud clud = new Clud( 1, 2 );

        Borg borg = new Borg( 100, "yallex", clud );
        borg.getClud().setCludInt( 2213 );  // this has no effect on the Borg's instance of Clud as copy is returned by getClud()

        System.out.println( borg );
        assert borg.getClud().getCludInt() == 1;
    }

}

/**
 * Borg is immutable
 */
@ToString
final class Borg{
    private final Integer borgInteger;
    private final String borgString;
    private final Clud clud;

    public Borg( final Integer borgInteger, final String borgString, final Clud clud) {
        this.borgInteger = borgInteger;
        this.borgString = borgString;
        this.clud = new Clud(clud);     // assign copy of non-immutable class
    }

    public Integer getBorgInteger() {
        return borgInteger;
    }

    public String getBorgString() {
        return borgString;
    }

    public Clud getClud() {
        return new Clud( clud );        // return copy using non-immutable class copy constructor
    }
}

/**
 * Clud is not immutable, it offers setters & is not final
 */
@Data
class Clud{
    private int cludInt;
    private long cludLong;

    public Clud(int cludInt, long cludLong) {
        this.cludInt = cludInt;
        this.cludLong = cludLong;
    }

    /**
     * Copy constructor
     */
    public Clud( final Clud clud ) {
        this( clud.getCludInt(), clud.getCludLong() );
    }
}

