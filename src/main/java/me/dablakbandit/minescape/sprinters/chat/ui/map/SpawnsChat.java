package me.dablakbandit.minescape.sprinters.chat.ui.map;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.utils.LocationUtils;
import me.dablakbandit.minescape.sprinters.chat.ui.list.DeleteListChat;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;

public class SpawnsChat extends DeleteListChat<Location>{
	
	protected SprintersMap	map;
	protected MapChat		returner;
	
	public SpawnsChat(SprintersMap map, MapChat returner){
		super("Spawns", "New");
		this.map = map;
		this.returner = returner;
	}
	
	@Override
	public String getString(Location location){
		return LocationUtils.locationToBasic(location);
	}
	
	@Override
	public List<Location> getList(){
		return map.getSpawnLocations();
	}
	
	@Override
	public void onClose(CorePlayers pl){
		setOpenChat(pl, returner);
	}
	
	@Override
	public void onSelect(CorePlayers pl, Location location){
		pl.getPlayer().teleport(location, PlayerTeleportEvent.TeleportCause.SPECTATE);
	}
	
	@Override
	public void onBefore(CorePlayers pl){
		map.getSpawnLocations().add(pl.getPlayer().getLocation());
		refresh(pl);
	}
	
	@Override
	public void onDelete(CorePlayers pl, int i){
		getList().remove(i);
		refresh(pl);
	}
}
