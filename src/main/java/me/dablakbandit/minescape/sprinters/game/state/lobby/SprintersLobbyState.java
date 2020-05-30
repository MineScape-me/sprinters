package me.dablakbandit.minescape.sprinters.game.state.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersState;
import me.dablakbandit.minescape.sprinters.game.state.game.SprintersGameState;
import me.dablakbandit.minescape.sprinters.player.info.SprintersInfo;

public class SprintersLobbyState extends SprintersState{
	
	private List<Location>			spawns		= new ArrayList();
	private Map<String, Location>	usedSpawns	= new HashMap<>();
	
	@Override
	public void init(){
		spawns = new ArrayList(gameInstance.getMap().getSpawnLocations());
		addListener(new LobbyMovementListener());
	}
	
	@Override
	public boolean addPlayers(CorePlayers pl){
		if(spawns.size() == 0){
			pl.getPlayer().sendMessage("Game mode is too full, please try another game.");
			return false;
		}
		SprintersInfo sprintersInfo = new SprintersInfo(pl);
		pl.addInfo(sprintersInfo);
		Location spawn = spawns.remove(0);
		sprintersInfo.setSafe(spawn.getBlock().getLocation());
		sprintersInfo.setMinimumY(getGameInstance().getMap().getMinimumY());
		
		pl.getPlayer().teleport(spawn.clone().getBlock().getLocation().add(0.5, 0, 0.5), PlayerTeleportEvent.TeleportCause.SPECTATE);
		pl.getPlayer().setFlying(false);
		pl.getPlayer().setAllowFlight(false);
		
		usedSpawns.put(pl.getUUIDString(), spawn);
		checkStart();
		threader.runTaskWithDelay(() -> {
			sendMessageToPlayers(ChatColor.YELLOW + ">> " + pl.getName() + " has joined.");
			sendSoundToPlayers(Sound.ENTITY_PARROT_STEP, 2f, 1.5f);
		});
		return true;
	}
	
	@Override
	public void removePlayers(CorePlayers pl){
		Location spawn = usedSpawns.get(pl.getUUIDString());
		spawns.add(spawn);
		cancelStart();
	}
	
	private static int			COUNTDOWN_TIME	= 15;
	private boolean				countdown;
	private List<BukkitTask>	tasks			= new ArrayList<>();
	
	public void checkStart(){
		if(countdown){ return; }
		if(!hasEnough(1)){ return; }
		countdown = true;
		for(int i = 0; i < COUNTDOWN_TIME; i++){
			int finalI = i;
			tasks.add(threader.runTaskWithDelay(() -> {
				sendTitleToPlayers(ChatColor.GOLD + "You can move in " + (COUNTDOWN_TIME - finalI) + "s", "", 5, 10, 5);
			}, i * 20));
		}
		tasks.add(threader.runTaskWithDelay(() -> {
			next();
		}, COUNTDOWN_TIME * 20));
	}
	
	public boolean hasEnough(int add){
		int count = getPlayersList().size() + add;
		return count >= gameInstance.getMap().getNeeded();
	}
	
	public void cancelStart(){
		if(!countdown){ return; }
		if(hasEnough(-1)){ return; }
		tasks.forEach(BukkitTask::cancel);
		sendMessageToPlayers(ChatColor.YELLOW + ">> " + ChatColor.RED + "Countdown cancelled");
		countdown = false;
	}
	
	@Override
	public SprintersState getNext(){
		return new SprintersGameState();
	}
}
