package gg.cubo.essentials.menu.impl;

import br.com.blecaute.inventory.InventoryBuilder;
import br.com.blecaute.inventory.button.Button;
import br.com.blecaute.inventory.button.ButtonType;
import br.com.blecaute.inventory.configuration.PaginatedConfiguration;
import com.google.common.collect.Lists;
import com.spigonate.Spigonate;
import com.spigonate.repository.SpigonateRepository;
import gg.cubo.essentials.Essentials;
import gg.cubo.essentials.entity.chest.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.menu.ChestInventory;
import gg.cubo.essentials.entity.chest.repository.ChestContentRepository;
import gg.cubo.essentials.entity.chest.repository.ChestRepository;
import gg.cubo.essentials.entity.home.Home;
import gg.cubo.essentials.entity.home.repository.HomeRepository;
import gg.cubo.essentials.menu.InventoryConfiguration;
import gg.cubo.essentials.menu.InventoryType;
import gg.cubo.essentials.menu.MenuInventory;
import gg.cubo.essentials.menu.impl.icon.IconItem;
import gg.cubo.essentials.util.bukkit.item.ItemTranslates;
import gg.cubo.essentials.util.bukkit.item.ItemUtil;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@InventoryConfiguration("icon-menu")
public class IconInventory implements MenuInventory {

    private final Spigonate spigonate = Essentials.getInstance().getSpigonate();

    @Override
    public void open(Player player, Object... objects) {
        InventoryType fromType = (InventoryType) objects[0];

        InventoryBuilder<IconItem> builder = createBuilder();

        builder.withObjects(PAGINATED_CONFIGURATION, ItemUtil.getValidIcons(), click -> {
            IconItem icon = click.getObject();
            String translateName = ItemTranslates.valueOf(icon.getMaterial(), icon.getMaterial().getMaxDurability()).getItemName();

            if (fromType == InventoryType.CHEST_INVENTORY) {
                ChestContent chest = (ChestContent) objects[1];
                ChestContentRepository repository = (ChestContentRepository) spigonate.getRepository(ChestContent.class);

                chest.setIcon(icon.getMaterial());
                repository.save(chest);
            } else if (fromType == InventoryType.ICON_INVENTORY) {

                Home home = (Home) objects[1];
                HomeRepository repository = (HomeRepository) spigonate.getRepository(Home.class);

                home.setIcon(icon.getMaterial());
                repository.save(home);
            }
            player.sendMessage("§eVocê selecionou o ícone: §f" + translateName);
        });

        builder.open(player);
    }
}
