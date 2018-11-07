package semaphore;

public class SuperCountingSemaphore {
    private CountingSemaphore semaphore;
    private CountingSemaphore upSemaphore;
    public SuperCountingSemaphore(int counter, int maximum) {
        semaphore = new CountingSemaphore(counter);
        upSemaphore = new CountingSemaphore(maximum-counter);
    }

    public SuperCountingSemaphore(int counter) {
        this(counter, counter);
    }

    public synchronized void V() {              // verhogen - take semaphore
        semaphore.V();
        upSemaphore.P();
    }

    public synchronized void P() {              // proberen - leave semaphore
        upSemaphore.V();
        semaphore.P();
    }
}
