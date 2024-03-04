package wood.mike.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;
import org.json.JSONObject;


public class MyHttpServer{

        private final InetSocketAddress address = new InetSocketAddress(8080);
        private final Path path = Path.of("/");

        public static void main(String[] args) {
            MyHttpServer webServer = new MyHttpServer();
            HttpServer server = webServer.createSlowServer();
            server.start();
        }

        private HttpServer createWith5xResponse() {
            String json = new JSONObject().put("message", "dead").toString();
            HttpHandler errHandler = HttpHandlers.of(500, Headers.of("Content-Type", "application/json"), json);

            HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
            server.createContext("/bulk", errHandler);
            return server;
        }

        private HttpServer createSlowServer() {
            HttpHandler hangingHandler = exchange -> {
                try {
                    System.out.println("Received request");
                    Thread.sleep(6000);
                    System.out.println("Timed out");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
            server.createContext("/bulk", hangingHandler);
            return server;
        }

        private HttpServer createBasic() {
            return SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
        }

        private HttpServer createWithHandler() throws IOException {
            HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
            HttpHandler handler = SimpleFileServer.createFileHandler(Path.of("/Users"));
            server.createContext("/test", handler);
            return server;
        }

        private HttpServer createWithHandler401Response() {
            Predicate<Request> findAllowedPath = r -> r.getRequestURI()
                    .getPath()
                    .equals("/test/allowed");

            HttpHandler allowedResponse = HttpHandlers.of(200, Headers.of("Allow", "GET"), "Welcome");
            HttpHandler deniedResponse = HttpHandlers.of(401, Headers.of("Deny", "GET"), "Denied");

            HttpHandler handler = HttpHandlers.handleOrElse(findAllowedPath, allowedResponse, deniedResponse);

            HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
            server.createContext("/test", handler);
            return server;
        }

        private HttpServer createWithFilter() throws IOException {
            Filter filter = SimpleFileServer.createOutputFilter(System.out, SimpleFileServer.OutputLevel.INFO);
            HttpHandler handler = SimpleFileServer.createFileHandler(Path.of("/Users"));
            return HttpServer.create(new InetSocketAddress(8080), 10, "/test", handler, filter);
        }
}
