package com.LRFLEW.PvP;

import org.bukkit.Bukkit;
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
					if ((plugin.spar.get(attacker.getName()) != null) || (plugin.spar.get(defender.getName()) != null)) {
						if (Misc.PvpMatchCheck(plugin.spar, attacker.getName(), defender.getName())) {
							if (defender == Bukkit.getPlayerExact(plugin.spar.get(attacker.getName()))) {
								event.setCancelled(false);
							} else {
								event.setCancelled(true);
							}
						}
					} else {
						if ( plugin.sets.pvpDefault ^ plugin.PvP.contains(attacker.getName()) || 
								plugin.sets.pvpDefault ^ plugin.PvP.contains(defender.getName()) ) {
							event.setCancelled(true);
							long cooldown = System.currentTimeMillis() + (plugin.sets.cooldownAttack*1000);
							if (plugin.cooldown.get(attacker.getName()) < cooldown)
									plugin.cooldown.put(attacker.getName(), cooldown);
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
			if (plugin.spar.containsKey(dier.getName())) {
				Player killer = Bukkit.getPlayerExact(plugin.spar.get(dier.getName()));
				plugin.spar.remove(dier.getName());
				plugin.spar.remove(killer.getName());
				killer.sendMessage(Settings.preFx + "You have killed " + dier.getDisplayName() + ".  " +
						"Have fun with the spoils");
			}
		}
	}
}