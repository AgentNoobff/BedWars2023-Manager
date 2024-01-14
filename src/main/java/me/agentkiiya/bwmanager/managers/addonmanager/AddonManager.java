package me.agentkiiya.bwmanager.managers.addonmanager;

import com.tomkeuper.bedwars.api.BedWars;
import me.agentkiiya.bwmanager.Manager;
import me.agentkiiya.bwmanager.managers.addonmanager.command.ProxyAddonInventory;
import me.agentkiiya.bwmanager.utils.Utility;
import me.agentkiiya.bwmanager.managers.addonmanager.command.AddonInventoryCommand;
import me.agentkiiya.bwmanager.managers.addonmanager.listeners.InventoryListener;
import me.agentkiiya.bwmanager.managers.addonmanager.listeners.bedwars2023.MessageReceive;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import java.util.logging.Logger;

import static me.agentkiiya.bwmanager.Manager.bedWars;

public final class AddonManager {

    public AddonManager() {
        enable();
    }


    // This code is messy since it was made in a short amount of time, other managers will be cleaner.
    private void enable() {
       if (Bukkit.getPluginManager().isPluginEnabled("BedWars2023")) {
            getLogger().info(Utility.c("&aBedWars2023 found! Hooking..."));
            bedWars = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

            getLogger().info(Utility.c("&eLoading listeners..."));
            Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());

            try {
                Bukkit.getPluginManager().registerEvents(new MessageReceive(), getInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            getLogger().info(Utility.c("&aListeners loaded successfully!"));

            getLogger().info(Utility.c("&eLoading commands..."));
            getServer().getPluginCommand("addons").setExecutor(new AddonInventoryCommand());
            getLogger().info(Utility.c("&aCommands loaded successfully!"));

            getLogger().info(Utility.c("&aAddon Manager for BedWars2023 has been enabled successfully!"));
        } else if (Bukkit.getPluginManager().isPluginEnabled("BWProxy2023")) {
            getLogger().info(Utility.c("&aBWProxy2023 found! Hooking..."));

            getLogger().info(Utility.c("&eLoading listeners..."));
            Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());

            try {
                Bukkit.getPluginManager().registerEvents(new MessageReceive(), getInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            getLogger().info(Utility.c("&aListeners loaded successfully!"));

            getLogger().info(Utility.c("&eLoading commands..."));
            getServer().getPluginCommand("addons").setExecutor(new ProxyAddonInventory());
            getLogger().info(Utility.c("&aCommands loaded successfully!"));

            getLogger().info(Utility.c("&aAddon Manager for BWProxy2023 has been enabled successfully!"));
        } else {
           getLogger().info(Utility.c("&cI can't run because BedWars2023 or BWProxy2023 wasn't found! Disabling..."));
           Bukkit.getPluginManager().disablePlugin(getInstance());
       }
    }

    private static Manager getInstance() {
        return Manager.getInstance();
    }

    private static Logger getLogger() {
        return Manager.getInstance().getLogger();
    }

    private static Server getServer() {
        return Manager.getInstance().getServer();
    }
}
