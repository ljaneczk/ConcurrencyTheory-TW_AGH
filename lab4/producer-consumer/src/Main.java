import task1.*;
import task2.*;
import task2.common.MyRandom;

public class Main {
    public static void main(String[] args) {
        runTask1();
        runTask2();
    }

    private static void runTask1() {
        int n = 10, p = 7; Data[] data = new Data[n];
        for (int i = 0; i < n; i++)
            data[i] = new Data(i);
        PipeBuffer pipeBuffer = new PipeBuffer(data);
        PipeWorker[] workers = new PipeWorker[p]; Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            workers[i] = new PipeWorker(i, i-1, pipeBuffer);
            threads[i] = new Thread(workers[i]);
        }
        for (int i = 0; i < p; i++)
            threads[i].start();
    }

    private static void runTask2() {
        //runTask2Unfair();
        runTask2Fair();
    }

    private static void runTask2Unfair() {
        int n0 = 100, n1 = 100, M = 10000;
        MyRandom random = new MyRandom(M);
        UnfairBuffer buffer = new UnfairBuffer(M);
        Thread[] threads = new Thread[n0+n1];
        for (int i = 0; i < n0; i++)
            threads[i] = new Thread(new UnfairProducer(random, buffer, i));
        for (int i = 0; i < n1; i++)
            threads[n0+i] = new Thread(new UnfairConsumer(random, buffer, i));
        for (Thread thread : threads)
            thread.start();
    }

    private static void runTask2Fair() {
        int n0 = 100, n1 = 100, M = 1000;
        MyRandom random = new MyRandom(M);
        FairBuffer buffer = new FairBuffer(M);
        Thread[] threads = new Thread[n0+n1];
        for (int i = 0; i < n0; i++)
            threads[i] = new Thread(new FairProducer(random, buffer, i));
        for (int i = 0; i < n1; i++)
            threads[n0+i] = new Thread(new FairConsumer(random, buffer, i));
        for (Thread thread : threads)
            thread.start();
    }
}
