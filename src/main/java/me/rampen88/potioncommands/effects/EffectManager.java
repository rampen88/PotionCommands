package me.rampen88.potioncommands.effects;

import me.rampen88.potioncommands.PotionCommands;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class EffectManager {

	private PotionCommands plugin;

	private HashSet<EffectCommand> commands = new HashSet<>();

	public EffectManager(PotionCommands plugin) {
		this.plugin = plugin;
		reload();
	}

	public void reload(){
		commands.clear();

		// TODO: Something.
		int minDuration = plugin.getConfig().getInt("MinDurationForClear", 12000);
		boolean clearIfNoPerm = plugin.getConfig().getBoolean("CheckPermOnExit", true);

		// Get all the keys under the Commands section
		ConfigurationSection commandSection = plugin.getConfig().getConfigurationSection("Commands");
		for(String s : commandSection.getKeys(false)){

			ConfigurationSection effect = commandSection.getConfigurationSection(s);

			// Make sure the PotionEffectType exists
			PotionEffectType type = PotionEffectType.getByName(effect.getString("Type"));
			if(type == null){
				plugin.getLogger().info("Invalid Potion Effect Type: " + effect.getString("Type") + ". Skipping it.");
				continue;
			}

			commands.add(new EffectCommand(effect, type, minDuration, clearIfNoPerm));
		}
	}

	public EffectCommand getByCommand(String command){

		for (EffectCommand cmd : commands) {

			if(cmd.checkCommand(command)) return cmd;

		}
		return null;
	}

	public void clearOnExit(Player p){
		commands.forEach(e -> e.clearOnExit(p));
	}




}
