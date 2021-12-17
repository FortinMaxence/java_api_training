package fr.lernejo.navy_battle;

import java.util.List;
import java.util.Map;

public class Boats {
    private final int size;
    private final int[] xPositions;
    private final int[] yPositions;

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
}
