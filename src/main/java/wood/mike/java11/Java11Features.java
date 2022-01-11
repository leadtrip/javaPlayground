package wood.mike.java11;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Features added with Java 11
 */
public class Java11Features {

    public static void main(String[] args) throws Exception{
        httpClient();
        nestMates();
        localVariableSyntaxForLambdaParameters();
        unicode10();
        flightRecorder();
        tls13();
        stringMethods();
        fileMethods();
        notPredicate();
        runFileWithSingleCommand();
    }

    /**
     * No need to compile
     */
    private static void runFileWithSingleCommand() {
        // you can run code defined in a single source file in one step, no need to compile e.g.
        // java MySingleSourceFile.java
    }

    /**
     * static not method added to Predicate
     */
    private static void notPredicate() {
        Stream.of("mike", "bike", "trike", "spike", "cake")
                .filter(Predicate.not(s -> s.endsWith("ike")))
                .forEach(s -> System.out.printf("We like to eat %s", s));
    }

    /**
     * static readString and writeString on Files class
     */
    private static void fileMethods() throws Exception{
        var tempDir = Path.of (System.getProperty("java.io.tmpdir"));
        Path filePath = Files.writeString(Files.createTempFile(tempDir, "space", ".txt"), "The universe is massive");
        System.out.println( Files.readString(filePath) );
    }

    /**
     * repeat
     * strip
     * isBlank
     * lines
     */
    private static void stringMethods() {
        System.out.println( "Baa ".repeat(2) + "blacksheep" );

        System.out.println( "  \t    stripped\n\n       ".strip() );

        System.out.println( " \n\t  ".isBlank() );

        "Another\nday\rin\r\nparadise".lines()
                .forEach(System.out::println);
    }

    /**
     * Support added for TLS 1.3 but not complete
     */
    private static void tls13() throws Exception{
        SSLSocketFactory factory =
                (SSLSocketFactory) SSLSocketFactory.getDefault();
        var socket = (SSLSocket) factory.createSocket("google.com", 443);
        socket.setEnabledProtocols(new String[]{"TLSv1.3"});
        socket.setEnabledCipherSuites(new String[]{"TLS_AES_128_GCM_SHA256"});
    }

    private static void flightRecorder() {
        // is no longer commercial & JMC has been removed but is available as separate app
    }

    /**
     * Support added for unicode version 10
     */
    private static void unicode10() {
        System.out.println( Character.toChars( 0x1F92A ));      // an emoji
    }

    /**
     * Allow var to be used when declaring the formal parameters of implicitly typed lambda expressions
     *
     */
    private static void localVariableSyntaxForLambdaParameters() {
        Stream.of(1,2,3,4,5,6)
                .map((@Nonnull var i) -> i * 2);    // allowing var for implicitly typed parameter means we can apply annotations
    }

    // private member variable and method
    private final String outerVariable = "outerVariable";
    private void outerMethod() {
        System.out.println("outerMethod");
    }

    /**
     * nest based access control means a nested member can access the enclosing class's
     * private members without compiler having to create bridging method
     */
    public class Inner {
        private void accessOuter() {
            System.out.println(outerVariable);  // access to outer private member variable
            outerMethod();                      // access to outer private method
        }
    }

    /**
     * isNestMateOf and getNestHost methods on Class
     */
    private static void nestMates() {
        System.out.printf("nestmates? %b", Java11Features.Inner.class.isNestmateOf(Java11Features.class));
        System.out.println("");
        System.out.printf("host of Inner is %s", Inner.class.getNestHost());
        System.out.println("");
    }

    /**
     * HttpClient is now standard feature in 11 after being introduced in 9
     */
    private static void httpClient() throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://www.bbc.co.uk"))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
