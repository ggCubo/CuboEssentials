package gg.cubo.essentials.util.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FileUtil {

    public static void createConfig(Plugin plugin, String file) {
        if (!new File(plugin.getDataFolder(), file).exists()) {
            plugin.saveResource(file, false);
        }
    }

    public static void createConfig(Plugin plugin, String file, String folder) {
        if (!new File(plugin.getDataFolder() + File.separator + folder, file).exists()) {
            plugin.saveResource(folder + File.separator + file, false);
        }
    }

    public static File createFolder(Plugin plugin, String folder) {
        try {
            File directory = new File(plugin.getDataFolder() + File.separator + folder);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            return directory;

        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getFile(Plugin plugin, String file) {
        return new File(plugin.getDataFolder(), file );
    }

    public static File getFile(Plugin plugin, String file, String folder) {
        return new File(plugin.getDataFolder() + File.separator + folder, file);
    }

    public static FileConfiguration getYamlConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }


}
