package me.dablakbandit.minescape.sprinters.game.map;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import me.dablakbandit.core.area.RectangleArea;
import me.dablakbandit.minescape.json.Exclude;
import me.dablakbandit.minescape.json.JSONData;
import me.dablakbandit.minescape.sprinters.game.regions.Region;

public class SprintersMap extends JSONData{
	
	@Exclude
	protected int				id				= -1;
	protected String			name;
	protected Location			from, to;
	protected List<Location>	spawnLocations	= new ArrayList();
	protected List<Region>		regions			= new ArrayList();
	protected int				minimumY		= 0;
	protected int				needed			= 4;
	@Exclude
	protected RectangleArea		area;
	
	public SprintersMap(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<Location> getSpawnLocations(){
		return spawnLocations;
	}
	
	public List<Region> getRegions(){
		return regions;
	}
	
	public int getMinimumY(){
		return minimumY;
	}
	
	public void setMinimumY(int minimumY){
		this.minimumY = minimumY;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	@Override
	public void init(){
		if(from != null && to != null){
			area = new RectangleArea(from, to);
		}
		if(needed == 0){
			needed = 4;
		}
	}
	
	public Location getFrom(){
		return from;
	}
	
	public Location getTo(){
		return to;
	}
	
	public void setArea(Location from, Location to){
		this.from = from;
		this.to = to;
		this.area = new RectangleArea(from, to);
	}
	
	public int getNeeded(){
		return needed;
	}
	
	public void setNeeded(int needed){
		this.needed = needed;
	}
	
	public boolean isIn(Location location){
		return area.isIn(location);
	}
}
