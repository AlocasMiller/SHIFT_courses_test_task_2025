package test.consoleApp;

import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    private static final Path basePath = Paths.get("src\\main\\java\\test\\consoleApp");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d*_?\\d+$");
    private static final Pattern FLOAT_PATTERN = Pattern.compile("^(-?\\d+\\.?\\d*E?-?\\d+)$");

    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_PROCESSING_ERROR = 2;
    private static final int EXIT_EXCEPTION = 3;

    public static void main(String[] args) {
        Params params = null;
        try {
            params = ParamsParser.parseArgs(args);
        } catch (ParseException ex) {
            System.exit(EXIT_EXCEPTION);
        }

        List<String> resultStrings = new ArrayList<>();
        List<String> resultIntegers = new ArrayList<>();
        List<String> resultFloats = new ArrayList<>();

        try {
            for (Path filePath : params.fileList()) {
                String pathString = basePath.resolve(filePath).toString();
                File file = new File(pathString);
                if (!file.exists()) {
                    System.out.println("Error: file on the path " + pathString + " does not exist");
                    System.exit(EXIT_PROCESSING_ERROR);
                }
                try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (INTEGER_PATTERN.matcher(line).matches()) {
                            resultIntegers.add(line);
                        } else if (FLOAT_PATTERN.matcher(line).matches()) {
                            resultFloats.add(line);
                        } else {
                            resultStrings.add(line);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Error. Error message: " + ex.getMessage());
                    System.exit(EXIT_EXCEPTION);
                }
            }

            StatisticType statisticType = params.statisticType();

            writeResult(params, resultIntegers, "integers");
            writeResult(params, resultFloats, "floats");
            writeResult(params, resultStrings, "strings");

            if (!resultIntegers.isEmpty() || !resultFloats.isEmpty() || !resultStrings.isEmpty()) {
                System.out.println("Statistics: ");
            }
            new StatisticInt().printStatistic(resultIntegers, statisticType);
            new StatisticFloat().printStatistic(resultFloats, statisticType);
            new StatisticString().printStatistic(resultStrings, statisticType);
            System.exit(EXIT_SUCCESS);
        } catch (Exception ex) {
            System.out.println("Unexpected error occurred: " + ex.getMessage());
            System.exit(EXIT_EXCEPTION);
        }
    }

    private static void checkDir(Path path) {
        if (path != null) {
            File folder = new File(path.toString());
            if (!folder.exists()) {
                boolean dir = folder.mkdirs();
                if (!dir) {
                    System.out.println("Error: the folder was not created");
                    System.exit(EXIT_PROCESSING_ERROR);
                }
            }
        }
    }

    private static void createFile(Path path, List<String> result, boolean append) {
        try {
            if (append) {
                Files.write(
                        path,
                        result,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } else {
                Files.write(
                        path,
                        result
                );
            }
        } catch (IOException ex) {
            System.out.println("Error: the file was not created. Error message: " + ex.getMessage());
            System.exit(EXIT_EXCEPTION);
        }
    }

    private static void writeResult(Params params, List<String> result, String resultType) {
        if (!result.isEmpty()) {
            boolean append = params.aEnabled();
            String prefix = "\\" + params.pAvailable();
            Path outputDir = basePath.resolve(
                    params.oAvailable().replaceFirst("^/+", "")
            );

            checkDir(outputDir);
            createFile(Path.of(outputDir + prefix + resultType + ".txt"), result, append);
        }
    }
}
