package erik;

import erik.animals.Animal;
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
        Future<Map<Field, List<Animal>>> island = executorService.submit(islandActions);

        executorService.close();
        scanner = new Scanner(System.in);
        System.out.println("Would you like to see visual representation of animals? \n " + " Y/N \n");
        String input = scanner.nextLine();
        if (input.equals("Y")) {
            islandActions.showAnimals(island);
        } else {
            return;
        }
        System.out.println("_________________________________");

        islandActions.move(island);
        islandActions.showAnimals(island);


        EatingFunctionality eatingFunctionality = new EatingFunctionality();


    }
}