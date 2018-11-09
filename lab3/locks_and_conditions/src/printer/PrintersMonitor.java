package printer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintersMonitor {
    private final int numberOfPrinters;
    private int numberOfTakenPrinters;
    private LinkedList<Printer> printers;
    private Lock lock = new ReentrantLock();
    private Lock searchPrinterLock = new ReentrantLock();
    private Condition fullCondition = lock.newCondition();
    private Condition notEmptyCondition = lock.newCondition();

    public PrintersMonitor(int numberOfPrinters) {
        this.numberOfPrinters = numberOfPrinters;
        this.printers = new LinkedList<>();
        for (int i = 0; i < numberOfPrinters; i++)
            this.printers.addLast(new Printer(i));
        this.numberOfTakenPrinters = 0;
    }

    public Printer takePrinter() throws InterruptedException {
        lock.lock();
        try {
            while (numberOfTakenPrinters == numberOfPrinters)
                fullCondition.await();
            searchPrinterLock.lock();
            if (printers.isEmpty())
                notEmptyCondition.await();
            Printer printer = printers.getFirst();
            printers.removeFirst();
            numberOfTakenPrinters++;
            searchPrinterLock.unlock();
            return printer;
        }
        finally {
            lock.unlock();
        }
    }

    public void leavePrinter(Printer printer) throws IllegalStateException {
        lock.lock();
        printers.addLast(printer);
        numberOfTakenPrinters--;
        fullCondition.signal();
        notEmptyCondition.signal();
        lock.unlock();
    }
}