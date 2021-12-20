package fr.lernejo.navy_battle;

import java.util.*;

public class Player {
    public final String[] adversaryURL = new String[1];
    public final int[][] sea = new int[10][10];
    public final String[][] enemySea = new String[10][10];
    public final List<Boats> boats = new ArrayList<>();

    public void initSeas(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){this.sea[i][j] = 0; this.enemySea[i][j] = "o";}
        }
    }

    public boolean validCoordinates(int boatSize, int direction, int xPos, int yPos){
        if(!((yPos<=9 && yPos>=0) && (xPos<=9 && xPos>=0))){System.out.println("You can't place the boat here!"); return false;}
        for(int i=0; i<boatSize; i++) {
            if (direction == 0)
                if (this.sea[xPos][yPos+i] != 0 || (yPos - 1) + boatSize > 9) {
                    System.out.println("You can't place the boat here!");
                    return false;
                }
            if (direction == 1)
                if (this.sea[xPos+i][yPos] != 0 || ((xPos) - 1) + boatSize > 9) {
                    System.out.println("You can't place the boat here!");
                    return false;
                }
        }
        return true;
    }

    public boolean checkCell(int xPos, int yPos, String cell){
        if(this.enemySea[xPos][yPos].equals("o") && cell.matches("^[A-J]{1}([1-9]|10)$"))
            return true;

        System.out.println("You can't fire here!");
        return false;
    }

    public String chooseCellToTarget(){
        String cell; int xPos; int yPos;
        System.out.println("Enter a cell to target:");
        do{
            xPos = new Random().nextInt(10);
            yPos = new Random().nextInt(10);
            cell = (char)(yPos+65) + "" + (xPos+1);
            System.out.println(cell);
        }while(!checkCell(xPos, yPos, cell));
        return cell;
    }

    public void placeBoats(Boats.typeBoats typeBoat){
        System.out.print("Boat : " + typeBoat + ", Size : " + typeBoat.size + "\n");
        System.out.println("Choose direction: horizontal(0) or vertical(1)");
        int direction = (int) Math.round(Math.random());
        System.out.println(direction);
        int xPos; int yPos;
        System.out.println("Choose a cell to place the head of your boat:");
        do{
            xPos = new Random().nextInt(10);
            yPos = new Random().nextInt(10);
            System.out.println((char)(xPos+65) + "" + yPos);
        }while(!validCoordinates(typeBoat.size, direction, xPos, yPos));
        setBoats(typeBoat, direction, xPos, yPos);
    }

    public void setBoats(Boats.typeBoats typeBoat, int direction, int xPos, int yPos){
        int[] x = new int[typeBoat.size]; int[] y = new int[typeBoat.size];
        for(int i = 0; i<typeBoat.size; i++){
            if(direction == 0){x[i] = (xPos); y[i] = (yPos+i);
                this.sea[xPos][yPos+i] = typeBoat.size;
            }
            if(direction == 1){x[i] = xPos+i; y[i] = yPos;
                this.sea[xPos+i][yPos] = typeBoat.size;
            }
        }
        this.boats.add(new Boats(typeBoat.size, x, y));
    }
}
