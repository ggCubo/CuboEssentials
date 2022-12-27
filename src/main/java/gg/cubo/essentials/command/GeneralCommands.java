package gg.cubo.essentials.command;

import com.spigonate.Spigonate;
import gg.cubo.essentials.command.annotation.NamedCommand;
import gg.cubo.essentials.types.GameModes;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.core.BukkitActor;

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
}
