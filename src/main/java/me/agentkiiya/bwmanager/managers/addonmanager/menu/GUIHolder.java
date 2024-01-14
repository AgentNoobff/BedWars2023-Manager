package me.agentkiiya.bwmanager.managers.addonmanager.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface GUIHolder extends InventoryHolder {
    void onInventoryClick(InventoryClickEvent e);
}
