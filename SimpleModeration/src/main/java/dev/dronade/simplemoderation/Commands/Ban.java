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
 * Ban command, usage: /ban <username> [duration] [reason]
 * covers both tempban and full ban
 * @author Emily
 */

public class Ban extends ModerationCommand implements CommandExecutor {

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

//            if (args.length < 1) {
//                player.sendMessage(Colours.colors("&4Please use in format '/ban <username> [duration] [reason]'."));
//            }

            if (args.length >= 2) {
                String reason = null;
                int duration = 0;
                // this if statement below is causing the string builder for ban without duration to stop working,
                // need to come up with a workaround
                if (args.length == 2){
                    try {
                        duration = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        reason = args[1];
                    }
                } else {
                    duration = Integer.parseInt(args[1]);
                    reason = args[2];
                }

                if (duration > 0 && reason != null) {
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

                } else if (reason == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, duration);
                    Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(targetPlayer.getName()),
                            //need to provide default reason?
                            Colours.colors("&4&l Banned"), calendar.getTime(), null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline = true) {
                        Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                                ("&4&o You have been banned by " + player.getName()));
                    }

                } else if (duration == 0) {
                    StringBuilder builtReason = new StringBuilder(args[1]);
                    for (int arg = 1; arg < args.length; arg++) {
                        builtReason.append(" ").append(args[arg]);
                    }
                    Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(targetPlayer.getName()),
                            Colours.colors("&4&l" + builtReason), null, null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline = true) {
                        Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                                ("&4&o You have been kicked by " + player.getName() + " for " + builtReason));
                    }

                } else {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(targetPlayer.getName()),
                            Colours.colors("&4&l Banned"), null, null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline = true) {
                        Objects.requireNonNull(targetPlayer.getPlayer()).kickPlayer(Colours.colors
                                ("&4&o You have been banned by " + player.getName()));
                    }
                }
            }
            return false;
        }
        return false;
    }
}