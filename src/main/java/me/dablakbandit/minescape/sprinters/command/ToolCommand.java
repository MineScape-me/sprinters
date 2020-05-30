package me.dablakbandit.minescape.sprinters.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dablakbandit.core.commands.AdvancedCommand;
import me.dablakbandit.core.players.selection.SelectionPlayerListener;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;

public class ToolCommand extends AdvancedCommand{
	
	private static ToolCommand instance = new ToolCommand();
	
	public static ToolCommand getInstance(){
		return instance;
	}
	
	public ToolCommand(){
		super(SprintersPlugin.getInstance(), "tool", null, null);
	}
	
	@Override
	public void onBaseCommand(CommandSender s, Command cmd, String label, String[] args){
		if(!s.isOp()){ return; }
		Player player = (Player)s;
		ItemStack tool = SelectionPlayerListener.getInstance().getTool();
		if(!player.getInventory().contains(tool)){
			player.getInventory().addItem(tool);
		}
	}
	
	@Override
	public void init(){
		
	}
}
