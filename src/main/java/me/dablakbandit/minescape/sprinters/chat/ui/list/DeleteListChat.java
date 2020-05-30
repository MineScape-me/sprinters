package me.dablakbandit.minescape.sprinters.chat.ui.list;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.core.utils.jsonformatter.click.RunCommandEvent;

public abstract class DeleteListChat<T>extends ListChat<T>{
	
	public DeleteListChat(String title, String before){
		super(title, before);
	}
	
	public abstract void onDelete(CorePlayers pl, int i);
	
	@Override
	public void addValue(JSONFormatter jf, T t, int position){
		jf.appendClick("[x] ", new RunCommandEvent(createCommand("delete " + position)));
		super.addValue(jf, t, position);
	}
	
	@Override
	public void onCommand(CorePlayers pl, Player player, Command cmd, String label, String[] args){
		switch(args[0]){
		case "delete":{
			if(args.length == 0){ return; }
			try{
				onDelete(pl, Integer.parseInt(args[1]));
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		default:
			super.onCommand(pl, player, cmd, label, args);
			break;
		}
	}
}
