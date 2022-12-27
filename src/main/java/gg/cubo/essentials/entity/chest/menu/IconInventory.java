package gg.cubo.essentials.entity.chest.menu;

import br.com.blecaute.inventory.InventoryBuilder;
import br.com.blecaute.inventory.button.Button;
import br.com.blecaute.inventory.button.ButtonType;
import br.com.blecaute.inventory.configuration.PaginatedConfiguration;
import com.google.common.collect.Lists;
import gg.cubo.essentials.Main;
import gg.cubo.essentials.entity.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.repository.ChestContentRepository;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IconInventory {

    private static final PaginatedConfiguration configuration = PaginatedConfiguration.builder("#icons")
            .start(10).size(28).end(44)
            .button(Button.of(ButtonType.NEXT_PAGE, 26, new ItemStack(Material.ARROW)))
            .button(Button.of(ButtonType.PREVIOUS_PAGE, 18, new ItemStack(Material.ARROW)))
            .validator((slot) -> slot == 17 || slot == 18 || (slot == 26 || slot == 27) || (slot == 35 || slot == 36))
            .build();


    public static void open(@NotNull Player player, @NotNull PrivateChest chest, @NotNull ChestContent content) {
        ChestContentRepository repository = (ChestContentRepository) Main.getInstance().getSpigonate()
                .getRepository(ChestContent.class);

        new InventoryBuilder<ChestContent>("Selecione um ícone", 6)
                .withItems(configuration, getValidItems(), click -> {
                            ItemStack item = click.getItemStack();

                            content.setIcon(item.getType());
                            repository.save(content);

                            player.sendMessage("§eO ícone do seu báu foi alterado para: §d" + item.getType().name());
                            ChestInventory.open(player, chest);
                        }
                )
                .open(player);
    }

    private static List<ItemStack> getValidItems() {
        List<ItemStack> items = Lists.newArrayList();

        for (Material material : Material.values()) {
            if (material == Material.BURNING_FURNACE || material == Material.SOIL) continue;

            if (CraftMagicNumbers.getItem(material) != null) {
                items.add(new ItemStack(material));
            }
        }

        return items;
    }

}
