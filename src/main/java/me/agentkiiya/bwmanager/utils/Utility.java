package me.agentkiiya.bwmanager.utils;

import org.bukkit.ChatColor;
import java.util.List;
import java.util.stream.Collectors;

public class Utility {

    /**
     * Colorize a string
     * @param text The string to colorize
     * @return The colorized string
     */
    public static String c(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Colorize a list of strings
     * @param list The list of strings to colorize
     * @return The colorized list of strings
     */
    public static List<String> cList(List<String> list) {
        return list.stream().map(Utility::c).collect(Collectors.toList());
    }
}
