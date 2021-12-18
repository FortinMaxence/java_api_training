package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoatsTest {

    @Test
    void isSunk_true(){
        Boats boat = new Boats(1, new int[]{-1}, new int[]{-1});
        Assertions.assertThat(boat.isSunk())
            .as("Test if boat is sunk - true")
            .isEqualTo(true);
    }

    @Test
    void isSunk_false(){
        Boats boat = new Boats(2, new int[]{-1, 0}, new int[]{-1, 1});
        Assertions.assertThat(boat.isSunk())
            .as("Test if boat is sunk - false")
            .isEqualTo(false);
    }
}
