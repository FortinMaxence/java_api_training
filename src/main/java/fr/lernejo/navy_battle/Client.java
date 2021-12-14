package fr.lernejo.navy_battle;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private final int port;

    public Client(int port){
        this.port = port;
    }

    public void sendPostRequest(String adversaryURL){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"Request from Client\"}"))
            .build();

        client.sendAsync(postRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
    }
}
