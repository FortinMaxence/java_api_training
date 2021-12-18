package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SchemaValidatorTest {
    public SchemaValidator schemavalidator = new SchemaValidator();
    final int port = 9876;
    final String[] consequence = new String[]{"miss", "hit", "sunk"};
    final boolean[] shipLeft = new boolean[]{true, false};
    final String jsonSchemaFire = "{\"$schema\":\"http://json-schema.org/schema#\",\"type\":\"object\"," +
        "\"properties\":{\"consequence\":{\"type\":\"string\",\"enum\":[\"miss\",\"hit\",\"sunk\"]}," +
        "\"shipLeft\":{\"type\":\"boolean\"}},\"required\":[\"consequence\",\"shipLeft\"]}";

    final String jsonSchemaPost = "{\"$schema\": \"http://json-schema.org/schema#\",\"type\": \"object\",\"properties\": " +
        "{\"id\": {\"type\": \"string\"},\"url\": {\"type\": " +
        "\"string\"},\"message\": {\"type\": \"string\"}},\"required\": " +
        "[\"id\",\"url\",\"message\"]}";


    @Test
    void schemaValidator_post_true(){
        Assertions.assertThat(this.schemavalidator.schemaValidation("{\"id\":\"0\", \"url\":\"http://localhost:" + this.port +
            "\", \"message\":\"this is a message\"}", this.jsonSchemaPost))
            .as("schemaValidation true for POST").isEqualTo(true);
    }

    @Test
    void schemaValidator_post_false(){
        Assertions.assertThat(this.schemavalidator.schemaValidation("{}", this.jsonSchemaPost))
            .as("schemaValidation false for POST").isEqualTo(false);
    }

    @Test
    void schemaValidator_fire_true(){
        for (String s : this.consequence) {
            for (boolean b : this.shipLeft) {
                Assertions.assertThat(this.schemavalidator.schemaValidation("{\"consequence\":\"" + s + "\", \"shipLeft\":" + b + "}", this.jsonSchemaFire))
                    .as("schemaValidation true for Fire").isEqualTo(true);
            }
        }
    }

    @Test
    void schemaValidator_fire_false(){
        Assertions.assertThat(this.schemavalidator.schemaValidation("{}", this.jsonSchemaFire))
            .as("schemaValidation false for Fire").isEqualTo(false);
    }
}
