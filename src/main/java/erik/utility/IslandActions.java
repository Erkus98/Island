package erik.utility;

import erik.Field;
import erik.animal_actions.AnimalMovement;
import erik.animals.Animal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class IslandActions implements Callable<Map<Field, List<Animal>>>, AnimalMovement {

    ThreadLocalRandom random;
    private final byte energyRequiredForMovement = 20;

    @Override
    public Map<Field, List<Animal>> call() throws Exception {

        AnimalCreator animalCreator = new AnimalCreator();
        Map<Field, List<Animal>> island = new HashMap<>();
        for (short i = 1; i <= Field.ISLAND_LENGTH; i++) {
            for (short j = 1; j <= Field.ISLAND_WIDTH; j++) {
                island.put(new Field(i, j), animalCreator.createAnimalList());
            }
        }
        return island;
    }


    public void showAnimals(Future<Map<Field, List<Animal>>> island) throws Exception {


        Map<Field, List<String>> transformedIsland = new HashMap<>();

        island.get().entrySet().forEach(entry -> {
            Field field = entry.getKey();
            List<Animal> animalList = entry.getValue();
            List<String> characterList = animalList.stream().map(Animal::transformFaces).toList();
            transformedIsland.put(field, characterList);
        });


        System.out.println(transformedIsland);
    }

    public void move(Future<Map<Field, List<Animal>>> islandFuture) {
        try {
            Map<Field, List<Animal>> islandMap = islandFuture.get();
            if (islandMap == null) {
                System.err.println("Error: Island map is null.");
                return;
            }
            List<Map.Entry<Field, List<Animal>>> entriesToProcess = new ArrayList<>(islandMap.entrySet());

            for (Map.Entry<Field, List<Animal>> entry : entriesToProcess) {
                Field currentField = entry.getKey();
                List<Animal> animalList = entry.getValue();
                List<Animal> animalsOnCurrentField = new ArrayList<>(animalList);

                for (Animal animal : animalsOnCurrentField) {

                    Field newAnimalDestination = calculateNewAnimalPosition(animal, currentField);


                    updateIslandMap(islandMap, animal, currentField, newAnimalDestination);
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted during island map retrieval: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.err.println("Error during island map retrieval: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private Field calculateNewAnimalPosition(Animal animal, Field currentField) {
        short newX = currentField.getX();
        short newY = currentField.getY();
        int steps = animal.getSpeedPerCycle();

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

    private void updateIslandMap(Map<Field, List<Animal>> islandMap, Animal animal, Field currentField, Field newAnimalDestination) {
        List<Animal> animalsOnCurrentField = islandMap.get(currentField);
        if (animalsOnCurrentField != null) {
            boolean animalWasRemoved = animalsOnCurrentField.remove(animal);
            if (!animalWasRemoved) {
                System.err.println("Error: Animal " + animal + " is not found on " + currentField + " for deletion!.");
            }

            if (animalsOnCurrentField.isEmpty()) {
                islandMap.remove(currentField);
            }
        } else {
            System.err.println("Warning: Current field " + currentField + " not found in map for animal " + animal + ".");
        }


        List<Animal> updatedAnimalList = islandMap.computeIfAbsent(newAnimalDestination, k -> new ArrayList<>());
        if(reduceHealthOfAnimal(animal)){
            return;
        }
        updatedAnimalList.add(animal);
    }

    private boolean reduceHealthOfAnimal(Animal animal){
        animal.setHealth((byte) (animal.getHealth() -energyRequiredForMovement));
        if(animal.getHealth() == 0){
            System.out.println(animal + " has died from starvation!");
            return true;
        }
        return false;
    }

}
