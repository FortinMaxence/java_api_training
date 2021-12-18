package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ServerTest {
    private final int port = 9876;
    private final Player player = new Player();
    private final Game game = new Game(this.player);
    private final Server server = new Server(port, this.game);

    /*@Test
    void start(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        server.start();

        String stringExpected = "HTTP server started on port " + this.port + "...\n";
        Assertions.assertThat(outContent.toString()).as("server message starting").isEqualTo(stringExpected);
    }*/
}
