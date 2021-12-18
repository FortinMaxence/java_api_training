package fr.lernejo.navy_battle;

public class Boats {
    public final int size;
    public final int[] xPositions;
    public final int[] yPositions;

    public Boats(int size, int[] xPositions, int[] yPositions){
        this.size = size;
        this.xPositions = xPositions;
        this.yPositions = yPositions;
    }

    public enum typeBoats{
        porteAvions(5), croiseur(4), contreTorpilleur1(3), contreTorpilleur2(3), torpilleur(2);
        public final int size;
        typeBoats(int size){
            this.size = size;
        }
    }

    public boolean isSunk(){
        for(int i=0; i<this.size; i++){
            if(this.xPositions[i] >= 0 || this.yPositions[i] >= 0)
                return false;
        }
        return true;
    }
}
