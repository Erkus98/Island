package erik.utility;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import erik.animals.Animal;

import java.io.File;
import java.io.IOException;


public class JsonParser {

    public <T extends Animal> T deserializeJson(T animal) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/animal.chars.json");
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        String animalType = animal.getClass()
                .getSimpleName()
                .toLowerCase();

        JsonNode animalDataNode = null;
        for (JsonNode jsonNode : rootNode) {
            if (jsonNode.has("type") && jsonNode.get("type").asText().equals(animalType)) {
                animalDataNode = jsonNode;
                break;
            }
        }


        objectMapper.readerForUpdating(animal).readValue(animalDataNode);
        return animal;
    }

}
