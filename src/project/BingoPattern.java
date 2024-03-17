package project;

import java.util.ArrayList;
import java.util.List;

public abstract class BingoPattern implements Runnable {
    List<BingoChecker> checkers;
    BingoCard card;

    public BingoPattern(BingoCard card) {
        this.card = card;
        checkers = new ArrayList<>();
    }

    @Override
    public void run() {
        // make the checker threads
        List<Thread> threads = checkers.stream()
                .map(Thread::new)
                .toList();

        // run the checker threads
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                threads.forEach(Thread::interrupt);
                System.out.println("Card " + card.id + " loses");
            }
        });

        // set bingo to true
        BingoGame.isBingo = true;
        System.out.println("Card " + card.id + " completes " + getClass().toString());
        System.out.println(card);
    }
}