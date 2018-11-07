public class Main {
    public static void main(String[] args) {
        // runCounters();
        runMonitors();
    }

    private static void runMonitors() {
        int s1 = 5;
        Buffer buffer = new Buffer();
        Consumer[] consumers = new Consumer[s1];
        Producer[] producers = new Producer[s1];
        for (int i = 0; i < s1; i++) {
            consumers[i] = new Consumer(buffer);
            producers[i] = new Producer(buffer);
            Thread thread1 = new Thread(consumers[i]);
            thread1.start();
            Thread thread2 = new Thread(producers[i]);
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            }
            catch (Exception ex) {
                System.out.println("UPS");
            }
        }
    }

    private static void runCounters() {
        Counter counter = new Counter();
        MyThreadIncrement myThreadIncrement = new MyThreadIncrement(counter);
        MyThreadDecrement myThreadDecrement = new MyThreadDecrement(counter);
        myThreadIncrement.start();
        myThreadDecrement.start();
        try {
            myThreadIncrement.join();
            myThreadDecrement.join();
        }
        catch (Exception exception) {
            System.out.println("UPS");
        }
        System.out.println(counter);
    }
}
