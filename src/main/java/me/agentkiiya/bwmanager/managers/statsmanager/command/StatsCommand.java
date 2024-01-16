package me.agentkiiya.bwmanager.managers.statsmanager.command;

import com.tomkeuper.bedwars.api.language.Language;
import me.agentkiiya.bwmanager.utils.Utility;
import me.agentkiiya.bwmanager.managers.addonmanager.menu.bedwars2023.AddonsMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!sender.hasPermission("bw.statsmanager") || !sender.isOp() || !sender.hasPermission("bw.*") || !sender.hasPermission("*") || !sender.equals(Bukkit.getConsoleSender())) {
            sender.sendMessage(Utility.c(Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(p, "prefix"))));
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage(Utility.c(Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(p, "prefix"))));
            return false;
        }

        String option;
        if (args.length == 0) {
            option = "--registered";
        } else {
            option = args[0];
        }

        new AddonsMenu(p, option);
        return false;
    }
}
