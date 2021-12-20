package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
;


public class PlayerTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);

    @Test
    void initSeas_ok(){
        this.player.initSeas();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                Assertions.assertThat(this.player.sea[i][j]).as("Player Sea").isEqualTo(0);
                Assertions.assertThat(this.player.enemySea[i][j]).as("Enemy Sea").isEqualTo("o");
            }
        }
    }

    @Test
    void validCoordinates_true_direction_0(){
        this.player.initSeas();
        Assertions.assertThat(this.player.validCoordinates(5, 0, 1, 1))
            .as("Coordinates are valid to place boat")
            .isEqualTo(true);
    }

    @Test
    void validCoordinates_false_border_direction_0(){
        this.player.initSeas();
        Assertions.assertThat(this.player.validCoordinates(5, 0, 1, 8))
            .as("Boat hits the limit of board")
            .isEqualTo(false);
    }

    @Test
    void validCoordinates_false_already_boat_direction_0(){
        this.player.initSeas();
        this.player.setBoats(Boats.typeBoats.porteAvions, 0, 1, 1);
        Assertions.assertThat(this.player.validCoordinates(5, 0, 1, 1))
            .as("Already boat here")
            .isEqualTo(false);
    }

    @Test
    void validCoordinates_true_direction_1(){
        this.player.initSeas();
        Assertions.assertThat(this.player.validCoordinates(5, 1, 1, 1))
            .as("Coordinates are valid to place boat")
            .isEqualTo(true);
    }

    @Test
    void validCoordinates_false_border_direction_1(){
        this.player.initSeas();
        Assertions.assertThat(this.player.validCoordinates(5, 1, 8, 1))
            .as("Boat hits the limit of board")
            .isEqualTo(false);
    }

    @Test
    void validCoordinates_false_already_boat_direction_1(){
        this.player.initSeas();
        this.player.setBoats(Boats.typeBoats.porteAvions, 1, 1, 1);
        Assertions.assertThat(this.player.validCoordinates(5, 1, 1, 1))
            .as("Already boat here")
            .isEqualTo(false);
    }

    @Test
    void setBoats_ok(){
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

    @Test
    void checkCell_good(){
        this.player.initSeas();
        Assertions.assertThat(this.player.checkCell(0, 0, "A1"))
            .as("checkCell good")
            .isEqualTo(true);
    }

    @Test
    void checkCell_wrong_already_fired_here(){
        this.player.initSeas();
        this.player.enemySea[0][0] = "x";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        boolean result = this.player.checkCell(0, 0, "A1");

        String stringExpected = "You can't fire here!";
        Assertions.assertThat(outContent.toString().replaceAll("\n", "").replaceAll("\r", "")).as("checkCell wrong message").isEqualTo(stringExpected);
        Assertions.assertThat(result).as("checkCell wrong").isEqualTo(false);
    }

    @Test
    void checkCell_wrong_not_good_format(){
        this.player.initSeas();
        this.player.enemySea[0][0] = "x";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        boolean result = this.player.checkCell(0, 0, "XX");

        String stringExpected = "You can't fire here!";
        Assertions.assertThat(outContent.toString().replaceAll("\n", "").replaceAll("\r", "")).as("checkCell wrong format").isEqualTo(stringExpected);
        Assertions.assertThat(result).as("checkCell wrong").isEqualTo(false);
    }

    @Test
    void placeBoats(){
        this.player.initSeas();
        this.player.placeBoats(Boats.typeBoats.porteAvions);
        int count = 0;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if (this.player.sea[i][j] > 0)
                    count++;
            }
        }
        Assertions.assertThat(count).as("check Boat well placed").isEqualTo(5);
    }

}
