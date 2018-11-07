package waiter;

import java.util.Random;

import static java.lang.Math.abs;

public class RestaurantCustomer implements Runnable {
    private int id;
    private int pairId;
    private Waiter waiter;
    private static Random random = new Random();

    public RestaurantCustomer(int id, int pairId, Waiter waiter) {
        this.id = id;
        this.pairId = pairId;
        this.waiter = waiter;
    }

    public int getId() { return id; }

    public int getPairId() { return pairId; }

    public void run() {
        int round = 0;
        while (true) {
            try {
                //Thread.sleep(randSleep());
                //System.out.println("\u001B[33m" + this + " WANTS A TABLE!" + "\u001B[0m");
                waiter.takeTable(this);
                //System.out.println("\u001B[32m" + this + " HAS A TABLE!" + "\u001B[0m");
                //Thread.sleep(randSleep());
                //System.out.println("\u001B[36m" + this + " FINISHED EATING!" + "\u001B[0m");
                waiter.leaveTable(this);
                System.out.println("\u001B[36m" + this + " FINISHED EATING!" + "\u001B[0m");
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            //round++;
        }
    }

    @Override
    public String toString() {
        return "Customer " + id;
    }

    private static int randSleep() {
        return abs(random.nextInt()) % 10000;
    }
}
