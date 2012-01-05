package com.LRFLEW.PvP;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PvP extends JavaPlugin {
	
	byte killSwitch = 0;
	HashSet<String> PvP = new HashSet<String>();
	HashMap<String, Long> cooldown = new HashMap<String, Long>();
	HashMap<String, String> spar = new HashMap<String, String>();
	HashMap<String, String> sparRequest = new HashMap<String, String>();
	HashMap<String, Long> sparExpire = new HashMap<String, Long>();
	
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
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, sets.priority, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);
		
		this.getCommand("pvp").setExecutor(new Commands(this));
		
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Misc.Cleanup(this), 
				sets.cleanuptime*2, sets.cleanuptime);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	
	/*{ // Test area :O
		
		getServer().getLogger().log(Level.WARNING, "My name is Michael J. Caboose, and I... Hate... BABIES!!!");
		
	}*/
	
}