package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

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
    void applyResponse(){
        String consequence = "miss";
        boolean shipLeft = true;
        JSONObject JSONresponse = new JSONObject("{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}");
        Assertions.assertThat(JSONresponse.toString())
            .as("Complete JSON response Fire")
            .isEqualTo("{\"consequence\":\"" + consequence + "\",\"shipLeft\":" + shipLeft + "}");

        String consequenceResponse = JSONresponse.getString("consequence");
        boolean shipLeftResponse = JSONresponse.getBoolean("shipLeft");
        Assertions.assertThat(consequenceResponse)
            .as("Consequence JSON response Fire")
            .isEqualTo("miss");

        Assertions.assertThat(shipLeftResponse)
            .as("shipLeft JSON response Fire")
            .isEqualTo(true);
    }

    @Test
    void handle(){

    }
}
