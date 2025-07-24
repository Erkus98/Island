package erik.utility;

import erik.Field;
import erik.animals.Entity;
import erik.animals.Plant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class IslandActions implements Callable<Map<Field, List<Entity>>>{

    ThreadLocalRandom random;
    private final Object lock;

    public IslandActions(Object lock) {
        this.lock = lock;
    }

    @Override
    public Map<Field, List<Entity>> call() throws Exception {

        EntityCreator entityCreator = new EntityCreator();
        Map<Field, List<Entity>> island = new ConcurrentHashMap<>();
        for (short i = 1; i <= Field.ISLAND_LENGTH; i++) {
            for (short j = 1; j <= Field.ISLAND_WIDTH; j++) {
                island.put(new Field(i, j), entityCreator.createAnimalList());
            }
        }
        return island;
    }


    public void showAnimals(Map<Field, List<Entity>> island) {

        synchronized (lock) {
            Map<Field, List<String>> transformedIsland = new ConcurrentHashMap<>();

            island.entrySet().forEach(entry -> {
                Field field = entry.getKey();
                List<Entity> entityList = Collections.synchronizedList(new ArrayList<>(entry.getValue()));
                List<String> characterList = entityList.stream().map(Entity::transformFaces).toList();
                transformedIsland.put(field, characterList);
            });


            System.out.println(transformedIsland);
            System.out.flush();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  void move(Map<Field, List<Entity>> islandFuture) {
        synchronized (lock) {
            Map<Field, List<Entity>> islandMap = islandFuture;
            if (islandMap == null) {
                System.err.println("Error: Island map is null.");
                return;
            }
            List<Map.Entry<Field, List<Entity>>> entriesToProcess = Collections.synchronizedList(new ArrayList<>(islandMap.entrySet()));

            for (Map.Entry<Field, List<Entity>> entry : entriesToProcess) {
                Field currentField = entry.getKey();
                List<Entity> entityList = entry.getValue();
                List<Entity> animalsOnCurrentField =Collections.synchronizedList(new ArrayList<>(entityList));

                for (Entity entity : animalsOnCurrentField) {
                    if(entity.getType().equals("plant")){
                        continue;
                    }
                    Field newAnimalDestination = calculateNewAnimalPosition(entity, currentField);


                    updateIslandMap(islandMap, entity, currentField, newAnimalDestination);
                }
            }
        }
    }


    private Field calculateNewAnimalPosition(Entity entity, Field currentField) {
        short newX = currentField.getX();
        short newY = currentField.getY();
        int steps = entity.getSpeedPerCycle();

        while (steps > 0) {
             random = ThreadLocalRandom.current();
            char direction = (char) (random.nextInt(4) + 'a');
            if (direction == 'a' && newX != 1) {
                newX -= 1;
            }
            if (direction == 'b' && newX != Field.ISLAND_LENGTH) {
                newX += 1;
            }
            if (direction == 'c' && newY != Field.ISLAND_WIDTH) {
                newY += 1;
            }
            if (direction == 'd' && newY != 1) {
                newY -= 1;
            }
            steps--;
        }
        return new Field(newX, newY);

    }

    private void updateIslandMap(Map<Field, List<Entity>> islandMap, Entity entity, Field currentField, Field newAnimalDestination) {
        List<Entity> animalsOnCurrentField = islandMap.get(currentField);
        if (animalsOnCurrentField != null) {
            boolean animalWasRemoved = animalsOnCurrentField.remove(entity);
            if (!animalWasRemoved) {
                System.err.println("Error: Animal " + entity + " is not found on " + currentField + " for deletion!.");
            }

            if (animalsOnCurrentField.isEmpty()) {
                islandMap.remove(currentField);
            }
        } else {
            System.err.println("Warning: Current field " + currentField + " not found in map for animal " + entity + ".");
        }


        List<Entity> updatedEntityList = islandMap.computeIfAbsent(newAnimalDestination, k -> Collections.synchronizedList(new ArrayList<>()));
        if(reduceHealthOfAnimal(entity)){
            return;
        }
        updatedEntityList.add(entity);

    }

    private boolean reduceHealthOfAnimal(Entity entity){
        byte energyRequiredForMovement = 10;
        entity.setHealth((byte) (entity.getHealth() - energyRequiredForMovement));
        if(entity.getHealth() <= 0){
            return true;
        }
        return false;
    }
    public boolean hasAnimals(Map<Field,List<Entity>> island){
        for(List<Entity> entities : island.values()){
            for(Entity entity : entities){
                if(!(entity instanceof Plant)){
                    return true;
                }
            }
        }
        return false;
    }

}
