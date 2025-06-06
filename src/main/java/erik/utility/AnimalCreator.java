package erik.utility;

import erik.animals.Animal;
import erik.animals.Plant;
import erik.animals.herbivores.*;
import erik.animals.predators.*;

import java.io.IOException;
import java.util.*;

public class AnimalCreator {

    Map<String, Class<? extends Animal>> animalTypes;

    public AnimalCreator() {
        animalTypes = new HashMap<>();
        animalTypes.put("boar", Boar.class);
        animalTypes.put("buffalo", Buffalo.class);
        animalTypes.put("caterpillar", Caterpillar.class);
        animalTypes.put("deer", Deer.class);
        animalTypes.put("duck", Duck.class);
        animalTypes.put("goat", Goat.class);
        animalTypes.put("horse", Horse.class);
        animalTypes.put("mouse", Mouse.class);
        animalTypes.put("rabbit", Rabbit.class);
        animalTypes.put("sheep", Sheep.class);
        animalTypes.put("bear", Bear.class);
        animalTypes.put("eagle", Eagle.class);
        animalTypes.put("fox", Fox.class);
        animalTypes.put("snake", Snake.class);
        animalTypes.put("wolf", Wolf.class);
        animalTypes.put("plant", Plant.class);

    }


    public List<Animal> createAnimalList() throws IOException {
        List<Animal> animals = new ArrayList<>();

        JsonParser jsonParser = new JsonParser();
        Random random = new Random();

        for (Map.Entry<String, Class<? extends Animal>> entry : animalTypes.entrySet()) {
            Animal sampleAnimal = null;
            Class<? extends Animal> animalClass = entry.getValue();
            try {
                sampleAnimal = animalClass.getDeclaredConstructor().newInstance();
                jsonParser.deserializeJson(sampleAnimal);
            } catch (Exception exception) {
                System.out.println("Method is absent!");
                continue;
            }
            if (sampleAnimal != null) {
                int amountOfAnimalsPerField = random.nextInt(sampleAnimal.getMaxAmountOnOneField());
                for (int i = 0; i < amountOfAnimalsPerField; i++) {
                    try {
                        animals.add(jsonParser.deserializeJson(animalClass.getDeclaredConstructor().newInstance()));
                    } catch (IOException exception) {
                        System.out.println("No such animal in JSON " + entry.getKey());
                    } catch (Exception exception) {
                        System.out.println("Error while creating this type " + entry.getKey());
                    }

                }
            }
        }

        return animals;
    }

    @Override
    public String toString() {
        return "AnimalCreator{" +
                "animalTypes=" + animalTypes +
                '}';
    }
}
