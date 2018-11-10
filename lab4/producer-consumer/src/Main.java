import task1.*;

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
        Worker[] workers = new Worker[p]; Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            workers[i] = new Worker(i, i-1, pipeBuffer);
            threads[i] = new Thread(workers[i]);
        }
        for (int i = 0; i < p; i++)
            threads[i].start();
    }

    private static void runTask2() {

    }
}
