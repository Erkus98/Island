package erik.utility;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import erik.animals.Entity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JsonParser {

    private static final Map<String, JsonNode> cachedEntities = new HashMap<>();
    private static boolean initialized = false;
    public JsonParser() throws IOException {
        // Кеш инициализируется только один раз
        if (!initialized) {
            synchronized (JsonParser.class) {
                if (!initialized) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    File jsonFile = new File("src/main/resources/animal.chars.json");
                    JsonNode rootNode = objectMapper.readTree(jsonFile);

                    for (JsonNode node : rootNode) {
                        if (node.has("type")) {
                            cachedEntities.put(node.get("type").asText(), node);
                        }
                    }
                    initialized = true;
                }
            }
        }
    }

    public <T extends Entity> T deserializeJson(T animal) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String animalType = animal.getClass().getSimpleName().toLowerCase();
        JsonNode animalDataNode = cachedEntities.get(animalType);

        if (animalDataNode == null) {
            throw new IOException("No cached JSON for type: " + animalType);
        }

        objectMapper.readerForUpdating(animal).readValue(animalDataNode);
        return animal;
    }

}
