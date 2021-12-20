package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.Scanner;

public class FireHandler implements HttpHandler {
    private final Game game;
    public FireHandler(Game game){
        this.game = game;
    }
    public final String schema = "{\"$schema\":\"http://json-schema.org/schema#\",\"type\":\"object\"," +
        "\"properties\":{\"consequence\":{\"type\":\"string\",\"enum\":[\"miss\",\"hit\",\"sunk\"]}," +
        "\"shipLeft\":{\"type\":\"boolean\"}},\"required\":[\"consequence\",\"shipLeft\"]}";

    public String sendFireRequest(String adversaryURL, String cell) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest fireRequest = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/fire?cell=" + cell))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .build();

        HttpResponse<String> response = client.send(fireRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void sendFireResponse(HttpExchange exchange, String consequence, boolean shipLeft) throws IOException {
        String response = "{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}";
        SchemaValidator schemaValidator = new SchemaValidator();
        if(schemaValidator.schemaValidation(response, this.schema)){
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(202, response.length());
        }
        else{
            response = "Bad request";
            exchange.sendResponseHeaders(400, response.length());}

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());}
    }

    public void applyResponse(String response, String cell) throws IOException {
        JSONObject JSONresponse = new JSONObject(response);
        System.out.println(JSONresponse);
        String consequenceResponse = JSONresponse.getString("consequence");
        boolean shipLeftResponse = JSONresponse.getBoolean("shipLeft");
        this.game.updateBoards(consequenceResponse, cell);
        this.game.displayBoards();
        if(!shipLeftResponse){System.out.println("YOU WON!"); System.exit(0);}
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            String cellTargeted = exchange.getRequestURI().toString().split("\\?")[1].split("=")[1];;
            String consequence = this.game.consequenceFire(cellTargeted);
            boolean shipLeft = this.game.isShipLeft();
            sendFireResponse(exchange, consequence, shipLeft);
            if(!shipLeft){System.out.println("YOU LOST!"); System.exit(0);}
            try {
                String cell = this.game.player.chooseCellToTarget();
                String response = sendFireRequest(this.game.player.adversaryURL, cell);
                applyResponse(response, cell);
            }
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
