package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class SlowModifier extends EffectModifier{
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		if(already){ return; }
		updatePotionEffect(pl.getPlayer(), PotionEffectType.SLOW, duration, level);
		int seconds = duration / 20;
		int delay = duration % 20;
		playSound(pl, Sound.ENTITY_ENDERMAN_DEATH, 1, 1);
		for(int second = 0; second < seconds; second++){
			int finalSecond = second;
			MineScapeThreader.getInstance().runTaskWithDelay(() -> {
				sendMessage(pl, ChatColor.YELLOW + ">> " + ChatColor.DARK_PURPLE + "Slowness ending in " + (seconds - finalSecond) + "...");
			}, delay + (second * 20));
		}
	}
}
