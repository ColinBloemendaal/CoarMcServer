package me.COAR.CoarMcServer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.COAR.CoarMcServer.Management.NegEffects;

public class MyListeners implements Listener {
	private Main main;
	public MyListeners(Main plugin) {
		this.main = plugin;
	}
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
//			if(!(player.hasPlayedBefore()))
//				main.seplayer.saveDefault(player); 
//			if(main.seplayer.getPlayerFile(player).getName() != player.getUniqueId().toString())
//				main.seplayer.saveDefault(player); 
			main.seplayer.saveDefault(player); 
		}
	}
	
	@EventHandler
	public void PlayerDamageEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(main.seplayer.getPlayerToggleData(player, "God") == true) {
				event.setCancelled(true);
			}
		}
		
	}
	@EventHandler
	public void PlayerHungerEvent(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(main.seplayer.getPlayerToggleData(player, "God") == true) {
				event.setCancelled(true);
			}
		}
		
	}
	@EventHandler
	public void PlayerPotionEvent(EntityPotionEffectEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(main.seplayer.getPlayerToggleData(player, "God") == true) {				
				// COAR me - Needs to be tested - When mc effect is added to player and god is enabled remove effect / disable event
				for(NegEffects bad : NegEffects.values()) {					
					if(bad.name().equalsIgnoreCase(event.getNewEffect().getType().getName())) {
		                event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event) {
		main.functions.tellConsole("haiii");
		Player p = event.getPlayer();
		main.seplayer.onLogoutEvent(p);
	}
}
