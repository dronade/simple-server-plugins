package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Mute command, usage: /mute <username> [reason]
 * @author Emily
 */

public class Mute implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //need to actually implement
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simplemoderation.mute")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (args.length < 1){
                player.sendMessage(Colours.colors("&4 Please use in format '/mute <username> [reason]'."));
                return false;
            }
            OfflinePlayer targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null){
                player.sendMessage(Colours.colors("&4 Player does not exist."));
                return false;
            }
        }
        return false;

    }
}

