package gg.cubo.essentials.util.bukkit.item;

import com.google.common.collect.Lists;
import gg.cubo.essentials.Essentials;
import gg.cubo.essentials.menu.impl.icon.IconItem;
import gg.cubo.essentials.util.bukkit.ColorUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemUtil {

    public static ItemStack toItem(FileConfiguration configuration, String path) {
        if (!configuration.isConfigurationSection(path)) return new ItemStack(Material.AIR);

        ItemBuilder builder = new ItemBuilder(Material.BARRIER);

        if (configuration.contains(path + ".url")) {
            String url = configuration.getString(path + ".url");

            if (!url.equalsIgnoreCase("{player}")) {
                builder = new ItemBuilder(SkullItem.getByUrl(configuration.getString(path + ".url")));
            } else {
                builder = new ItemBuilder(SkullItem.getByName("{player}"));
            }
        } else if (configuration.contains(path + ".material-data")) {
            String[] split = configuration.getString(path + ".material-data").split(":");
            String id = String.valueOf(split[0]);

            int data = 0;

            if (split.length > 1) {
                data = Integer.parseInt(split[1]);
            }

            try {
                ItemStack item = new ItemStack(Material.getMaterial(id), 1, (short) data);
                builder = new ItemBuilder(item);
            } catch (Exception e) {
                Essentials.getInstance().getLogger().warning("Invalid material-data: " + configuration.getString(path + ".material-data"));
            }
        }

        if (configuration.contains(path + ".hex")) {
            Color color = ColorUtil.toBukkitColor(Objects.requireNonNull(configuration.getString(path + ".hex")));

            builder.setLeatherArmorColor(color);
        }

        if (configuration.contains(path + ".name")) {
            builder.setName(Objects.requireNonNull(configuration.getString(path + ".name")).replace("&", "§"));
        }

        if (configuration.contains(path + ".lore")) {
            builder.setLore(configuration.getStringList(path + ".lore").stream()
                    .map(line -> line.replace("&", "§"))
                    .collect(Collectors.toList()));
        }

        return builder.toItemStack();
    }

    public static ItemStack toItem(String path) {
        return toItem(Essentials.getInstance().getConfig(), path);
    }

    public static ItemStack toItem(Plugin plugin, String path) {
        return toItem(plugin.getConfig(), path);
    }

    public static Pair<Integer, ItemStack> toItemSlot(FileConfiguration configuration, String path) {
        int slot = configuration.get(path + ".slot") != null ? configuration.getInt(path + ".slot") : 0;

        return Pair.of(slot, toItem(configuration, path));
    }

    public static List<ItemStack> getValidIconItems() {
        List<ItemStack> items = Lists.newArrayList();

        for (Material material : Material.values()) {
            if (material == Material.BURNING_FURNACE || material == Material.SOIL) continue;

            if (CraftMagicNumbers.getItem(material) != null) {
                items.add(new ItemStack(material));
            }
        }

        return items;
    }

    public static List<IconItem> getValidIcons() {
        return getValidIconItems().stream()
                .map(item -> new IconItem(item.getType()))
                .collect(Collectors.toList());
    }
}
