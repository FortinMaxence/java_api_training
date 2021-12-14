package fr.lernejo.navy_battle;

import org.apache.commons.validator.routines.UrlValidator;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please enter a port number !");
            return;
        }
        final int port = Integer.parseInt(args[0]);

        Server server = new Server(port);
        server.start();

        if (args.length > 1){
            UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
            if(!urlValidator.isValid(args[1])){
                System.out.println("Adversary's URL " + args[1] + " isn't valid !");
                return;
            }
            Client client = new Client(port);
            client.sendPostRequest(args[1]);
        }
    }
}
