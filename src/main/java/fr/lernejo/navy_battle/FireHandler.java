package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FireHandler implements HttpHandler {
    public final String schema = "{\"$schema\":\"http://json-schema.org/schema#\",\"type\":\"object\"," +
        "\"properties\":{\"consequence\":{\"type\":\"string\",\"enum\":[\"miss\",\"hit\",\"sunk\"]}," +
        "\"shipLeft\":{\"type\":\"boolean\"}},\"required\":[\"consequence\",\"shipLeft\"]}";

    public String getParams(HttpExchange httpExchange) {
        return httpExchange.
            getRequestURI()
            .toString()
            .split("\\?")[1]
            .split("=")[1];
    }

    public void sendFireRequest(String adversaryURL, String cell){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest fireRequest = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/fire?cell=" + cell))
            .build();

        client.sendAsync(fireRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();

    }

    public void sendFireResponse(HttpExchange exchange, String consequence, boolean shipLeft) throws IOException {
        String response = "{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}";
        SchemaValidator schemaValidator = new SchemaValidator();

        if(schemaValidator.schemaValidation(response, this.schema))
            exchange.sendResponseHeaders(202, response.length());
        else{
            response = "Bad request";
            exchange.sendResponseHeaders(400, response.length());}

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());}
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            String requestParamValue = getParams(exchange);

            String consequence = "miss";
            boolean shipLeft = true;
            sendFireResponse(exchange, consequence, shipLeft);
        }
    }
}
