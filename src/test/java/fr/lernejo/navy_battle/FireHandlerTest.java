package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FireHandlerTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);
    private final Server server = new Server(9876, this.game);
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void sendFireRequest() throws IOException, InterruptedException {
        server.start();
        this.player.initSeas();
        this.player.boats.add(new Boats(1, new int[]{0}, new int[]{0}));
        FireHandler fire = new FireHandler(this.game);
        String consequence = "miss";
        boolean shipLeft = true;
        Assertions.assertThat(fire.sendFireRequest("http://localhost:9876", "A1"))
            .as("Fire Request return response")
            .isEqualTo("{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}");
        server.stop();
    }

    @Test
    void sendFireResponse_send202() throws IOException, InterruptedException {
        server.start();
        this.player.initSeas();
        this.player.boats.add(new Boats(2, new int[]{0,0}, new int[]{0,1}));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest fireRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1"))
            .build();

        Assertions.assertThat(client.send(fireRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("Response Post request /api/game/start 202")
            .isEqualTo(202);
        server.stop();
    }

    @Test
    void getParams(){

    }

    @Test
    void applyResponse(){

    }

    @Test
    void handle(){

    }
}
