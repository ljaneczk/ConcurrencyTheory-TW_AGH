public class MyThreadDecrement extends Thread {
    Counter counter;
    MyThreadDecrement(Counter counter) {
        this.counter = counter;
    }
    public void run() {
        for (int i = 0; i < 100000000; i++)
            counter.decrement();
    }
}
