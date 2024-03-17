package project;

public class BingoRowChecker extends BingoChecker {
    int row;
    public BingoRowChecker(BingoCard card, int row) {
        super(card);
        this.row = row;
    }

    @Override
    public void run() {
        for (int col = 0; col < 5; col++) {
            int num = card.numbers[row][col];

            while (!BingoGame.drawnNumbers[num]) {
                try {
                    synchronized (BingoGame.drawnNumbers) {
                        BingoGame.drawnNumbers.wait();
                    }
                } catch (InterruptedException ignored) {}
            }
        }

        //BingoGame.drawnNumbers.notifyAll();
        System.out.println("Card #" + card.id + " (row" + row + ") done:"  + "\n" + card);
        BingoGame.isBingo = true;
    }
}