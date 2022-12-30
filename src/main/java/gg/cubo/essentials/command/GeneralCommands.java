package gg.cubo.essentials.command;

import com.spigonate.Spigonate;
import gg.cubo.essentials.command.annotation.NamedCommand;
import gg.cubo.essentials.entity.home.Home;
import gg.cubo.essentials.entity.home.HomeType;
import gg.cubo.essentials.entity.home.repository.HomeRepository;
import gg.cubo.essentials.menu.InventoryType;
import gg.cubo.essentials.types.GameModes;
import gg.cubo.essentials.util.Util;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Cooldown;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.core.BukkitActor;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class GeneralCommands {

    private final Spigonate spigonate;

    @NamedCommand("gamemode-command")
    public void gamemode(@NotNull BukkitActor actor, @Default("me") Player target, int id) {
        GameModes mode = GameModes.findById(id);

        if (mode == null) {
            actor.errorLocalized("invalid-gamemode");
            return;
        }

        target.setGameMode(mode.getMode());
        actor.replyLocalized("gamemode-changed", mode.name().toLowerCase());
    }

    @Cooldown(value = 10)
    @NamedCommand("hat-command")
    public void hat(@NotNull BukkitActor actor) {
        Player player = actor.getAsPlayer();
        ItemStack hand = player.getItemInHand();
        ItemStack helmet = player.getInventory().getHelmet();

        if (hand != null && hand.getType() != Material.AIR) {
            player.getInventory().setHelmet(hand);
            player.setItemInHand(helmet);
            actor.replyLocalized("success-hat-changed");
        } else {
            actor.errorLocalized("invalid-hat-type");
        }
    }

    @NamedCommand("home-command")
    public void home(@NotNull BukkitActor player, String name) {
        HomeRepository repository = (HomeRepository) spigonate.getRepository(Home.class);
        Optional<Home> optional = repository.findByName(name);

        if (!optional.isPresent()) {
            player.replyLocalized("error-home-not-found");
            return;
        }

        Home home = optional.get();

        if (!home.getOwner().equals(player.getName()) && home.getType() == HomeType.PRIVATE) {
            player.errorLocalized("error-player-cant-access-home");
            return;
        }

        player.getAsPlayer().teleport(home.getLocation());
        player.replyLocalized("success-home-teleported-target", home.getName(), home.getOwner());
    }

    @NamedCommand("homes-command")
    public void homes(@NotNull Player player) {
        HomeRepository repository = (HomeRepository) spigonate.getRepository(Home.class);
        Collection<Home> homes = repository.findAllByOwner(player.getName());

        if (homes == null || homes.isEmpty()) {
            player.sendMessage("§cVocê não possui nenhuma home criada.");
            return;
        }

        InventoryType.HOME_INVENTORY.open(player, homes);
        player.sendMessage("§eMostrando todas as suas " + homes.size() + " homes.");
    }

    @NamedCommand("sethome-command")
    public void sethome(@NotNull BukkitActor actor, String name, HomeType homeType) {
        Player player = actor.getAsPlayer();
        HomeRepository repository = (HomeRepository) spigonate.getRepository(Home.class);
        Collection<Home> homes = repository.findAllByOwner(player.getName());

        if (homes != null && !homes.isEmpty() && homes.size() >= Util.getMaxHomes(player)) {
            actor.replyLocalized("error-max-homes-reached");
            return;
        }

        if (homes != null && homes.stream().anyMatch(home -> home.getName().equalsIgnoreCase(name))) {
            actor.errorLocalized("invalid-home-name");
            return;
        }

        Home home = repository.save(Home.builder()
                        .location(player.getLocation())
                        .name(name)
                        .icon(Material.ENDER_PEARL)
                        .owner(player.getName())
                        .type(homeType)
                .build());

        actor.replyLocalized("success-home-created", home.getType().getTranslated().toLowerCase(), home.getName());
        actor.replyLocalized("see-homes-command");
    }
}
