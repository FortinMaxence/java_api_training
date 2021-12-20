package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FireHandlerTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void sendFireRequest() throws IOException, InterruptedException {
        HttpServer server = new Server().launch(9876, this.game);
        server.start();
        this.player.initSeas();
        this.player.boats.add(new Boats(1, new int[]{0}, new int[]{0}));
        FireHandler fire = new FireHandler(this.game);
        String consequence = "miss";
        boolean shipLeft = true;
        Assertions.assertThat(fire.sendFireRequest("http://localhost:9876", "A1"))
            .as("Fire Request return response")
            .isEqualTo("{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}");
        server.stop(1);
    }

    @Test
    void sendFireResponse_send202() throws IOException, InterruptedException {
        HttpServer server = new Server().launch(9876, this.game);
        server.start();
        this.player.initSeas();
        this.player.boats.add(new Boats(2, new int[]{0,0}, new int[]{0,1}));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest fireRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1"))
            .build();

        Assertions.assertThat(client.send(fireRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("Response Fire request /api/game/fire 202")
            .isEqualTo(202);
        server.stop(1);
    }

    @Test
    void applyResponse_miss() throws IOException {
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
            .isEqualTo(consequence);

        Assertions.assertThat(shipLeftResponse)
            .as("shipLeft JSON response Fire")
            .isEqualTo(true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        FireHandler fire = new FireHandler(this.game);
        this.player.initSeas();
        fire.applyResponse(JSONresponse.toString(), "A1");

        String expected = JSONresponse.toString()+"   A B C D E F G H I J   A B C D E F G H I J1  0 0 0 0 0 0 0 0 0 0   x o o o o o o o o o" +
            "2  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o3  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "4  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o5  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "6  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o7  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "8  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o9  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "10 0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o";
        Assertions.assertThat(outContent.toString().replaceAll("\n", "").replaceAll("\r", "")).as("applyResponse miss")
            .isEqualTo(expected);
    }

    @Test
    void applyResponse_hit() throws IOException {
        String consequence = "hit";
        boolean shipLeft = true;
        JSONObject JSONresponse = new JSONObject("{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}");
        Assertions.assertThat(JSONresponse.toString())
            .as("Complete JSON response Fire")
            .isEqualTo("{\"consequence\":\"" + consequence + "\",\"shipLeft\":" + shipLeft + "}");

        String consequenceResponse = JSONresponse.getString("consequence");
        boolean shipLeftResponse = JSONresponse.getBoolean("shipLeft");
        Assertions.assertThat(consequenceResponse)
            .as("Consequence JSON response Fire")
            .isEqualTo(consequence);

        Assertions.assertThat(shipLeftResponse)
            .as("shipLeft JSON response Fire")
            .isEqualTo(true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        FireHandler fire = new FireHandler(this.game);
        this.player.initSeas();
        fire.applyResponse(JSONresponse.toString(), "A1");

        String expected =JSONresponse.toString()+"   A B C D E F G H I J   A B C D E F G H I J1  0 0 0 0 0 0 0 0 0 0   H o o o o o o o o o" +
            "2  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o3  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "4  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o5  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "6  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o7  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "8  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o9  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "10 0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o";
        Assertions.assertThat(outContent.toString().replaceAll("\n", "").replaceAll("\r", "")).as("applyResponse hit")
            .isEqualTo(expected);
    }

    @Test
    void applyResponse_sunk() throws IOException {
        String consequence = "sunk";
        boolean shipLeft = true;
        JSONObject JSONresponse = new JSONObject("{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}");
        Assertions.assertThat(JSONresponse.toString())
            .as("Complete JSON response Fire")
            .isEqualTo("{\"consequence\":\"" + consequence + "\",\"shipLeft\":" + shipLeft + "}");

        String consequenceResponse = JSONresponse.getString("consequence");
        boolean shipLeftResponse = JSONresponse.getBoolean("shipLeft");
        Assertions.assertThat(consequenceResponse)
            .as("Consequence JSON response Fire")
            .isEqualTo(consequence);

        Assertions.assertThat(shipLeftResponse)
            .as("shipLeft JSON response Fire")
            .isEqualTo(true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        FireHandler fire = new FireHandler(this.game);
        this.player.initSeas();
        fire.applyResponse(JSONresponse.toString(), "A1");

        String expected = JSONresponse.toString()+"   A B C D E F G H I J   A B C D E F G H I J1  0 0 0 0 0 0 0 0 0 0   H o o o o o o o o o" +
            "2  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o3  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "4  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o5  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "6  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o7  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "8  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o9  0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o" +
            "10 0 0 0 0 0 0 0 0 0 0   o o o o o o o o o o";
        Assertions.assertThat(outContent.toString().replaceAll("\n", "").replaceAll("\r", "")).as("applyResponse sunk")
            .isEqualTo(expected);
    }

    @Test
    void handle(){

    }
}
