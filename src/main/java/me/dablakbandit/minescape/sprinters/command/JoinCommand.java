package me.dablakbandit.minescape.sprinters.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dablakbandit.core.commands.AdvancedCommand;
import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.minescape.gamemanager.GameManager;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;

public class JoinCommand extends AdvancedCommand{
	
	private static JoinCommand instance = new JoinCommand();
	
	public static JoinCommand getInstance(){
		return instance;
	}
	
	public JoinCommand(){
		super(SprintersPlugin.getInstance(), "join", null, null);
	}
	
	@Override
	public void onBaseCommand(CommandSender s, Command cmd, String label, String[] args){
		GameManager.getInstance().joinGame(SprintersGame.getInstance(), CorePlayerManager.getInstance().getPlayer((Player)s));
	}
	
	@Override
	public void init(){
		
	}
}
