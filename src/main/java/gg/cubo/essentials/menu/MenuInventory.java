package gg.cubo.essentials.menu;

import br.com.blecaute.inventory.InventoryBuilder;
import br.com.blecaute.inventory.button.Button;
import br.com.blecaute.inventory.button.ButtonType;
import br.com.blecaute.inventory.callback.ItemCallback;
import br.com.blecaute.inventory.configuration.PaginatedConfiguration;
import br.com.blecaute.inventory.type.InventoryItem;
import gg.cubo.essentials.Essentials;
import gg.cubo.essentials.placeholder.PlaceholderReplacer;
import gg.cubo.essentials.util.bukkit.item.ItemBuilder;
import gg.cubo.essentials.util.bukkit.item.ItemUtil;
import gg.cubo.essentials.util.file.FileUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public interface MenuInventory {

    PaginatedConfiguration PAGINATED_CONFIGURATION = PaginatedConfiguration.builder("#")
            .start(10).size(28).end(44)
            .button(Button.of(ButtonType.NEXT_PAGE, 26, new ItemStack(Material.ARROW)))
            .button(Button.of(ButtonType.PREVIOUS_PAGE, 18, new ItemStack(Material.ARROW)))
            .validator((slot) -> slot == 17 || slot == 18 || (slot == 26 || slot == 27) || (slot == 35 || slot == 36))
            .build();

    void open(Player player, Object... objects);

    default InventoryConfiguration getManifest() {
        InventoryConfiguration manifest = getClass().getAnnotation(InventoryConfiguration.class);

        if (manifest == null) {
            throw new IllegalStateException("InventoryConfiguration annotation not found.");
        }

        return manifest;
    }

    default FileConfiguration getConfiguration() {
        return FileUtil.getYamlConfiguration(
                FileUtil.getFile(Essentials.getInstance(), getManifest().value() + ".yml", "menu")
        );
    }

    default <T extends InventoryItem, S extends PlaceholderReplacer<S>> void addUnsafeItem(InventoryBuilder<T> builder, S entity, String key, ItemCallback<T> callback) {
        FileConfiguration configuration = getConfiguration();
        Pair<Integer, ItemStack> pair = ItemUtil.toItemSlot(configuration, "inventory.items." + key);

        ItemStack itemStack = pair.getValue();
        ItemBuilder itemBuilder = new ItemBuilder(itemStack.clone());

        builder.withItem(pair.getKey(), new ItemBuilder(itemStack.clone())
                .setName(entity.replace(itemBuilder.getName(), entity))
                .setLore(itemBuilder.getLore().stream()
                        .map(line -> entity.replace(line, entity))
                        .collect(Collectors.toList()))
                .toItemStack(), callback);
    }

    default <T extends InventoryItem> void addItem(InventoryBuilder<T> builder, String key, ItemCallback<T> callback) {
        FileConfiguration configuration = getConfiguration();
        Pair<Integer, ItemStack> pair = ItemUtil.toItemSlot(configuration, "inventory.items." + key);

        builder.withItem(pair.getKey(), pair.getValue(), callback);
    }

    default <T extends InventoryItem, S extends PlaceholderReplacer<S>> void addItemReplaced(InventoryBuilder<T> builder, S data, String key, ItemCallback<T> callback) {
        FileConfiguration configuration = getConfiguration();
        Pair<Integer, ItemStack> pair = ItemUtil.toItemSlot(configuration, "inventory.items." + key);

        ItemBuilder itemBuilder = new ItemBuilder(pair.getValue());

        builder.withItem(pair.getKey(), itemBuilder
                .setName(data.replace(itemBuilder.getName(), data))
                .setLore(itemBuilder.getLore().stream()
                        .map(line -> data.replace(line, data))
                        .collect(Collectors.toList()))
                .toItemStack(), callback);
    }

    default <T extends InventoryItem> void addItem(InventoryBuilder<T> builder, String key) {
        addItem(builder, key, null);
    }

    default <T extends InventoryItem> void addItem(InventoryBuilder<T> builder, String key, int slot) {
        FileConfiguration configuration = getConfiguration();
        Pair<Integer, ItemStack> pair = ItemUtil.toItemSlot(configuration, "inventory.items." + key);

        builder.withItem(slot, pair.getValue(), null);
    }

    default <T extends InventoryItem> InventoryBuilder<T> createBuilder() {
        FileConfiguration configuration = getConfiguration();

        String title = configuration.getString("inventory.title");
        int lines = configuration.getInt("inventory.lines", 6);

        return new InventoryBuilder<>(title, lines);
    }

    default <T extends InventoryItem, S extends PlaceholderReplacer<S>> void setupDefaultReplaced(InventoryBuilder<T> builder, S data, String... excluded) {
        setupDefaultReplaced(builder, data, data, excluded);
    }

    default <T extends InventoryItem, S> void setupDefaultReplaced(InventoryBuilder<T> builder, PlaceholderReplacer<S> replacer, S data, String... excluded) {
        FileConfiguration configuration = getConfiguration();

        keys:
        for (String key : configuration.getConfigurationSection("inventory.items").getKeys(false)) {
            if (excluded != null) {
                for (String exclude : excluded) {
                    if (exclude.equalsIgnoreCase(key)) {
                        continue keys;
                    }
                }
            }

            Pair<Integer, ItemStack> slotItem = ItemUtil.toItemSlot(configuration, "inventory.items." + key);

            ItemBuilder itemBuilder = new ItemBuilder(slotItem.getValue().clone());

            builder.withItem(slotItem.getKey(), itemBuilder
                    .setName(replacer.replace(itemBuilder.getName(), data))
                    .setLore(itemBuilder.getLore().stream()
                            .map(line -> replacer.replace(line, data))
                            .collect(Collectors.toList()))
                    .toItemStack());
        }
    }

    default <T extends InventoryItem> void setupDefault(InventoryBuilder<T> builder, String... excluded) {
        FileConfiguration configuration = getConfiguration();

        keys:
        for (String key : configuration.getConfigurationSection("inventory.items").getKeys(false)) {
            if (excluded != null) {
                for (String exclude : excluded) {
                    if (exclude.equalsIgnoreCase(key)) {
                        continue keys;
                    }
                }
            }

            Pair<Integer, ItemStack> slotItem = ItemUtil.toItemSlot(configuration, "inventory.items." + key);
            builder.withItem(slotItem.getKey(), slotItem.getValue());
        }
    }

}
