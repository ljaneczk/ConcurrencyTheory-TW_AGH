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

    public PrintersMonitor(int numberOfPrinters) {
        this.numberOfPrinters = numberOfPrinters;
        this.printers = new LinkedList<>();
        for (int i = 0; i < numberOfPrinters; i++)
            this.printers.add(new Printer(i));
        this.numberOfTakenPrinters = 0;
    }

    public Printer takePrinter() throws InterruptedException {
        lock.lock();
        try {
            while (numberOfTakenPrinters == numberOfPrinters)
                fullCondition.await();
            searchPrinterLock.lock();
            Printer printer = printers.getFirst();
            printers.removeFirst();
            searchPrinterLock.unlock();
            return printer;
        }
        finally {
            lock.unlock();
        }
    }

    public void leavePrinter(Printer printer) throws IllegalStateException {
        lock.lock();
        printers.add(printer);
        lock.unlock();
    }
}


/*
package printer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintersMonitor {
    private final int numberOfPrinters;
    private Printer[] printers;
    private int numberOfTakenPrinters;
    private boolean[] takenPrinters;
    private Lock lock = new ReentrantLock();
    private Lock searchPrinterLock = new ReentrantLock();
    private Condition fullCondition = lock.newCondition();

    public PrintersMonitor(int numberOfPrinters) {
        this.numberOfPrinters = numberOfPrinters;
        this.printers = new Printer[numberOfPrinters];
        for (int i = 0; i < numberOfPrinters; i++)
            this.printers[i] = new Printer(i);
        this.numberOfTakenPrinters = 0;
        this.takenPrinters = new boolean[numberOfPrinters];
        for (int i = 0; i < numberOfPrinters; i++)
            this.takenPrinters[i] = false;
    }

    public Printer takePrinter() throws InterruptedException {
        lock.lock();
        try {
            while (numberOfTakenPrinters == numberOfPrinters)
                fullCondition.await();
            searchPrinterLock.lock();
            int index = -1;
            for (int i = 0; i < numberOfPrinters && index == -1; i++)
                if (!takenPrinters[i]) {
                    takenPrinters[i] = true;
                    numberOfTakenPrinters++;
                    index = i;
                }
            searchPrinterLock.unlock();
            return printers[index];
        }
        finally {
            lock.unlock();
        }
    }

    public void leavePrinter(Printer printer) throws IllegalStateException {
        lock.lock();
        try {
            if (takenPrinters[printer.getId()]) {
                takenPrinters[printer.getId()] = false;
                numberOfTakenPrinters--;
                fullCondition.signal();
            } else
                throw new IllegalStateException("Printer is already free.");
        }
        finally {
            lock.unlock();
        }
    }
}
 */