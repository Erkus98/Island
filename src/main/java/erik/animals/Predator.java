package erik.animals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import erik.utility.ConsumingRatioCache;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Map<Entity,Entity> hunterAndVictim = new ConcurrentHashMap<>();

        for(Entity victim : animalsOnCurrentField){
            hunterAndVictim.put(hunter,victim);
            if(victim.getType().equals("plant")){
                continue;
            }
            if(getConsumingRatioDecision(hunter,victim)){
                gainHealth(hunter,victim);
                System.out.println(hunter.getType() + " killed and ate " + victim.getType());
                return animalsOnCurrentField.indexOf(victim);
            }
        }
        return 0;
    }
    public boolean getConsumingRatioDecision(Entity hunter, Entity victim) {
        Optional<Double> chance = ConsumingRatioCache.getChance(hunter.getType(), victim.getType());
        return chance.map(p -> ThreadLocalRandom.current().nextInt(100) < p).orElse(false);

    }
    private void gainHealth(Entity hunter,Entity victim){
        if(hunter.getKgToBeFull() < victim.getWeight()){
            hunter.setHealth((byte)100);
        }
        int healthIncreaseAmount = (int) ((int) victim.getWeight() * 100 / hunter.getKgToBeFull());
        hunter.setHealth((byte) (hunter.getHealth() + healthIncreaseAmount));
    }
    }
