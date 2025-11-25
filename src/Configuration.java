import java.io.File;
import java.io.IOException;

/**
 * Abstract class to share same token array
 */
abstract class Configuration implements ConfigurationInterface, FileUtilityInterface {
    /**
     * Map current values with token array to be written or set values using tokenConfig array
     * Add new entries here, with update and normal behaviour
     */
    public void MapKeys(boolean update) {
        for (Token token : tokenConfig) {
            Logger.DEBUG.Log("Current: " + token.toString());
            switch (token.getKey().replace("\t", "")) {
                case "output_debug":
                    if (update) {
                        token.setValue(String.valueOf(Logger.getDebugOutput()));
                        token.setCategoryType("Logger Options");
                        break;
                    }
                    Logger.setDebugOutput(BooleanParse(token.getValue(), false));
                    break;
                case "verbose_log_file":
                    if (update) {
                        token.setValue(String.valueOf(Logger.getVerboseLogFile()));
                        token.setCategoryType("Logger Options");
                        break;
                    }
                    Logger.setVerboseLogFile(BooleanParse(token.getValue(), false));
                    break;
                case "enable_stack_traces":
                    if (update) {
                        token.setValue(String.valueOf(Logger.getEnableStackTraces()));
                        token.setCategoryType("Logger Options");
                        break;
                    }
                    Logger.setEnableStackTraces(BooleanParse(token.getValue(), false));
                    break;
                case "coloured_output":
                    if (update) {
                        token.setValue(String.valueOf(Logger.getColouredOutput()));
                        token.setCategoryType("Logger Options");
                        break;
                    }
                    Logger.setColouredOutput(BooleanParse(token.getValue(), false));
                    break;
                case "log_path":
                    if (update) {
                        token.setValue(Logger.getLog_path());
                        token.setCategoryType("Logger Options");
                        break;
                    }
                    if (token.getValue().isEmpty()) token.setValue(Logger.getLog_path());
                    Logger.setLog_path(token.getValue());
                    break;
                case "quiet":
                    if (update) {
                        token.setValue(String.valueOf(Logger.getQuiet()));
                        token.setCategoryType("Logger Options");
                        break;
                    }
                    Logger.setQuiet(BooleanParse(token.getValue(), false));
                    Logger.INFO.LogIfTrue("Logger quiet", Logger.getQuiet(), true, true);
                    break;
                case "use_formatting":
                    if (update) {
                        token.setValue(String.valueOf(JSONParser.getUseFormatting()));
                        token.setCategoryType("JSON Options");
                        break;
                    }
                    JSONParser.setUseFormatting(BooleanParse(token.getValue(), true));
                    break;
                case "name_of_logger":
                    if (update) {
                        token.setValue(Logger.getNameOfLogger());
                        token.setCategoryType("Random Customization");
                        break;
                    }
                    if (token.getValue().isEmpty()) token.setValue(Logger.getLog_path());
                    Logger.setNameOfLogger(token.getValue());
                    break;
                default:
                    Logger.WARN.LogSilently("Key \"" + token.getKey() + "\" is invalid");
            }
        }
    }

    /**
     * Checks if directory exists, if it doesn't it creates it and returns the file path
     * @param fileNameWithExtension Name of file to check and create
     * @return  Full path of the file
     * @throws IOException  If creating folder fails throws IOException
     */
    public File MkDirs(String fileNameWithExtension) throws IOException {
        if (!folderPath.exists()) {
            if (!folderPath.mkdir()) {
                throw new IOException("Failed to create config directory");
            }
            Logger.INFO.LogSilently("Created config directory");
        }
        File filePath = new File(folderPath + File.separator + fileNameWithExtension);
        if (!filePath.exists()) {
            boolean status = filePath.createNewFile();
            if (!status) {
                throw new IOException("Failed to create config file");
            }
            Logger.INFO.LogSilently("Created config file");
        }
        return filePath;
    }

    /**
     * BooleanParse reimplementation but with default return value in case string is invalid
     * @param value String value to parse as boolean
     * @param returnValue   Default return value if string is invalid
     * @return  default value or boolean parsed
     */
    private boolean BooleanParse(String value, boolean returnValue) {
        value = value.replace(" ", "");
        Logger.DEBUG.Log("value=" + value);
        if (value.equals("true") || value.equals("false")) {
            return value.equals("true");
        }
        if (!value.isEmpty()) Logger.ERROR.LogSilently("Key value \"" + value +"\" is not valid. Expected \"true\" or \"false\".");
        return returnValue;
    }
}
