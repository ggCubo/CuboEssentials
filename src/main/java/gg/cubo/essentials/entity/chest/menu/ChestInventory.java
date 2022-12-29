package gg.cubo.essentials.entity.chest.menu;

import br.com.blecaute.inventory.InventoryBuilder;
import br.com.blecaute.inventory.button.Button;
import br.com.blecaute.inventory.button.ButtonType;
import br.com.blecaute.inventory.configuration.PaginatedConfiguration;
import gg.cubo.essentials.entity.chest.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.holder.PrivateChestHolder;
import gg.cubo.essentials.menu.InventoryType;
import gg.cubo.essentials.menu.impl.IconInventory;
import gg.cubo.essentials.menu.InventoryConfiguration;
import gg.cubo.essentials.menu.MenuInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@InventoryConfiguration("particular-chest-menu")
public class ChestInventory implements MenuInventory {

    @Override
    public void open(Player player, Object... objects) {
        PrivateChest chest = (PrivateChest) objects[0];

        InventoryBuilder<ChestContent> builder = createBuilder();
        builder.withObjects(PAGINATED_CONFIGURATION, chest.getSortedChests(), click -> {
            ChestContent content = click.getObject();

            if (click.getEvent().isLeftClick()) {
                Inventory inventory = Bukkit.createInventory(new PrivateChestHolder(content), content.getLine() * 9, "Baú particular");
                inventory.setContents(content.getContent());

                player.openInventory(inventory);
            } else {
                InventoryType.ICON_INVENTORY.open(player, InventoryType.CHEST_INVENTORY, content);
            }
        });
        builder.open(player);
    }
}
