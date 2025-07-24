package erik.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ConsumingRatioCache {
    private static final Map<String, Map<String, Double>> ratioMap = new ConcurrentHashMap<>();

    static {
        loadConsumingRatios();
    }

    private static void loadConsumingRatios() {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/consuming.ratio.json");

        try {
            JsonNode rootArray = objectMapper.readTree(jsonFile);
            for (JsonNode node : rootArray) {
                String hunter = node.get("hunter").asText();
                String victim = node.get("victim").asText();
                double percentage = node.get("percentage").asDouble();

                ratioMap
                        .computeIfAbsent(hunter, k -> new HashMap<>())
                        .put(victim, percentage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load consuming.ratio.json", e);
        }
    }

    public static Optional<Double> getChance(String hunter, String victim) {
        if (ratioMap.containsKey(hunter)) {
            return Optional.ofNullable(ratioMap.get(hunter).get(victim));
        }
        return Optional.empty();
    }

}
