package com.LRFLEW.PvP;

import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHashSet extends HashSet<String> {
	private static final long serialVersionUID = -3075874293119857672L;
	
	public boolean add(Player p) {
		return this.add(p.getName());
	}
	
	public boolean contains(Player p) {
		return this.contains(p.getName());
	}
	
	public boolean remove(Player p) {
		return this.remove(p.getName());
	}
	
	public Iterator<Player> iteratorP() {
		return new PlayerHashSetIterator();
	}
	
	private class PlayerHashSetIterator implements Iterator<Player> {
		Iterator<String> subIt = iterator();

		@Override
		public boolean hasNext() {
			return subIt.hasNext();
		}

		@Override
		public Player next() {
			return Bukkit.getPlayerExact(subIt.next());
		}

		@Override
		public void remove() {
			subIt.remove();
		}
		
	}
	
}
