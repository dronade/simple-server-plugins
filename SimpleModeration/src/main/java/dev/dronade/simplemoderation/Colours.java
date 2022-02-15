package dev.dronade.simplemoderation;

import org.bukkit.ChatColor;

import java.util.Objects;

public class Colours {
    public static String colors(String text) {
        Objects.requireNonNull(text, "Must supply text");
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
