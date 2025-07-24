package erik.utility;

import erik.Field;
import erik.animals.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ReproduceFunctionality {

    private final EntityCreator entityCreator;

    public ReproduceFunctionality(EntityCreator entityCreator) {
        this.entityCreator = entityCreator;
    }

    public void reproduce(Future<Map<Field, List<Entity>>> island){

        try {
            Map<Field, List<Entity>> futureIsland = island.get();
            List<Map.Entry<Field, List<Entity>>> entriesToProcess = new ArrayList<>(futureIsland.entrySet());

            for (Map.Entry<Field, List<Entity>> entry : entriesToProcess) {
                List<Entity> entityList = entry.getValue();
                List<Entity> copy = new ArrayList<>(entityList);

                for (int i = 0; i < entityList.size() - 1; i++) {
                    Entity current = entityList.get(i);
                    Entity next = entityList.get(i + 1);

                    if (current.getType().equals(next.getType())) {
                        Entity newborn = createNewEntity(current.getType());
                        if (newborn != null) {
                            copy.add(newborn);

                        }
                    }
                }


                futureIsland.put(entry.getKey(), Collections.synchronizedList(copy));
            }

        } catch (ExecutionException e) {
            System.out.println("Exception in reproduce: " + e.getCause());
        } catch (InterruptedException e) {
            System.out.println("Interrupted in reproduce: " + e.getCause());
        }
    }
    private Entity createNewEntity(String type) {
        Class<? extends Entity> clazz = entityCreator.getEntityTypes().get(type);
        if (clazz == null) return null;
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Failed to create new entity for type: " + type);
            return null;
        }
    }

}
