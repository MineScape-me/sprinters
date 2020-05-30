package me.dablakbandit.minescape.gamemanager.lobby;

import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.listener.CorePlayersListener;
import me.dablakbandit.minescape.gamemanager.GameManager;
import me.dablakbandit.minescape.gamemanager.game.GameInstance;

public class LobbyManager extends CorePlayersListener{
	
	private static LobbyManager lobbyManager = new LobbyManager();
	
	public static LobbyManager getInstance(){
		return lobbyManager;
	}
	
	private GameInstance defaultGameInstance;
	
	private LobbyManager(){
		CorePlayerManager.getInstance().addListener(this);
	}
	
	public void enable(){
		
	}
	
	public GameInstance getDefaultGameInstance(){
		return defaultGameInstance;
	}
	
	public void setDefaultGameInstance(GameInstance defaultGameInstance){
		this.defaultGameInstance = defaultGameInstance;
	}
	
	@Override
	public void addCorePlayers(CorePlayers pl){
		if(defaultGameInstance == null){ return; }
		GameManager.getInstance().joinGame(defaultGameInstance, pl);
	}
	
	@Override
	public void loadCorePlayers(CorePlayers pl){
		if(pl.getPlayer().getGameMode() == GameMode.CREATIVE){ return; }
		pl.getPlayer().setGameMode(GameMode.ADVENTURE);
		pl.getPlayer().setFlying(false);
		pl.getPlayer().setAllowFlight(false);
		pl.getPlayer().teleport(pl.getPlayer().getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.SPECTATE);
	}
	
	@Override
	public void saveCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void removeCorePlayers(CorePlayers pl){
		
	}
}
