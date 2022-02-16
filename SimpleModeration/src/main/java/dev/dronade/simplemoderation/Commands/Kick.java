package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


// '/kick <username> [reason]'
public class Kick extends ModerationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //to do:
        // abstract out player permissions, argument length check, and player exists check (moderationCommand)
        // check if player is online before kicking
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            boolean permitted = player.hasPermission("simpleModeration.kick");
            boolean isOp = player.isOp();

            if (!permitted | !isOp) {
                player.sendMessage(Colours.colors("&4 You are not permitted to use this command."));
                return false;
            }

            if (args.length > 2){
                player.sendMessage(Colours.colors("&4 Please use in format '/kick <username> [reason]'."));
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
            targetPlayer.kickPlayer(Colours.colors("&4&o You have been kicked by " + player.getName()));

        }



        return false;

    }
}
