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
        // condition for a duration but no reason
        // be able to perform command from terminal

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            boolean permitted = player.hasPermission("simpleModeration.ban");
            boolean isOp = player.isOp();

            if (!permitted | !isOp) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (args.length > 3){
                player.sendMessage(Colours.colors("&4Please use in format '/ban <username> [duration] [reason]'."));
                return false;
            }
            // arg 0: username
            // arg 1: duration/reason
            // arg 2: reason

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null){
                targetPlayer = (Player) Bukkit.getServer().getOfflinePlayer(args[0]);
                if (targetPlayer == null){
                    player.sendMessage(Colours.colors("&4 Player does not exist."));
                }
                return false;
            }
            // might be better off in a switch statement?
            // case: no duration or reason
            if (args.length == 1){
                Bukkit.getBanList(BanList.Type.NAME).addBan(targetPlayer.getName(),
                        Colours.colors("&4&l Banned" ), null, null);
                boolean isOnline = targetPlayer.isOnline();
                if (isOnline = true){
                    targetPlayer.kickPlayer(Colours.colors("&4&o You have been banned by " + player.getName()));
                }
            } else if (args.length == 2){
                String reason = null;
                int duration = 0;
                try {
                    duration = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    reason = args[1];
                }
                // case: only duration no reason
                if (reason == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, duration);
                    Bukkit.getBanList(BanList.Type.NAME).addBan(targetPlayer.getName(),
                            Colours.colors("&4&l Banned" ), calendar.getTime(), null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline = true){
                        targetPlayer.kickPlayer(Colours.colors("&4&o You have been banned by " + player.getName()));
                    }
                    // case: only reason, no duration
                } else {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(targetPlayer.getName(),
                            Colours.colors("&4&l" + reason), null, null);
                    boolean isOnline = targetPlayer.isOnline();
                    if (isOnline = true) {
                        targetPlayer.kickPlayer(Colours.colors("&4&o You have been kicked by " + player.getName() + "&4&l for " + reason));
                    }
                }
                // case: duration AND reason
            } else if (args.length == 3){
                int duration;
                try {
                    duration = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Colours.colors("&4 Please enter a valid number of hours!"));
                    return false;
                }
                String reason = args[3];
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, duration);
                Bukkit.getBanList(BanList.Type.NAME).addBan(targetPlayer.getName(),
                        Colours.colors("&4&l" + reason ), calendar.getTime(), null);
                boolean isOnline = targetPlayer.isOnline();
                if (isOnline = true){
                    targetPlayer.kickPlayer(Colours.colors("&4&o You have been banned by " + player.getName() + "&4&l for " + reason));
                }
            } else {
                player.sendMessage(Colours.colors("&4 Too many arguments!"));
            }

        }

        return false;

    }
}
