public class Buffer {
    private String buffer = null;
    void put(String message) {
        synchronized (this) {
            while (buffer != null) {
                try {
                    wait();
                }
                catch (InterruptedException exc) {}
            }
            buffer = message;
            notify();
        }
    }
    String take() {
        synchronized (this) {
            while (buffer == null) {
                try {
                    wait();
                }
                catch (InterruptedException exc) {}
            }
            String message = buffer;
            buffer = null;
            notify();
            return message;
        }
    }
}
