package gg.cubo.essentials.entity.chest.menu;

import br.com.blecaute.inventory.InventoryBuilder;
import br.com.blecaute.inventory.button.Button;
import br.com.blecaute.inventory.button.ButtonType;
import br.com.blecaute.inventory.configuration.PaginatedConfiguration;
import gg.cubo.essentials.entity.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.holder.PrivateChestHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChestInventory {

    private static final PaginatedConfiguration configuration = PaginatedConfiguration.builder("#ranks")
            .start(10).size(28).end(44)
            .button(Button.of(ButtonType.NEXT_PAGE, 26, new ItemStack(Material.ARROW)))
            .button(Button.of(ButtonType.PREVIOUS_PAGE, 18, new ItemStack(Material.ARROW)))
            .validator((slot) -> slot == 17 || slot == 18 || (slot == 26 || slot == 27) || (slot == 35 || slot == 36))
            .build();

    public static void open(@NotNull Player player, @NotNull PrivateChest chest) {
        new InventoryBuilder<ChestContent>("Seus baús particulares", 6)
                .withObjects(configuration, chest.getSortedChests(), click -> {
                    ChestContent content = click.getObject();

                    if (click.getEvent().isLeftClick()) {
                        Inventory inventory = Bukkit.createInventory(new PrivateChestHolder(content), content.getLine() * 9, "Baú particular");
                        inventory.setContents(content.getContent());

                        player.openInventory(inventory);
                    } else {
                        IconInventory.open(player, chest, content);
                    }
                })
                .open(player);
    }

}
