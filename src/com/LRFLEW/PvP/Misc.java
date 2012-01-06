package com.LRFLEW.PvP;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Misc {
	
	public static boolean PvpMatchCheck ( HashMap<String, String> map, String attacker, String defender) {
		if (map.get(attacker) != null && 
				attacker != map.get(map.get(attacker))) { 
			// if the attacker's spar partner isn't sparing him...
			map.clear();
			Bukkit.broadcastMessage("All spars canceled due to exception");
			new MapMissmatchException(attacker + " -> " + defender).printStackTrace();
			return false;
		} else if (map.get(defender) != null && 
				defender != map.get(map.get(defender))) {
			//if the defender's spar partner isn't sparing him...
			map.clear();
			Bukkit.broadcastMessage("All spars canceled due to exception");
			new MapMissmatchException(defender + " -> " + attacker).printStackTrace();
			return false;
		} 
		return true;
	}
	
	public static class MapMissmatchException extends Exception {
		private static final long serialVersionUID = 5414711621521839496L;
		public MapMissmatchException(String string) { super(string); }
	}
	
	public static class Cleanup implements Runnable {
		private final PvP plugin;
		
		public Cleanup (PvP instance) {
			plugin = instance;
		}

		@Override
		public void run() {
			for (Map.Entry<String, Long> e : plugin.cooldown.entrySet()) {
				if (e.getValue() <= System.currentTimeMillis())
					plugin.cooldown.remove(e.getKey());
			}
		}
		
	}
	
	public static void announceExclude (String msg, Player... players) {
		Player[] list = Bukkit.getOnlinePlayers();
		for (Player p : list) {
			boolean test = true;
			for (Player t : players) {
				if (p == t) test = false;
			}
			if (test) p.sendMessage(msg);
		}
	}
	
	public static void cleanupCooldown (PvP plugin, String name) {
		if (plugin.cooldown.get(name) != null && 
				plugin.cooldown.get(name) <= System.currentTimeMillis())
			plugin.cooldown.remove(name);
	}
	
}
