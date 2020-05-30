package me.dablakbandit.minescape.log;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MineScapeLog{
	
	private static String prefix = ChatColor.GRAY + "[" + ChatColor.GREEN + "MineScape" + ChatColor.GRAY + "] ";
	
	public static void printNo(String message){
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	public static void print(String message){
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + message);
	}
	
	public static void info(String message){
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.DARK_AQUA + message);
	}
	
	public static void debug(String message){
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + message);
	}
	
	public static void debug(Object... vals){
		debug(Arrays.stream(vals).map(val -> val != null ? val.toString() : "null").collect(Collectors.joining(" | ")));
	}
	
	public static void debug(Object message){
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + message);
	}
	
	public static void debug(boolean debug, Object message){
		if(debug){
			debug(message);
		}
	}
	
	public static void error(String message){
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + message);
	}
	
}
