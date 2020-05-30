package me.dablakbandit.minescape.sprinters.chat.ui.map;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.core.utils.NMSUtils;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.core.utils.jsonformatter.click.RunCommandEvent;
import me.dablakbandit.core.utils.jsonformatter.click.SuggestCommandEvent;
import me.dablakbandit.core.utils.jsonformatter.hover.ShowTextEvent;
import me.dablakbandit.minescape.sprinters.chat.ui.base.CommandAccepter;
import me.dablakbandit.minescape.sprinters.chat.ui.base.OpenChat;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.sprinters.game.regions.modifiers.Modifier;

public class RegionEditChat extends OpenChat implements CommandAccepter{
	
	protected SprintersMap	map;
	protected Region		region;
	protected RegionsChat	returner;
	
	public RegionEditChat(SprintersMap map, Region region, RegionsChat returner){
		this.map = map;
		this.region = region;
		this.returner = returner;
	}
	
	@Override
	public void open(CorePlayers pl, Player player){
		ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
		JSONFormatter jf = new JSONFormatter();
		jf.append(cai.getHeaderFooterString()).newLine();
		Modifier modifier = region.getModifier();
		
		int count = 0;
		try{
			List<Field> fields = NMSUtils.getFieldsIncludingUpper(modifier.getClass());
			for(Field field : fields){
				String value = "" + field.get(modifier);
				jf.append(" ").appendHoverClick(field.getName() + ": " + value, new ShowTextEvent(field.getType().getSimpleName()), new SuggestCommandEvent(createCommand("edit " + (count++) + " " + value))).newLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		jf.newLine(17 - count);
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
		case "edit":{
			if(args.length < 3){ return; }
			try{
				int set = Integer.parseInt(args[1]);
				String value = Arrays.stream(Arrays.copyOfRange(args, 2, args.length)).collect(Collectors.joining(" "));
				Modifier modifier = region.getModifier();
				List<Field> fields = NMSUtils.getFieldsIncludingUpper(modifier.getClass());
				Field field = fields.get(set);
				field.set(modifier, mappers.get(field.getType()).apply(value));
				refresh(pl);
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		}
	}
	
	private static Map<Class<?>, Function<String, Object>> mappers = new HashMap<>();
	
	static{
		mappers.put(Integer.class, Integer::parseInt);
		mappers.put(int.class, Integer::parseInt);
	}
	
}
