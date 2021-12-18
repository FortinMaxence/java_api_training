package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class CallHandlerTest {
    public HttpServer server;

    @Test
    void handle() throws IOException, InterruptedException {
        this.server = HttpServer.create(new InetSocketAddress(9876), 0);
        server.createContext("/ping", new CallHandler());

        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/ping"))
            .build();

        Assertions.assertThat(client.send(request, HttpResponse.BodyHandlers.ofString()).body())
            .as("/ping return ok")
            .isEqualTo("OK");
        Assertions.assertThat(client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("/ping return statusCode 200")
            .isEqualTo(200);

        server.stop(1);
    }
}
