package producer_and_consumer;

import lock.BoundedBuffer;

public class Producer implements Runnable {
    private BoundedBuffer buffer;
    private final int number;
    private int times = 5;

    public Producer(BoundedBuffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {

        for(int i = 0; i < times; i++) {
            try {
                buffer.put("Producer " + number + ": message " + i);
            }
            catch (Exception exc) {
                System.err.println("Error with putting message.");
            }
        }

    }
}
