package test.consoleApp;

import java.util.LinkedList;

public class StatisticInt extends Statistic {

    @Override
    public void printStatistic(LinkedList<String> result, String statisticType) {
        System.out.println("\nСтатистика для целых чисел");
        System.out.println("Количество записанных элементов: " + result.size());

        if (statisticType.equals("-f")) {
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

            System.out.println("Минимальное значение: " + min);
            System.out.println("Максимальное значение: " + max);
            System.out.println("Сумма: " + sum);
            System.out.println("Среднее значение: " + sum/result.size());
        }
    }
}
