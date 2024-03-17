package lesson.thread_synchronization;

import java.util.HashMap;
import java.util.Map;

public class CakeMaker {

    /* INSTANCE VARIABLES */
    private final Map<String, Boolean> toolsInUse;

    /* CONSTRUCTOR */
    public CakeMaker() {
        toolsInUse = new HashMap<>();
        toolsInUse.put("oven", false);
        toolsInUse.put("whisk", false);
        toolsInUse.put("mixing bowl", false);
    }


    /* MAIN METHOD */
    public static void main(String[] args) {

        CakeMaker cakeMaker = new CakeMaker();
        try {

            Thread preheatOven = new Thread(cakeMaker::preheatOven, "PREHEAT OVEN");
            Thread mixDryIngredients = new Thread(cakeMaker::mixDryIngredients, "MIX DRY INGREDIENTS");
            Thread mixWetIngredients = new Thread(cakeMaker::mixWetIngredients, "MIX WET INGREDIENTS");
            Thread combineIngredients = new Thread(cakeMaker::combineIngredients, "COMBINE INGREDIENTS");
            Thread bakeCake = new Thread(cakeMaker::bakeCake, "BAKE CAKE");
            Thread makeFrosting = new Thread(cakeMaker::makeFrosting, "MAKE FROSTING");
            Thread frostCake = new Thread(cakeMaker::frostCake, "FROST CAKE");

            // Add logic to start and initial.join threads here!
            // There should be a .start() and .initial.join() method call for each thread, seven in total.
            preheatOven.start();
            mixDryIngredients.start();
            mixWetIngredients.start();
            makeFrosting.start();

            mixDryIngredients.join();
            mixWetIngredients.join();
            combineIngredients.start();

            preheatOven.join();
            combineIngredients.join();
            makeFrosting.join();

            bakeCake.start();
            bakeCake.join();
            frostCake.start();
            frostCake.join();


            System.out.println("Cake complete!");
        } catch (Exception e) {
            System.out.println(e);
        }
    } // END OF MAIN


    /* HELPER METHODS */

    /**
     * THREAD WAIT
     *
     * <p> The wait() method pauses the execution of a thread until henceforth notified.
     * In this example, the useEquipment() would wait for the equipment in toolsInUse
     * until they are not in use.
     * */
    private void useEquipment(String equipment) throws InterruptedException {
        synchronized (this) {
            while (toolsInUse.get(equipment)) {
                printTask("Waiting for the " + equipment + "...");
                wait();
            }
            toolsInUse.put(equipment, true);
            printTask("Using " + equipment + "...");
        }
    }

    /**
     * THREAD NOTIFY ALL
     *
     * <p> The notifyAll() method informs the waiting threads that they could now use the
     * resource they have been waiting for and thus continue their execution. In the example,
     * once the equipment is released (meaning done being used), all threads waiting for the
     * equipment would be notified.
     * */
    private void releaseEquipment(String equipment) {
        synchronized (this) {
            printTask("Releasing mixing bowl!");
            toolsInUse.put(equipment, false);
            notifyAll();
        }
    }


    /* INSTANCE METHODS */

    public void preheatOven() {
        try {
            printTask("Oven pre-heating...");

            useEquipment("oven");
            Thread.sleep(10000);
            releaseEquipment("oven");
            printTask("Done!");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void mixDryIngredients() {
        try {
            printTask("Mixing dry ingredients...");

            // Use mixing bowl
            useEquipment("mixing bowl");

            // Step Tasks
            Thread.sleep(200);
            printTask("Adding cake flour");
            Thread.sleep(200);
            printTask("Adding salt");
            Thread.sleep(200);
            printTask("Adding baking powder");
            Thread.sleep(200);
            printTask("Adding baking soda");
            Thread.sleep(200);
            useEquipment("whisk");
            printTask("Mixing...");
            Thread.sleep(200);
            releaseEquipment("whisk");

            // release mixing bowl
            releaseEquipment("mixing bowl");

            printTask("Done!");

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    };

    public void mixWetIngredients() {
        try {
            printTask("Mixing wet ingredients...");

            // use mixing bowl
            useEquipment("mixing bowl");

            // step task
            Thread.sleep(1000);
            printTask("Adding butter...");
            Thread.sleep(500);
            printTask("Adding eggs...");
            Thread.sleep(500);
            printTask("Adding vanilla extract...");
            Thread.sleep(500);
            printTask("Adding buttermilk...");
            Thread.sleep(500);
            useEquipment("whisk");
            printTask("Mixing...");
            Thread.sleep(1500);
            releaseEquipment("whisk");

            // release mixing bowl
            releaseEquipment("mixing bowl");

            printTask("Done!");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    };

    public void combineIngredients() {
        try {
            printTask("Combining ingredients...");

            useEquipment("mixing bowl");


            Thread.sleep(1000);
            printTask("Adding dry mix to wet mix...");
            Thread.sleep(1500);
            useEquipment("whisk");
            printTask("Mixing...");
            Thread.sleep(1500);
            releaseEquipment("whisk");
            releaseEquipment("mixing bowl");
            printTask("Done!");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    };

    public void bakeCake() {
        try {
            printTask("Baking cake...");
            useEquipment("mixing bowl");
            Thread.sleep(10000);
            releaseEquipment("mixing bowl");
            printTask("Done!");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void makeFrosting() {
        try {
            printTask("Making frosting...");
            useEquipment("mixing bowl");
            useEquipment("whisk");
            printTask("Adding butter...");
            Thread.sleep(200);
            printTask("Adding milk...");
            Thread.sleep(200);
            printTask("Adding sugar...");
            Thread.sleep(200);
            printTask("Adding vanilla extract...");
            Thread.sleep(200);
            printTask("Adding salt...");
            Thread.sleep(200);
            releaseEquipment("mixing bowl");
            releaseEquipment("whisk");
            printTask("Done!");

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void frostCake() {
        try {
            printTask("Frosting cake...");
            Thread.sleep(1500);
            printTask("Done!");

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private void printTask(String task) {
        System.out.println(Thread.currentThread().getName() + " " + " - " + task);
    }
}