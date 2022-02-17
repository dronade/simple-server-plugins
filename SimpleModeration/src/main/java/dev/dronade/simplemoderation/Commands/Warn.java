package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Warn command, usage: /warn <username> [reason]
 * @author Emily
 */

public class Warn implements CommandExecutor {
    //to do:
    // abstract out player permissions, argument length check, and player exists check (moderationCommand)
    // decide on some way to check how many warns + the reasons a player has said warns (probably a database *sigh*)
    // be able to perform command from terminal
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            boolean permitted = player.hasPermission("simpleModeration.warn");
            boolean isOp = player.isOp();

            if (!permitted | !isOp) {
                player.sendMessage(Colours.colors("&4 You are not permitted to use this command."));
                return false;
            }

            if (args.length > 2){
                player.sendMessage(Colours.colors("&4 Please use in format '/warn <username> [reason]'."));
                return false;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null){
                targetPlayer = (Player) Bukkit.getServer().getOfflinePlayer(args[0]);
                if (targetPlayer == null){
                    player.sendMessage(Colours.colors("&4 Player does not exist."));
                }
                return false;
            }
            String reason = args[1];
            targetPlayer.sendMessage(Colours.colors("&4&o You have been warned for " + reason));

        }

        return false;

    }
}
