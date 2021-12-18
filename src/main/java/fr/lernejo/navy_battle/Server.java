package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final Game game;
    public HttpServer server;

    public Server(int port, Game game){
        this.port = port;
        this.game = game;
    }

    public void start(){
        try {
            this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
            server.createContext("/ping", new CallHandler());
            server.createContext("/api/game/start", new PostHandler(this.game));
            server.createContext("/api/game/fire", new FireHandler(this.game));
            server.setExecutor(Executors.newFixedThreadPool(1));
            server.start();
            System.out.print("HTTP server started on port " + this.port + "...\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        this.server.stop(1);
    }
}
