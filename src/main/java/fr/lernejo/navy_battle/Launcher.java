package fr.lernejo.navy_battle;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please enter a port number !");
            return;
        }
        int port = Integer.parseInt(args[0]);

        if (args.length < 2){
            Server server = new Server(port);
            server.start();
        }
    }
}
