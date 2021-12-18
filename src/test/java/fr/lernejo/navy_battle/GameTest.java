package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GameTest {
    private final Player player = new Player();
    private final Game game = new Game(this.player);

    @Test
    void checkCell_good(){
        Assertions.assertThat(this.game.checkCell("A1"))
            .as("Test right cell")
            .isEqualTo(true);
    }

    @Test
    void checkCell_wrong(){
        Assertions.assertThat(this.game.checkCell("Z12"))
            .as("Test wrong cell")
            .isEqualTo(false);
    }

    @Test
    void consequenceFire_miss(){
        this.player.initSeas();
        Assertions.assertThat(this.game.consequenceFire("A1"))
            .as("Consequence Fire Miss")
            .isEqualTo("miss");
    }

    @Test
    void consequenceFire_hit(){
        this.player.initSeas();
        this.player.sea[0][0] = 2;
        this.player.sea[0][1] = 2;
        this.player.boats.add(new Boats(2, new int[]{0,0}, new int[]{0,1}));
        Assertions.assertThat(this.game.consequenceFire("A1"))
            .as("Consequence Fire Hit - return value")
            .isEqualTo("hit");
        Assertions.assertThat(this.player.sea[0][0])
            .as("Consequence Fire Hit - sea return to 0")
            .isEqualTo(0);
        Assertions.assertThat(this.player.boats.get(0).xPositions[0])
            .as("Consequence Fire Hit - xPos to -1")
            .isEqualTo(-1);
        Assertions.assertThat(this.player.boats.get(0).yPositions[0])
            .as("Consequence Fire Hit - yPos to -1")
            .isEqualTo(-1);
    }

    @Test
    void consequenceFire_sunk(){
        this.player.initSeas();
        this.player.sea[0][0] = 1;
        this.player.boats.add(new Boats(1, new int[]{0}, new int[]{0}));
        Assertions.assertThat(this.game.consequenceFire("A1"))
            .as("Consequence Fire Sunk")
            .isEqualTo("sunk");
        Assertions.assertThat(this.player.sea[0][0])
            .as("Consequence Fire Sunk - sea return to 0")
            .isEqualTo(0);
        Assertions.assertThat(this.player.boats.get(0).xPositions[0])
            .as("Consequence Fire Sunk - xPos to -1")
            .isEqualTo(-1);
        Assertions.assertThat(this.player.boats.get(0).yPositions[0])
            .as("Consequence Fire Sunk - yPos to -1")
            .isEqualTo(-1);
    }

    @Test
    void isShipLeft_true(){
        this.player.initSeas();
        this.player.boats.add(new Boats(1, new int[]{0}, new int[]{0}));
        Assertions.assertThat(this.game.isShipLeft())
            .as("Test shipLeft True")
            .isEqualTo(true);
    }

    @Test
    void isShipLeft_false(){
        Assertions.assertThat(this.game.isShipLeft())
            .as("Test shipLeft False")
            .isEqualTo(false);
    }

    @Test
    void updateBoards_miss(){
        this.player.initSeas();
        this.game.updateBoards("miss", "A1");
        Assertions.assertThat(this.game.player.enemySea[0][0])
            .as("Update Boards x for Miss")
            .isEqualTo("x");
    }

    @Test
    void updateBoards_hit(){
        this.player.initSeas();
        this.game.updateBoards("hit", "A1");
        Assertions.assertThat(this.game.player.enemySea[0][0])
            .as("Update Boards H for Hit")
            .isEqualTo("H");
    }

    @Test
    void updateBoards_sunk(){
        this.player.initSeas();
        this.game.updateBoards("sunk", "A1");
        Assertions.assertThat(this.game.player.enemySea[0][0])
            .as("Update Boards H for Sunk")
            .isEqualTo("H");
    }

    @Test
    void displayBoards() throws IOException {

    }

    @Test
    void initGame(){

    }
}
