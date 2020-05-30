package me.dablakbandit.minescape.gamemanager.game;

import java.util.ArrayList;
import java.util.List;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public abstract class GameInstance<T extends GameState>{
	
	protected static MineScapeThreader	threader	= MineScapeThreader.getInstance();
	protected T							gameState;
	protected List<CorePlayers>			playersList	= new ArrayList<>();
	
	public T getGameState(){
		return gameState;
	}
	
	protected void setGameState(T gameState){
		if(this.gameState != null){
			this.gameState.deregisterListeners();
		}
		this.gameState = gameState;
		if(this.gameState != null){
			this.gameState.setGameInstance(this);
			this.gameState.init();
			this.gameState.registerListeners();
		}
	}
	
	public abstract void initGameState();
	
	public abstract void nextGameState();
	
	public List<? extends CorePlayers> getPlayersList(){
		return playersList;
	}
	
	public void addPlayersToList(CorePlayers pl){
		playersList.add(pl);
	}
	
	public void removePlayersFromList(CorePlayers pl){
		playersList.remove(pl);
	}
	
	public boolean containsPlayers(CorePlayers pl){
		return playersList.contains(pl);
	}
	
	public abstract boolean addPlayers(CorePlayers pl);
	
	public abstract void removePlayers(CorePlayers pl);
	
	public void resetGameState(){
		playersList.clear();
		initGameState();
	}
}
