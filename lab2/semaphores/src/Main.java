import producer_and_consumer.Buffer;
import producer_and_consumer.Consumer;
import producer_and_consumer.Producer;
import shopping.Shop;
import shopping.ShoppingCart;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        //runMonitors();
        runShopping();
    }

    private static void runShopping() {
        int shoppingCartsNumber = 3, clients = 5;
        ShoppingCart[] shoppingCarts = new ShoppingCart[clients];
        Shop shop = new Shop(shoppingCartsNumber);
        Random random = new Random();
        for (int client = 0; client < clients; client++) {
            final int clientID = client;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    shoppingCarts[clientID] = shop.takeShoppingCart(clientID);
                    System.out.println("Client " + clientID + " takes " + shoppingCarts[clientID] + ".");
                    int seconds = (abs(random.nextInt()) % 8);
                    System.out.println("Client " + clientID + " shops " + seconds + " seconds.");
                    try { sleep(seconds * 1000); } catch (Exception exc) { exc.printStackTrace(); }
                    System.out.println("Client " + clientID + " puts away " + shoppingCarts[clientID] + ".");
                    shop.putAwayShoppingCart(shoppingCarts[clientID], clientID);
                }
            }).start();
        }
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
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
