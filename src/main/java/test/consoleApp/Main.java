package test.consoleApp;

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
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    static Path basePath = Paths.get("src\\main\\java\\test\\consoleApp");

    public static void main(String[] args) {
        LinkedList<String> filesToRead = new LinkedList<>();

        String statisticType = null;
        boolean append = false;
        String o = "";
        String p = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s", "-f":
                    statisticType = args[i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-o":
                    if (i + 1 < args.length) {
                        o = args[++i];
                    } else {
                        System.out.println("Ошибка: отсутствует значение после -o");
                        System.exit(0);
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        p = args[++i];
                    } else {
                        System.out.println("Ошибка: отсутствует значение после -p");
                        System.exit(0);
                    }
                    break;
                default:
                    filesToRead.add(args[i]);
            }
        }

        if (statisticType == null) {
            System.out.println("Ошибка: выберите вид отображения статистики");
            System.exit(0);
        }

        if (filesToRead.isEmpty()) {
            System.out.println("Ошибка: отсутствуют входные файлы");
            System.exit(0);
        }

        List<BufferedReader> readers = new ArrayList<>();

        try {
            for (String filePath : filesToRead) {
                File file = new File(basePath + "\\" + filePath);
                if (!file.exists()) {
                    System.out.println("Ошибка: файла по пути " + basePath + "\\" + filePath + " не существует");
                    System.exit(0);
                }
                readers.add(new BufferedReader(new FileReader(file, StandardCharsets.UTF_8)));
            }

            LinkedList<String> resultStrings = new LinkedList<>();
            LinkedList<String> resultIntegers = new LinkedList<>();
            LinkedList<String> resultFloats = new LinkedList<>();

            Pattern patternInt = Pattern.compile("^-?\\d*_?\\d+$");
            Pattern patternFloat = Pattern.compile("^(-?\\d+\\.?\\d*E?-?\\d+)$");

            boolean hasNextLine = true;
            while (hasNextLine) {
                hasNextLine = false;
                for (BufferedReader reader : readers) {
                    String line = reader.readLine();
                    if (line != null) {
                        if (patternInt.matcher(line).matches()) {
                            resultIntegers.add(line);
                        } else if (patternFloat.matcher(line).matches()) {
                            resultFloats.add(line);
                        } else {
                            resultStrings.add(line);
                        }
                        hasNextLine = true;
                    }
                }
            }

            System.out.println("Статистика: ");
            if (!resultIntegers.isEmpty()) {
                checkDir(o);
                createFile(Path.of(basePath + "\\" + o + "\\" + p + "integers.txt"), resultIntegers, append);
                StatisticInt statisticInt = new StatisticInt();
                statisticInt.printStatistic(resultIntegers, statisticType);
            }
            if (!resultFloats.isEmpty()) {
                checkDir(o);
                createFile(Path.of(basePath + "\\" + o + "\\" + p + "floats.txt"), resultFloats, append);
                StatisticFloat statisticFloat = new StatisticFloat();
                statisticFloat.printStatistic(resultFloats, statisticType);
            }
            if (!resultStrings.isEmpty()) {
                checkDir(o);
                createFile(Path.of(basePath + "\\" + o + "\\" + p + "strings.txt"), resultStrings, append);
                StatisticString statisticStrings = new StatisticString();
                statisticStrings.printStatistic(resultStrings, statisticType);
            }
        } catch (Exception e) {
            System.out.println("Ошибка. Сообщение ошибки: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void checkDir(String o) {
        if (!o.isEmpty()) {
            String pathToFolder = basePath + "\\" + o;
            File folder = new File(pathToFolder);
            if (!folder.exists()) {
                boolean dir = folder.mkdirs();
                if (!dir) {
                    System.out.println("Ошибка: папка не создана.");
                    System.exit(0);
                }
            }
        }
    }

    public static void createFile(Path path, LinkedList<String> result, boolean append) {
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
        } catch (IOException e) {
            System.out.println("Ошибка: файл не создан. Сообщение ошибки: " + e.getMessage());
            System.exit(0);
        }
    }
}