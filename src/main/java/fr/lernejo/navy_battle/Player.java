package fr.lernejo.navy_battle;

import java.util.*;

public class Player {
    public String adversaryURL;
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

    public int chooseBoatDirection(){
        Scanner sc = new Scanner(System.in);
        int direction;
        do{
            System.out.println("Choose direction: horizontal(0) or vertical(1)");
            while(!sc.hasNextInt()){System.out.println("Choose direction: horizontal(0) or vertical(1)"); sc.next();}
            direction = sc.nextInt();
        }while(direction > 1 || direction < 0 );
        return direction;
    }

    public void placeBoats(Boats.typeBoats typeBoat){
        System.out.print("Boat : " + typeBoat + ", Size : " + typeBoat.size + "\n");
        Scanner sc = new Scanner(System.in);
        int direction = chooseBoatDirection();
        int xPos; int yPos; String cell;
        System.out.println("Choose a cell to place the head of your boat:");
        do{
            cell = "";
            while(!cell.matches("^[A-J]{1}([1-9]|10)$")){
                cell = sc.nextLine();
                if(!cell.matches("^[A-J]{1}([1-9]|10)$"))
                    System.out.println("Choose an existing cell!");
            }
            xPos = Character.getNumericValue(cell.charAt(1))-1;
            yPos = (int)cell.charAt(0)-65;
        }while(!validCoordinates(typeBoat.size, direction, xPos, yPos));
        setBoats(typeBoat, direction, xPos, yPos);
    }

    public boolean checkCell(String cell){
        if(!cell.matches("^[A-J]{1}([1-9]|10)$")){
            return false;
        }
        return true;
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
