package Erik.utility;

import Erik.Field;
import Erik.animal_actions.Movement;
import Erik.animals.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class IslandActions implements Callable<Map<Field, List<Animal>>>, Movement {

    Map<Field, List<Animal>> island;


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
    public void move(Future<Map<Field, List<Animal>>> island) {

        try {
            for (Map.Entry<Field, List<Animal>> entry : new ArrayList<>(island.get().entrySet())) {
                Field currentField = entry.getKey();
                List<Animal> animalList = entry.getValue();
                /*Idea behind this is that currentField direction will be randomly chosen by 4 letters(a,b,c,d)
                Each letter represents direction a - left, b-right, c - up, d - down*/
                List<Animal> animalsOnCurrentField = new ArrayList<>(animalList);
                for (Animal animal : animalsOnCurrentField) {
                    int steps = animal.getSpeedPerCycle();
                    short newX = currentField.getX();
                    short newY = currentField.getY();
                    while (steps > 0) {
                        ThreadLocalRandom random = ThreadLocalRandom.current();
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
                    Field newAnimalDestination = new Field(newX,newY);

                    boolean animalWasRemoved = animalList.remove(animal);
                    if(!animalWasRemoved){
                        System.err.println("Error: Animal " + animal + " is not found on " + currentField + " for deletion!.");
                        continue;
                    }
                    if (animalList.isEmpty()) {
                        island.get().remove(currentField);
                    }

                    List<Animal> updatedAnimalList = island.get().get(newAnimalDestination);
                    if(updatedAnimalList == null){
                        updatedAnimalList = new ArrayList<>();
                        island.get().put(newAnimalDestination,updatedAnimalList);
                    }
                    updatedAnimalList.add(animal);


                }



            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
