package me.agentkiiya.bwmanager;

import com.tomkeuper.bedwars.api.BedWars;
import me.agentkiiya.bwmanager.managers.addonmanager.AddonManager;
import me.agentkiiya.bwmanager.managers.addonmanager.command.AddonInventoryCommand;
import me.agentkiiya.bwmanager.managers.arenamanager.ArenaManager;
import me.agentkiiya.bwmanager.managers.command.MainCommand;
import me.agentkiiya.bwmanager.managers.statsmanager.StatsManager;
import me.agentkiiya.bwmanager.utils.Utility;
import org.bukkit.plugin.java.JavaPlugin;

public class Manager extends JavaPlugin {

    // This is the instance of the plugin, it is used to get the plugin instance.
    private static Manager instance;

    // This is a BedWars2023 API instance, it is used to get the BedWars2023 API.
    public static BedWars bedWars;
    public static com.tomkeuper.bedwars.stats.StatsManager statsManager;
    public static boolean isBw2023 = false;
    public static boolean isBwProxy2023 = false;

    @Override
    public void onEnable() {

        isBw2023 = getServer().getPluginManager().isPluginEnabled("BedWars2023");
        isBwProxy2023 = getServer().getPluginManager().isPluginEnabled("BedWarsProxy");

        instance = this;

        getLogger().info(Utility.c("&eLoading commands..."));
        getServer().getPluginCommand("bwm").setExecutor(new MainCommand());
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
