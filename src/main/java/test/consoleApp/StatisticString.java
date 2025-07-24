package test.consoleApp;

import java.util.List;
import java.util.Optional;

public class StatisticString extends Statistic {
    
    @Override
    public void printStatistic(List<String> result, String statisticType) {
        if (result.isEmpty()) {
            return;
        }
        System.out.println("\nStatistics for strings");
        System.out.println("Number of recorded items: " + result.size());

        if (statisticType.equals("full")) {
            Optional<String> shortest = result.stream()
                    .min((s1, s2) -> Integer.compare(s1.length(), s2.length()));
            Optional<String> longest = result.stream()
                    .max((s1, s2) -> Integer.compare(s1.length(), s2.length()));
            System.out.println("The shortest string has a length of: " + shortest.get().length());
            System.out.println("The longest string has a length of: " + longest.get().length());
        }
    }
}
