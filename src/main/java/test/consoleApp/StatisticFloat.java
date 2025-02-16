package test.consoleApp;

import java.util.LinkedList;

public class StatisticFloat extends Statistic {

    @Override
    public void printStatistic(LinkedList<String> result, String statisticType) {
        System.out.println("\nСтатистика для вещественных чисел");
        System.out.println("Количество записанных элементов: " + result.size());

        if (statisticType.equals("-f")) {
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

            System.out.println("Минимальное значение: " + min);
            System.out.println("Максимальное значение: " + max);
            System.out.println("Сумма: " + sum);
            System.out.println("Среднее значение: " + sum/result.size());
        }
    }
}
