package me.dablakbandit.minescape.gamemanager.game;

import org.bukkit.event.Listener;

public class GameStateListener<T extends GameState> implements Listener{
	
	protected T gameState;
	
	protected void setGameState(T gameState){
		this.gameState = gameState;
	}
	
	public T getGameState(){
		return gameState;
	}
}
