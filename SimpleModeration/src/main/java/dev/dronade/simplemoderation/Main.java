package dev.dronade.simplemoderation;

import dev.dronade.simplemoderation.Commands.Ban;
import dev.dronade.simplemoderation.Commands.Kick;
import dev.dronade.simplemoderation.Commands.Mute;
import dev.dronade.simplemoderation.Commands.Warn;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("kick").setExecutor(new Kick());
        getCommand("warn").setExecutor(new Warn());
        getCommand("ban").setExecutor(new Ban());
        getCommand("mute").setExecutor(new Mute());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
