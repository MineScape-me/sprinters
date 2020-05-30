package me.dablakbandit.minescape.sprinters.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dablakbandit.core.commands.AdvancedCommand;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;

public class SpeedCommand extends AdvancedCommand{
	
	private static SpeedCommand instance = new SpeedCommand();
	
	public static SpeedCommand getInstance(){
		return instance;
	}
	
	public SpeedCommand(){
		super(SprintersPlugin.getInstance(), "speed", null, null);
	}
	
	@Override
	public void onBaseCommand(CommandSender s, Command cmd, String label, String[] args){
		if(!s.isOp()){ return; }
		Player player = (Player)s;
		int speed = 1;
		try{
			speed = Integer.parseInt(args[0]);
		}catch(Exception e){
			sendFormattedMessage(s, "Invalid " + args[0]);
			return;
		}
		if(speed < 1){
			speed = 1;
		}else if(speed > 20){
			speed = 20;
		}
		player.setFlySpeed(0.05f * speed);
	}
	
	@Override
	public void init(){
		
	}
}
