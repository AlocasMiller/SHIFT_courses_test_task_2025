package test.consoleApp;

import java.nio.file.Path;
import java.util.List;

public record Params(
        StatisticType statisticType,
        boolean aEnabled,
        String oAvailable,
        String pAvailable,
        List<Path> fileList
) {}
