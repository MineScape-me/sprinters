package me.dablakbandit.minescape.gamemanager.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;
import me.dablakbandit.minescape.sprinters.util.MessageUtil;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public abstract class GameState<T extends GameInstance>{
	
	protected static MineScapeThreader				threader	= MineScapeThreader.getInstance();
	protected T										gameInstance;
	protected List<GameStateListener<GameState>>	listeners	= new ArrayList<>();
	
	public GameState(){
		
	}
	
	public T getGameInstance(){
		return gameInstance;
	}
	
	protected void setGameInstance(T gameInstance){
		this.gameInstance = gameInstance;
	}
	
	protected void next(){
		gameInstance.nextGameState();
	}
	
	public abstract void init();
	
	protected void deregisterListeners(){
		listeners.forEach(HandlerList::unregisterAll);
	}
	
	protected void registerListeners(){
		PluginManager manager = Bukkit.getPluginManager();
		listeners.forEach(listener -> manager.registerEvents(listener, SprintersPlugin.getInstance()));
	}
	
	protected void addListener(GameStateListener listener){
		listener.setGameState(this);
		listeners.add(listener);
	}
	
	public abstract boolean addPlayers(CorePlayers pl);
	
	public abstract void removePlayers(CorePlayers pl);
	
	public List<CorePlayers> getPlayersList(){
		return gameInstance.getPlayersList();
	}
	
	public void sendMessageToPlayers(String message){
		getPlayersList().forEach(pl -> pl.getPlayer().sendMessage(message));
	}
	
	public void sendHoverToPlayers(String message){
		getPlayersList().forEach(pl -> MessageUtil.sendHover(pl, message));
	}
	
	public void sendTitleToPlayers(String title, String subtitle, int in, int seconds, int out){
		getPlayersList().forEach(pl -> pl.getPlayer().sendTitle(title, subtitle, in, seconds, out));
	}
	
	public void sendSoundToPlayers(Sound sound, float a, float b){
		getPlayersList().forEach(pl -> pl.getPlayer().playSound(pl.getPlayer().getLocation(), sound, a, b));
	}
	
}
