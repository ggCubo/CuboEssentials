package gg.cubo.essentials.util;

import com.avaje.ebeaninternal.server.core.Message;
import gg.cubo.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.MessageFormat;

public class Log {

    private static final Essentials plugin = Essentials.getInstance();

    public static void info(String message, Object... values) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + MessageFormat.format("[" + plugin.getName() + "] " + message, values));
    }

    public static void error(String message, Object... values) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + MessageFormat.format("[" + plugin.getName() + "] " + message, values));
    }

}
