package com.LRFLEW.PvP;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;

public class Settings {
	
	public boolean pvpDefault = false;
	public int cooldownOnToOff = 10;
	public int cooldownOffToOn = 5;
	public int cooldownAttack = 5;
	public int cooldownSparRequest = 30;
	public int cooldownSparCombat = 120;
	
	public boolean announce = true;
	public boolean loginMessage = true;
	public boolean adminOverride = true;
	
	public Event.Priority priority = Event.Priority.Low;
	
	public static final ChatColor preFx = ChatColor.RED;

	public void load () {
		
	}
	
	public void save () {
		
	}
	
}
