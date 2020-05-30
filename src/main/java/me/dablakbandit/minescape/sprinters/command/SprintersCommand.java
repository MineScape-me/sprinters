package me.dablakbandit.minescape.sprinters.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dablakbandit.core.commands.AdvancedCommand;
import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;
import me.dablakbandit.minescape.sprinters.chat.ui.map.MapListChat;

public class SprintersCommand extends AdvancedCommand{
	
	private static SprintersCommand instance = new SprintersCommand();
	
	public static SprintersCommand getInstance(){
		return instance;
	}
	
	public SprintersCommand(){
		super(SprintersPlugin.getInstance(), "sprinters", null, null);
	}
	
	@Override
	public void onBaseCommand(CommandSender s, Command cmd, String label, String[] args){
		if(!s.isOp()){ return; }
		Player player = (Player)s;
		CorePlayerManager.getInstance().getPlayer(player).getInfo(ChatAPIInfo.class).setOpenChat(new MapListChat());
	}
	
	@Override
	public void init(){
		
	}
}
