package fr.lernejo.navy_battle;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SchemaValidator {
    public boolean schemaValidation(String request, String schema){
        JSONTokener schemaData = new JSONTokener(schema);
        JSONObject jsonSchema = new JSONObject(schemaData);
        JSONTokener jsonData = new JSONTokener(request);
        JSONObject jsonObject = new JSONObject(jsonData);
        Schema schemaValidator = SchemaLoader.load(jsonSchema);
        try {
            schemaValidator.validate(jsonObject);
            return true;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream().map(ValidationException::getMessage).forEach(System.out::println);
            return false;
        }
    }
}
