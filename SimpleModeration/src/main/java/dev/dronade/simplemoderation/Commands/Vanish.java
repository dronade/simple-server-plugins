package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Vanish command, usage: /vanish
 * @author Emily
 */

public class Vanish implements CommandExecutor {
    private List<UUID> vanished = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simplemoderation.vanish")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (vanished.contains(player.getUniqueId())) {
                vanished.remove(player.getUniqueId());
                for (Player target :Bukkit.getOnlinePlayers()){
                    target.showPlayer(player);
                }
                player.sendMessage(Colours.colors("&4 You are no longer vanished"));
                
            } else {
                vanished.add(player.getUniqueId());
                for (Player target :Bukkit.getOnlinePlayers()){
                    target.hidePlayer(player);
                }
                player.sendMessage(Colours.colors("&2 You are now vanished"));
            }
        }
        return false;

    }
}
