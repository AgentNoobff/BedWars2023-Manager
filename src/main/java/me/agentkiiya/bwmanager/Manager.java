package me.agentkiiya.bwmanager;

import com.tomkeuper.bedwars.api.BedWars;
import me.agentkiiya.bwmanager.managers.addonmanager.AddonManager;
import me.agentkiiya.bwmanager.managers.arenamanager.ArenaManager;
import me.agentkiiya.bwmanager.managers.statsmanager.StatsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Manager extends JavaPlugin {
    private static Manager instance;

    public static BedWars bedWars;

    @Override
    public void onEnable() {
        instance = this;
        new AddonManager();
        new ArenaManager();
        new StatsManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Manager getInstance() {
        return instance;
    }
}
