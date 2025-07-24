package erik;

import erik.animals.Entity;
import erik.utility.EatingFunctionality;
import erik.utility.IslandActions;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {


    public static void main(String[] args) throws Exception {

        Object lock = new Object();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        IslandActions islandActions = new IslandActions(lock);
        EatingFunctionality eatingFunctionality = new EatingFunctionality(lock);
        Future<Map<Field, List<Entity>>> island = executorService.submit(islandActions);
        Map<Field, List<Entity>> islandMap = island.get();

        while (islandActions.hasAnimals(islandMap)) {

            System.out.println("Start showing the island!");
            System.out.flush();

            Future<?> showData = executorService.submit(() -> islandActions.showAnimals(islandMap));
            showData.get();
            System.out.println("Ending showing the island!");
            System.out.flush();
            Future<?> moveData = executorService.submit(() -> islandActions.move(islandMap));
            moveData.get();


            Future<?> eatData = executorService.submit(() -> eatingFunctionality.consume(islandMap));
            eatData.get();


            System.out.println("_______End of cycle!_______");
        }

        System.out.println("All animals are dead!");
        executorService.shutdown();

    }

}