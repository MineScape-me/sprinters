package me.dablakbandit.minescape.sprinters.database.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import me.dablakbandit.core.database.listener.SQLListener;
import me.dablakbandit.minescape.json.JSON;
import me.dablakbandit.minescape.sprinters.game.map.SprintersMap;

public class SprintersMapDatabase extends SQLListener{
	
	private static SprintersMapDatabase database = new SprintersMapDatabase();
	
	public static SprintersMapDatabase getInstance(){
		return database;
	}
	
	private SprintersMapDatabase(){
		
	}
	
	private static PreparedStatement createMap, updateMap, getMapByTime, getMaps, deleteMaps;
	
	@Override
	public void setup(Connection connection){
		try{
			connection.prepareStatement("CREATE TABLE IF NOT EXISTS `sprinters_map` ( `id` INT NOT NULL AUTO_INCREMENT, `json` MEDIUMTEXT NOT NULL, `created` TIMESTAMP NOT NULL, PRIMARY KEY (`id`));").execute();
			createMap = connection.prepareStatement("INSERT INTO `sprinters_map` (`json`, `created`) VALUES (?,?);");
			updateMap = connection.prepareStatement("UPDATE `sprinters_map` SET `json` = ? WHERE `id` = ?;");
			getMapByTime = connection.prepareStatement("SELECT `id` FROM `sprinters_map` WHERE `created` = ?;");
			getMaps = connection.prepareStatement("SELECT * FROM `sprinters_map`;");
			deleteMaps = connection.prepareStatement("DELETE FROM `sprinters_map` WHERE `id` = ?;");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<SprintersMap> getMaps(){
		List<SprintersMap> maps = new ArrayList<>();
		try{
			ResultSet rs = getMaps.executeQuery();
			while(rs.next()){
				String json = rs.getString("json");
				SprintersMap map = JSON.fromJSON(json, SprintersMap.class);
				map.setId(rs.getInt("id"));
				maps.add(map);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return maps;
	}
	
	public void createMap(SprintersMap map){
		try{
			String json = map.toJSON().toString();
			long time = System.currentTimeMillis();
			Timestamp t = new Timestamp(time);
			
			createMap.setString(1, json);
			createMap.setTimestamp(2, t);
			createMap.execute();
			
			getMapByTime.setTimestamp(1, t);
			ResultSet rs = getMapByTime.executeQuery();
			if(rs.next()){
				map.setId(rs.getInt("id"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateMap(SprintersMap map){
		try{
			updateMap.setString(1, map.toJSON().toString());
			updateMap.setInt(2, map.getId());
			updateMap.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteMap(SprintersMap map){
		try{
			deleteMaps.setInt(1, map.getId());
			deleteMaps.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void close(Connection connection){
		try{
			closeStatements();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
