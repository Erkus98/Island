package erik.animals;

import erik.utility.ConsumingRatioCache;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


public class Herbivore extends Entity{
    public Herbivore(){

    }
    @Override
    public int eat(Entity hunter, List<Entity> animalsOnCurrentField) {

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
                if(getConsumingRatioDecision(hunter,victim)){
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

    public boolean getConsumingRatioDecision(Entity hunter, Entity victim) {
        Optional<Double> chance = ConsumingRatioCache.getChance(hunter.getType(), victim.getType());
        return chance.map(p -> ThreadLocalRandom.current().nextInt(100) < p).orElse(false);
    }
}
