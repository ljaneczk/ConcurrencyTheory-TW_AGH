package printer;

import java.util.Random;

import static java.lang.Math.abs;

public class Printer {
    private int id;
    private static Random random = new Random();
    Printer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void print(String message) {
        System.out.println(this + " is printing: \"" + message + "\"");
        System.out.println("PRINTING FINISHED");
    }

    @Override
    public String toString() {
        return "Printer " + id;
    }

    private static int randSleep() {
        return abs(random.nextInt()) % 10000;
    }

}
