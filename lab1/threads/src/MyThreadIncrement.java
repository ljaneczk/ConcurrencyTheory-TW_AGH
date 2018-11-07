public class MyThreadIncrement extends Thread {
    Counter counter;
    MyThreadIncrement(Counter counter) {
        this.counter = counter;
    }
    public void run() {
        for (int i = 0; i < 100000000; i++)
            counter.increment();
    }
}
