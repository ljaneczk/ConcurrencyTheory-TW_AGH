package task2.common;

import java.util.Random;

import static java.lang.Math.abs;

public class MyRandom extends Random {
    private int M;
    public MyRandom(int M) { assert M > 0; this.M = M; }

    public int getNumberOfElements() {
        return abs(this.nextInt()) % (M-1) + 1;
    }

    public int getMillisecondsToSleep() { return abs(this.nextInt()) % 10000; }
}
