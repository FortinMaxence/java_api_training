package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ServerTest {
    private final int port = 9876;
    private final Player player = new Player();
    private final Game game = new Game(this.player);

    @Test
    void launch() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        HttpServer server = new Server().launch(this.port, this.game);
        server.start();

        String stringExpected = "HTTP server started on port " + this.port + "...\n";
        Assertions.assertThat(outContent.toString()).as("server message starting").isEqualTo(stringExpected);
        server.stop(1);
    }
}
