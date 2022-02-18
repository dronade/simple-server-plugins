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
 * Warn command, usage: /warn <username> [reason]
 * @author Emily
 */

public class Warn implements CommandExecutor {
    //to do:
    // abstract out player permissions, argument length check, and player exists check (moderationCommand)
    // decide on some way to check how many warns + the reasons a player has said warns (probably a database *sigh*)
    // be able to perform command from terminal
    // a way to revoke a warn?
    // need to add isOnline
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simplemoderation.warn")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (args.length < 1){
                player.sendMessage(Colours.colors("&4 Please use in format '/warn <username> [reason]'."));
                return false;
            }
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);
            if (targetPlayer == null){
                player.sendMessage(Colours.colors("&4 Player does not exist."));
                return false;
            }

            if (args.length == 1){
                Objects.requireNonNull(targetPlayer.getPlayer()).sendMessage(Colours.colors
                        ("&4&o You have been warned" ));
            } else {
                StringBuilder reason = new StringBuilder(args[1]);
                for (int arg = 2; arg < args.length; arg++) {
                    reason.append(" ").append(args[arg]);
                }
                Objects.requireNonNull(targetPlayer.getPlayer()).sendMessage(Colours.colors
                        ("&4&o You have been warned for " + reason));
            }

        }

        return false;

    }
}
