package NewGen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Lexer {
    private final Path sourceFile;

    public Lexer(Path sourceFile) {
        this.sourceFile = sourceFile;
    }

    public boolean pathExists() {
        return Files.exists(sourceFile);
    }

    private boolean isComment(String line) {
        if (line.startsWith("#")) {
            return true;
        }
        return line.trim().startsWith("#");
    }

    private boolean isEmptyLine(String line) {
        if (line.isBlank()) {
            return true;
        }
        return line.trim().isEmpty();
    }

    protected Token tokenize(String line) throws IllegalArgumentException {
        String[] pair = line.split(":", 2);

        if (pair.length != 2) throw new IllegalArgumentException("Expected value, got neither...");
        //if (pair[1].trim().startsWith("{")) object = true;
        if (pair[1].trim().startsWith("[")) {
            return new Token(pair[0], Arrays.toString(formatArray(formatValue(pair[1]))), false, true);
        }
        // for now...
        return new Token(pair[0], (String) formatValue(pair[1]));
    }

    // -1 if invalid, 0 if correct, 1 if should see next line
    private int validateLine(String line) throws IllegalArgumentException {
        String[] pair = line.split(":", 2);
        if (pair.length != 2) throw new IllegalArgumentException("Failed line validation.");
        return line.trim().endsWith(";") ? 0 : 1;
    }

    private String[] formatArray(String value) {
        String[] split = value.split(",");
        for (int i = 0; i < split.length; i++) {
            if (i == 0 || i == split.length - 1) {
                split[i] = split[i].replace((i == 0 ? "[" : "]"), "").trim();
            }
            split[i] = formatArrayItem(split[i].trim());
        }
        return split;
    }

    private String formatArrayItem(String item) {
        return item.replace("\"", "").trim();
    }

    private String formatValue(String value) {
        return value.replace("\"", "").replace(";", "").trim();
    }

    public void readFile() {
        try (BufferedReader bf = new BufferedReader(new FileReader(sourceFile.toFile()))) {
            String line;
            boolean requireNextLine = false;
            StringBuilder combineLines = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                if (isComment(line) || isEmptyLine(line)) continue;
                if (requireNextLine) {
                    if (line.endsWith(";")) {
                        requireNextLine = false;
                    }
                    combineLines.append(line);
                    if (!requireNextLine) {
                        line = combineLines.toString();
                    } else {
                        continue;
                    }
                }
                switch (validateLine(line)) {
                    case 1:
                        requireNextLine = true;
                        combineLines.append(line);
                        continue;
                    case -1:
                        System.out.println("-1");
                        break;
                }
                System.out.println(tokenize(line));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
}
