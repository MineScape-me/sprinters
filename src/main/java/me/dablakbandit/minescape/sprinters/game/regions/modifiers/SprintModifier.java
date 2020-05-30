package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;

public class SprintModifier extends EffectModifier{
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		updatePotionEffect(pl.getPlayer(), PotionEffectType.SPEED, duration, level);
		if(already){ return; }
		playSound(pl, Sound.BLOCK_GLASS_BREAK, 1, 1);
		sendMessage(pl, ChatColor.YELLOW + ">> Boost!");
	}
}
