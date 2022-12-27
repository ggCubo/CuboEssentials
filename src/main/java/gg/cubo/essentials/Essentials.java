package gg.cubo.essentials;

import br.com.blecaute.inventory.InventoryHelper;
import com.spigonate.Spigonate;
import com.spigonate.SpigonateCredentials;
import gg.cubo.essentials.command.ChestCommand;
import gg.cubo.essentials.database.ConnectionType;
import gg.cubo.essentials.database.credentials.EssentialsCredentials;
import gg.cubo.essentials.entity.PrivateChest;
import gg.cubo.essentials.entity.chest.ChestContent;
import gg.cubo.essentials.entity.chest.mapper.InventoryMapper;
import gg.cubo.essentials.entity.chest.mapper.ItemArrayMapper;
import gg.cubo.essentials.entity.chest.mapper.MaterialMapper;
import gg.cubo.essentials.entity.chest.repository.ChestContentRepository;
import gg.cubo.essentials.entity.chest.repository.ChestRepository;
import gg.cubo.essentials.listener.InventoryUpdateListener;
import gg.cubo.essentials.loader.impl.CommandInMemoryLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.File;

public class Essentials extends JavaPlugin {

    @Getter
    private static Essentials instance;

    @Getter
    private Spigonate spigonate;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        InventoryHelper.enable(this);

        this.spigonate = new Spigonate(new EssentialsCredentials(ConnectionType.PRODUCTION, this),
                this);

        spigonate.debug(true);

        spigonate.map(Material.class, new MaterialMapper());
        spigonate.map(Inventory.class, new InventoryMapper());
        spigonate.map(ItemStack[].class, new ItemArrayMapper());

        spigonate.registerRepository(ChestContent.class, ChestContentRepository.class);
        spigonate.registerRepository(PrivateChest.class, ChestRepository.class);

        setupLoaders();

        Bukkit.getPluginManager().registerEvents(new InventoryUpdateListener(spigonate), this);
    }

    @Override
    public void onDisable() {
    }

    private void setupLoaders() {
        new CommandInMemoryLoader(spigonate).load(this);
    }

    public Essentials() {
        super();
    }

    protected Essentials(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }
}
