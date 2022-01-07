package wood.mike.functional;

public class StringIteration {

    public static void main(String[] args) {
        final String str = "w00t";

        // following prints numeric representation of character, not the character itself
        str.chars()
                .forEach(ch -> System.out.println(ch));
        //119
        //48
        //48
        //116

        // using helper method and method reference we can get the character
        str.chars()
                .forEach(StringIteration::printChar);
        //w
        //0
        //0
        //t

        // no need for helper method when we can do work when processing stream
        str.chars()
                .mapToObj(ch -> (char) ch)
                .forEach(System.out::println);

        // we can do other stuff like only get digits
        str.chars()
                .filter(Character::isDigit)
                .forEach(StringIteration::printChar);
        //0
        //0
    }

    /**
     * Print character associated with integer value
     */
    private static void printChar(int aChar) {
        System.out.println((char)(aChar));
    }
}
