package me.agentkiiya.bwmanager.managers.arenamanager;

import me.agentkiiya.bwmanager.Manager;
import org.bukkit.Server;

import java.util.logging.Logger;

public class ArenaManager {
    public ArenaManager() {
        enable();
    }

    public void enable() {

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
