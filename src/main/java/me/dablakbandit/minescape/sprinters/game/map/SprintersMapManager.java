package me.dablakbandit.minescape.sprinters.game.map;

import java.util.ArrayList;
import java.util.List;

import me.dablakbandit.minescape.sprinters.database.databases.SprintersMapDatabase;

public class SprintersMapManager{
	
	private static SprintersMapManager mapManager = new SprintersMapManager();
	
	public static SprintersMapManager getInstance(){
		return mapManager;
	}
	
	private List<SprintersMap> maps = new ArrayList<>();
	
	private SprintersMapManager(){
		
	}
	
	public List<SprintersMap> getMaps(){
		return maps;
	}
	
	public void enable(){
		maps = SprintersMapDatabase.getInstance().getMaps();
	}
	
}
