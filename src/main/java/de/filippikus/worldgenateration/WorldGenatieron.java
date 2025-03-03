package de.filippikus.worldgenateration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldGenatieron implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length == 0){
            player.sendMessage("§a§l on/off");
            return true;
        }
        if (args[0].equalsIgnoreCase("on")){
            Listener.Chunk = true;
        }else if (args[0].equalsIgnoreCase("off")){
            Listener.Chunk = false;
        }
        return false;
    }
}
