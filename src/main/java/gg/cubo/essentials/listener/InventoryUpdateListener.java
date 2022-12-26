package gg.cubo.essentials.listener;

import com.spigonate.Spigonate;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.holder.PrivateChestHolder;
import gg.cubo.essentials.entity.chest.repository.ChestContentRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

@RequiredArgsConstructor
public class InventoryUpdateListener implements Listener {

    private final Spigonate spigonate;

    @EventHandler
    public void handle(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof PrivateChestHolder) {
            PrivateChestHolder holder = (PrivateChestHolder) inventory.getHolder();

            ChestContentRepository repository = (ChestContentRepository) spigonate.getRepository(ChestContent.class);

            Optional<ChestContent> optChest = repository.findById(holder.getChestContent().getId());

            if (!optChest.isPresent()) {
                return;
            }

            ChestContent content = optChest.get();
            content.setContent(inventory.getContents());
            repository.save(content);
            player.sendMessage("§eOs itens do seu baú particular foram salvos!");
        }
    }

}
