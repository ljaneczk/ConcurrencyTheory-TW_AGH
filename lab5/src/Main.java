import task1.Mandelbrot;
import task1.StatisticsCalculator;

public class Main {

    private static final int columnLength = 22;

    public static void main(String[] args) {
        int[] numberOfThreads = {1, 2, 4, 8, 20};
        int[] maxIterations = {200, 1000, 5000, 10000};
        int[] strategies = {1, 2, 3};
        final int trials = 10;

        printRow("MaxIteration", "NumberOfThreads","Strategy","Mean","Standard deviation");

        for (int maxIteration : maxIterations) {
            printRow(getColumn("", "-"), getColumn("", "-"), getColumn("", "-"),
                     getColumn("", "-"), getColumn("", "-"));
            for (int numberOfThread : numberOfThreads) {
                for (int strategy : strategies) {
                    try {
                        test(numberOfThread, maxIteration, strategy, trials);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }
    }

    private static void test(int numberOfThreads, int maxIteration, int strategy, int trials) throws Exception {
        StatisticsCalculator calculator = new StatisticsCalculator();
        long start, end;
        for (int i = 0; i < trials; i++) {
            start = System.nanoTime();
            Mandelbrot mandelbrot = new Mandelbrot(numberOfThreads, maxIteration, strategy);
            mandelbrot.setVisible(true);
            //mandelbrot.dispose();
            end = System.nanoTime();
            calculator.add(end - start);
        }
        /*System.out.println("----------------------------------------------------------------");
        System.out.println("Test: " + numberOfThreads + " thread" + (numberOfThreads==1 ? "" : "s") +
                           ", max iteration = " + maxIteration + " " + printStrategy(strategy));
        System.out.println("   - mean = " + calculator.getMean());
        System.out.println("   - standard deviation = " + calculator.getStandardDeviation());*/
        printRow(maxIteration, numberOfThreads, strategy, calculator.getMean(), calculator.getStandardDeviation());
    }

    private static String getColumn(Object o) {
        return getColumn(o, " ");
    }

    private static String getColumn(Object o, String filler) {
        String string = o.toString();
        if (o instanceof Double)
            string = String.format("%12.8e", o);
        while (string.length() < columnLength) {
            string = filler + string;
        }
        return string;
    }

    private static void printRow(Object maxIter, Object threads, Object strategy, Object mean, Object stddev) {
        String row = getColumn(maxIter) + " | " + getColumn(threads) + " | " +
                getColumn(strategy) + " | " + getColumn(mean) + " | " + getColumn(stddev);
        System.out.println(row);
    }

    private static String printStrategy(int i) {
        assert i == 1 || i == 2 || i == 3;
        if (i == 1)
            return "strategy 1 = |tasks| ~ |threads|";
        if (i == 2)
            return "strategy 2 = |tasks| ~ 10 * |threads|";
        return "strategy 3 = |tasks| = width * height";
    }
}
