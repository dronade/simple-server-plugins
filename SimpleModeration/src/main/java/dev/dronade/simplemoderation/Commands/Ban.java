package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Objects;

/**
 * Ban command, usage: /ban [ign] [duration/per/p] [reason]
 * covers both tempban and full ban
 * @author Emily
 */

public class Ban extends ModerationCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //to do:
        // abstract out player permissions, argument length check, and player exists check (moderationCommand)
        // add pardon command to reverse this
        // be able to perform command from terminal
        // check if player is already banned

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simplemoderation.ban")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }
            // need to fix
            OfflinePlayer targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                player.sendMessage(Colours.colors("&4 Player does not exist."));
                return false;
            }

            if (args.length >= 3) {
                if (args[1].equals("perm") | args[1].equals("p")){
                    StringBuilder builtReason = new StringBuilder(args[2]);
                    for (int arg = 3; arg < args.length; arg++) {
                        builtReason.append(" ").append(args[arg]);
                    }
                    Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(targetPlayer.getName()),
                            Colours.colors("&4&l" + builtReason), null, null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline) {
                        Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                                ("&4&o You have been kicked by " + player.getName() + " for " + builtReason));
                    }
                } else if (args[1].matches("-?\\d+")) {
                    int duration = Integer.parseInt(args[1]);
                    StringBuilder builtReason = new StringBuilder(args[2]);
                    for (int arg = 3; arg < args.length; arg++) {
                        builtReason.append(" ").append(args[arg]);
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, duration);
                    Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(targetPlayer.getName()),
                            Colours.colors("&4" + builtReason), calendar.getTime(), null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline) {
                        Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                                ("&4&o You have been banned by " + player.getName() + " for " + builtReason));
                    }
                } else {
                    player.sendMessage(Colours.colors("&4Please enter a valid duration'."));
                }
            } else {
                player.sendMessage(Colours.colors("&4Please use in format '/ban <username> [duration] [reason]'."));
            }
            return false;
        }
        return false;
    }
}