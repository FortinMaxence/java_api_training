package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    @Test
    void main_launch_ping_one_arg() throws Exception {
        Launcher.main(new String[]{"1234"});
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:1234/ping"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).as("Launcher ping test one arg").isEqualTo(200);
    }

}
