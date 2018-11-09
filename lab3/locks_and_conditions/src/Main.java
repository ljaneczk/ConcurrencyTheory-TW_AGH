import lock.BoundedBuffer;
import printer.PrintersCustomer;
import printer.PrintersMonitor;
import producer_and_consumer.Consumer;
import producer_and_consumer.Producer;
import waiter.RestaurantCustomer;
import waiter.Waiter;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        //runMonitors();
        //runPrinters();
        runWaiter();
    }

    private static void runMonitors() {
        int s1 = 5;
        BoundedBuffer buffer = new BoundedBuffer();
        Consumer[] consumers = new Consumer[s1];
        Producer[] producers = new Producer[s1];
        for (int i = 0; i < s1; i++) {
            consumers[i] = new Consumer(buffer, i);
            producers[i] = new Producer(buffer, i);
            Thread thread1 = new Thread(consumers[i]);
            thread1.start();
            Thread thread2 = new Thread(producers[i]);
            thread2.start();
            /*try {
                thread1.join();
                thread2.join();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }*/
        }
    }

    private static void runPrinters() {
        int N = 5, M = 3;
        PrintersMonitor printersMonitor = new PrintersMonitor(M);
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(new PrintersCustomer(i, printersMonitor));
            threads[i].start();
            /*try {
                thread.join();
            }
            catch (InterruptedException exc) {
                System.err.println("ERROR");
            }*/
        }
    }

    private static void runWaiter() {
        int N = 3;
        Waiter waiter = new Waiter(N);
        Map<RestaurantCustomer, RestaurantCustomer> pairs = new HashMap<>();
        RestaurantCustomer[] customers = new RestaurantCustomer[2*N];
        for (int i = 0; i < N; i++) {
            customers[i] = new RestaurantCustomer(i, i, waiter);
            customers[N+i] = new RestaurantCustomer(N+i, i, waiter);
            pairs.put(customers[i], customers[N+i]);
            pairs.put(customers[N+i], customers[i]);
        }
        waiter.setPairs(pairs);
        for (int i = 0; i < N; i++) {
            Thread thread1 = new Thread(customers[i]);
            Thread thread2 = new Thread(customers[N+i]);
            thread1.start();
            thread2.start();
        }
    }
}
