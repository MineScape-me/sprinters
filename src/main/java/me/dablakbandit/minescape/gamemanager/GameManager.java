package me.dablakbandit.minescape.gamemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.listener.CorePlayersListener;
import me.dablakbandit.minescape.gamemanager.game.GameInstance;
import me.dablakbandit.minescape.gamemanager.listener.MovementManager;
import me.dablakbandit.minescape.gamemanager.scoreboard.GameScoreboardManager;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;

public class GameManager extends CorePlayersListener{
	
	private static GameManager instance = new GameManager();
	
	public static GameManager getInstance(){
		return instance;
	}
	
	private GameManager(){
		CorePlayerManager.getInstance().addListener(this);
		CorePlayerManager.getInstance().addListener(GameScoreboardManager.getInstance());
	}
	
	public void enable(){
		Bukkit.getPluginManager().registerEvents(MovementManager.getInstance(), SprintersPlugin.getInstance());
	}
	
	@Override
	public void addCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void loadCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void saveCorePlayers(CorePlayers pl){
		
	}
	
	@Override
	public void removeCorePlayers(CorePlayers pl){
		gamesMap.values().stream().forEach(list -> list.stream().filter(game -> game.containsPlayers(pl)).forEach(game -> {
			game.removePlayersFromList(pl);
			game.removePlayers(pl);
		}));
	}
	
	public void joinGame(GameInstance instance, CorePlayers pl){
		if(getGameInstance(pl) != null){ return; }
		if(instance.addPlayers(pl)){
			instance.addPlayersToList(pl);
		}
	}
	
	public GameInstance getGameInstance(CorePlayers pl){
		GameInstance instance = null;
		for(List<GameInstance> value : gamesMap.values()){
			instance = value.stream().filter(game -> game.containsPlayers(pl)).findFirst().orElse(null);
			if(instance != null){
				break;
			}
		}
		return instance;
	}
	
	public void end(GameInstance gameInstance){
		List<CorePlayers> players = new ArrayList(gameInstance.getPlayersList());
		players.forEach(gameInstance::removePlayersFromList);
		players.forEach(gameInstance::removePlayers);
		players.forEach(pl -> {
			pl.getPlayer().setFlying(false);
			pl.getPlayer().setAllowFlight(false);
			pl.getPlayer().teleport(pl.getPlayer().getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.SPECTATE);
		});
	}
	
	private Map<Plugin, List<GameInstance>> gamesMap = new HashMap<>();
	
	public void registerGameInstance(Plugin plugin, GameInstance gameInstance){
		List<GameInstance> gameInstances = gamesMap.computeIfAbsent(plugin, (p) -> new ArrayList<>());
		gameInstances.add(gameInstance);
		gameInstance.initGameState();
	}
}
