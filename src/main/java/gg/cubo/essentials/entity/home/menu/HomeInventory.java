package gg.cubo.essentials.entity.home.menu;

import br.com.blecaute.inventory.InventoryBuilder;
import gg.cubo.essentials.entity.home.Home;
import gg.cubo.essentials.menu.InventoryConfiguration;
import gg.cubo.essentials.menu.InventoryType;
import gg.cubo.essentials.menu.MenuInventory;
import org.bukkit.entity.Player;

import java.util.Collection;

@InventoryConfiguration("homes-menu")
public class HomeInventory implements MenuInventory {

    @SuppressWarnings("unchecked cast")
    @Override
    public void open(Player player, Object... objects) {
        Collection<Home> homes = (Collection<Home>) objects[0];
        InventoryBuilder<Home> builder = createBuilder();

        builder.withObjects(PAGINATED_CONFIGURATION, homes, click -> {
            Home content = click.getObject();

            if (click.getEvent().isLeftClick()) {
                player.teleport(content.getLocation());
                player.sendMessage("(" + content.getType().getTranslated() + ") §eVocê foi teleportado para a home §f" + content.getName());
            } else {
                InventoryType.ICON_INVENTORY.open(player, InventoryType.HOME_INVENTORY, content);
            }
        });

        builder.open(player);
    }
}
