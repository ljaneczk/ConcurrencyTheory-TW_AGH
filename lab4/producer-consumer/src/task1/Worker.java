package task1;

import java.util.Random;

import static java.lang.Math.abs;

public class Worker implements Runnable {
    private int id;
    private int prevId;
    private int n;
    private PipeBuffer pipeBuffer;

    private static Random random = new Random();
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String END = "\u001B[0m";

    public Worker(int id, int prevId, PipeBuffer pipeBuffer) {
        this.id = id;
        this.prevId = prevId;
        this.pipeBuffer = pipeBuffer;
        this.n = pipeBuffer.getN();
    }

    public int getId() {
        return id;
    }

    public int getPrevId() {
        return prevId;
    }

    public void run() {
        int velocity = rand(); Data data;
        for (int i = 0; i < n; i++) {
            data = pipeBuffer.getDataToProceed(i, this);
            System.out.println(YELLOW + this + " BEGAN task " + i);
            System.out.println(GREEN + this + " got " + data + END);
            try { Thread.sleep(velocity); } catch (InterruptedException exc) { exc.printStackTrace(); }
            data.incrementIteration();
            System.out.println(CYAN + this + " FINISHED task " + i);
            pipeBuffer.finishProceedingData(i, data, this);
        }
    }

    @Override
    public String toString() {
        return "Worker " + id;
    }

    private static int rand() {
        return abs(random.nextInt()) % 7000;
    }
}
