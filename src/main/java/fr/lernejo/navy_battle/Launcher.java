package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Veuillez saisir un port d'écoute !");
            return;}

        int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("HTTP server started on port " + port + "...");
        server.createContext("/ping", new CallHandler());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        server.setExecutor(executor);
        server.start();

    }
}
