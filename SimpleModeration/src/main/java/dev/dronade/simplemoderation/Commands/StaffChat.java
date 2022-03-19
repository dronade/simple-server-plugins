package dev.dronade.simplemoderation.Commands;

import dev.dronade.simplemoderation.Colours;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * staff chat command, usage: /sc [message]
 * @author Emily
 */

public class StaffChat implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String commandLabel, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("simpleserver.staffchat")){
                if(args.length > 0){
                    StringBuilder message = new StringBuilder(args[0]);
                    for (int arg = 1; arg < args.length; arg++) {
                        message.append(" ").append(args[arg]);
                    }

                    for(Player otherStaff:Bukkit.getOnlinePlayers()){
                        if(otherStaff.hasPermission("simpleserver.staffchat")){
                            otherStaff.sendMessage(Colours.colors("&4&l[StaffChat] &r" + player.getName() + ": " + message.toString()));
                        }
                    }
                    return true;
                }
            }else{
                player.sendMessage(Colours.colors("&4You are not permitted to use this command."));
                return true;
            }
        }
        return false;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();
            for(Player otherStaff: Bukkit.getOnlinePlayers()){
                if(otherStaff.hasPermission("simpleserver.staffchat")){
                    otherStaff.sendMessage(Colours.colors( "&4&l[StaffChat] "+  player.getName() + "&f&r: " + msg));
                }
            }
        }
}
