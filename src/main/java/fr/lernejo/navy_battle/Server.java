package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    public HttpServer launch(int port, Game game) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", new CallHandler());
        server.createContext("/api/game/start", new PostHandler(game));
        server.createContext("/api/game/fire", new FireHandler(game));
        server.setExecutor(Executors.newFixedThreadPool(1));
        System.out.print("HTTP server started on port " + port + "...\n");

        return server;
    }
}
