package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class ConfusionModifier extends EffectModifier{
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		if(!already){
			updatePotionEffect(pl.getPlayer(), PotionEffectType.CONFUSION, duration, level);
			int seconds = duration / 20;
			int delay = duration % 20;
			for(int second = 0; second < seconds; second++){
				int finalSecond = second;
				MineScapeThreader.getInstance().runTaskWithDelay(() -> {
					pl.getPlayer().sendMessage(ChatColor.YELLOW + ">> " + ChatColor.LIGHT_PURPLE + "Confusion ending in " + (seconds - finalSecond) + "!");
				}, delay + (second * 20));
			}
		}
	}
}
