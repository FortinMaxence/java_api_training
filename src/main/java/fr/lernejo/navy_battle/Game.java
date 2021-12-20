package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.Scanner;

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

    public void displayBoards(){
        StringBuilder builder = new StringBuilder();
        for(int x=0; x<2; x++){
            builder.append("  ");
            for (int i=0; i< 10; i++) builder.append(" ").append((char)(i+65));
        }
        System.out.println(builder.toString());
        for (int i = 0; i < 10; i++) {
            System.out.print(i+1);
            if(i<9) System.out.print(" ");
            for (int j = 0; j < 10; j++) System.out.print(" " + this.player.sea[i][j]);
            System.out.print("  ");
            for (int j = 0; j < 10; j++) System.out.print(" " + this.player.enemySea[i][j]);
            System.out.print("\n");
        }
    }

    public String consequenceFire(String cellTargeted){
        int xPos = Character.getNumericValue(cellTargeted.charAt(1))-1;
        int yPos = (int)cellTargeted.charAt(0)-65;
        if(this.player.sea[xPos][yPos] == 0)
            return "miss";

        if(this.player.sea[xPos][yPos] >= 1){
            Boats boatHitted;
            for(int i =0; i<this.player.boats.toArray().length; i++){
                for(int j=0; j<this.player.boats.get(i).size; j++){
                    if(this.player.boats.get(i).xPositions[j] == xPos && this.player.boats.get(i).yPositions[j] == yPos){
                        this.player.boats.get(i).xPositions[j]=-1; this.player.boats.get(i).yPositions[j]=-1;
                        boatHitted = this.player.boats.get(i);
                        this.player.sea[xPos][yPos] = 0;
                        if(boatHitted.isSunk()) return "sunk";
                        else return "hit";}}}}
        return "error";
    }

    public boolean isShipLeft(){
        for(int i=0; i<this.player.boats.toArray().length; i++){
            for (int j=0; j<this.player.boats.get(i).size; j++){
                if(this.player.boats.get(i).xPositions[j] >= 0 || this.player.boats.get(i).yPositions[j] >= 0)
                    return true;
            }
        }
        return false;
    }

    public void updateBoards(String consequence, String cell){
        if(consequence.equals("miss"))
            this.player.enemySea[Character.getNumericValue(cell.charAt(1))-1][((int)cell.charAt(0))-65] = "x";

        if(consequence.equals("hit") || consequence.equals("sunk"))
            this.player.enemySea[Character.getNumericValue(cell.charAt(1))-1][((int)cell.charAt(0))-65] = "H";
    }
}
