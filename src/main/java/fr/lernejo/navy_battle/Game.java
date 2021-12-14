package fr.lernejo.navy_battle;

public class Game {
    Player[] player = new Player[2];

    public void fire(String adversaryURL){
        FireHandler fire = new FireHandler();
        fire.sendFireRequest(adversaryURL, "F2");
    }

}
