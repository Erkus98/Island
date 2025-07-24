package erik.utility;

import erik.animals.Entity;
import erik.animals.Plant;
import erik.animals.herbivores.*;
import erik.animals.predators.*;

import java.io.IOException;
import java.util.*;

public class EntityCreator {

    Map<String, Class<? extends Entity>> EntityTypes;

    public EntityCreator() {
        EntityTypes = new HashMap<>();
        EntityTypes.put("boar", Boar.class);
        EntityTypes.put("buffalo", Buffalo.class);
        EntityTypes.put("caterpillar", Caterpillar.class);
        EntityTypes.put("deer", Deer.class);
        EntityTypes.put("duck", Duck.class);
        EntityTypes.put("goat", Goat.class);
        EntityTypes.put("horse", Horse.class);
        EntityTypes.put("mouse", Mouse.class);
        EntityTypes.put("rabbit", Rabbit.class);
        EntityTypes.put("sheep", Sheep.class);
        EntityTypes.put("bear", Bear.class);
        EntityTypes.put("eagle", Eagle.class);
        EntityTypes.put("fox", Fox.class);
        EntityTypes.put("snake", Snake.class);
        EntityTypes.put("wolf", Wolf.class);
        EntityTypes.put("plant", Plant.class);

    }


    public List<Entity> createAnimalList() throws IOException {
        List<Entity> entities =Collections.synchronizedList(new ArrayList<>());

        JsonParser jsonParser = new JsonParser();
        Random random = new Random();

        for (Map.Entry<String, Class<? extends Entity>> entry : EntityTypes.entrySet()) {
            Entity sampleEntity = null;
            Class<? extends Entity> animalClass = entry.getValue();
            try {
                sampleEntity = animalClass.getDeclaredConstructor().newInstance();
                jsonParser.deserializeJson(sampleEntity);
            } catch (Exception exception) {
                System.out.println(sampleEntity + " cannot be inserted!");
                continue;
            }
            if (sampleEntity != null) {
                int amountOfAnimalsPerField = random.nextInt(sampleEntity.getMaxAmountOnOneField());
                for (int i = 0; i < amountOfAnimalsPerField; i++) {
                    try {
                        entities.add(jsonParser.deserializeJson(animalClass.getDeclaredConstructor().newInstance()));
                    } catch (IOException exception) {
                        System.out.println("No such animal in JSON " + entry.getKey());
                    } catch (Exception exception) {
                        System.out.println("Error while creating this type " + entry.getKey());
                    }

                }
            }
        }

        return entities;
    }

    @Override
    public String toString() {
        return "AnimalCreator{" +
                "animalTypes=" + EntityTypes +
                '}';
    }
}
