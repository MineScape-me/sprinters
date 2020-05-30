package me.dablakbandit.minescape.sprinters.chat.ui.map;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.core.players.selection.SelectionPlayerInfo;
import me.dablakbandit.core.utils.LocationUtils;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.core.utils.jsonformatter.click.RunCommandEvent;
import me.dablakbandit.minescape.sprinters.chat.ui.base.ChatSelectionListener;
import me.dablakbandit.minescape.sprinters.chat.ui.base.CommandAccepter;
import me.dablakbandit.minescape.sprinters.chat.ui.base.OpenChat;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.sprinters.game.regions.RegionType;

public class RegionNewChat extends OpenChat implements CommandAccepter, ChatSelectionListener{
	
	protected SprintersMap	map;
	protected RegionType	type;
	protected RegionsChat	returner;
	
	public RegionNewChat(SprintersMap map, RegionType type, RegionsChat returner){
		this.map = map;
		this.type = type;
		this.returner = returner;
	}
	
	@Override
	public void open(CorePlayers pl, Player player){
		ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
		JSONFormatter jf = new JSONFormatter();
		jf.append(cai.getHeaderFooterString()).newLine();
		jf.append(" ").appendClick("Create >", new RunCommandEvent(createCommand("create"))).newLine();
		SelectionPlayerInfo selection = pl.getInfo(SelectionPlayerInfo.class);
		jf.append(" ").append("New Region:").newLine();
		jf.append(" ").append("Point 1: " + LocationUtils.locationToBasic(selection.getPoint1())).newLine();
		jf.append(" ").append("Point 2: " + LocationUtils.locationToBasic(selection.getPoint2())).newLine();
		jf.append(" ").appendClick("Region Type: " + type.name(), new RunCommandEvent(createCommand("type"))).newLine();
		jf.newLine(12);
		jf.append(" ").appendClick("< Back", new RunCommandEvent(createCommand("back"))).newLine();
		jf.append(cai.getHeaderFooterString());
		send(jf, pl);
	}
	
	@Override
	public void onCommand(CorePlayers pl, Player player, Command cmd, String label, String[] args){
		switch(args[0]){
		case "back":{
			setOpenChat(pl, returner);
			break;
		}
		case "type":{
			type = nextEnum(type);
			refresh(pl);
			break;
		}
		case "create":{
			SelectionPlayerInfo selection = pl.getInfo(SelectionPlayerInfo.class);
			if(selection.getPoint1() == null || selection.getPoint2() == null){ return; }
			Region region = new Region(type, selection.getPoint1(), selection.getPoint2());
			map.getRegions().add(region);
			setOpenChat(pl, new RegionEditChat(map, region, returner));
			break;
		}
		}
	}
	
	public static <T extends Enum> T nextEnum(T t){
		T[] a = (T[])t.getClass().getEnumConstants();
		return a[(t.ordinal() + 1) % a.length];
	}
	
}
