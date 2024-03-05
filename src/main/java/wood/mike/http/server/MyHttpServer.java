package wood.mike.http.server;

import com.sun.net.httpserver.*;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class MyHttpServer{

    private final InetSocketAddress address = new InetSocketAddress(8080);
    private final Path path = Path.of("/");

    public static void main(String[] args) {
        MyHttpServer webServer = new MyHttpServer();
        HttpServer server = webServer.createSlowServer();
        server.start();
    }

    private HttpServer createSlowServer() {
        HttpHandler hangingHandler = exchange -> {
            try {
                System.out.printf("%s Received request%n", now());
                Thread.sleep(6000);
                System.out.printf("%s Timed out%n", now());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
        server.createContext("/bulk", hangingHandler);
        return server;
    }

    private HttpServer createWith5xResponse() {
        String json = new JSONObject().put("message", "dead").toString();
        HttpHandler errHandler = HttpHandlers.of(500, Headers.of("Content-Type", "application/json"), json);

        HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
        server.createContext("/bulk", errHandler);
        return server;
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
