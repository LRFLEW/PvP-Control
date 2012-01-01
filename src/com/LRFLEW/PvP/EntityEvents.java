package com.LRFLEW.PvP;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class EntityEvents extends EntityListener{
	
    private final PvP plugin;
	
	public EntityEvents(PvP instance) {
        plugin = instance;
    }
	
	@Override
	public void onEntityDamage (EntityDamageEvent event) {
		if ( event instanceof EntityDamageByEntityEvent ) {
			EntityDamageByEntityEvent entEvent = (EntityDamageByEntityEvent)event;
			if ( (entEvent.getDamager() instanceof Player) && (entEvent.getEntity() instanceof Player) ) {
				Player attacker = (Player)entEvent.getDamager();
				Player defender = (Player)entEvent.getEntity();
				if (plugin.killSwitch == 0) {
					if ((plugin.spar.get(attacker) != null) || (plugin.spar.get(defender) != null)) {
						if (Misc.PvpMatchCheck(plugin.spar, attacker, defender)) {
							if (defender == plugin.spar.get(attacker)) {
								event.setCancelled(false);
							} else {
								event.setCancelled(true);
							}
						}
					} else if (plugin.sets.pvpDefault){
						if ( plugin.PvP.contains(attacker) || plugin.PvP.contains(defender) ) {
							event.setCancelled(true);
						} else {
							event.setCancelled(false);
						}
					} else {
						if ( !plugin.PvP.contains(attacker) || !plugin.PvP.contains(defender) ) {
							event.setCancelled(true);
						} else {
							event.setCancelled(false);
						}
					}
				} else if (plugin.killSwitch >= 1){
					event.setCancelled(false);
				} else {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@Override
	public void onEntityDeath (EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player dier = (Player)event.getEntity();
			if (plugin.spar.containsKey(dier)) {
				Player killer = plugin.spar.get(dier);
				plugin.spar.remove(dier);
				plugin.spar.remove(killer);
				killer.sendMessage(Settings.preFx + "You have killed " + dier.getDisplayName() + ".  " +
						"Have fun with the spoils");
			}
		}
	}
}