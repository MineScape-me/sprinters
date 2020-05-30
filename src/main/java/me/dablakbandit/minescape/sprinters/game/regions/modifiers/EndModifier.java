package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.Sound;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.sprinters.game.state.game.SprintersGameState;
import net.md_5.bungee.api.ChatColor;

public class EndModifier extends Modifier{
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		SprintersGameState gameState = (SprintersGameState)game.getGameState();
		gameState.finish(pl);
		gameState.sendMessageToPlayers(ChatColor.YELLOW + ">> " + pl.getName() + " has finished");
		pl.getPlayer().playSound(pl.getPlayer().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
	}
}
