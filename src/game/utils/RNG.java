package game.utils;

import java.util.Random;


public class RNG {
    private static Random rand = new Random();

    public static int getRandomIntBetween(int lowerBound, int upperBound) {
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandInt(int upperBound) {
        return rand.nextInt(upperBound);
    }

    public static int numberOrRandom(int number, int upperBound) {
        int choice = rand.nextInt(2);

        return choice == 1 ? getRandomIntBetween(1, upperBound) : number;
    }
}
