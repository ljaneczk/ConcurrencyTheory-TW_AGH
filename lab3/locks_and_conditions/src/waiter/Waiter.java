package waiter;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private RestaurantTable table;
    private int numberOfCustomers;
    private int numberOfPairs;
    private Map<RestaurantCustomer, RestaurantCustomer> pairs;
    private boolean[] hasArrived;
    private boolean[] hasTable;
    private ReentrantLock[] pairLocks;
    private Condition[] pairConditions;
    private ReentrantLock firstInQueueLock;
    private ReentrantLock firsSeatLock;
    private ReentrantLock secondSeatLock;
    private ReentrantLock[] locks;

    public Waiter(int numberOfPairs) {
        table = new RestaurantTable();
        this.numberOfPairs = numberOfPairs;
        this.numberOfCustomers = 2 * numberOfPairs;
        this.hasArrived = new boolean[numberOfCustomers];
        this.hasTable = new boolean[numberOfCustomers];
        for (int i = 0; i < numberOfCustomers; i++)
            this.hasArrived[i] = this.hasTable[i] = false;
        this.pairLocks = new ReentrantLock[numberOfPairs];
        this.pairConditions = new Condition[numberOfPairs];
        for (int i = 0; i < numberOfPairs; i++) {
            pairLocks[i] = new ReentrantLock();
            pairConditions[i] = pairLocks[i].newCondition();
        }
        this.firstInQueueLock = new ReentrantLock();
        this.firsSeatLock = new ReentrantLock();
        this.secondSeatLock = new ReentrantLock();
        this.locks = new ReentrantLock[3];
        this.locks[0] = this.firstInQueueLock;
        this.locks[1] = this.firsSeatLock;
        this.locks[2] = this.secondSeatLock;
    }

    public void setPairs(Map<RestaurantCustomer, RestaurantCustomer> pairs) {
        this.pairs = pairs;
    }

    void takeTable(RestaurantCustomer customer) throws InterruptedException {
        hasArrived[customer.getId()] = true;
        if (!hasArrived[pairs.get(customer).getId()]) {
            int pairId = customer.getPairId();
            pairLocks[pairId].lock();
            while (!hasArrived[pairs.get(customer).getId()])    // Partner must arrive
                pairConditions[pairId].await();
            firstInQueueLock.lock();                // First in queue - waiting for free table
            firsSeatLock.lock();                 // Wait for place for first person
            firsSeatLock.unlock();
            secondSeatLock.lock();                // Wait for place for second person
            secondSeatLock.unlock();
            hasTable[customer.getId()] = true;      // Take table
            hasArrived[customer.getId()] = false;
            pairConditions[pairId].signal();        // Inform partner about free table
            firsSeatLock.lock();                 // Take seat
            pairLocks[pairId].unlock();
        }
        else {
            int pairId = customer.getPairId();
            pairLocks[pairId].lock();
            pairConditions[pairId].signal();                    // Inform partner about arrival
            pairConditions[pairId].await();
            hasTable[customer.getId()] = true;                  // Take table
            hasArrived[customer.getId()] = false;
            secondSeatLock.lock();                            // Take seat
            pairLocks[pairId].unlock();
        }
    }

    void leaveTable(RestaurantCustomer customer) {
        hasTable[customer.getId()] = false;
        for (ReentrantLock lock : locks)
            if (lock.isHeldByCurrentThread())
                lock.unlock();
    }
}
