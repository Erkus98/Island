package Erik;

import Erik.animals.Animal;
import Erik.utility.AnimalCreator;
import Erik.utility.IslandActions;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {
    static Scanner scanner;

    public static void main(String[] args) throws Exception {


        AnimalCreator animalCreator = new AnimalCreator();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        IslandActions islandActions = new IslandActions();
        Future<Map<Field, List<Animal>>> island = executorService.submit(islandActions);

        executorService.close();
        scanner = new Scanner(System.in);
        System.out.println("Would you like to see visual representation of animals? \n " + " Y/N \n");
        String input = scanner.nextLine();
        switch (input) {
            case "Y" -> islandActions.showAnimals(island);
            default -> {
                return;
            }
        }
        System.out.println("_________________________________");

        islandActions.move(island);
        islandActions.showAnimals(island);


    }
}