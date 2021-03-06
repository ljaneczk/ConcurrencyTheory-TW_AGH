package task2.unfair;

import task2.common.Data;
import task2.common.MyRandom;

public class UnfairConsumer implements Runnable {
    private MyRandom random;
    private UnfairBuffer buffer;
    private int id;

    public UnfairConsumer(MyRandom random, UnfairBuffer buffer, int id) {
        this.random = random;
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        int round = 0; boolean shouldContinue = true;
        while (shouldContinue) {
            //try { Thread.sleep(random.getMillisecondsToSleep()); } catch (InterruptedException exc) { exc.printStackTrace(); }
            int numberOfElements = random.getNumberOfElements();
            //System.out.println(this + " wants to receive " + numberOfElements + " element" + ((numberOfElements > 1) ? "s." : "."));
            Data[] dataToGet = buffer.get(numberOfElements);
            //System.out.println(this + " received " + dataToGet.length + " element" + ((dataToGet.length > 1) ? "s." : "."));
            round++;
            //if (round >= 1) shouldContinue = false;
        }
    }

    @Override
    public String toString() {
        return "Consumer " + id;
    }
}
