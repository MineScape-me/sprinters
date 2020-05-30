package me.dablakbandit.minescape.sprinters.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dablakbandit.core.commands.AdvancedCommand;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;

public class FlyCommand extends AdvancedCommand{
	
	private static FlyCommand instance = new FlyCommand();
	
	public static FlyCommand getInstance(){
		return instance;
	}
	
	public FlyCommand(){
		super(SprintersPlugin.getInstance(), "fly", null, null);
	}
	
	@Override
	public void onBaseCommand(CommandSender s, Command cmd, String label, String[] args){
		if(!s.isOp()){ return; }
		Player player = (Player)s;
		player.setAllowFlight(true);
		player.setFlying(true);
	}
	
	@Override
	public void init(){
		
	}
}
