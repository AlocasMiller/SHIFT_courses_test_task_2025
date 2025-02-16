package test.consoleApp;

import java.util.LinkedList;
import java.util.Optional;

public class StatisticString extends Statistic {
    
    @Override
    public void printStatistic(LinkedList<String> result, String statisticType) {
        System.out.println("\nСтатистика для строк");
        System.out.println("Количество записанных элементов: " + result.size());

        if (statisticType.equals("-f")) {
            Optional<String> shortest = result.stream()
                    .min((s1, s2) -> Integer.compare(s1.length(), s2.length()));
            Optional<String> longest = result.stream()
                    .max((s1, s2) -> Integer.compare(s1.length(), s2.length()));
            System.out.println("Самая короткая строка имеет длину: " + shortest.get().length());
            System.out.println("Самая длинная строка имеет длину: " + longest.get().length());
        }
    }
}
