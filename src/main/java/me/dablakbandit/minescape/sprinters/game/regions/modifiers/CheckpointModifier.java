package me.dablakbandit.minescape.sprinters.game.regions.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.minescape.sprinters.game.SprintersGame;
import me.dablakbandit.minescape.sprinters.game.regions.Region;
import me.dablakbandit.minescape.sprinters.game.state.game.SprintersGameState;
import me.dablakbandit.minescape.sprinters.player.info.SprintersInfo;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class CheckpointModifier extends Modifier{
	
	private int	checkpoint;
	private int	mininumY;
	
	@Override
	public void onEnter(SprintersGame game, Region region, CorePlayers pl, boolean already){
		SprintersInfo sprintersInfo = pl.getInfo(SprintersInfo.class);
		if(sprintersInfo.getCheckpoint() < checkpoint){
			SprintersGameState state = (SprintersGameState)game.getGameState();
			pl.getInfo(SprintersInfo.class).setSafe(region.getMiddle().getBlock().getLocation());
			sprintersInfo.setCheckpoint(checkpoint);
			sprintersInfo.setMinimumY(mininumY);
			pl.getPlayer().playSound(pl.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			MineScapeThreader.getInstance().runTaskWithDelay(() -> {
				pl.getPlayer().sendMessage(ChatColor.YELLOW + ">> " + ChatColor.GREEN + "Checkpoint!");
			});
		}
	}
}
