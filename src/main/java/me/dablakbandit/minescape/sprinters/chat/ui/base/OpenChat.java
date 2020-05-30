/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.sprinters.chat.ui.base;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.minescape.json.Exclude;
import me.dablakbandit.minescape.threader.MSPacketUtils;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public abstract class OpenChat extends me.dablakbandit.core.players.chatapi.OpenChat{
	
	@Exclude
	private static MineScapeThreader threader = MineScapeThreader.getInstance();
	
	public static String getCommand(){
		return "chat";
	}
	
	public static String createCommand(String add){
		return getCommand() + " " + add;
	}
	
	protected void send(JSONFormatter jf, CorePlayers pl){
		ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
		cai.setAllowed(cai.getAllowed() + jf.getSize());
		Object packet = jf.getPacket();
		threader.runTaskWithDelay(() -> {
			MSPacketUtils.dispatchPacket(pl, packet);
		});
	}
	
	protected JSONFormatter newJSONFormatter(){
		return new JSONFormatter(true);
	}
	
	public void setOpenChat(CorePlayers pl, OpenChat chat){
		pl.getInfo(ChatAPIInfo.class).setOpenChat(chat);
	}
	
	public void refresh(CorePlayers pl){
		pl.getInfo(ChatAPIInfo.class).refresh();
	}
	
}
