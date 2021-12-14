package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
            server.setExecutor(Executors.newFixedThreadPool(1));
            server.start();
            System.out.println("HTTP server started on port " + this.port + "...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
