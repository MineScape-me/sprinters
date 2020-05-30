/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.gamemanager.event;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import me.dablakbandit.core.players.CorePlayers;

public abstract class PlayersEvent extends PlayerEvent{
	
	protected CorePlayers pl;
	
	@Deprecated
	public PlayersEvent(CorePlayers pl, Player player){
		super(player);
		this.pl = pl;
	}
	
	public PlayersEvent(CorePlayers pl){
		super(pl.getPlayer());
		this.pl = pl;
	}
	
	public CorePlayers getPlayers(){
		return pl;
	}
}
