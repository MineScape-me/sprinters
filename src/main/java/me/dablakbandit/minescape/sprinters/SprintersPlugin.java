package me.dablakbandit.minescape.sprinters;

import org.bukkit.plugin.java.JavaPlugin;

import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.minescape.gamemanager.GameManager;
import me.dablakbandit.minescape.gamemanager.lobby.LobbyManager;
import me.dablakbandit.minescape.sprinters.command.*;
import me.dablakbandit.minescape.sprinters.configuration.SprintersConfiguration;
import me.dablakbandit.minescape.sprinters.database.SprintersDatabaseManager;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMapManager;
import me.dablakbandit.minescape.sprinters.player.SprintersPlayerManager;

public class SprintersPlugin extends JavaPlugin{
	
	private static SprintersPlugin main;
	
	public static SprintersPlugin getInstance(){
		return main;
	}
	
	@Override
	public void onLoad(){
		main = this;
		CorePlayerManager.getInstance().enableChatAPI();
		LobbyManager.getInstance();
		GameManager.getInstance();
		
		// commands
		SprintersCommand.getInstance();
		ToolCommand.getInstance();
		FlyCommand.getInstance();
		ChatCommand.getInstance();
		SpeedCommand.getInstance();
		JoinCommand.getInstance();
		// Initialize configuration
		SprintersConfiguration.getConfiguration().load();
		
		SprintersDatabaseManager.getInstance().load();
	}
	
	@Override
	public void onEnable(){
		// Setup
		SprintersMapManager.getInstance().enable();
		LobbyManager.getInstance().enable();
		GameManager.getInstance().enable();
		
		CorePlayerManager.getInstance().enableSelectionListener();
		CorePlayerManager.getInstance().addListener(SprintersPlayerManager.getInstance());
		
		// Setup default game move to hub selection later
		SprintersMap map = SprintersMapManager.getInstance().getMaps().stream().findFirst().orElse(null);
		if(map != null){
			SprintersGame.getInstance().setMap(map);
			GameManager.getInstance().registerGameInstance(this, SprintersGame.getInstance());
			CorePlayerManager.getInstance().getPlayers().values().forEach(pl -> LobbyManager.getInstance().addCorePlayers(pl));
		}
	}
	
	@Override
	public void onDisable(){
		
	}
}
