package erik.animals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Herbivore extends Entity{
    public Herbivore(){

    }
    @Override
    public int eat(Entity hunter, List<Entity> animalsOnCurrentField) {
        System.out.println("Herbivore starts the hunt!");

        for(Entity victim : animalsOnCurrentField){
            if(victim instanceof Plant){
                gainHealthByEatingGrass(hunter);
                System.out.println("Herbivore ate grass!");
                return animalsOnCurrentField.indexOf(victim);
            }
            if(victim instanceof Predator){
                continue;
            }
            if((hunter.getType().equals("mouse") && victim.getType().equals("caterpillar")) ||
                    (hunter.getType().equals("boar") && victim.getType().equals("mouse")) ||
                    (hunter.getType().equals("boar") && victim.getType().equals("caterpillar")) ||
                    (hunter.getType().equals("duck") && victim.getType().equals("caterpillar"))){
                Map<Entity,Entity> hunterAndVictim = new ConcurrentHashMap<>();
                hunterAndVictim.put(hunter,victim);
                if(getConsumingRatioDecision(hunterAndVictim)){
                    gainHealth(hunter,victim);
                    System.out.println(hunter.getType() + " killed and ate " + victim.getType());
                    return animalsOnCurrentField.indexOf(victim);
                }

            }
        }
        return 0;
    }
    private void gainHealthByEatingGrass(Entity hunter){
        int healthIncreaseAmount = (int) (100 / hunter.getKgToBeFull());
        hunter.setHealth((byte) (hunter.getHealth() + healthIncreaseAmount));
    }
    private void gainHealth(Entity hunter, Entity victim){
        if(hunter.getKgToBeFull() < victim.getWeight()){
            hunter.setHealth((byte)100);
        }
        int healthIncreaseAmount = (int) ((int) victim.getWeight() * 100 / hunter.getKgToBeFull());
        hunter.setHealth((byte) (hunter.getHealth() + healthIncreaseAmount));
    }

    public boolean getConsumingRatioDecision(Map<Entity,Entity> animalList) {
        String hunter;
        String victim;
        double percentage;

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
}
