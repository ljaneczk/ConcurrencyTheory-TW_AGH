public class Producer implements Runnable {
    private Buffer buffer;
    private int ILOSC = 5;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0; i < ILOSC; i++) {
            buffer.put("message " + i);
        }

    }
}
