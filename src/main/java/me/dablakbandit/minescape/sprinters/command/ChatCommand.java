/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.sprinters.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dablakbandit.core.commands.AdvancedCommand;
import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;
import me.dablakbandit.minescape.sprinters.chat.ui.base.CommandAccepter;

public class ChatCommand extends AdvancedCommand{
	
	private static ChatCommand command = new ChatCommand();
	
	public static ChatCommand getInstance(){
		return command;
	}
	
	private ChatCommand(){
		super(SprintersPlugin.getInstance(), "chat", null, null);
	}
	
	@Override
	public void onBaseCommand(CommandSender s, Command cmd, String label, String[] args){
		if(!s.isOp()){ return; }
		Player player = (Player)s;
		CorePlayers pl = CorePlayerManager.getInstance().getPlayer(player);
		if(args.length == 0){
			
		}else{
			ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
			if(cai.getOpenChat() instanceof CommandAccepter){
				CommandAccepter ca = (CommandAccepter)cai.getOpenChat();
				ca.onCommand(pl, player, cmd, label, args);
			}
		}
	}
	
	@Override
	public void init(){
		
	}
}
