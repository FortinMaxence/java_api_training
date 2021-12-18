package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class PlayerTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);

    @Test
    void initSeas(){
        this.player.initSeas();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                Assertions.assertThat(this.player.sea[i][j]).as("Player Sea").isEqualTo(0);
                Assertions.assertThat(this.player.enemySea[i][j]).as("Enemy Sea").isEqualTo("o");
            }
        }
    }

    @Test
    void validCoordinatesTrue(){
        this.player.initSeas();
        Assertions.assertThat(this.player.validCoordinates(5, 0, 1, 1))
            .as("Coordinates are valid to place boat")
            .isEqualTo(true);
    }

    @Test
    void validCoordinatesFalseBorder(){
        this.player.initSeas();
        Assertions.assertThat(this.player.validCoordinates(5, 0, 1, 8))
            .as("Boat hits the limit of board")
            .isEqualTo(false);
    }

    @Test
    void validCoordinatesFalseAlreadyBoat(){
        this.player.initSeas();
        this.player.setBoats(Boats.typeBoats.porteAvions, 0, 1, 1);
        Assertions.assertThat(this.player.validCoordinates(5, 0, 1, 1))
            .as("Already boat here")
            .isEqualTo(false);
    }

    @Test
    void setBoats(){
        this.player.initSeas();
        this.player.setBoats(Boats.typeBoats.porteAvions, 0, 1, 1);
        Assertions.assertThat(this.player.boats.toArray().length)
            .as("Test boats array size")
            .isEqualTo(1);
        Assertions.assertThat(this.player.boats.get(0).size)
            .as("Test boat size")
            .isEqualTo(Boats.typeBoats.porteAvions.size);
        Assertions.assertThat(this.player.boats.get(0).xPositions[0])
            .as("Test boat xPos")
            .isEqualTo(1);
        Assertions.assertThat(this.player.boats.get(0).yPositions[0])
            .as("Test boat yPos")
            .isEqualTo(1);
    }

}