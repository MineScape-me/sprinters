package me.dablakbandit.minescape.sprinters.game.state.game;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.gamemanager.event.movement.PlayersMovementEvent;
import me.dablakbandit.minescape.gamemanager.game.GameStateListener;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.sprinters.game.regions.modifiers.EffectModifier;
import me.dablakbandit.minescape.sprinters.player.info.SprintersInfo;

public class GameMovementListener extends GameStateListener<SprintersGameState>{
	
	@EventHandler
	public void onPlayersMovement(PlayersMovementEvent event){
		SprintersGameState game = getGameState();
		if(!game.getGameInstance().containsPlayers(event.getPlayers())){ return; }
		checkArea(event);
		if(game.isSpectator(event.getPlayers())){ return; }
		event.getPlayer().setSprinting(true);
		EffectModifier.updatePotionEffect(event.getPlayer(), PotionEffectType.SPEED, 50000, 5);
		int minY = event.getPlayers().getInfo(SprintersInfo.class).getMinimumY();
		checkRegions(game, event.getPlayers(), event.getFrom(), event.getTo());
		if(event.getTo().getBlockY() > minY){ return; }
		event.setCancelled(true);
		event.setRestore(event.getPlayers().getInfo(SprintersInfo.class).getSafe());
		game.sendMessageToPlayers(ChatColor.YELLOW + ">> " + ChatColor.GRAY + "" + event.getPlayer().getName() + " fell and respawned!");
		game.sendSoundToPlayers(Sound.ENTITY_PARROT_STEP, 2f, 1.5f);
		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
	}
	
	private void checkRegions(SprintersGameState gameState, CorePlayers pl, Location from, Location to){
		List<Region> regions = gameState.getGameInstance().getMap().getRegions();
		List<Region> entering = regions.stream().filter(region -> region.isIn(to)).collect(Collectors.toList());
		List<Region> leaving = regions.stream().filter(region -> region.isIn(from)).collect(Collectors.toList());
		entering.forEach(region -> region.getModifier().onEnter(gameState.getGameInstance(), region, pl, leaving.contains(region)));
		leaving.removeIf(entering::contains);
		leaving.forEach(region -> region.getModifier().onLeave(gameState.getGameInstance(), region, pl));
	}
	
	private void checkArea(PlayersMovementEvent event){
		SprintersGameState game = getGameState();
		if(!game.getGameInstance().getMap().isIn(event.getTo())){
			event.setCancelled(true);
			event.setRestore(event.getFrom().getBlock().getLocation());
		}
		return;
	}
}
