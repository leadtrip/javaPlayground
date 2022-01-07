package wood.mike.functional.designing.fluent;

public class SubOptimalMailer1 {
    public void from(final String address) { /*... */ }
    public void to(final String address)   { /*... */ }
    public void subject(final String line) { /*... */ }
    public void body(final String message) { /*... */ }
    public void send() { System.out.println("sending..."); }

    /**
     * Noisy code
     * We've created an instance of the mailer, what do we do with it when done if anything? can it be reused?
     * mailer is repeated multiple times
     */
    public static void main(final String[] args) {
        SubOptimalMailer1 mailer = new SubOptimalMailer1();
        mailer.from("build@agiledeveloper.com");
        mailer.to("venkats@agiledeveloper.com");
        mailer.subject("build notification");
        mailer.body("...your code sucks...");
        mailer.send();
    }
}
