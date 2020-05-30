package me.dablakbandit.minescape.sprinters.player.info;

import org.bukkit.Location;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.info.CorePlayersInfo;

public class SprintersInfo extends CorePlayersInfo{
	
	private Location	safe;
	private int			checkpoint, minimumY;
	
	public SprintersInfo(CorePlayers pl){
		super(pl);
	}
	
	public Location getSafe(){
		return safe.clone();
	}
	
	public void setSafe(Location safe){
		this.safe = safe;
	}
	
	public int getCheckpoint(){
		return checkpoint;
	}
	
	public void setCheckpoint(int checkpoint){
		this.checkpoint = checkpoint;
	}
	
	public int getMinimumY(){
		return minimumY;
	}
	
	public void setMinimumY(int minimumY){
		this.minimumY = minimumY;
	}
	
	@Override
	public void load(){
		
	}
	
	@Override
	public void save(){
		
	}
}
