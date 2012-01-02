package com.LRFLEW.PvP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PvP extends JavaPlugin {
	
	byte killSwitch = 0;
	Timer timer = new Timer();
	HashSet<String> PvP = new HashSet<String>();
	HashMap<String, Long> cooldown = new HashMap<String, Long>();
	PlayerHashMap spar = new PlayerHashMap();
	PlayerHashMap sparRequest = new PlayerHashMap();
	
	public final Settings sets = new Settings();
	
	public void onDisable() {
		
		sets.save();

		// NOTE: All registered events are automatically unregistered when a plugin is disabled
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " says Goodbye!" );
	}

	public void onEnable() {
		
		sets.load();
		
		// Register our events
		PluginManager pm = getServer().getPluginManager();
		EntityEvents entityListener = new EntityEvents(this);
		PlayerEvents playerListener = new PlayerEvents(this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Low, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
		
		this.getCommand("pvp").setExecutor(new Commands(this));
		
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	
	/*{ // Test area :O
		
		getServer().getLogger().log(Level.WARNING, "My name is Michael J. Caboose, and I... Hate... BABIES!!!");
		
	}*/
	
}