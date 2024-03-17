package lesson.making_threads;

import java.util.Random;

public class CrystalBallRunnable implements Runnable {
    private final Question question;

    // CONSTRUCTORS
    public CrystalBallRunnable(Question question) {
        this.question = question;
    }


    // INSTANCE METHODS
    @Override
    public void run() {
        ask(this.question);
    }

    public void ask(Question question) {
        System.out.println("Good question! You asked: " + question.getQuestion());
        this.think(question);
        System.out.println("Answer: " + this.answer());
    }


    // HELPER METHODS
    private void think(Question question) {
        System.out.println("Hmm... Thinking");
        try {
            Thread.sleep(this.getSleepTimeInMs(question.getDifficulty()));
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Done!");
    }

    private String answer() {
        String[] answers = {
                "Signs point to yes!",
                "Certainly!",
                "No opinion",
                "Answer is a little cloudy. Try again.",
                "Surely.",
                "No.",
                "Signs point to no.",
                "It could very well be!"
        };
        return answers[new Random().nextInt(answers.length)];
    }

    private int getSleepTimeInMs(Question.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 1000;
            case MEDIUM -> 2000;
            case HARD -> 3000;
            default -> 500;
        };
    }
}