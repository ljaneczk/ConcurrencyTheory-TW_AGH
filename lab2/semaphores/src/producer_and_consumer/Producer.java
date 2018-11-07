package producer_and_consumer;

public class Producer implements Runnable {
    private Buffer buffer;
    private int times = 5;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0; i < times; i++) {
            buffer.put("message " + i);
        }

    }
}
