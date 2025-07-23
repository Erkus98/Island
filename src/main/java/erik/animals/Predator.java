package erik.animals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Predator extends Entity{

    protected String hunter;
    protected String victim;
    protected double percentage;

    public Predator(){

    }


    @Override
    public int eat(Entity hunter, List<Entity> animalsOnCurrentField) {
        System.out.println("Predator starts the hunt!");
        Map<Entity,Entity> hunterAndVictim = new ConcurrentHashMap<>();

        for(Entity victim : animalsOnCurrentField){
            hunterAndVictim.put(hunter,victim);
            if(victim.getType().equals("plant")){
                continue;
            }
            if(getConsumingRatioDecision(hunterAndVictim)){
                gainHealth(hunter,victim);
                System.out.println(hunter.getType() + " killed and ate " + victim.getType());
                return animalsOnCurrentField.indexOf(victim);
            }
        }
        return 0;
    }
    public boolean getConsumingRatioDecision(Map<Entity,Entity> animalList) {


        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/consuming.ratio.json");
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Double chancesOfBeingEaten = -1.00;
        Map<String,String> animals = animalList
                .entrySet()
                .stream()
                .collect(Collectors
                        .toMap(entry -> entry
                                .getKey().getType(),entry -> entry
                                .getValue().getType()));



        for(JsonNode root : jsonNode){
            hunter = root.get("hunter").asText();
            victim = root.get("victim").asText();
            percentage = root.get("percentage").asDouble();
            if(animals.containsKey(hunter) && animals.get(hunter).equals(victim)){
                chancesOfBeingEaten = percentage;
                break;
            }


        }
        if(chancesOfBeingEaten == -1.00){
            System.out.println("Animal consuming ratio was not found!");
            return false;
        }

        return ThreadLocalRandom.current().nextInt(100) < chancesOfBeingEaten;
    }
    private void gainHealth(Entity hunter,Entity victim){
        if(hunter.getKgToBeFull() < victim.getWeight()){
            hunter.setHealth((byte)100);
        }
        int healthIncreaseAmount = (int) ((int) victim.getWeight() * 100 / hunter.getKgToBeFull());
        hunter.setHealth((byte) (hunter.getHealth() + healthIncreaseAmount));
    }
    }
