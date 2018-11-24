package task2.unfair;

import task2.common.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UnfairBuffer {
    private int Mx2;
    private int M;
    private int inBuffer;
    private Data[] data;
    private ReentrantLock lock;
    private Condition prod;
    private Condition cons;

    public UnfairBuffer(int M) {
        assert M > 0;
        this.M = M;
        this.Mx2 = 2 * M;
        this.inBuffer = 0;
        this.data = new Data[Mx2];
        this.lock = new ReentrantLock();
        this.prod = lock.newCondition();
        this.cons = lock.newCondition();
    }

    public void put(int k, Data[] dataToPlace) {
        assert k == dataToPlace.length && k <= M;
        long time1 = System.nanoTime();
        lock.lock();
        while (Mx2 - inBuffer < k)
            try { prod.await(); } catch (InterruptedException exc) { exc.printStackTrace(); }
        int putIndex = 0;
        for (int i = 0; i < Mx2 && putIndex < k; i++) {
            if (data[i] == null) {
                data[i] = dataToPlace[putIndex];
                putIndex++;
                inBuffer++;
            }
        }
        cons.signal();
        lock.unlock();
        long time2 = System.nanoTime();
        System.out.println("P " + k + " " + (time2 - time1));
    }

    public Data[] get(int k) {
        assert 1 <= k && k <= M;
        long time1 = System.nanoTime();
        lock.lock();
        while (inBuffer < k)
            try { cons.await(); } catch (InterruptedException exc) { exc.printStackTrace(); }
        Data[] dataToReturn = new Data[k];
        int getIndex = 0;
        for (int i = 0; i < Mx2 && getIndex < k; i++)
            if (data[i] != null) {
                dataToReturn[getIndex] = data[i];
                data[i] = null;
                getIndex++;
                inBuffer--;
            }
        prod.signal();
        lock.unlock();
        long time2 = System.nanoTime();
        System.out.println("C " + k + " " + (time2 - time1));
        return dataToReturn;
    }
}
