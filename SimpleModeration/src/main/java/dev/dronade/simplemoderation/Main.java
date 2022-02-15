package dev.dronade.simplemoderation;

import dev.dronade.simplemoderation.Commands.Kick;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("kick").setExecutor(new Kick());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
