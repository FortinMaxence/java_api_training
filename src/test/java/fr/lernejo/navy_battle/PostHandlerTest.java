package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostHandlerTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void sendPOSTResponse_send202() throws IOException, InterruptedException {
        HttpServer server = new Server().launch(9876, this.game);
        server.start();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:9876\", \"message\":\"POST REQUEST\"}"))
            .build();

        Assertions.assertThat(this.client.send(postRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("Response Post request /api/game/start 202")
            .isEqualTo(202);
        server.stop(1);
    }

    @Test
    void sendPOSTResponse_send400() throws IOException, InterruptedException {
        HttpServer server = new Server().launch(9876, this.game);
        server.start();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{}"))
            .build();

        Assertions.assertThat(this.client.send(postRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("Response Post request /api/game/start 400")
            .isEqualTo(400);
        server.stop(1);
    }
}
