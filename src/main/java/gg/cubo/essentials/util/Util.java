package gg.cubo.essentials.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Util {

    public static int getMaxHomes(@NotNull Player player) {
        return player.getEffectivePermissions().stream()
                .filter(attachment -> attachment.getPermission().startsWith("cubo.essentials.homes"))
                .mapToInt(value -> Integer.parseInt(value.getPermission().split("\\.")[3]))
                .max()
                .orElse(2); // Número máximo de homes permitidas por padrão (Deixar configurável)
    }

}
