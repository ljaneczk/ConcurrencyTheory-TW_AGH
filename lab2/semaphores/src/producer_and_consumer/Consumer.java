package producer_and_consumer;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int times;

    public Consumer(Buffer buffer, int times) { this.buffer = buffer; this.times = times; }
    public Consumer(Buffer buffer) { this(buffer, 5); }

    public void run() {

        for(int i = 0; i < times; i++) {
            String message = buffer.take();
            System.out.println(message);
        }

    }
}