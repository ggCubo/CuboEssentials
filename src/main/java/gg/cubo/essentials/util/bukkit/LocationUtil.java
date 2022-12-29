package gg.cubo.essentials.util.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class LocationUtil {

    public static String serialize(Location location) {
        return location.getWorld().getName() + "," +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getYaw() + "," +
                location.getPitch();
    }

    public static String beauty(Location location, ChatColor initialColor, ChatColor coordsColor) {
        return initialColor +
                "X: " + coordsColor + location.getBlockX() + initialColor + ", " +
                "Y: " + coordsColor + location.getBlockY() + initialColor + ", " +
                "Z: " + coordsColor + location.getBlockZ() + initialColor;

    }

    public static Location deserialize(String location) {
        String[] split = location.split(",");

        return new Location(Bukkit.getWorld(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5]));
    }

}
