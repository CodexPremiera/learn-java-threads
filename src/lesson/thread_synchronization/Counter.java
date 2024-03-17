package lesson.thread_synchronization;

public class Counter {
    /* FIELDS */
    private int count = 0;

    public int getCount() {
        return this.count;
    };

    public void setCount(int count) {
        this.count = count;
    };


    /**
     * SYNCHRONIZED METHODS
     *
     * <p> Synchronizing methods means letting only one thread to have sole access to
     * the method at a time to avoid inconsistencies.
     * */
    public synchronized void increment() {
        this.setCount(this.getCount() + 1);
    }


    /* MAIN METHOD */
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread aIncrementor = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        });

        Thread bIncrementor = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        });

        // Start both threads here
        aIncrementor.start();
        bIncrementor.start();

        // Join both threads here
        aIncrementor.join();
        bIncrementor.join();

        System.out.println("Counter: " + counter.getCount());

    } // END OF MAIN
}