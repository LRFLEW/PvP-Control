package com.LRFLEW.PvP;

import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Misc {
	
	public static boolean PvpMatchCheck ( PlayerHashMap map, Player attacker, Player defender) {
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
		private final PlayerHashMap set;

		public SparRCncl (Player sparri, PlayerHashMap set) {
			this.set = set;
			this.sparri = sparri;
		}

		@Override
		public void run() {
			sparri.sendMessage(Settings.preFx + "The spar request with " + set.get(sparri).getDisplayName() + " has been canceled");
			set.get(sparri).sendMessage(Settings.preFx + "The spar request with " + sparri.getDisplayName() + " has been canceled");
			set.remove(sparri);
			this.cancel();
		}
	}
	
	public static class SparCncl extends TimerTask {
		private final Player sparri;
		private final PlayerHashMap set;

		public SparCncl (Player sparri, PlayerHashMap set) {
			this.set = set;
			this.sparri = sparri;
		}

		@Override
		public void run() {
			sparri.sendMessage(Settings.preFx + "The spar request with " + ChatColor.WHITE + set.get(sparri).getDisplayName() + 
					Settings.preFx + " has been canceled");
			set.get(sparri).sendMessage(Settings.preFx + "The spar request with " + ChatColor.WHITE + sparri.getDisplayName() + 
					Settings.preFx + " has been canceled");
			set.remove(sparri);
			this.cancel();
		}
	}
	
}
