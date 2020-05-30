package me.dablakbandit.minescape.sprinters.game.regions;

import org.bukkit.Location;

import me.dablakbandit.core.area.CuboidArea;
import me.dablakbandit.minescape.json.Exclude;
import me.dablakbandit.minescape.json.JSON;
import me.dablakbandit.minescape.json.JSONData;
import me.dablakbandit.minescape.sprinters.game.regions.modifiers.Modifier;

public class Region extends JSONData{
	
	protected RegionType	type;
	protected String		json;
	protected Location		from, to;
	@Exclude
	protected Modifier		modifier;
	@Exclude
	protected CuboidArea	cuboidArea;
	
	public Region(RegionType type, Location from, Location to){
		this.type = type;
		this.from = from;
		this.to = to;
		this.cuboidArea = new CuboidArea(from, to);
		this.modifier = type.getNew();
	}
	
	@Override
	public void init(){
		super.init();
		// dynamically load modifier
		
		modifier = JSON.fromJSON(json, type.getModifier());
		// clear memory
		json = null;
		cuboidArea = new CuboidArea(from, to);
	}
	
	@Override
	public void term(){
		super.term();
		// dynamically save modifier
		json = modifier.toJSON().toString();
	}
	
	public RegionType getType(){
		return type;
	}
	
	public boolean isIn(Location location){
		return cuboidArea.isIn(location);
	}
	
	public Modifier getModifier(){
		return modifier;
	}
	
	public Location getMiddle(){
		double x = Math.min(from.getX(), to.getX());
		double y = Math.min(from.getY(), to.getY());
		double z = Math.min(from.getZ(), to.getZ());
		x += Math.abs(from.getX() - to.getX()) / 2;
		y += Math.abs(from.getY() - to.getY()) / 2;
		z += Math.abs(from.getZ() - to.getZ()) / 2;
		return new Location(from.getWorld(), x, y, z);
	}
}
