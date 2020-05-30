package me.dablakbandit.minescape.gamemanager.scoreboard;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.listener.CorePlayersListener;
import me.dablakbandit.minescape.gamemanager.scoreboard.info.ScoreboardInfo;

public class GameScoreboardManager extends CorePlayersListener{
	
	private static GameScoreboardManager instance = new GameScoreboardManager();
	
	public static GameScoreboardManager getInstance(){
		return instance;
	}
	
	private GameScoreboardManager(){
		
	}
	
	@Override
	public void addCorePlayers(CorePlayers pl){
		pl.addInfo(new ScoreboardInfo(pl));
	}
	
	@Override
	public void loadCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void saveCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void removeCorePlayers(CorePlayers pl){
		
	}
}
