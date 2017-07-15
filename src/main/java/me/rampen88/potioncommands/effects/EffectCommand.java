package me.rampen88.potioncommands.effects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EffectCommand {

	private List<String> commands;

	private int minDurationForClear;
	private int amplifier;

	private boolean clearIfNoPerm;
	private boolean clearOnExit;

	private float volume;
	private float pitch;

	private PotionEffectType type;
	private Sound toggleSound;

	private String permission;
	private String permissionMessage;

	private List<String> commandsOnEnable;
	private List<String> commandsOnDisable;


	EffectCommand(ConfigurationSection section, PotionEffectType type, int minDurationForClear, boolean clearIfNoPerm){
		this.minDurationForClear = minDurationForClear;
		this.clearIfNoPerm = clearIfNoPerm;
		this.type = type;

		clearOnExit = section.getBoolean("ClearOnExit", true);
		permission = section.getString("Permission", "potion.command.default");
		permissionMessage = ChatColor.translateAlternateColorCodes('&', section.getString("NoPermissionMessage", "&4You do not have permission to do that."));
		commands = section.getStringList("Commands");
		amplifier = section.getInt("Amplifier", 0);

		commandsOnEnable = section.getStringList("CommandsOnEnable");
		commandsOnDisable = section.getStringList("CommandsOnDisable");

		try{

			toggleSound = Sound.valueOf(section.getString("ToggleSound.Sound"));
			volume = (float) section.getDouble("ToggleSound.Volume", 1);
			pitch = (float) section.getDouble("ToggleSound.Pitch", 1);

		}catch (IllegalArgumentException | NullPointerException ignored){ } // Either removed or invalid sound type if this is throw, unimportant so just ignore.

	}

	void clearOnExit(Player p){
		if(clearOnExit){
			// use hasEffect() here instead of && to prevent it possibly checking twice if both options are active and player does not have the effect.
			if(hasEffect(p)) p.removePotionEffect(type);

		}else if(clearIfNoPerm && !hasPerm(p) && hasEffect(p)){

			p.removePotionEffect(type);

		}
	}

	public boolean hasPerm(Player p) {
		return permission == null || p.hasPermission(permission);
	}

	public void toggle(Player p){

		// Check if player has the potion effect, and add / remove it depending on that.
		if(hasEffect(p)){
			p.removePotionEffect(type);
			runCommands(commandsOnDisable, p.getName());
		}else{
			p.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, amplifier), true);
			runCommands(commandsOnEnable, p.getName());
		}
		p.playSound(p.getLocation(), toggleSound, volume, pitch);
	}

	private void runCommands(List<String> commandsToRun, String playerName){
		if(commandsToRun == null) return;
		commandsToRun.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", playerName)));

	}

	boolean checkCommand(String command){
		for(String s : commands){
			if(s.equalsIgnoreCase(command)) return true;
		}
		return false;
	}

	private boolean hasEffect(Player p){

		PotionEffect effect = p.getPotionEffect(type);

		return effect != null && effect.getDuration() > minDurationForClear;

	}

	public String getPermissionMessage() {
		return permissionMessage;
	}
}
