package me.dablakbandit.minescape.sprinters.configuration;

import org.bukkit.plugin.Plugin;

import me.dablakbandit.core.configuration.PluginConfiguration;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;

public class SprintersConfiguration extends PluginConfiguration{
	
	private static SprintersConfiguration configuration = new SprintersConfiguration(SprintersPlugin.getInstance());
	
	public static SprintersConfiguration getConfiguration(){
		return configuration;
	}
	
	private SprintersConfiguration(Plugin plugin){
		super(plugin);
	}
	
	public void load(){
		super.loadPaths();
	}
}
