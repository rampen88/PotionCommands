package me.rampen88.potioncommands.listener;

import me.rampen88.potioncommands.effects.EffectCommand;
import me.rampen88.potioncommands.effects.EffectManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{

	private EffectManager effectManager;

	public PlayerListener(EffectManager effectManager) {
		this.effectManager = effectManager;
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		// Check if the command the player is trying to execute is an EffectCommand, substring to remove the /
		EffectCommand cmd = effectManager.getByCommand(e.getMessage().substring(1));
		if(cmd == null) return;

		// Cancel event so it does not return 'Unknown command'.
		e.setCancelled(true);

		// Make sure player has permission
		Player p = e.getPlayer();
		if(!cmd.hasPerm(p)){
			p.sendMessage(cmd.getPermissionMessage());
			return;
		}

		// Toggle the effect.
		cmd.toggle(p);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		effectManager.clearOnExit(e.getPlayer());
	}

}
