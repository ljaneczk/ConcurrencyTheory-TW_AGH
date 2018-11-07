package semaphore;

public class BinarySemaphore {
    private boolean free = true;
    public synchronized void P() {       // proberen - leave semaphore
        while (!free) {
            try {
                wait();
            }
            catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
        free = false;
        notifyAll();
    }
    public synchronized void V() {       // verhogen - take semaphore
        while (free) {
            try {
                wait();
            }
            catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
        free = true;
        notifyAll();
    }
}
