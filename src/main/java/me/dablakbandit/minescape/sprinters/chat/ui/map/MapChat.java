package me.dablakbandit.minescape.sprinters.chat.ui.map;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.core.players.selection.SelectionPlayerInfo;
import me.dablakbandit.core.utils.LocationUtils;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.core.utils.jsonformatter.click.RunCommandEvent;
import me.dablakbandit.core.utils.jsonformatter.click.SuggestCommandEvent;
import me.dablakbandit.minescape.sprinters.chat.ui.base.CommandAccepter;
import me.dablakbandit.minescape.sprinters.chat.ui.base.OpenChat;
import me.dablakbandit.minescape.sprinters.database.databases.SprintersMapDatabase;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMapManager;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class MapChat extends OpenChat implements CommandAccepter{
	
	private static SprintersMapManager	mapManager	= SprintersMapManager.getInstance();
	protected SprintersMap				map;
	protected MapListChat				returner;
	
	public MapChat(SprintersMap map, MapListChat returner){
		this.map = map;
		this.returner = returner;
	}
	
	@Override
	public void open(CorePlayers pl, Player player){
		ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
		JSONFormatter jf = new JSONFormatter();
		jf.append(cai.getHeaderFooterString()).newLine();
		jf.append(" ").appendClick("Name: " + map.getName(), new SuggestCommandEvent(createCommand("name "))).newLine();
		jf.append(" ").appendClick("Spawns: " + map.getSpawnLocations().size(), new RunCommandEvent(createCommand("spawns"))).newLine();
		jf.append(" ").appendClick("Regions: " + map.getRegions().size(), new RunCommandEvent(createCommand("regions"))).newLine();
		jf.append(" ").appendClick("Minimum Y: " + map.getMinimumY(), new SuggestCommandEvent(createCommand("minY "))).newLine();
		jf.append(" ").append("Area: " + LocationUtils.locationToBasic(map.getFrom()) + " | " + LocationUtils.locationToBasic(map.getTo())).append(" ").appendClick("Update", new RunCommandEvent(createCommand("area"))).newLine();
		jf.append(" ").appendClick("Needed: " + map.getNeeded(), new SuggestCommandEvent(createCommand("needed "))).newLine();
		jf.newLine(11);
		jf.append(" ").appendClick("< Save", new RunCommandEvent(createCommand("save"))).newLine();
		jf.append(cai.getHeaderFooterString());
		send(jf, pl);
	}
	
	@Override
	public void onCommand(CorePlayers pl, Player player, Command cmd, String label, String[] args){
		switch(args[0]){
		case "area":{
			SelectionPlayerInfo selection = pl.getInfo(SelectionPlayerInfo.class);
			if(selection.getPoint1() != null && selection.getPoint2() != null){
				map.setArea(selection.getPoint1(), selection.getPoint2());
			}
			refresh(pl);
			break;
		}
		case "save":{
			MineScapeThreader.getInstance().runAsyncTaskWithDelay(() -> {
				if(map.getId() == -1){
					SprintersMapDatabase.getInstance().createMap(map);
				}else{
					SprintersMapDatabase.getInstance().updateMap(map);
				}
			});
			setOpenChat(pl, returner);
			break;
		}
		case "name":{
			if(args.length == 1){ return; }
			String newName = Arrays.stream(Arrays.copyOfRange(args, 1, args.length)).collect(Collectors.joining(" "));
			// update new name
			map.setName(newName);
			refresh(pl);
			break;
		}
		case "spawns":{
			setOpenChat(pl, new SpawnsChat(map, this));
			break;
		}
		case "regions":{
			setOpenChat(pl, new RegionsChat(map, this));
			break;
		}
		case "minY":{
			if(args.length == 1){ return; }
			try{
				map.setMinimumY(Integer.parseInt(args[1]));
				refresh(pl);
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		case "needed":{
			if(args.length == 1){ return; }
			try{
				map.setNeeded(Integer.parseInt(args[1]));
				refresh(pl);
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		}
	}
}
