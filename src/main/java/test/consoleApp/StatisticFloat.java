package test.consoleApp;

import java.util.List;

public class StatisticFloat extends Statistic {

    @Override
    public void printStatistic(List<String> result, String statisticType) {
        if (result.isEmpty()) {
            return;
        }
        System.out.println("\nStatistics for integers");
        System.out.println("Number of recorded items: " + result.size());

        if (statisticType.equals("full")) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            double sum = 0;

            for (String str : result) {
                if (Double.parseDouble(str) < min) {
                    min = Double.parseDouble(str);
                }
                if (Double.parseDouble(str) > max) {
                    max = Double.parseDouble(str);
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
