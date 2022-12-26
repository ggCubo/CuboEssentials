package gg.cubo.essentials.entity.chest.holder;

import gg.cubo.essentials.entity.chest.ChestContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@AllArgsConstructor
@Getter
public class PrivateChestHolder implements InventoryHolder {

    private ChestContent chestContent;

    @Override
    public Inventory getInventory() {
        return null;
    }
}
