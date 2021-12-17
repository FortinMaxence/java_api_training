package fr.lernejo.navy_battle;

import java.io.IOException;

public class Game {
    public final Player player;
    public Game(Player player){
        this.player = player;
    }

    public void initGame() throws IOException {
        this.player.initSeas();
        for(Boats.typeBoats typeBoat : Boats.typeBoats.values()){
            this.player.placeBoats(typeBoat);
            displayBoards();
        }

    }

    public void displayBoards() throws IOException {
        StringBuilder builder = new StringBuilder();
        for(int x=0; x<2; x++){
            builder.append("  ");
            for (int i=0; i< 10; i++){
                builder.append(" ").append((char)(i+65));
            }
        }
        System.out.println(builder.toString());
        for (int i = 0; i < 10; i++) {
            System.out.print(i+1);
            if(i<9) System.out.print(" ");
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + this.player.sea[i][j]);
            }
            System.out.print("  ");
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + this.player.enemySea[i][j]);
            }
            System.out.println();
        }
    }



}
