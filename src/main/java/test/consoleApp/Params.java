package test.consoleApp;

import java.nio.file.Path;
import java.util.List;

public record Params(
        String statisticType,
        boolean aEnabled,
        String oAvailable,
        String pAvailable,
        List<Path> fileList
) {}
