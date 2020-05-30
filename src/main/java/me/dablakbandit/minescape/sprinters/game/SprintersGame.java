package me.dablakbandit.minescape.sprinters.game;

import org.bukkit.Bukkit;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.gamemanager.game.GameInstance;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;
import me.dablakbandit.minescape.sprinters.game.state.SprintersListener;
import me.dablakbandit.minescape.sprinters.game.state.lobby.SprintersLobbyState;

public class SprintersGame extends GameInstance<SprintersState>{
	
	private static SprintersGame sprintersGame = new SprintersGame();
	
	public static SprintersGame getInstance(){
		return sprintersGame;
	}
	
	private SprintersMap map;
	
	private SprintersGame(){
		Bukkit.getPluginManager().registerEvents(new SprintersListener(), SprintersPlugin.getInstance());
	}
	
	public SprintersMap getMap(){
		return map;
	}
	
	public void setMap(SprintersMap map){
		this.map = map;
	}
	
	@Override
	public void initGameState(){
		setGameState(new SprintersLobbyState());
	}
	
	@Override
	public void nextGameState(){
		setGameState(gameState.getNext());
	}
	
	@Override
	public boolean addPlayers(CorePlayers pl){
		if(!gameState.addPlayers(pl)){ return false; }
		return true;
	}
	
	@Override
	public void removePlayers(CorePlayers pl){
		gameState.removePlayers(pl);
	}
}
