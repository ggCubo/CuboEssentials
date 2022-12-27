package gg.cubo.essentials.command.actor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import revxrsal.commands.command.CommandActor;

public interface PlayerActor extends CommandActor {

    default Player asPlayer() {
        return Bukkit.getPlayer(getUniqueId());
    }

}
