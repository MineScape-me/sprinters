package me.dablakbandit.minescape.sprinters.chat.ui.map;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.core.utils.jsonformatter.click.RunCommandEvent;
import me.dablakbandit.minescape.sprinters.chat.ui.list.DeleteListChat;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.sprinters.game.regions.RegionType;

public class RegionsChat extends DeleteListChat<Region>{
	
	protected SprintersMap	map;
	protected MapChat		returner;
	
	public RegionsChat(SprintersMap map, MapChat returner){
		super("Regions", null);
		this.map = map;
		this.returner = returner;
		lines -= 1;
	}
	
	@Override
	public void addBefore(JSONFormatter jf){
		super.addBefore(jf);
		jf.newLine().append(" ");
		for(RegionType value : RegionType.values()){
			if(value != RegionType.values()[0]){
				jf.append(" | ");
			}
			jf.appendClick(value.name(), new RunCommandEvent(createCommand("type " + value.name())));
		}
	}
	
	@Override
	public String getString(Region region){
		return region.getType().name();
	}
	
	@Override
	public List<Region> getList(){
		return map.getRegions();
	}
	
	@Override
	public void onClose(CorePlayers pl){
		setOpenChat(pl, returner);
	}
	
	@Override
	public void onSelect(CorePlayers pl, Region region){
		setOpenChat(pl, new RegionEditChat(map, region, this));
	}
	
	@Override
	public void onDelete(CorePlayers pl, int i){
		map.getRegions().remove(i);
		refresh(pl);
	}
	
	@Override
	public void onCommand(CorePlayers pl, Player player, Command cmd, String label, String[] args){
		switch(args[0]){
		case "type":{
			if(args.length == 1){ return; }
			RegionType type = RegionType.valueOf(args[1]);
			setOpenChat(pl, new RegionNewChat(map, type, this));
		}
		default:{
			super.onCommand(pl, player, cmd, label, args);
			break;
		}
		}
	}
}
