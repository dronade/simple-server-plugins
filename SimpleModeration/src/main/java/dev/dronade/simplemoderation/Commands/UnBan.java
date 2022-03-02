package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Unban command, usage: /unban <username>
 * @author Emily
 */

public class UnBan implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simplemoderation.unban")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }

            if (args.length < 1){
                player.sendMessage(Colours.colors("&4 Please use in format '/unban <username> '."));
                return false;
            }

            OfflinePlayer targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null){
                player.sendMessage(Colours.colors("&4 Player does not exist."));
                return false;
            }
            if(targetPlayer.isBanned()){
                Bukkit.getBanList(BanList.Type.NAME).pardon(Objects.requireNonNull(targetPlayer.getName()));
            } else{
                player.sendMessage(Colours.colors(" &4 Player is not banned"));
            }

        // will make this system for console usage of commands better, but for now this will do
        } else if (commandSender instanceof ConsoleCommandSender){
            if (args.length < 1){
                System.out.println("Please use in format '/unban <username> '.");
                return false;
            }

            OfflinePlayer targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null){
                System.out.println("Player does not exist.");
                return false;
            }

            if(targetPlayer.isBanned()){
                Bukkit.getBanList(BanList.Type.NAME).pardon(Objects.requireNonNull(targetPlayer.getName()));
            } else{
                System.out.println("Player is not banned");
            }
        }
        return false;

    }
}
