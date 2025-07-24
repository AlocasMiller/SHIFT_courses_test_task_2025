package test.consoleApp;

import java.util.List;

public class StatisticInt extends Statistic {

    @Override
    public void printStatistic(List<String> result, String statisticType) {
        if (result.isEmpty()) {
            return;
        }
        System.out.println("\nStatistics for integers");
        System.out.println("Number of recorded items: " + result.size());

        if (statisticType.equals("full")) {
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            double sum = 0;

            for (String str : result) {
                if (Long.parseLong(str) < min) {
                    min = Long.parseLong(str);
                }
                if (Long.parseLong(str) > max) {
                    max = Long.parseLong(str);
                }
                sum += Double.parseDouble(str);
            }

            System.out.println("Minimum value: " + min);
            System.out.println("Maximum value: " + max);
            System.out.println("The amount: " + sum);
            System.out.println("The average value: " + sum/result.size());
        }
    }
}
