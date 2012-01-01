package com.LRFLEW.PvP;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHashMap extends HashMap<String, String> {
	private static final long serialVersionUID = 5175749743135991209L;
	
	public Player get(Player k) {
		return Bukkit.getPlayerExact(get(k.getName()));
	}
	
	public Player put(Player k, Player v) {
		return Bukkit.getPlayerExact(put(k.getName(), v.getName()));
	}
	
	public Player remove(Player k) {
		return Bukkit.getPlayerExact(remove(k.getName()));
	}
	
	//Awkward code :P
	
	public Set<Map.Entry<Player,Player>> entrySetP() {
		Set<Map.Entry<Player, Player>> set = new HashSet<Map.Entry<Player, Player>>();
		
		for (Map.Entry<String, String> entry : entrySet()) {
			set.add(new Entry(entry));
		}
		
		return set;
	}
	
	public Set<Player> keySetP() {
		Set<Player> set = new HashSet<Player>();
		
		for (String s : keySet()) {
			set.add(Bukkit.getPlayerExact(s));
		}
		
		return set;
	}
	
	public Collection<Player> valuesP() {
		Set<Player> set = new HashSet<Player>();
		
		for (String s : values()) {
			set.add(Bukkit.getPlayerExact(s));
		}
		
		return set;
	}
	
	private class Entry implements Map.Entry<Player, Player> {
		private final Map.Entry<String, String> entry;
		
		Entry (Map.Entry<String, String> e) {
			entry = e;
		}

		@Override
		public Player getKey() {
			return Bukkit.getPlayerExact(entry.getKey());
		}

		@Override
		public Player getValue() {
			return Bukkit.getPlayerExact(entry.getValue());
		}

		@Override
		public Player setValue(Player paramV) {
			return Bukkit.getPlayerExact(entry.setValue(paramV.getName()));
		}
		
	}
	
}
