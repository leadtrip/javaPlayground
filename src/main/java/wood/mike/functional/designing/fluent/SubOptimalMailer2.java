package wood.mike.functional.designing.fluent;


public class SubOptimalMailer2 {
    public SubOptimalMailer2 from(final String address) { /*... */; return this; }
    public SubOptimalMailer2 to(final String address)   { /*... */; return this; }
    public SubOptimalMailer2 subject(final String line) { /*... */; return this; }
    public SubOptimalMailer2 body(final String message) { /*... */; return this; }
    public void send() { System.out.println("sending..."); }

    /**
     * An improvement on SubOptimalMailer1 using method chaining, all the non-terminal methods return the instance
     * Still not great though as we're using new & doesn't stop someone assigning to a variable and
     * attempting to reuse
     * Also need to ensure some methods are only called once e.g. from
     */
    public static void main(final String[] args) {
        new SubOptimalMailer2()
                .from("build@agiledeveloper.com")
                .to("venkats@agiledeveloper.com")
                .subject("build notification")
                .body("...it sucks less...")
                .send();
    }
}
