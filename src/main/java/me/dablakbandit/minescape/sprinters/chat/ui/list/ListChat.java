package me.dablakbandit.minescape.sprinters.chat.ui.list;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.core.utils.jsonformatter.click.RunCommandEvent;
import me.dablakbandit.core.utils.jsonformatter.hover.ShowTextEvent;
import me.dablakbandit.minescape.sprinters.chat.ui.base.CommandAccepter;
import me.dablakbandit.minescape.sprinters.chat.ui.base.OpenChat;

public abstract class ListChat<T>extends OpenChat implements CommandAccepter{
	
	protected int		lines	= 15;
	protected int		down	= 0;
	protected String	title, before;
	
	public ListChat(String title, String before){
		this.title = title;
		this.before = before;
		if(this.before != null){
			lines -= 1;
		}
	}
	
	public abstract String getString(T t);
	
	public abstract List<T> getList();
	
	public void addBefore(JSONFormatter jf){
		jf.append(" ").append(title);
		if(before != null){
			jf.newLine();
			jf.append(" ").appendClick(before, new RunCommandEvent(createCommand("before")));
		}
	}
	
	public void onBefore(CorePlayers pl){
		
	}
	
	public abstract void onClose(CorePlayers pl);
	
	public abstract void onSelect(CorePlayers pl, T t);
	
	public void addValue(JSONFormatter jf, T t, int position){
		jf.appendClick(getString(t), new RunCommandEvent(createCommand("select " + position)));
	}
	
	@Override
	public void open(CorePlayers pl, Player player){
		ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
		JSONFormatter jf = new JSONFormatter();
		jf.append(cai.getHeaderFooterString()).newLine();
		addBefore(jf);
		jf.newLine();
		
		List<T> list = getList();
		int size = list.size();
		
		for(int position = down; position < down + Math.min(size, lines); position++){
			jf.append(" ");
			addValue(jf, list.get(position), position);
			jf.newLine();
		}
		
		jf.newLine(Math.max(0, lines - down - size));
		
		appendScroll(jf, size, down);
		jf.append(" ").appendClick("< Back", new RunCommandEvent(createCommand("back"))).newLine();
		jf.append(cai.getHeaderFooterString());
		send(jf, pl);
	}
	
	protected void appendScroll(JSONFormatter jf, int size, int current){
		jf.append("                                       ");
		jf.append(ChatColor.GOLD + "|").resetAll();
		if(current != 0){
			int line = Math.max(current - lines, 0);
			jf.appendHoverClick(" \u25B2 ", new ShowTextEvent("Up " + lines), new RunCommandEvent(createCommand("line " + line)));
		}else{
			jf.append("   ");
		}
		jf.append(ChatColor.GOLD + "|").resetAll();
		if(current != 0){
			jf.appendHoverClick(" \u25B2 ", new ShowTextEvent("Up"), new RunCommandEvent(createCommand("line " + (current - 1))));
		}else{
			jf.append("   ");
		}
		jf.append(ChatColor.RED + "|").resetAll();
		if(current < size - lines){
			jf.appendHoverClick(" \u25BC ", new ShowTextEvent("Down"), new RunCommandEvent(createCommand("line " + (current + 1))));
		}else{
			jf.append("   ");
		}
		jf.append(ChatColor.GOLD + "|").resetAll();
		if(current < size - lines){
			int line = Math.min(current + lines, size - lines);
			jf.appendHoverClick(" \u25BC ", new ShowTextEvent("Down " + lines), new RunCommandEvent(createCommand("line " + line)));
		}else{
			jf.append("   ");
		}
		jf.append(ChatColor.GOLD + "|").resetAll().newLine();
	}
	
	@Override
	public void onCommand(CorePlayers pl, Player player, Command cmd, String label, String[] args){
		switch(args[0]){
		case "before":{
			onBefore(pl);
			break;
		}
		case "line":{
			if(args.length == 1){ return; }
			down = Math.max(0, Math.min(getList().size() - lines, Integer.parseInt(args[1])));
			refresh(pl);
			break;
		}
		case "select":{
			if(args.length == 1){ return; }
			try{
				onSelect(pl, getList().get(Integer.parseInt(args[1])));
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		case "back":{
			onClose(pl);
			break;
		}
		}
	}
}
