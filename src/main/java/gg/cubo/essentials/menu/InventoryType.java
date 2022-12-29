package gg.cubo.essentials.menu;

import gg.cubo.essentials.entity.chest.menu.ChestInventory;
import gg.cubo.essentials.entity.home.menu.HomeInventory;
import gg.cubo.essentials.menu.impl.IconInventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public enum InventoryType {

    CHEST_INVENTORY(new ChestInventory()),
    HOME_INVENTORY(new HomeInventory()),
    ICON_INVENTORY(new IconInventory());

    @NotNull
    private final MenuInventory inventory;

    public void open(Player player) {
        inventory.open(player);
    }

    public void open(Player player, Object... objects) {
        inventory.open(player, objects);
    }
}
