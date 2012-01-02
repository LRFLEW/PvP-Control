package com.LRFLEW.PvP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	private final PvP plugin;
	
	protected Commands (PvP instance) {
		plugin = instance;
	}
	
	public boolean onCommand (CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("on")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (plugin.cooldown.get(player.getName()) != null && plugin.cooldown.get(player.getName()) > System.currentTimeMillis()) {
					player.sendMessage(Settings.preFx + "You need to wait " + plugin.sets.cooldownOnToOff + " seconds before you can turn PVP off");
					return true;
				} else {
					if (plugin.cooldown.containsKey(player.getName())) plugin.cooldown.remove(player.getName());
					if (!plugin.PvP.contains(player.getName())) {
						plugin.PvP.add(player.getName());
						plugin.cooldown.put(player.getName(), System.currentTimeMillis() + (plugin.sets.cooldownOnToOff*1000));
					}
					player.sendMessage(Settings.preFx + "PvP is " + ChatColor.WHITE + "On" + Settings.preFx + " for you. Beware! " +
							"Turn it off by typing " + ChatColor.WHITE + "/pvp off");
					return true;
				}
			}
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("off")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (plugin.cooldown.get(player.getName()) != null && plugin.cooldown.get(player.getName()) > System.currentTimeMillis()) {
					player.sendMessage(Settings.preFx + "You need to wait " + plugin.sets.cooldownOnToOff + " seconds before you can turn PVP off");
					return true;
				} else {
					if (plugin.PvP.contains(player.getName())) {
						plugin.PvP.remove(player.getName());
						plugin.cooldown.put(player.getName(), System.currentTimeMillis() + (plugin.sets.cooldownOffToOn*1000));
					}
					player.sendMessage(Settings.preFx + "PvP is " + ChatColor.WHITE + "Off" + Settings.preFx + " for you. " +
							"Just look out for spiders :)");
					return true;
				}
			}
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("spar")) {
			if (sender instanceof Player) {
				Player sparrer = (Player)sender;
				
				if (args.length > 1) {
					Player sparri = plugin.getServer().getPlayer(args[1]);
					if (sparri == null) {
						sparrer.sendMessage("Player not Found");
						return true;
					}
					plugin.sparRequest.put(sparri, sparrer);
					sparri.sendMessage(sparrer.getDisplayName() + Settings.preFx + " wants to spar you.  ");
					sparri.sendMessage(Settings.preFx + "Accept by typing " + ChatColor.WHITE + "/pvp yes" + 
							Settings.preFx + " or deny with " + ChatColor.WHITE + "/pvp no");
					sparrer.sendMessage("Request sent to " + sparri.getDisplayName());
					return true;
				}
			}
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("yes")) {
			if (sender instanceof Player) {
				Player sparri = (Player)sender;
				if (plugin.sparRequest.containsKey(sparri)) {
					if (plugin.sparRequest.get(sparri) == null) {
						plugin.sparRequest.remove(sparri);
						System.out.println("ERROR: " + sparri.getName() + " has been requested to be sparred by null");
					} else {
						Player sparrer = plugin.sparRequest.get(sparri);
						plugin.sparRequest.remove(sparri);
						plugin.spar.put(sparri, sparrer);
						plugin.spar.put(sparrer, sparri);
						sparri.sendMessage(Settings.preFx + "You are now sparring " + ChatColor.WHITE + sparrer.getDisplayName() + 
								Settings.preFx + ".  Good luck");
						sparrer.sendMessage(Settings.preFx + "You are now sparring " + ChatColor.WHITE + sparrer.getDisplayName() + 
								Settings.preFx + ".  Good luck");
						return true;
					}
				}
			}
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("no")) {
			if (sender instanceof Player) {
				Player sparri = (Player)sender;
				if (plugin.sparRequest.containsKey(sparri)) {
					if (plugin.sparRequest.get(sparri) == null) {
						plugin.sparRequest.remove(sparri);
						System.out.println("ERROR: " + sparri.getName() + " has denied to be sparred by null");
					} else {
						Player sparrer = plugin.sparRequest.get(sparri);
						plugin.sparRequest.remove(sparri);
						sparri.sendMessage("You have denied the spar request from " + sparrer.getDisplayName());
						sparrer.sendMessage(sparrer.getDisplayName() + " Denied you sparring request.  Sorry");
						return true;
					}
				}
			}
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("killswitch") || args.length >= 1 && args[0].equalsIgnoreCase("ks")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (player.isOp()) {
					if (args[1].equalsIgnoreCase("dissable") || args[1].equalsIgnoreCase("dis")) {
						plugin.killSwitch = 0;
						player.sendMessage("Users can now set their own PvP status");
					} else {
						if (args[1].equalsIgnoreCase("on")) {
							plugin.killSwitch = 1;
							player.sendMessage("PvP is now forced on for everybody");
						} if (args[1].equalsIgnoreCase("off")) {
							plugin.killSwitch = -1;
							player.sendMessage("PvP is now forced off for everybody");
						}
					}
					return true;
				} else {
					player.sendMessage("You don't have permissions to run this command");
				}
			} else {
				if (args[1] == "null") {
					plugin.killSwitch = 0;
					System.out.println("Users can now set their own PvP status");
				} else {
					plugin.killSwitch = Byte.parseByte(args[1]);
					if (plugin.killSwitch >= 1) {
						System.out.println("PvP is now forced on for everybody");
					} else {
						System.out.println("PvP is now forced off for everybody");
					}
				}
			}
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("list")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				player.sendMessage(Settings.preFx + "Players with PvP on:");
				for (String p : plugin.PvP) {
					player.sendMessage("    " + Bukkit.getPlayerExact(p).getDisplayName());
				}
			} else {
				System.out.println("Players with PvP on:");
				for (String p : plugin.PvP) {
					System.out.println("\t" + p);
				}
			}
			return true;
		}
		if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				player.sendMessage(Settings.preFx + "Usage:");
				player.sendMessage(ChatColor.WHITE + "   /PvP on" + Settings.preFx + " - you can be attacked by players");
				player.sendMessage(ChatColor.WHITE + "   /PvP off" + Settings.preFx + " - players can't attack you");
				return true;
			} else {
				System.out.println("Usage:");
				System.out.println("\t/PvP on" + Settings.preFx + " - you can be attacked by players");
				System.out.println("\t/PvP off" + Settings.preFx + " - players can't attack you");
			}
		}
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if ( plugin.PvP.contains(player.getName())) {
				player.sendMessage(Settings.preFx + "PvP is " + ChatColor.WHITE + "On" + Settings.preFx + " for you. Beware!");
			} else {
				player.sendMessage(Settings.preFx + "PvP is " + ChatColor.WHITE + "Off" + Settings.preFx + " for you. " +
						"Just look out for spiders :)");
			}
			player.sendMessage(Settings.preFx + "Usage:");
			player.sendMessage(ChatColor.WHITE + "   /PvP on" + Settings.preFx + " - you can be attacked by players");
			player.sendMessage(ChatColor.WHITE + "   /PvP off" + Settings.preFx + " - players can't attack you");
			return true;
		} else {
			System.out.println("Type \"pvp help\" for a list of commands");
		}
		return true;
	}
	
}
