package erik.utility;

import erik.Field;
import erik.animals.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ReproduceFunctionality {
    public void reproduce(Future<Map<Field, List<Entity>>> island){

        try {
            Map<Field,List<Entity>> futureIsland = island.get();
            List<Map.Entry<Field, List<Entity>>> entriesToProcess = new ArrayList<>(futureIsland.entrySet());
            for (Map.Entry<Field, List<Entity>> entry : entriesToProcess) {
                List<Entity> entityList = entry.getValue();
                List<Entity> animalsOnCurrentField = new ArrayList<>(entityList);

                for (Entity entity : entityList){

                }
            }

        } catch (ExecutionException e) {
            System.out.println("Exception thrown during invoking consume method from EatingFunctionality " + e.getCause());
        } catch (InterruptedException e) {
            System.out.println(e.getCause() + " Consuming func. in Eating Class");
        }
    }

}
