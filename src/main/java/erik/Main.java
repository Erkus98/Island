package erik;

import erik.animal_actions.AnimalActivities;
import erik.animals.Entity;
import erik.animals.Herbivore;
import erik.utility.EatingFunctionality;
import erik.utility.IslandActions;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {


    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        IslandActions islandActions = new IslandActions();
        EatingFunctionality eatingFunctionality = new EatingFunctionality();
        Future<Map<Field, List<Entity>>> island = executorService.submit(islandActions);
        island.get();

        while (islandActions.hasAnimals(island)){

            System.out.println("Показ острова!");
            System.out.flush();

            Future<?> showData  = executorService.submit(() -> islandActions.showAnimals(island));
            showData.get();
            System.out.println("Конец показа острова!");
            System.out.flush();
            Future<?> moveData = executorService.submit(() -> islandActions.move(island));
            moveData.get();


           Future<?> eatData = executorService.submit(() ->eatingFunctionality.consume(island));
          eatData.get();


            System.out.println("_______Конец цикла!_______");
        }

        System.out.println("All animals are dead!");
        executorService.shutdown();


//        islandActions.showAnimals(island);
//
//        System.out.println("_________________________________");
//
//        islandActions.move(island);
//        islandActions.showAnimals(island);
//
//        eatingFunctionality.consume(island);
//        islandActions.showAnimals(island);
//        executorService.close();






    }

}