package gg.cubo.essentials.menu.impl.icon;

import br.com.blecaute.inventory.property.InventoryProperty;
import br.com.blecaute.inventory.type.InventoryItem;
import gg.cubo.essentials.Essentials;
import gg.cubo.essentials.placeholder.PlaceholderReplacer;
import gg.cubo.essentials.placeholder.Replaceable;
import gg.cubo.essentials.util.bukkit.item.ItemBuilder;
import gg.cubo.essentials.util.bukkit.item.ItemTranslates;
import gg.cubo.essentials.util.bukkit.item.ItemUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class IconItem implements InventoryItem {

    @NotNull
    private final Material material;

    public @Nullable ItemStack getItem(@NotNull Inventory inventory, @NotNull InventoryProperty inventoryProperty) {
        ItemBuilder builder = new ItemBuilder(ItemUtil.toItem(Essentials.getInstance(), "icon-menu-item"));
        builder.setMaterial(material);

        return builder.toItemStack();
    }
}
