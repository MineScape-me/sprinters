package me.dablakbandit.minescape.sprinters.chat.ui.map;

import java.util.List;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.chat.ui.list.DeleteListChat;
import me.dablakbandit.minescape.sprinters.database.databases.SprintersMapDatabase;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMapManager;

public class MapListChat extends DeleteListChat<SprintersMap>{
	
	public MapListChat(){
		super("Maps", "New");
	}
	
	@Override
	public String getString(SprintersMap sprintersMap){
		return sprintersMap.getName();
	}
	
	@Override
	public List<SprintersMap> getList(){
		return SprintersMapManager.getInstance().getMaps();
	}
	
	@Override
	public void onClose(CorePlayers pl){
		setOpenChat(pl, null);
	}
	
	@Override
	public void onSelect(CorePlayers pl, SprintersMap map){
		setOpenChat(pl, new MapChat(map, this));
	}
	
	@Override
	public void onBefore(CorePlayers pl){
		SprintersMap newMap = new SprintersMap("Sprinters");
		SprintersMapManager.getInstance().getMaps().add(newMap);
		setOpenChat(pl, new MapChat(newMap, this));
	}
	
	@Override
	public void onDelete(CorePlayers pl, int i){
		SprintersMap map = getList().remove(i);
		SprintersMapDatabase.getInstance().deleteMap(map);
		refresh(pl);
	}
}
