package semaphore;

public class CountingSemaphore {
    private int counter;

    public CountingSemaphore() { this.counter = 1; }
    public CountingSemaphore(int counter) {
        this.counter = counter;
    }

    public synchronized void P() {       // proberen - leave semaphore
        while (counter <= 0) {
            try {
                wait();
            }
            catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
        counter--;
    }

    public synchronized void V() {       // verhogen - take semaphore
        counter++;
        notifyAll();
    }
}
