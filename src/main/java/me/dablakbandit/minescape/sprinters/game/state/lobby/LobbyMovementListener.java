package me.dablakbandit.minescape.sprinters.game.state.lobby;

import org.bukkit.event.EventHandler;

import me.dablakbandit.minescape.gamemanager.event.movement.PlayersMovementEvent;
import me.dablakbandit.minescape.gamemanager.game.GameStateListener;
import me.dablakbandit.minescape.sprinters.player.info.SprintersInfo;

public class LobbyMovementListener extends GameStateListener<SprintersLobbyState>{
	
	@EventHandler
	public void onPlayersMovement(PlayersMovementEvent event){
		SprintersLobbyState lobby = getGameState();
		if(!lobby.getGameInstance().containsPlayers(event.getPlayers())){ return; }
		event.setCancelled(true);
		event.setRestore(event.getPlayers().getInfo(SprintersInfo.class).getSafe());
	}
}
