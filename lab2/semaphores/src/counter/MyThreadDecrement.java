package counter;

import counter.Counter;

public class MyThreadDecrement extends Thread {
    Counter counter;
    public MyThreadDecrement(Counter counter) {
        this.counter = counter;
    }
    public void run() {
        for (int i = 0; i < 100000000; i++)
            counter.decrement();
    }
}
