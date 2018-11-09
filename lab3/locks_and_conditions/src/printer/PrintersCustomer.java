package printer;

public class PrintersCustomer implements Runnable {
    private int id;
    private PrintersMonitor printersMonitor;
    public PrintersCustomer(int id, PrintersMonitor printersMonitor) {
        this.id = id;
        this.printersMonitor = printersMonitor;
    }

    public void run() {
        int round = 0; boolean continuePrinting = true; Printer printer;
        while (continuePrinting) {
            String taskToPrint = "Message " + round + " from " + this;
            try {
                printer = printersMonitor.takePrinter();
                printer.print(taskToPrint);
                printersMonitor.leavePrinter(printer);
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
            /*round++;
            if (round > 10)
                continuePrinting = false;*/
        }
    }

    @Override
    public String toString() {
        return "PrintersCustomer " + id;
    }
}
