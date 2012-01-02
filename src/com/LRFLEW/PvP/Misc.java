package com.LRFLEW.PvP;

import java.util.HashMap;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Misc {
	
	public static boolean PvpMatchCheck ( HashMap<String, String> map, String attacker, String defender) {
		if (map.get(attacker) != null && 
				attacker != map.get(map.get(attacker))) { 
			// if the attacker's spar partner isn't sparing him...
			map.clear();
			Bukkit.broadcastMessage("All spars canceled due to exception");
			new MapMissmatchException().printStackTrace();
			return false;
		} else if (map.get(defender) != null && 
				defender != map.get(map.get(defender))) {
			//if the defender's spar partner isn't sparing him...
			map.clear();
			Bukkit.broadcastMessage("All spars canceled due to exception");
			new MapMissmatchException().printStackTrace();
			return false;
		} 
		return true;
	}
	
	public static class MapMissmatchException extends Exception {
		private static final long serialVersionUID = 5414711621521839496L;
	}
	
	public static class SparRCncl extends TimerTask {
		private final Player sparri;
		private final HashMap<String, String> set;

		public SparRCncl (Player sparri, HashMap<String, String> set) {
			this.set = set;
			this.sparri = sparri;
		}

		@Override
		public void run() {
			sparri.sendMessage(Settings.preFx + "The spar request with " + Bukkit.getPlayerExact(set.get(sparri)).getDisplayName() + " has been canceled");
			Bukkit.getPlayerExact(set.get(sparri.getName())).sendMessage(Settings.preFx + "The spar request with " + sparri.getDisplayName() + " has been canceled");
			set.remove(sparri.getName());
			this.cancel();
		}
	}
	
	public static class SparCncl extends TimerTask {
		private final Player sparri;
		private final HashMap<String, String> set;

		public SparCncl (Player sparri, HashMap<String, String> set) {
			this.set = set;
			this.sparri = sparri;
		}

		@Override
		public void run() {
			sparri.sendMessage(Settings.preFx + "The spar request with " + ChatColor.WHITE + Bukkit.getPlayerExact(set.get(sparri)).getDisplayName() + 
					Settings.preFx + " has been canceled");
			Bukkit.getPlayerExact(set.get(sparri.getName())).sendMessage(Settings.preFx + "The spar request with " + ChatColor.WHITE + sparri.getDisplayName() + 
					Settings.preFx + " has been canceled");
			set.remove(sparri.getName());
			this.cancel();
		}
	}
	
}
