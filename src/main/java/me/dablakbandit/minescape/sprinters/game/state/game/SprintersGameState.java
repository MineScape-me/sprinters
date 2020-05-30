package me.dablakbandit.minescape.sprinters.game.state.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersState;
import me.dablakbandit.minescape.sprinters.game.state.end.SprintersEndState;
import me.dablakbandit.minescape.sprinters.player.info.SprintersInfo;

public class SprintersGameState extends SprintersState{
	
	private List<CorePlayers>	finished	= new ArrayList();
	private List<CorePlayers>	spectators	= new ArrayList<>();
	
	@Override
	public void init(){
		addListener(new GameMovementListener());
		getPlayersList().forEach(pl -> {
			pl.getInfo(SprintersInfo.class).setSafe(pl.getPlayer().getLocation().getBlock().getLocation());
		});
		sendTitleToPlayers(ChatColor.YELLOW + "Go!", "", 5, 30, 5);
	}
	
	@Override
	public boolean addPlayers(CorePlayers pl){
		// SPECTATOR
		spectators.add(pl);
		pl.getPlayer().setAllowFlight(true);
		pl.getPlayer().setFlying(true);
		
		pl.getPlayer().teleport(gameInstance.getMap().getSpawnLocations().get(0));
		threader.runTaskWithDelay(() -> {
			sendMessageToPlayers(ChatColor.YELLOW + ">> " + ChatColor.GRAY + pl.getName() + " has joined as a spectator");
		});
		return true;
	}
	
	public boolean isSpectator(CorePlayers pl){
		return spectators.contains(pl) || finished.contains(pl);
	}
	
	public boolean allFinished(){
		return gameInstance.getPlayersList().stream().filter(pl -> !spectators.contains(pl)).allMatch(finished::contains);
	}
	
	@Override
	public void removePlayers(CorePlayers pl){
		if(gameInstance.getPlayersList().size() == 0){
			end();
		}
		Arrays.stream(PotionEffectType.values()).forEach(pl.getPlayer()::removePotionEffect);
	}
	
	private static int	COUNTDOWN_TIME	= 15;
	public boolean		ending;
	
	public void end(){
		if(ending){ return; }
		ending = true;
		for(int i = 0; i < COUNTDOWN_TIME; i++){
			int finalI = i;
			threader.runTaskWithDelay(() -> {
				sendHoverToPlayers(ChatColor.RED + "Game ending in " + (COUNTDOWN_TIME - finalI) + "s");
			}, i * 20);
		}
		threader.runTaskWithDelay(() -> {
			next();
		}, COUNTDOWN_TIME * 20);
	}
	
	public void finish(CorePlayers pl){
		finished.add(pl);
		pl.getPlayer().setAllowFlight(true);
		pl.getPlayer().setFlying(true);
		end();
	}
	
	@Override
	public SprintersState getNext(){
		return new SprintersEndState();
	}
	
}
