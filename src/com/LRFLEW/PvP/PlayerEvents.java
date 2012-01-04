package com.LRFLEW.PvP;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents extends PlayerListener{
	
	private final PvP plugin;
	
	public PlayerEvents(PvP instance) {
        plugin = instance;
    }
	
	// Player references Events
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!plugin.sets.loginMessage) return;
		if (plugin.sets.pvpDefault) {
			event.getPlayer().sendMessage(Settings.preFx + "PvP is now turned " + 
					ChatColor.WHITE + "On" + Settings.preFx + " for you. " +
					"To turn it on, type " + ChatColor.WHITE + "/pvp off");
		} else {
			event.getPlayer().sendMessage(Settings.preFx + "PvP is now turned " + 
					ChatColor.WHITE + "Off" + Settings.preFx + " for you. " +
					"To turn it on, type " + ChatColor.WHITE + "/pvp on");
		}
	}
	
	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		plugin.PvP.remove(name);
		plugin.cooldown.remove(name);
		if (plugin.spar.get(name) != null)
			plugin.spar.remove(plugin.spar.get(name));
		plugin.spar.remove(name);
		if (plugin.sparRequest.get(name) != null)
			plugin.sparRequest.remove(
					plugin.sparRequest.get(name));
		plugin.sparRequest.remove(name);
	}
	
}

