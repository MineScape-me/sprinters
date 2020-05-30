package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.json.JSONData;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;

public abstract class Modifier extends JSONData{
	
	public abstract void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already);
	
	public void onLeave(SprintersGame game, Region region, CorePlayers pl){
		
	}
	
	public static void updatePotionEffect(Player player, PotionEffectType type, int duration, int level){
		for(PotionEffect effect : player.getActivePotionEffects()){
			if(effect.getType().equals(type) && (effect.getAmplifier() <= level)){
				player.removePotionEffect(type);
				player.addPotionEffect(type.createEffect(duration, level));
				return;
			}
		}
		player.addPotionEffect(type.createEffect(duration, level));
	}
	
}
