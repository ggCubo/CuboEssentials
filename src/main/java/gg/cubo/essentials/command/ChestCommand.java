package gg.cubo.essentials.command;

import com.spigonate.Spigonate;
import gg.cubo.essentials.entity.chest.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.menu.ChestInventory;
import gg.cubo.essentials.entity.chest.repository.ChestContentRepository;
import gg.cubo.essentials.entity.chest.repository.ChestRepository;
import gg.cubo.essentials.menu.InventoryType;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;

import java.util.*;

@RequiredArgsConstructor
public class ChestCommand {

    private final Spigonate spigonate;

    @Command("baus")
    public void see(@NotNull Player player) {
        ChestRepository repository = (ChestRepository) spigonate.getRepository(PrivateChest.class);
        Optional<PrivateChest> optChest = repository.findById(player.getUniqueId().toString());

        if (!optChest.isPresent()) {
            player.sendMessage("§cVocê não tem um baú particular ainda, compre um baú com o mercador!");
            return;
        }

        PrivateChest chest = optChest.get();
        InventoryType.CHEST_INVENTORY.open(player, chest);
    }

    @Command("bau")
    public void chest(@NotNull Player player) {
        ChestRepository repository = (ChestRepository) spigonate.getRepository(PrivateChest.class);
        ChestContentRepository contentRepository = (ChestContentRepository) spigonate.getRepository(ChestContent.class);

        Optional<PrivateChest> optChest = repository.findById(player.getUniqueId().toString());

        if (!optChest.isPresent()) {
            repository.save(PrivateChest.of(player, 5));
            player.sendMessage("§eUma nova conta para baús particulares foi salva. Utilize o comando /bau para criar um baú particular");
            return;
        }

        PrivateChest chest = optChest.get();

        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Baú " + (chest.getLastChestIndex() + 1) + " # " + player.getName());
        inventory.addItem(Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(itemStack -> itemStack.getType() != null)
                .toArray(ItemStack[]::new));

        ChestContent content = contentRepository.save(ChestContent.builder()
                        .withIcon(Material.CHEST)
                        .withContent(inventory.getContents())
                        .withLine(6)
                .build());

        chest.addChest(content);
        repository.save(chest);

        player.openInventory(inventory);
    }
}
