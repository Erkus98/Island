package erik;

import erik.animals.Entity;
import erik.utility.EatingFunctionality;
import erik.utility.IslandActions;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {
    static Scanner scanner;

    public static void main(String[] args) throws Exception {


        ExecutorService executorService = Executors.newFixedThreadPool(3);
        IslandActions islandActions = new IslandActions();
        Future<Map<Field, List<Entity>>> island = executorService.submit(islandActions);

        executorService.close();
        islandActions.showAnimals(island);

        System.out.println("_________________________________");

        islandActions.move(island);
        islandActions.showAnimals(island);


        EatingFunctionality eatingFunctionality = new EatingFunctionality();


    }
}