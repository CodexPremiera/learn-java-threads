package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BingoGame implements Runnable {
    /* UTILS */
    final Scanner scanner = new Scanner(System.in);
    final Random random = new Random();


    /* FIELDS */
    List<BingoCard> cards;
    static boolean[] drawnNumbers;
    static boolean isBingo;
    int cardCount;
    int drawnCount;


    /* CONSTRUCTOR */
    private void initializeValues() {
        cards = new ArrayList<>();
        drawnNumbers = new boolean[76];
        drawnNumbers[0] = true;
        drawnCount = 0;
        isBingo = false;

        System.out.print("How many players? ");
        cardCount = scanner.nextInt();
    }


    /* RUN METHOD */
    @Override
    public void run() {
        this.initializeValues();

        for (int i = 0; i < cardCount; i++) {
            BingoCard newCard = new BingoCard(i + 1);
            cards.add(newCard);
            newCard.printCard();
        }


        // TODO create your checker threads per card
        // TODO start all threads
        Thread[] checkerRowThreads = new Thread[cardCount];
        Thread[] checkerColThreads = new Thread[cardCount];
        for (int i = 0; i < cardCount; i++) {
            for (int j = 0; j < 5; j++) {
                checkerRowThreads[i] = new Thread(new BingoRowChecker(cards.get(i), j));
                checkerColThreads[i] = new Thread(new BingoColChecker(cards.get(i), j));

                checkerRowThreads[i].start();
                checkerColThreads[i].start();
            }
        }

        // TODO randomly get number from 1-75 while not bingo
        while (!isBingo && drawnCount < 75) {
            int currentNumber = getRandomNumber();
            System.out.println("New draw: " + currentNumber);

            printDrawnNumbers();
            synchronized (drawnNumbers) {
                drawnNumbers.notifyAll();
            }

            drawnCount++;
        }


        // End checkers
        for (Thread thread : checkerRowThreads)
            thread.interrupt();

        for (Thread thread : checkerColThreads)
            thread.interrupt();

        System.out.println("All numbers have been selected.");

    }


    /* HELPER METHODS */
    private int getRandomNumber() {
        while (true) {
            int randomNumber = random.nextInt(75) + 1;

            if (drawnNumbers[randomNumber])
                continue;

            drawnNumbers[randomNumber] = true;
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {}

            return randomNumber;
        }
    }

    private void printDrawnNumbers() {
        System.out.print("Drawn numbers");
        char[] bingo = new char[] {'B', 'I', 'N', 'G', 'O'};
        int letter = 0;

        for (int i = 0; i <= 75; i++) {
            if ((i - 1) % 15 == 0)
                System.out.print("\n" + "    " + bingo[letter++] + ":  ");

            if (i == 0) continue;

            System.out.printf("%3s ", drawnNumbers[i] ? i : "_");
        }
        System.out.println("\n");
    }

}
