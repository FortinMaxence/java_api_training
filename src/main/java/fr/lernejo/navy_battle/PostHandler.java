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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostHandler implements HttpHandler  {

    private final String schema = "{\"$schema\": \"http://json-schema.org/schema#\",\"type\": \"object\",\"properties\": " +
        "{\"id\": {\"type\": \"string\"},\"url\": {\"type\": " +
        "\"string\"},\"message\": {\"type\": \"string\"}},\"required\": " +
        "[\"id\",\"url\",\"message\"]}";

    public boolean schemaValidation(String request){
        JSONTokener schemaData = new JSONTokener(schema);
        JSONObject jsonSchema = new JSONObject(schemaData);
        JSONTokener jsonData = new JSONTokener(request);
        JSONObject jsonObject = new JSONObject(jsonData);
        Schema schemaValidator = SchemaLoader.load(jsonSchema);
        try {
            schemaValidator.validate(jsonObject);
            return true;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream().map(ValidationException::getMessage).forEach(System.out::println);
            return false;
        }
    }

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

        if(exchange.getRequestMethod().equals("POST")){
            String json = getJsonRequest(exchange);
            if(schemaValidation(json)){
                final int port = exchange.getHttpContext().getServer().getAddress().getPort();
                final String message = "Response from server.";
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
