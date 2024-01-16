package me.agentkiiya.bwmanager.managers.command;

import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.stats.IPlayerStats;
import com.tomkeuper.bedwars.stats.StatsAPI;
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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import static me.agentkiiya.bwmanager.Manager.*;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            Utility.sendMessage(sender, "&cUsage: /bwm <addons|arenas|stats> ... [args]");
            return false;
        }
        if (args[0].equalsIgnoreCase("addons") || args[0].equalsIgnoreCase("addon")) {
            if (isBw2023) {
                if (sender instanceof ConsoleCommandSender) {
                    Utility.sendMessage(sender, "&cYou must be in the server to use this command");
                    return false;
                }
                Player player = (Player) sender;

                if (!sender.hasPermission("bw.addons") || !sender.isOp() || !sender.hasPermission("bw.*") || !sender.hasPermission("*")) {
                    Utility.sendMessage(sender, Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(player, "prefix")));
                    return false;
                }

                if (args.length > 2) {
                    Utility.sendMessage(sender, Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(player, "prefix")));
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
            } else if (isBwProxy2023) {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(Utility.c("&cYou must be in the server to use this command"));
                    return false;
                }
                Player player = (Player) sender;

                if (!player.hasPermission("bw.addons") || !player.isOp() || !player.hasPermission("bw.*") || !player.hasPermission("*")) {
                    player.sendMessage(Utility.c(Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(player, "prefix"))));
                    return false;
                }

                if (args.length > 2) {
                    player.sendMessage(Utility.c(Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(player, "prefix"))));
                    return false;
                }

                String option;
                if (args.length == 1) {
                    option = "--registered";
                } else {
                    option = args[1];
                }

                new me.agentkiiya.bwmanager.managers.addonmanager.menu.proxy.AddonsMenu(player, option);
                return false;
            }
        } else if (args[0].equalsIgnoreCase("stats")) {
            Player player = (Player) sender;

            if (!sender.hasPermission("bw.statsmanager") && !sender.isOp() && !sender.hasPermission("bw.*") && !sender.hasPermission("*")) {
                if (player instanceof ConsoleCommandSender) {
                    Utility.sendMessage(sender, Language.getMsg(Language.getDefaultLanguage(), Bukkit.getPlayer("AgentNoobff"), "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(player, "prefix")));
                } else {
                    Utility.sendMessage(sender, Language.getMsg(player, "cmd-not-found").replace("%bw_lang_prefix%", Language.getMsg(player, "prefix")));
                }
                return false;
            }


            if (args.length < 3) {
                Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
                return false;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (target == null) {
                Utility.sendMessage(sender, "&cThat player does not exist!");
                return false;
            }

            IPlayerStats playerStats;
            playerStats = statsManager.getUnsafe(target.getUniqueId());
            if (playerStats == null) {
                playerStats = bedWars.getRemoteDatabase().fetchStats(target.getUniqueId());
            }
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
                    Utility.sendMessage(sender, "&aBeds Destroyed: " + beds);
                    Utility.sendMessage(sender, "&aTotal Kills: " + totalKills);
                    Utility.sendMessage(sender, "&aFinal Kills: " + finalKills);
                    Utility.sendMessage(sender, "&aFinal Deaths: " + finalDeaths);
                    Utility.sendMessage(sender, "&aKills: " + kills);
                    Utility.sendMessage(sender, "&aDeaths: " + deaths);
                    Utility.sendMessage(sender, "&aFirst Played: " + Timestamp.from(firstPlay));
                    Utility.sendMessage(sender, "&aLast Played: " + Timestamp.from(lastPlay));
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
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has played &e" + gamesPlayed + "&a games.");
                            break;
                        case "wins":
                        case "win":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has won &e" + wins + "&a games.");
                            break;
                        case "losses":
                        case "loss":
                        case "loses":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has lost &e" + losses + "&a games.");
                            break;
                        case "beds":
                        case "bedsdestroyed":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has broken &e" + beds + "&a beds.");
                            break;
                        case "totaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalDeaths + "&a deaths in total (normal + final).");
                            break;
                        case "totalkills":
                        case "totalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalKills + "&a kills in total (normal + final).");
                            break;
                        case "finalkills":
                        case "finals":
                        case "finalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalKills + "&a final kills.");
                            break;
                        case "finaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalDeaths + "&a final deaths.");
                            break;
                        case "kills":
                        case "normalkills":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + kills + "&a normal kills.");
                            break;
                        case "deaths":
                        case "normaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + deaths + "&a normal deaths.");
                            break;
                        case "firstplayed":
                        case "firstplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a first played on this server on &e" + Timestamp.from(firstPlay));
                            break;
                        case "lastplayed":
                        case "lastplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a last played on this server on &e" + Timestamp.from(lastPlay));
                            break;
                        default:
                            Utility.sendMessage(sender, "&cAvailable stats: gamesPlayed, wins, losses, beds, totalDeaths, totalKills, finalKills, finalDeaths, kills, deaths, firstplay, lastplay");
                    }
                } else if (args[2].equalsIgnoreCase("set")) {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> set <stat> <value>");
                } else {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
                }
            } else if (args.length == 5) {
                String stat = args[3].toLowerCase();
                int valueInt = Integer.parseInt(args[4]);
                if (args[2].equalsIgnoreCase("get")) {
                    switch (stat) {
                        case "gamesplayed":
                        case "games":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has played &e" + gamesPlayed + "&a games.");
                            break;
                        case "wins":
                        case "win":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has won &e" + wins + "&a games.");
                            break;
                        case "losses":
                        case "loss":
                        case "loses":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has lost &e" + losses + "&a games.");
                            break;
                        case "beds":
                        case "bedsdestroyed":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has broken &e" + beds + "&a beds.");
                            break;
                        case "totaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalDeaths + "&a deaths in total (normal + final).");
                            break;
                        case "totalkills":
                        case "totalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + totalKills + "&a kills in total (normal + final).");
                            break;
                        case "finalkills":
                        case "finals":
                        case "finalkill":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalKills + "&a final kills.");
                            break;
                        case "finaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + finalDeaths + "&a final deaths.");
                            break;
                        case "kills":
                        case "normalkills":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + kills + "&a normal kills.");
                            break;
                        case "deaths":
                        case "normaldeaths":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + deaths + "&a normal deaths.");
                            break;
                        case "firstplayed":
                        case "firstplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a first played on this server on &e" + Timestamp.from(firstPlay));
                            break;
                        case "lastplayed":
                        case "lastplay":
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a last played on this server on &e" + Timestamp.from(lastPlay));
                            break;
                        default:
                            Utility.sendMessage(sender, "&cAvailable stats: gamesPlayed, wins, losses, beds, totalDeaths, totalKills, finalKills, finalDeaths, kills, deaths, firstplay, lastplay");
                    }
                } else if (args[2].equalsIgnoreCase("set")) {
                    switch (stat) {
                        case "gamesplayed":
                        case "games":
                            playerStats.setGamesPlayed(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has played &e" + valueInt + "&a games.");
                            break;
                        case "wins":
                        case "win":
                            playerStats.setWins(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has won &e" + valueInt + "&a games.");
                            break;
                        case "losses":
                        case "loss":
                        case "loses":
                            playerStats.setLosses(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has lost &e" + valueInt + "&a games.");
                            break;
                        case "beds":
                        case "bedsdestroyed":
                            playerStats.setBedsDestroyed(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has broken &e" + valueInt + "&a beds.");
                            break;
                        case "finalkills":
                        case "finals":
                        case "finalkill":
                            playerStats.setFinalKills(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + valueInt + "&a final kills.");
                            break;
                        case "finaldeaths":
                            playerStats.setFinalDeaths(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + valueInt + "&a final deaths.");
                            break;
                        case "kills":
                        case "normalkills":
                            playerStats.setKills(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + valueInt + "&a normal kills.");
                            break;
                        case "deaths":
                        case "normaldeaths":
                            playerStats.setDeaths(valueInt);
                            bedWars.getRemoteDatabase().saveStats(playerStats);
                            Utility.sendMessage(sender, "&e" + target.getName() + "&a has &e" + valueInt + "&a normal deaths.");
                            break;
                        default:
                            Utility.sendMessage(sender, "&cAvailable stats: gamesPlayed, wins, losses, beds, finalKills, finalDeaths, kills, deaths");
                    }
                } else {
                    Utility.sendMessage(sender, "&cUsage: /bwm stats <player> set <stat> <value>");
                }
            } else {
                Utility.sendMessage(sender, "&cUsage: /bwm stats <player> <get|set> <stat> <value>");
            }
            return false;
        }
        return true;
    }
}
