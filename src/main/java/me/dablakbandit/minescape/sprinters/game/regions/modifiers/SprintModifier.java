package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;

public class SprintModifier extends EffectModifier{
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		updatePotionEffect(pl.getPlayer(), PotionEffectType.SPEED, duration, level);
		if(!already){
			pl.getPlayer().sendMessage(ChatColor.YELLOW + ">> Boost!");
		}
	}
}
