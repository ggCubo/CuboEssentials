package gg.cubo.essentials.loader;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface InMemoryLoader {

    void load(@NotNull Plugin plugin);

}
