package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;

public class JumpModifier extends EffectModifier{
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		updatePotionEffect(pl.getPlayer(), PotionEffectType.JUMP, duration, level);
		if(already){ return; }
		sendMessage(pl, ChatColor.YELLOW + ">> " + ChatColor.RED + "Jump Boost!");
		playSound(pl, Sound.ENTITY_BLAZE_SHOOT, 1, 1);
	}
	
}
