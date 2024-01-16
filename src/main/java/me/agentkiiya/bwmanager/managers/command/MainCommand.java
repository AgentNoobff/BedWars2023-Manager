package me.agentkiiya.bwmanager.managers.command;

import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.stats.IPlayerStats;
import jdk.jshell.execution.Util;
import me.agentkiiya.bwmanager.utils.Utility;
import me.agentkiiya.bwmanager.managers.addonmanager.menu.bedwars2023.AddonsMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

import static me.agentkiiya.bwmanager.Manager.statsManager;
public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            Utility.sendMessage(sender, "&cUsage: /bwm <addons|arenas|stats> ... [args]");
        }
        if (args[0].equalsIgnoreCase("addons") || args[0].equalsIgnoreCase("addon")) {
            if (sender instanceof ConsoleCommandSender) {
                Utility.sendMessage(sender, "&cYou must be in the server to use this command");
                return false;
            }
            Player player = (Player) sender;

            if (!sender.hasPermission("bw.addons") || !sender.isOp() || !sender.hasPermission("bw.*") || !sender.hasPermission("*")) {
                Utility.sendMessage(sender, Language.getMsg(p, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(p, "prefix")));
                return false;
            }

            if (args.length > 2) {
                Utility.sendMessage(sender, Language.getMsg(p, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(p, "prefix")));
                return false;
            }

            String option;
            if (args.length == 1) {
                option = "--registered";
            } else {
                option = args[1];
            }

            new AddonsMenu(player, option);
            return false;
        } else if (args[0].equalsIgnoreCase("stats")) {
            Player player = (Player) sender;

            if (!sender.hasPermission("bw.statsmanager") || !sender.isOp() || !sender.hasPermission("bw.*") || !sender.hasPermission("*") || !(sender instanceof ConsoleCommandSender)) {
                Utility.sendMessage(sender, Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(p, "prefix")));
                return false;
            }


            if (args.length < 3) {
                Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
                return false;
            }

            OfflinePlayer target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                Utility.sendMessage(sender, "&cThat player does not exist!");
                return false;
            }

            IPlayerStats playerStats = statsManager.get(target.getUniqueId());
            int beds = playerStats.getBedsDestroyed();
            int finalDeaths = playerStats.getFinalDeaths();
            int finalKills = playerStats.getFinalKills();
            int deaths = playerStats.getDeaths();
            int kills = playerStats.getKills();
            int totalDeaths = playerStats.getDeaths() + playerStats.getFinalDeaths();
            int totalKills = playerStats.getTotalKills();
            Instant firstPlay = playerStats.getFirstPlay();
            Instant lastPlay = playerStats.getLastPlay();
            int gamesPlayed = playerStats.getGamesPlayed();
            int wins = playerStats.getWins();
            int losses = playerStats.getLosses();

            if (args.length == 3) {
                if (args[2].equalsIgnoreCase("get")) {
                    Utility.sendMessage(sender, "&e" + target.getName() + "&a's stats:");
                    Utility.sendMessage(sender, "");
                    Utility.sendMessage(sender, "&aGames Played: " + gamesPlayed);
                    Utility.sendMessage(sender, "&aWins: " + wins);
                    Utility.sendMessage(sender, "&aLosses: " + losses);
                    Utility.sendMessage(sender, "&aTotal Kills: " + totalKills);
                    Utility.sendMessage(sender, "&aFinal Kills: " + finalKills);
                    Utility.sendMessage(sender, "&aFinal Deaths: " + finalDeaths);
                    Utility.sendMessage(sender, "&aKills: " + kills);
                    Utility.sendMessage(sender, "&aDeaths: " + deaths);
                    Utility.sendMessage(sender, "&aFirst Played: " + firstPlay);
                    Utility.sendMessage(sender, "&aLast Played: " + lastPlay);
                    return true;
                } else if (args[2].equalsIgnoreCase("set")) {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> set <stat> <value>");
                } else {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
                }
            } else if (args.length == 4) {
                if (args[2].equalsIgnoreCase("get")) {
                    String stat = args[3].toLowerCase();
                    switch (stat) {
                        case "gamesplayed":
                        case "games":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has played &e" + gamesPlayed + "&agames.");
                            break;
                        case "wins":
                        case "win":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has won &e" + wins + "&agames.");
                            break;
                        case "losses":
                        case "loss":
                        case "loses":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has lost &e" + losses + "&agames.");
                            break;
                        case "totaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalDeaths + "&adeaths in total (normal + final).");
                            break;
                        case "totalkills":
                        case "totalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalKills + "&akills in total (normal + final).");
                            break;
                        case "finalkills":
                        case "finals":
                        case "finalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalKills + "&afinal kills.");
                            break;
                        case "finaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalDeaths + "&afinal deaths.");
                            break;
                        case "kills":
                        case "normalkills":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + kills + "&anormal kills.");
                            break;
                        case "deaths":
                        case "normaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + deaths + "&anormal deaths.");
                            break;
                        case "firstplayed":
                        case "firstplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a first played on this server on &e" + firstPlay);
                            break;
                        case "lastplayed":
                        case "lastplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a last played on this server on &e" + lastPlay);
                            break;
                        default:
                            Utility.sendMessage(sender, "&cAvailable stats: gamesPlayed, wins, losses, totalDeaths, totalKills, finalKills, finalDeaths, kills, deaths, firstplay, lastplay");
                    }
                } else if (args[2].equalsIgnoreCase("set")) {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> set <stat> <value>");
                } else {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
                }
            } else if (args.length == 5) {
                String stat = args[3].toLowerCase();
                int valueInt = Integer.parseInt(args[4]);
                Instant valueInst = Instant.parse(args[4]);
                if (args[2].equalsIgnoreCase("get")) {
                    switch (stat) {
                        case "gamesplayed":
                        case "games":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has played &e" + gamesPlayed + "&agames.");
                            break;
                        case "wins":
                        case "win":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has won &e" + wins + "&agames.");
                            break;
                        case "losses":
                        case "loss":
                        case "loses":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has lost &e" + losses + "&agames.");
                            break;
                        case "totaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalDeaths + "&adeaths in total (normal + final).");
                            break;
                        case "totalkills":
                        case "totalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalKills + "&akills in total (normal + final).");
                            break;
                        case "finalkills":
                        case "finals":
                        case "finalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalKills + "&afinal kills.");
                            break;
                        case "finaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalDeaths + "&afinal deaths.");
                            break;
                        case "kills":
                        case "normalkills":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + kills + "&anormal kills.");
                            break;
                        case "deaths":
                        case "normaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + deaths + "&anormal deaths.");
                            break;
                        case "firstplayed":
                        case "firstplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a first played on this server on &e" + firstPlay);
                            break;
                        case "lastplayed":
                        case "lastplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a last played on this server on &e" + lastPlay);
                            break;
                        default:
                            Utility.sendMessage(sender, "&cAvailable stats: gamesPlayed, wins, losses, totalDeaths, totalKills, finalKills, finalDeaths, kills, deaths, firstplay, lastplay");
                    }
                } else if (args[2].equalsIgnoreCase("set")) {
                    switch (stat) {
                        case "gamesplayed":
                        case "games":
                            playerStats.setGamesPlayed(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has played &e" + gamesPlayed + "&agames.");
                            break;
                        case "wins":
                        case "win":
                            playerStats.setWins(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has won &e" + wins + "&agames.");
                            break;
                        case "losses":
                        case "loss":
                        case "loses":
                            playerStats.setLosses(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has lost &e" + losses + "&agames.");
                            break;
                        case "finalkills":
                        case "finals":
                        case "finalkill":
                            playerStats.setFinalKills(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalKills + "&afinal kills.");
                            break;
                        case "finaldeaths":
                            playerStats.setFinalDeaths(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalDeaths + "&afinal deaths.");
                            break;
                        case "kills":
                        case "normalkills":
                            playerStats.setKills(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + kills + "&anormal kills.");
                            break;
                        case "deaths":
                        case "normaldeaths":
                            playerStats.setDeaths(valueInt);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + deaths + "&anormal deaths.");
                            break;
                        case "firstplayed":
                        case "firstplay":
                            playerStats.setFirstPlay(valueInst);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a first played on this server on &e" + firstPlay);
                            break;
                        case "lastplayed":
                        case "lastplay":
                            playerStats.setLastPlay(valueInst);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a last played on this server on &e" + lastPlay);
                            break;
                        default:
                            Utility.sendMessage(sender, "&cAvailable stats: gamesPlayed, wins, losses, finalKills, finalDeaths, kills, deaths, firstplay, lastplay");
                    }
                } else {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> set <stat> <value>");
                }
            } else {
                Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
            }
            return false;
        }
    }
}
