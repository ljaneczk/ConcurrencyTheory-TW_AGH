package waiter;

import java.util.Random;

import static java.lang.Math.abs;

public class RestaurantCustomer implements Runnable {
    private int id;
    private int pairId;
    private Waiter waiter;

    private static final Random random = new Random();
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String END = "\u001B[0m";

    public RestaurantCustomer(int id, int pairId, Waiter waiter) {
        this.id = id;
        this.pairId = pairId;
        this.waiter = waiter;
    }

    public int getId() { return id; }

    public int getPairId() { return pairId; }

    public void run() {
        int round = 0; boolean continueEating = true;
        while (continueEating) {
            try {
                Thread.sleep(randSleep());
                System.out.println(YELLOW + this + " WANTS A TABLE!" + END);
                waiter.takeTable(this);
                System.out.println(GREEN + this + " HAS A TABLE!" + END);
                Thread.sleep(randSleep());
                System.out.println(CYAN + this + " FINISHED EATING!" + END);
                waiter.leaveTable(this);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            if (round > 3)
                continueEating = false;
            round++;
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
