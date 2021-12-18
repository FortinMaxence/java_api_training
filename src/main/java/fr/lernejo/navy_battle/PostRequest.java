package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostRequest {
    private final int port;

    public PostRequest(int port){
        this.port = port;
    }

    public String sendPostRequest(String adversaryURL) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"Request from Client\"}"))
            .build();

        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
