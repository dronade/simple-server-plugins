package dev.dronade.simplemoderation.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ModerationCommand {
    private String permission;
    private String noPermissionError;

    public void setPermission(String permission) {
        this.permission = permission;
    }
    // to implement these at some point but it makes my brain hurt
    public boolean permissionCheck(CommandSender commandSender, Command command, String label, String[] args) {

        return false;
    }
    public boolean argumentCheck(CommandSender commandSender){

        return false;
    }
    public boolean playerExistsCheck(CommandSender commandSender){

        return false;
    }
}
