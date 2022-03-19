package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // add ability for staff chat to see message
        //after testing, make it so you can't send yourself a message
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("simpleserver.message")) {
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return false;
            }
            if (args.length >= 2){
                if (Bukkit.getPlayerExact(args[0]) != null ){
                    Player target = Bukkit.getPlayerExact(args[0]);
                    StringBuilder message = new StringBuilder(args[1]);
                    for (int arg = 2; arg < args.length; arg++) {
                        message.append(" ").append(args[arg]);
                    }
                    player.sendMessage(Colours.colors("&6You: -> "+ target.getName() + message));
                    target.sendMessage(Colours.colors("&6"+player.getName() + " -> You: "+ message));
                } else {
                    player.sendMessage(Colours.colors("&4Player not found."));
                }
            } else {
                player.sendMessage(Colours.colors("&4Please use in format '/message [ign] [message]'."));
            }

        }
        return false;

    }
}
