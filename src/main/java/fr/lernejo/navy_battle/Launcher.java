package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.apache.commons.validator.routines.UrlValidator;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {System.out.println("Please enter a port number !"); System.exit(1);}
        try{
            final int port = Integer.parseInt(args[0]);
            Player player = new Player(); Game game = new Game(player);
            HttpServer server = new Server().launch(port, game); server.start();
            game.initGame();
            if (args.length > 1){
                UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
                if(!urlValidator.isValid(args[1])){ System.out.println("Adversary's URL " + args[1] + " isn't valid !");
                    System.exit(1);}
                game.player.adversaryURL = args[1];
                PostRequest postRequest = new PostRequest(port); postRequest.sendPostRequest(args[1]);
            }
        } catch (Exception e){throw new NumberFormatException(e.getMessage());}
    }
}
