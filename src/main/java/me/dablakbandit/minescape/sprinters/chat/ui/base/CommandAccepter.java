/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.sprinters.chat.ui.base;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;

public interface CommandAccepter{
	
	void onCommand(CorePlayers pl, Player player, Command cmd, String label, String[] args);
	
}
