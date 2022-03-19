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

public class Kick extends AbstractCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //to do:
        // abstract out player permissions, argument length check, and player exists check (moderationCommand)
        // be able to perform command from terminal
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simpleserver.kick")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (args.length < 1){
                player.sendMessage(Colours.colors("&4 Please use in format '/kick [ign] [reason]'."));
                return false;
            }
            OfflinePlayer targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null){
                player.sendMessage(Colours.colors("&4 Player does not exist."));
                return false;
            }
            boolean isOnline = targetPlayer.isOnline();
            if (isOnline) {
                if (args.length == 1) {
                    Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                            ("&4&o You have been kicked by " + player.getName()));
                } else {
                    StringBuilder reason = new StringBuilder(args[1]);
                    for (int arg = 2; arg < args.length; arg++) {
                        reason.append(" ").append(args[arg]);
                    }
                    Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                            ("&4&o You have been kicked by " + player.getName() + "&4&o for " + reason));
                }

            } else {
                player.sendMessage(Colours.colors("&4 Player is not online"));
            }

        }

        return false;

    }
}
