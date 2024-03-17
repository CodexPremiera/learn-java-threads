package project;

public class BingoMain {
    public static void main(String[] args) {
        Thread game = new Thread(new BingoGame());
        game.start();
    }
}
