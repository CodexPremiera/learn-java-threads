package lesson.making_threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FortuneTeller {
    /* FIELDS */
    private static List<Question> questions = Arrays.asList(
            new Question(Question.Difficulty.EASY, "Am I a good coder?"),
            new Question(Question.Difficulty.MEDIUM, "Will I be able to finish this course?"),
            new Question(Question.Difficulty.EASY, "Will it rain tomorrow?"),
            new Question(Question.Difficulty.EASY, "Will it snow today?"),
            new Question(Question.Difficulty.HARD, "Are you really all-knowing?"),
            new Question(Question.Difficulty.HARD, "Do I have any hidden talents?"),
            new Question(Question.Difficulty.HARD, "Will I live to be greater than 100 years old?"),
            new Question(Question.Difficulty.MEDIUM, "Will I be rich one day?"),
            new Question(Question.Difficulty.MEDIUM, "Should I clean my room?")
    );
    private static final List<Thread> threads = new ArrayList<>();


    /* MAIN METHOD */
    public static void main(String[] args) {

        // WAYS TO MAKE A THREAD: Java currently supports 3 ways
        startThreadClass();
        startRunnableThread();
        startLambdaThread();


        // SUPERVISING THREADS
        superviseThreads();

    } // END OF MAIN


    /* HELPER METHODS */
    /**
     * EXTENDING THREAD
     *
     * <p> Threads can be made using the Thread class. The extending class should override
     * the run() method, which should contain the instruction which the thead would execute.
     *
     * <p> To make a thread this way, just call the constructor of the class.
     * */
    private static void startThreadClass() {
        System.out.println("""
                
                -----
                EXTENDING THREADS:
                """);

        questions.forEach(question -> {
            CrystalBallThread c = new CrystalBallThread(question);
            threads.add(c);
            c.start();
        });

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        });

        threads.clear();
    }

    /**
     * IMPLEMENTING RUNNABLE
     *
     * <p> Threads can be made using the Runnable interface. This is helpful since most of the time,
     * we only want the threading capability, not the Thread class as a whole. Moreover, we could
     * only extend one superclass in Java. Thus, by not extending the Thread class, our class could
     * extend another, often more important class.
     *
     * <p> To make a thread this way, pass the runnable class to the thread constructor.
     * */
    private static void startRunnableThread() {
        System.out.println("""
                
                -----
                IMPLEMENTING RUNNABLE:
                """);

        questions.forEach(question -> {
            CrystalBallRunnable c = new CrystalBallRunnable(question);
            Thread thread = new Thread(c);
            threads.add(thread);
            thread.start();
        });

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        });

        threads.clear();
    }


    /**
     * LAMBDA EXPRESSION
     *
     * <p> Threads can be made using lambda expressions (similar to arrow functions in JavaScript).
     * To make a new thread this way, pass an anonymous method in Thread constructor.
     * The anonymous method will then become the run method of the new thread.
     * */
    private static void startLambdaThread() {
        System.out.println("""
                
                -----
                LAMBDA EXPRESSION:
                """);

        CrystalBall c = new CrystalBall();
        questions.forEach(question ->
                new Thread(() -> c.ask(question)).start()
        );

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        });

        threads.clear();
    }


    /**
     * SUPERVISING THREADS
     *
     * <p> Other threads can monitor the status of other threads. Known as Supervisor threads,
     * these threads are extremely helpful in multithreaded programs and could help in things
     * like debugging.
     *
     * <p> Often, supervisor threads are made using methods. These methods must take in a thread
     * (or a collection of threads) that the supervisor must monitor.
     * */
    private static void superviseThreads() {
        System.out.println("""
                
                -----
                SUPERVISING THREADS:
                """);

        CrystalBall c = new CrystalBall();
        List<Thread> threads = questions.stream()
                .map(question -> new Thread( () -> c.ask(question) ))
                .toList();
        Thread supervisor = createSupervisor(threads);

        threads.forEach(Thread::start);
        supervisor.start();
    }

    private static Thread createSupervisor(List<Thread> threads) {
        Thread supervisor = new Thread(() -> {
            while (true) {
                List<String> activeThreads = threads.stream()
                        .filter(Thread::isAlive)
                        .map(Thread::getName)
                        .toList();

                System.out.println(Thread.currentThread().getName() + " - Currently running threads: " + activeThreads);

                if (activeThreads.isEmpty())
                    break;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    System.out.println(exception);
                }
            }

            System.out.println(Thread.currentThread().getName() + " - All threads completed!");
        });

        supervisor.setName("Supervisor");
        return supervisor;
    }
}