package me.rampen88.potioncommands;

import me.rampen88.potioncommands.commands.ReloadCommand;
import me.rampen88.potioncommands.effects.EffectManager;
import me.rampen88.potioncommands.listener.PlayerListener;

import me.rampen88.potioncommands.util.MessageUtil;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class PotionCommands extends JavaPlugin {

	private EffectManager effectManager;
	private MessageUtil messageUtil;

	@Override
	public void onEnable() {
		// Save config with comments.
		saveDefaultConfig();

		messageUtil = new MessageUtil(this);
		effectManager = new EffectManager(this);

		registerListeners();
		registerCommands();
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}

	private void registerListeners(){
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new PlayerListener(effectManager), this);
	}

	private void registerCommands(){
		PluginCommand reload = getCommand("pcommand");

		reload.setExecutor(new ReloadCommand(this));
		reload.setAliases(Arrays.asList("pcommands", "potioncommand", "potioncommands"));
	}

	public void reload(){
		reloadConfig();
		effectManager.reload();
	}

	public MessageUtil getMessageUtil() {
		return messageUtil;
	}
}
