package task1;

import java.util.ArrayList;
import java.util.List;

public class StatisticsCalculator {
    private List<Long> times = new ArrayList<>();

    public void add(long timeDifference) {
        times.add(timeDifference);
    }

    public double getMean() {
        return 1.0 * (times.stream().mapToLong(Long::longValue).sum()) / times.size();
    }

    public double getVariance() {
        double mean = getMean();
        return 1.0 * (times.stream().mapToDouble(i -> (i - mean) * (i - mean)).sum()) / (times.size());
    }

    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }
}
