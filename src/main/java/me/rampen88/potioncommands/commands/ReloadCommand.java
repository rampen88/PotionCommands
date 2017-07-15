package me.rampen88.potioncommands.commands;

import me.rampen88.potioncommands.PotionCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor{

	private PotionCommands plugin;

	public ReloadCommand(PotionCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		// TODO: Require /<command> reload
		// Check if sender has permission
		if(!commandSender.hasPermission("potion.commands.reload")){
			commandSender.sendMessage(plugin.getMessageUtil().getMessage("NoPermission"));
			return true;
		}

		plugin.reload();
		commandSender.sendMessage(plugin.getMessageUtil().getMessage("Reload"));

		return true;
	}

}
