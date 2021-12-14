package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class PostHandler implements HttpHandler  {

    private final String schema = "{\"$schema\": \"http://json-schema.org/schema#\",\"type\": \"object\",\"properties\": " +
        "{\"id\": {\"type\": \"string\"},\"url\": {\"type\": " +
        "\"string\"},\"message\": {\"type\": \"string\"}},\"required\": " +
        "[\"id\",\"url\",\"message\"]}";

    public String getJsonRequest(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        String value = br.readLine();
        JSONObject json = new JSONObject(value);
        return json.toString();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final String response;
        SchemaValidator schemaValidator = new SchemaValidator();

        if(exchange.getRequestMethod().equals("POST")){
            String json = getJsonRequest(exchange);
            if(schemaValidator.schemaValidation(json, this.schema)){
                final int port = exchange.getHttpContext().getServer().getAddress().getPort();
                response = "{\"id\":\"0\", \"url\":\"http://localhost:" + port +
                    "\", \"message\":\"Response from Server\"}";

                exchange.sendResponseHeaders(202, response.length());
            }
            else{
                response = "Bad request";
                exchange.sendResponseHeaders(400, response.length());}

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());}

        }
    }
}
