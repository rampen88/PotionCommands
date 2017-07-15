package me.rampen88.potioncommands.util;

import me.rampen88.potioncommands.PotionCommands;
import org.bukkit.ChatColor;

public class MessageUtil {

	private PotionCommands plugin;

	public MessageUtil(PotionCommands plugin) {
		this.plugin = plugin;
	}

	public String getMessage(String path){
		String msg = plugin.getConfig().getString("Messages." + path, "&4Error: Failed to find message. Please inform staff.");
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
