package erik.utility;
import erik.Field;
import erik.animals.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class EatingFunctionality {
    private final Object lock;

    public EatingFunctionality(Object lock) {
        this.lock = lock;
    }

    public void consume(Map<Field, List<Entity>> island) {

        synchronized (lock) {
            Map<Field, List<Entity>> futureIsland = island;
            List<Map.Entry<Field, List<Entity>>> entriesToProcess = Collections.synchronizedList(new ArrayList<>(futureIsland.entrySet()));
            for (Map.Entry<Field, List<Entity>> entry : entriesToProcess) {
                List<Entity> entityList = entry.getValue();
                List<Entity> animalsOnCurrentField =Collections.synchronizedList(new ArrayList<>(entityList));

                for (Entity entity : entityList) {
                    int victim = entity.eat(entity, animalsOnCurrentField);
                    if (victim == 0 || victim == animalsOnCurrentField.indexOf(entity)) {
                        continue;
                    }
                    System.out.println(animalsOnCurrentField.get(victim).getType() + " was eaten by " + entity.getType());
                    animalsOnCurrentField.remove(victim);
                }
            }


        }
    }

}
