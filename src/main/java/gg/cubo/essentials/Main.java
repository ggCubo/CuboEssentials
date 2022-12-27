package gg.cubo.essentials;

import br.com.blecaute.inventory.InventoryHelper;
import com.spigonate.Spigonate;
import com.spigonate.SpigonateCredentials;
import gg.cubo.essentials.command.ChestCommand;
import gg.cubo.essentials.entity.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.mapper.InventoryMapper;
import gg.cubo.essentials.entity.chest.mapper.ItemArrayMapper;
import gg.cubo.essentials.entity.chest.mapper.MaterialMapper;
import gg.cubo.essentials.entity.chest.repository.ChestContentRepository;
import gg.cubo.essentials.entity.chest.repository.ChestRepository;
import gg.cubo.essentials.listener.InventoryUpdateListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private Spigonate spigonate;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        InventoryHelper.enable(this);

        this.spigonate = new Spigonate(SpigonateCredentials.builder()
                .withDatabase(getConfig().getString("mysql.database"))
                .withUsername(getConfig().getString("mysql.username"))
                .withPassword(getConfig().getString("mysql.password"))
                .withAddress(getConfig().getString("mysql.address"))
                .build(), this);

        spigonate.map(Material.class, new MaterialMapper());
        spigonate.map(Inventory.class, new InventoryMapper());
        spigonate.map(ItemStack[].class, new ItemArrayMapper());

        spigonate.registerRepository(ChestContent.class, ChestContentRepository.class);
        spigonate.registerRepository(PrivateChest.class, ChestRepository.class);

        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.register(new ChestCommand(spigonate));

        Bukkit.getPluginManager().registerEvents(new InventoryUpdateListener(spigonate), this);
    }

    @Override
    public void onDisable() {
    }
}
