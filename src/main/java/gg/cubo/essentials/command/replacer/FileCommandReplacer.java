package gg.cubo.essentials.command.replacer;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import gg.cubo.essentials.util.file.FileUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Cooldown;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.annotation.dynamic.Annotations;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileCommandReplacer {

    private final File dataFolder;
    private final Plugin plugin;
    private final LoadingCache<String, FileConfiguration> cache;

    public FileCommandReplacer(File dataFolder, Plugin plugin) {
        this.dataFolder = dataFolder;
        this.plugin = plugin;

        cache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .build(s -> {
                    File file = FileUtil.getFile(plugin, s);
                    return file.exists() ? FileUtil.getYamlConfiguration(file) : null;
                });
    }


    @SneakyThrows
    public List<Annotation> replace(@Nullable String file, @NotNull String path, Class<? extends Annotation> command) {
        FileConfiguration config = cache.get(StringUtils.isBlank(file) ? "config.yml" : file);
        if (config == null) return null;

        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) return null;

        List<Annotation> annotations = new ArrayList<>();

        if (section.isSet("command")) {
            String[] values = section.getStringList("command").toArray(new String[0]);
            annotations.add(Annotations.create(command, "value", values));
        }

        if (section.isSet("usage")) {
            String usage = section.getString("usage");
            annotations.add(Annotations.create(Usage.class, "value", usage));
        }

        if (section.isSet("description")) {
            String description = section.getString("description");
            annotations.add(Annotations.create(Description.class, "value", description));
        }

        if (section.isSet("permission")) {
            String permission = section.getString("description");
            annotations.add(Annotations.create(CommandPermission.class, "value", permission));
        }

        if (section.isSet("cooldown")) {
            int cooldown = section.getInt("cooldown");
            annotations.add(Annotations.create(Cooldown.class, "value", cooldown));
        }

        return annotations;
    }

    public void invalidate() {
        this.cache.invalidateAll();
    }


}
