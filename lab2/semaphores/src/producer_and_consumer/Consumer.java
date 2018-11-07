package producer_and_consumer;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int times = 5;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0; i < times; i++) {
            String message = buffer.take();
            System.out.println(message);
        }

    }
}