package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BingoCard {
    int[][] numbers;
    int id;

    // TODO randomize the numbers
    public BingoCard(int id) {
        this.id = id;
        this.numbers = new int[5][5];

        Random random = new Random();
        int min = 1;
        int max = 15;

        for (int col = 0; col < 5; col++) {
            // Add unique numbers to temp col
            ArrayList<Integer> newColumn = new ArrayList<>();

            while (newColumn.size() < 5) {
                int randomNumber = random.nextInt(min, max);

                if (!newColumn.contains(randomNumber))
                    newColumn.add(randomNumber);
            }

            // Add numbers to the current column
            for (int row = 0; row < 5; row++) {
                if (col == 2 && row == 2)
                    continue;

                numbers[row][col] = newColumn.get(row);
            }

            // Increment the min and max
            min += 15;
            max += 15;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("B\tI\tN\tG\tO\n");

        for (int row = 0;row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                sb.append(numbers[row][col]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public void printCard() {
        System.out.println("Card " + this.id);
        System.out.println(this);
    }
}