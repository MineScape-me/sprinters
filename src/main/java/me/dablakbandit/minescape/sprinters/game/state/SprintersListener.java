package me.dablakbandit.minescape.sprinters.game.state;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class SprintersListener implements Listener{
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){
		event.blockList().clear();
	}
	
	@EventHandler
	public void onRegainHealth(EntityRegainHealthEvent event){
		event.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityClick(PlayerInteractEntityEvent event){
		if(event.getRightClicked() instanceof ArmorStand || event.getRightClicked() instanceof Minecart){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityCombust(EntityDamageEvent event){
		if(event.getEntityType() == EntityType.PLAYER){
			if(event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
				event.setCancelled(true);
				event.getEntity().setFireTicks(0);
			}else{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event){
		if(event.getEntityType() == EntityType.PLAYER){
			event.setCancelled(true);
			event.getEntity().setFireTicks(0);
		}
	}
}
