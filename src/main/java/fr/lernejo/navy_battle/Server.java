package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private final int port;

    public Server(int port){
        this.port = port;
    }

    public void start(){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
            server.createContext("/ping", new CallHandler());
            server.createContext("/api/game/start", new PostHandler());
            server.createContext("/api/game/fire", new FireHandler());
            server.setExecutor(Executors.newFixedThreadPool(1));
            server.start();
            System.out.println("HTTP server started on port " + this.port + "...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
