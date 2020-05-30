package me.dablakbandit.minescape.sprinters.game.state.end;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.gamemanager.GameManager;
import me.dablakbandit.minescape.sprinters.game.SprintersState;
import me.dablakbandit.minescape.sprinters.game.state.lobby.SprintersLobbyState;

public class SprintersEndState extends SprintersState{
	
	private static int COUNTDOWN_TIME = 5;
	
	@Override
	public void init(){
		for(int i = 0; i < COUNTDOWN_TIME; i++){
			int finalI = i;
			threader.runTaskWithDelay(() -> {
				sendTitleToPlayers(ChatColor.RED + "Game reset in " + (COUNTDOWN_TIME - finalI) + "s", "", 5, 10, 5);
			}, i * 20);
		}
		threader.runTaskWithDelay(() -> {
			end();
		}, COUNTDOWN_TIME * 20);
		getPlayersList().forEach(pl -> {
			Arrays.stream(PotionEffectType.values()).forEach(pl.getPlayer()::removePotionEffect);
		});
	}
	
	@Override
	public boolean addPlayers(CorePlayers pl){
		return false;
	}
	
	@Override
	public void removePlayers(CorePlayers pl){
		
	}
	
	public void end(){
		GameManager.getInstance().end(getGameInstance());
		next();
	}
	
	@Override
	public SprintersState getNext(){
		return new SprintersLobbyState();
	}
}
