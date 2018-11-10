package task1;

import javax.management.RuntimeErrorException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PipeBuffer {
    private int n;
    private Data[] buffer;
    private int[] processIds;       // task finished last by
    private ReentrantLock[] locks;
    private Condition[] conditions;

    public PipeBuffer(Data[] data) {
        if (data == null)
            throw new NullPointerException("Cannot create buffer from null data.");
        this.buffer = data;
        this.n = data.length;
        this.processIds = new int[n];
        this.locks = new ReentrantLock[n];
        this.conditions = new Condition[n];
        for (int i = 0; i < n; i++) {
            this.processIds[i] = -1;
            locks[i] = new ReentrantLock();
            conditions[i] = locks[i].newCondition();
        }
    }

    public Data getDataToProceed(int i, Worker worker) {
        if (i < 0 || i >= n)
            throw new IndexOutOfBoundsException("Buffer indices are in range(0, " + (n-1) + ").");
        locks[i].lock();
        while (processIds[i] != worker.getPrevId())
            try { conditions[i].await(); } catch (InterruptedException exc) { exc.printStackTrace(); }
        return buffer[i];
    }

    public void finishProceedingData(int i, Data data, Worker worker) {
        if (i < 0 || i >= n)
            throw new IndexOutOfBoundsException("Buffer indices are in range(0, " + (n-1) + ").");
        if (!locks[i].isHeldByCurrentThread())
            throw new RuntimeException("Cannot unlock not locked lock.");
        buffer[i] = data;
        processIds[i] = worker.getId();
        conditions[i].signalAll();
        locks[i].unlock();
    }

    public int getN() {
        return n;
    }
}
