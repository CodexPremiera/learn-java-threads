package project;

public class BingoColChecker extends BingoChecker {
    int col;
    public BingoColChecker(BingoCard card, int col) {
        super(card);
        this.col = col;
    }

    @Override
    public void run() {
        for (int row = 0; row < 5; row++) {
            int num = card.numbers[row][col];

            while (!BingoGame.drawnNumbers[num]) {
                try {
                    synchronized (BingoGame.drawnNumbers) {
                        BingoGame.drawnNumbers.wait();
                    }
                } catch (InterruptedException ignored) {}
            }
        }

        BingoGame.isBingo = true;
        System.out.println("Card #" + card.id + " (col" + col + ") done:" + "\n" + card);
    }
}