package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;

public class PostRequestTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);
    private final Server server = new Server(9876, this.game);
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void sendPostRequest() throws IOException, InterruptedException {
        server.start();
        PostRequest postRequest = new PostRequest(9876);
        String response = postRequest.sendPostRequest("http://localhost:9876");
        Assertions.assertThat(response)
            .as("Post Request return response")
            .isEqualTo("{\"id\":\"0\", \"url\":\"http://localhost:9876\", \"message\":\"Response from Server\"}");
        server.stop();

    }
}
