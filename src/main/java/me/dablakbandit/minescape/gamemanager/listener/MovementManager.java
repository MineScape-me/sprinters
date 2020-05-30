package me.dablakbandit.minescape.gamemanager.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.gamemanager.event.movement.MovementType;
import me.dablakbandit.minescape.gamemanager.event.movement.PlayersMovementEvent;
import me.dablakbandit.minescape.log.MineScapeLog;

public class MovementManager implements Listener{
	
	private static MovementManager movementManager = new MovementManager();
	
	public static MovementManager getInstance(){
		return movementManager;
	}
	
	private MovementManager(){
		
	}
	
	private static CorePlayerManager corePlayerManager = CorePlayerManager.getInstance();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		checkMove(event.getPlayer(), event.getFrom(), event.getTo(), MovementType.WALK);
	}
	
	public boolean checkMove(Player player, Location from, Location to, MovementType type){
		CorePlayers pl = corePlayerManager.getPlayer(player);
		boolean cancel = false;
		if(!hasMoved(from, to)){ return false; }
		if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR){
			Block fromBlock = from.getBlock();
			Block toBlock = to.getBlock();
			PlayersMovementEvent event = new PlayersMovementEvent(pl, from, to, fromBlock, toBlock, type);
			Bukkit.getPluginManager().callEvent(event);
			if(cancel = event.isCancelled()){
				if(event.getRestore() != null){
					restoreToSafe(player, event.getRestore(), to);
				}else{
					MineScapeLog.error("Event cancelled with no restore");
				}
			}
		}
		return cancel;
	}
	
	public boolean hasMoved(Location from, Location to){
		return from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ();
	}
	
	public void restoreToSafe(Player player, Location to, Location current){
		to.setPitch(current.getPitch());
		to.setYaw(current.getYaw());
		to.add(0.5, 0, 0.5);
		player.teleport(to, PlayerTeleportEvent.TeleportCause.SPECTATE);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event){
		if(event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE){ return; }
		if(checkMove(event.getPlayer(), event.getFrom(), event.getTo(), MovementType.TELEPORT)){
			event.setCancelled(true);
		}
	}
}
