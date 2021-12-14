package fr.lernejo.navy_battle;

import org.apache.commons.validator.routines.UrlValidator;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please enter a port number !");
            return;
        }
        final int port = Integer.parseInt(args[0]);
        Game game = new Game();

        Server server = new Server(port, game);
        server.start();

        if (args.length > 1){
            UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
            if(!urlValidator.isValid(args[1])){
                System.out.println("Adversary's URL " + args[1] + " isn't valid !");
                return;
            }
            PostRequest postRequest = new PostRequest(port);
            postRequest.sendPostRequest(args[1]);
            //FireHandler fire = new FireHandler();
            //fire.sendFireRequest(args[1], "F2");
        }
    }
}
