package producer_and_consumer;

import lock.BoundedBuffer;

public class Consumer implements Runnable {
    private BoundedBuffer buffer;
    private final int number;
    private int times = 5;

    public Consumer(BoundedBuffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {

        for(int i = 0; i < times; i++) {
            String message;
            try { message = buffer.take().toString(); } catch (Exception exc) {message = "";}
            System.out.println("Consumer " + number + " took: \"" + message + "\"");
        }

    }
}