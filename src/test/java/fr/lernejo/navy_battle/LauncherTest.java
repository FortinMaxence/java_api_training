package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LauncherTest {

    @Test
    void main_wrong_arg(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{"xxx"});
        });
    }

    @Test
    void main_no_arg(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{""});
        });
    }

    @Test
    void main_not_well_formed_args(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{"a", "b"});
        });
    }
}
