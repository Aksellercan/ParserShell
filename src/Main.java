import java.util.Scanner;

public class Main {
    public static void clearScreen() {
        final String getOS = System.getProperty("os.name").toLowerCase();
        try {
            if (getOS.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            Logger.ERROR.LogException(e, "Error clearing screen, Operating System: " + getOS);
        }
    }

    public static String GetConfigInfo(boolean useJson) {
        return "Colour: " + Logger.getColouredOutput() +
                "\nDebug: " + Logger.getDebugOutput() +
                "\nStackTraces: " + Logger.getEnableStackTraces() +
                "\nVerbose: " + Logger.getVerboseLogFile() +
                "\nName of Logger: " + Logger.getNameOfLogger() +
                "\nLog Path: " + Logger.getLog_path() +
                ((useJson) ? "\nJSON Formatting: " + JSONParser.getUseFormatting() : "") +
                "\nQuiet: " + Logger.getQuiet();
    }

    public static void SetPath() {
        String path;
        Scanner pathEntry = new Scanner(System.in);
        System.out.print(ConsoleColours.BLUE_BRIGHT + "Enter new log path: " + ConsoleColours.RESET);
        path = pathEntry.nextLine();
        if (!path.equals("qc")) Logger.setLog_path(path);
    }

    public static void SetName() {
        String name;
        Scanner nameEntry = new Scanner(System.in);
        System.out.print(ConsoleColours.GREEN + "Enter new logger name: " + ConsoleColours.RESET);
        name = nameEntry.nextLine();
        if (!name.equals("qc")) Logger.setNameOfLogger(name);
    }

    public static void main(String[] args) {
        boolean useJson = false;
        Scanner sc = new Scanner(System.in);
        System.out.println(ConsoleColours.RED_BRIGHT + "SHELL" + ConsoleColours.RESET);
        System.out.println(ConsoleColours.PURPLE_BRIGHT + "Input: " + ConsoleColours.RESET);
        JSONParser jsonParser = new JSONParser();
        YAMLParser yamlParser = new YAMLParser();
        while (true) {
            System.out.print("=> " + (!useJson ? ConsoleColours.YELLOW_BRIGHT + "YAML: " + ConsoleColours.RESET : ConsoleColours.GREEN_BRIGHT + "JSON: " + ConsoleColours.RESET));
            String ls;
            switch (ls = sc.next()) {
                case "use", "1":
                    ls = sc.next();
                    if (ls.equals("json")) {
                        useJson = true;
                    }
                    if (ls.equals("yaml")) {
                        useJson = false;
                    }
                    System.out.println("Using: " + (!useJson ? "YAML" : "JSON"));
                    break;
                case "echo":
                    while (!ls.isEmpty()) {
                        ls = sc.nextLine();
                        System.out.print(ls + " ");
                    }
                    break;
                case "w", "write", "save", "2":
                    if (useJson) {
                        jsonParser.MapAndWriteConfig();
                    } else {
                        yamlParser.MapAndWriteConfig();
                    }
                    break;
                case "cd":
                    SetPath();
                    break;
                case "s", "read", "r", "3":
                    if (useJson) {
                        jsonParser.ReadConfigAndMap();
                    } else {
                        yamlParser.ReadConfigAndMap();
                    }
                    break;
                case "e", "i", "info", "ls", "4":
                    System.out.println(GetConfigInfo(useJson));
                    break;
                case "5", "set", "c", "toggle":
                    System.out.println(GetConfigInfo(useJson));
                    boolean exit = false;
                    while (!exit) {
                        System.out.print("=> " + ConsoleColours.CYAN_BRIGHT + "Toggle Settings: " + ConsoleColours.RESET);
                        switch (ls = sc.next()) {
                            case "d", "debug", "2":
                                Logger.setDebugOutput(!Logger.getDebugOutput());
                                break;
                            case "v", "verbose", "4":
                                Logger.setVerboseLogFile(!Logger.getVerboseLogFile());
                                break;
                            case "c", "colour", "1":
                                Logger.setColouredOutput(!Logger.getColouredOutput());
                                break;
                            case "s", "stacktraces", "stack", "3":
                                Logger.setEnableStackTraces(!Logger.getEnableStackTraces());
                                break;
                            case "f", "format", "5":
                                if (useJson) {
                                    JSONParser.setUseFormatting(!JSONParser.getUseFormatting());
                                } else {
                                    System.out.println(ConsoleColours.BLUE_BOLD_BRIGHT + "Nothing ever happens..." + ConsoleColours.RESET);
                                }
                                break;
                            case "p", "path", "log path", "6":
                                SetPath();
                                break;
                            case "n", "name", "7":
                                SetName();
                                break;
                            case "qu", "quiet", "8":
                                Logger.setQuiet(!Logger.getQuiet());
                                break;
                            case "h", "help", "9":
                                System.out.println("Coloured: c or 1" +
                                        "\nDebug: d or 2" +
                                        "\nStackTraces: s or 3" +
                                        "\nVerbose: v or 4" +
                                        "\nLog Path: p, path, log path or 6" +
                                        "\nName of Logger: n or 7" +
                                        "\nQuiet: qu or 8" +
                                        ((useJson) ? "\nJSON Formatting: f, format, 5" : "") +
                                        "\nHelp: h or 9" +
                                        "\nCancel prompt: qc" +
                                        "\nQuit: q or 0");
                                continue;
                            case "q", "quit", "back", "0":
                                break;
                            default:
                                System.out.println(ConsoleColours.RED + "Invalid command " + ls + ConsoleColours.RESET);
                                continue;
                        }
                        exit = true;
                    }
                    System.out.println(ConsoleColours.PURPLE_BRIGHT + "Input: " + ConsoleColours.RESET);
                    break;
                case "git":
                    System.out.println(ConsoleColours.YELLOW_BOLD_BRIGHT + "git gud" + ConsoleColours.RESET);
                    break;
                case "clear", "cls":
                    clearScreen();
                    break;
                case "version", "ver":
                    System.out.println("v2.4");
                    break;
                case "6", "h", "help":
                    System.out.println("""
                            Use: use or 1 followed by json or yaml\
                            
                            Read: r, s, read or 3\
                            
                            Write: w, save, write or 2\
                            
                            Info: e, i, info, ls or 4\
                            
                            Change Settings: c, set, toggle or 5\
                            
                            Clear Screen: clear or cls\
                            
                            Change log path : cd\
                            
                            Version: ver or version\
                            
                            Help: h or 6\
                            
                            Quit: q, quit or 0""");
                    break;
                case "wq":
                    if (useJson) {
                        jsonParser.MapAndWriteConfig();
                    } else {
                        yamlParser.MapAndWriteConfig();
                    }
                    return;
                case "q", "quit", "0":
                    return;
                default:
                    System.out.println( ConsoleColours.RED + "Invalid command " + ls + ConsoleColours.RESET);
                    break;
            }
        }
    }
}
