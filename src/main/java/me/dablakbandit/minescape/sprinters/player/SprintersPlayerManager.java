package me.dablakbandit.minescape.sprinters.player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.listener.CorePlayersListener;
import me.dablakbandit.core.players.selection.SelectionPlayerInfo;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class SprintersPlayerManager extends CorePlayersListener{
	
	private static SprintersPlayerManager playerManager = new SprintersPlayerManager();
	
	public static SprintersPlayerManager getInstance(){
		return playerManager;
	}
	
	private SprintersPlayerManager(){
		
	}
	
	@Override
	public void addCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void loadCorePlayers(CorePlayers pl){
		MineScapeThreader.getInstance().runTaskWithDelay(() -> {
			pl.getInfo(SelectionPlayerInfo.class).addListener(new SelectionListener());
		});
		pl.getPlayer().sendMessage("Do /join to join the race");
	}
	
	@Override
	public void saveCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void removeCorePlayers(CorePlayers pl){
		
	}
}
