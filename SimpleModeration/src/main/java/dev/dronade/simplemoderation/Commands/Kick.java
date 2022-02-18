package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Kick command, usage: /kick <username> [reason]
 * @author Emily
 */

public class Kick extends ModerationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //to do:
        // abstract out player permissions, argument length check, and player exists check (moderationCommand)
        // be able to perform command from terminal
        // need to add string builder
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simplemoderation.kick")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (args.length < 1){
                player.sendMessage(Colours.colors("&4 Please use in format '/kick <username> [reason]'."));
                return false;
            }
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);
            if (targetPlayer == null){
                player.sendMessage(Colours.colors("&4 Player does not exist."));
                return false;
            }
            boolean isOnline = targetPlayer.isOnline();
            if (isOnline) {
                if (args.length == 1) {
                    Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                            ("&4&o You have been kicked by " + player.getName()));
                } else if (args.length == 2) {
                    String reason = args[1];
                    Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                            ("&4&o You have been kicked by " + player.getName() + "&4&o for " + reason));
                } else {
                    player.sendMessage(Colours.colors("&4 Too many arguments."));
                }
            } else {
                player.sendMessage(Colours.colors("&4 Player is not online"));
            }

        }

        return false;

    }
}
