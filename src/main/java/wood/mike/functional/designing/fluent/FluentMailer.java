package wood.mike.functional.designing.fluent;

import java.util.function.Consumer;

public class FluentMailer {
    private FluentMailer() {}

    public FluentMailer from(final String address) { /*... */; return this; }
    public FluentMailer to(final String address)   { /*... */; return this; }
    public FluentMailer subject(final String line) { /*... */; return this; }
    public FluentMailer body(final String message) { /*... */; return this; }

    public static void send(final Consumer<FluentMailer> block) {
        final FluentMailer mailer = new FluentMailer();
        block.accept(mailer);
        System.out.println("sending...");
    }

    /**
     * All non-terminal methods return instance to allow chaining
     * Constructor is private disallowing instance creation
     * We also made the terminal method, send() a static method and it expects a Consumer as a parameter.
     * Rather than creating an instance, users will now invoke send() and pass a block of code.
     * The send() method will create an instance, yield it to the block, and, upon return, complete any
     * required validations and perform its final send operations.
     */
    public static void main(final String[] args) {
        FluentMailer.send(mailer ->
                mailer.from("build@agiledeveloper.com")
                        .to("venkats@agiledeveloper.com")
                        .subject("build notification")
                        .body("...much better..."));
    }
}