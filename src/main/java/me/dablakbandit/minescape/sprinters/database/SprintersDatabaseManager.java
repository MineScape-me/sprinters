package me.dablakbandit.minescape.sprinters.database;

import java.sql.Connection;

import org.bukkit.Bukkit;

import me.dablakbandit.core.configuration.Configuration;
import me.dablakbandit.core.database.DatabaseManager;
import me.dablakbandit.core.database.mysql.MySQLConfiguration;
import me.dablakbandit.core.database.mysql.MySQLDatabase;
import me.dablakbandit.minescape.sprinters.SprintersPlugin;
import me.dablakbandit.minescape.sprinters.database.databases.SprintersMapDatabase;

public class SprintersDatabaseManager{
	
	private static SprintersDatabaseManager instance = new SprintersDatabaseManager();
	
	public static SprintersDatabaseManager getInstance(){
		return instance;
	}
	
	private MySQLDatabase database;
	
	private SprintersDatabaseManager(){
		database = DatabaseManager.getInstance().createMySQLDatabase(new MySQLConfiguration(new Configuration(SprintersPlugin.getInstance(), "mysql.yml")), true);
	}
	
	public void load(){
		try{
			Connection connection = database.getConnection();
			if(connection == null){
				Bukkit.shutdown();
				return;
			}
			connection.prepareStatement("SET NAMES 'utf8mb4';").execute();
			connection.prepareStatement("SET CHARACTER SET utf8mb4;").execute();
			connection.prepareStatement("ALTER DATABASE " + database.getInfo().getDatabase() + " CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;").execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		database.addListener(SprintersMapDatabase.getInstance());
	}
}
