public class Consumer implements Runnable {
    private Buffer buffer;
    private int ILOSC = 5;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0; i < ILOSC; i++) {
            String message = buffer.take();
            System.out.println(message);
        }

    }
}