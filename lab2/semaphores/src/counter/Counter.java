package counter;

public class Counter {
    private long value = 0;
    synchronized void increment()  {
        value++;
    }
    synchronized void decrement() {
        value--;
    }
    public String toString() {
        return Long.toString(value);
    }
}
