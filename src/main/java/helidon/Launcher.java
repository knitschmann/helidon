package helidon;

import helidon.api.UserService;
import io.helidon.webserver.Http;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.json.JsonSupport;

import java.util.logging.Logger;

public class Launcher {

    private static final Logger logger = Logger.getLogger(Launcher.class.getName());


    public static void main(String[] args) {
        WebServer
                .create(helloWorldRouting())
                .start()
                .thenAccept(ws ->
                        logger.info("Service running at: http://localhost:" + ws.port()));
    }

    private static Routing userRouting() {
        return Routing.builder()
                // Add JSON support to all end-points
                .register(JsonSupport.get())
                .register("/api", new UserService())
                // Global exception handler
                .error(Exception.class, (req, res, ex) -> {
                    res.status(Http.Status.BAD_REQUEST_400).send();
                })
                .build();
    }

    private static Routing helloWorldRouting() {
        return Routing.builder()
                .get("/hello", (req, res) -> res.send("Hello World"))
                .build();
    }

}
