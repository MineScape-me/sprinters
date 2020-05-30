/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.sprinters.util;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;

public class MessageUtil{
	
	public static void sendHover(CorePlayers pl, String message){
		try{
			JSONFormatter.sendRawMessage(pl.getPlayer(), message, (byte)2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
