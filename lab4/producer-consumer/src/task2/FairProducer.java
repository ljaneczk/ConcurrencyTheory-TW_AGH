package task2;

import task2.common.Data;
import task2.common.MyRandom;

public class FairProducer implements Runnable {
    private MyRandom random;
    private FairBuffer buffer;
    private int id;

    public FairProducer(MyRandom random, FairBuffer buffer, int id) {
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
            Data[] dataToPut = new Data[numberOfElements];
            for (int i = 0; i < numberOfElements; i++)
                dataToPut[i] = new Data();
            //System.out.println(this + " wants to put " + dataToPut.length + " element" + ((dataToPut.length > 1) ? "s." : "."));
            buffer.put(numberOfElements, dataToPut);
            //System.out.println(this + " put " + dataToPut.length + " element" + ((dataToPut.length > 1) ? "s." : "."));
            round++;
            //if (round >= 1) shouldContinue = false;
        }
    }

    @Override
    public String toString() {
        return "Producer " + id;
    }
}
