package test.consoleApp;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class ParamsParser {

    public static Params parseArgs(String[] args) throws ParseException {
        CommandLine commandLine = parse(args);
        List<Path> fileNames = commandLine.getArgList().stream()
                .map(Paths::get)
                .toList();
        return new Params(
                commandLine.hasOption("f") ? "full" : "short",
                commandLine.hasOption("a"),
                Objects.requireNonNullElse(commandLine.getOptionValue("o"), ""),
                Objects.requireNonNullElse(commandLine.getOptionValue("p"), ""),
                fileNames
        );
    }

    private static CommandLine parse(String[] args) throws ParseException {
        Options options = new Options();
        var fsGroup = new OptionGroup()
                .addOption(new Option("f", "option f required if s not specified"))
                .addOption(new Option("s", "option s required if f not specified"));
        fsGroup.setRequired(true);
        options.addOptionGroup(fsGroup);
        options.addOption("a", "option a is optional");
        options.addOption(new Option("o", true, "option o is optional"));
        options.addOption(new Option("p", true,"option p is optional"));

        try {
            CommandLine commandLine = new DefaultParser().parse(options, args);
            if (commandLine.getArgList().isEmpty())
                throw new ParseException("Not enough files specified");
            return commandLine;
        } catch (ParseException e) {
            if (args.length > 1) {
                System.out.println(e.getMessage());
            } else new HelpFormatter().printHelp("""
                Args: (-f|-s) [-a] [-o /some/path] [-p result_] in1.txt
                    in1.txt - some file with data""", options);
            throw e;
        }
    }
}
