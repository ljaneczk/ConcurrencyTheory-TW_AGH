package producer_and_consumer;

public class Producer implements Runnable {
    private Buffer buffer;
    private int times;

    public Producer(Buffer buffer, int times) { this.buffer = buffer; this.times = times; }
    public Producer(Buffer buffer) { this(buffer, 5); }

    public void run() {

        for(int i = 0; i < times; i++) {
            buffer.put("message " + i);
        }

    }
}
