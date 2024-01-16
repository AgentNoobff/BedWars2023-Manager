package me.agentkiiya.bwmanager;

import com.tomkeuper.bedwars.api.BedWars;
import me.agentkiiya.bwmanager.managers.addonmanager.AddonManager;
import me.agentkiiya.bwmanager.managers.addonmanager.command.AddonInventoryCommand;
import me.agentkiiya.bwmanager.managers.arenamanager.ArenaManager;
import me.agentkiiya.bwmanager.managers.statsmanager.StatsManager;
import me.agentkiiya.bwmanager.managers.statsmanager.command.StatsCommand;
import me.agentkiiya.bwmanager.utils.Utility;
import org.bukkit.plugin.java.JavaPlugin;

public class Manager extends JavaPlugin {

    // This is the instance of the plugin, it is used to get the plugin instance.
    private static Manager instance;

    // This is a BedWars2023 API instance, it is used to get the BedWars2023 API.
    public static BedWars bedWars;
    public static com.tomkeuper.bedwars.stats.StatsManager statsManager;

    @Override
    public void onEnable() {

        instance = this;

        getLogger().info(Utility.c("&eLoading commands..."));
        getServer().getPluginCommand("bwm").setExecutor(new AddonInventoryCommand());
        getLogger().info(Utility.c("&aCommands loaded successfully!"));

        // Here we initialize the managers.
        new AddonManager();
        new ArenaManager();
        new StatsManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // This is the method used to get the plugin instance.
    public static Manager getInstance() {
        return instance;
    }
}
