package producer_and_consumer;
import semaphore.BinarySemaphore;

public class Buffer {
    private String buffer = null;
    private BinarySemaphore binarySemaphore;

    public Buffer() {
        binarySemaphore = new BinarySemaphore();
    }
    void put(String message) {
        binarySemaphore.P();
        buffer = message;
    }
    String take() {
        binarySemaphore.V();
        return buffer;
    }
}
