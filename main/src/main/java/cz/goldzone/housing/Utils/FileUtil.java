package cz.goldzone.housing.Utils;

import com.google.common.io.ByteStreams;
import cz.goldzone.housing.Plugin;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@UtilityClass
public class FileUtil {

    /**
     * Save file configuration
     *
     * @param file     {@link org.bukkit.configuration.file.FileConfiguration} - Configuration of file
     * @param fileName {@link java.lang.String} - Name of file to which should be configuration saved
     */
    public void saveFile(FileConfiguration file, String fileName) {
        try {
            file.save(new File(Plugin.getInstance().getDataFolder() + "/" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load file configuration
     *
     * @param path {@link java.lang.String} - Path to file
     * @param fileName {@link java.lang.String} - Name of file
     * @return {@link org.bukkit.configuration.file.YamlConfiguration} - Configuration of file
     */
    public YamlConfiguration loadFile(String path, String fileName) {
        File file = new File(path, fileName);
        checkFile(file);
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Check if file exists, if not - load it from resources
     *
     * @param file {@link java.io.File} - File that should be found
     */
    private void checkFile(File file) {
        Plugin plugin = Plugin.getInstance();
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (InputStream input = plugin.getResource(file.getName());
                     OutputStream output = new FileOutputStream(file)) {
                    ByteStreams.copy(input, output);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
