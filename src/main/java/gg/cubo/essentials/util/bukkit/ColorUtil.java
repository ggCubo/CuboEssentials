package gg.cubo.essentials.util.bukkit;

import org.bukkit.Color;

public class ColorUtil {

    public static Color toBukkitColor(String hex) {
        return Color.fromRGB(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        );
    }

}
