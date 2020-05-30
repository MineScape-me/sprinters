/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.gamemanager.event.movement;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.gamemanager.event.PlayersEvent;

public class PlayersMovementEvent extends PlayersEvent implements Cancellable{
	
	private Location		from, to;
	private Block			fromBlock, toBlock;
	private boolean			cancelled	= false;
	private Location		restore;
	private MovementType	type;
	
	public PlayersMovementEvent(CorePlayers pl, Location from, Location to, Block fromBlock, Block toBlock, MovementType type){
		super(pl);
		this.from = from;
		this.to = to;
		this.fromBlock = fromBlock;
		this.toBlock = toBlock;
		this.type = type;
	}
	
	public MovementType getType(){
		return type;
	}
	
	public Location getFrom(){
		return from;
	}
	
	public Location getTo(){
		return to;
	}
	
	public Block getFromBlock(){
		return fromBlock;
	}
	
	public Block getToBlock(){
		return toBlock;
	}
	
	public void setRestore(Location restore){
		this.restore = restore;
	}
	
	public Location getRestore(){
		return restore;
	}
	
	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	@Override
	public boolean isCancelled(){
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean b){
		this.cancelled = b;
	}
}
