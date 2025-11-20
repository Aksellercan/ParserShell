import java.io.File;
import java.io.IOException;

public interface FileUtilityInterface {
    File folderPath = new File("Config");
    File MkDirs(String fileNameWithExtension) throws IOException;
}
