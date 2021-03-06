package me.COAR.CoarMcServer.Essentials.Msg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class Msg implements CommandExecutor {
	private Main main;
	public Msg(Main plugin) {
		this.main = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("msg") || lbl.equalsIgnoreCase("tell")) {
			if(sender.hasPermission("CoarMcServer.Msg")) {
				if(args.length == 0) {
					// Add usage message for the /msg command
					sender.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.Usage"), sender, sender, ""));
					return true;
				} else if(args.length == 1) { 
					// Add usage message for the /msg command
					sender.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.Usage"), sender, sender, ""));
					return true;
				} else if(args.length >= 2) {
					String message = "";
					for(int i = 1; i < args.length; i++) {
						message += args[i] + " ";
					}
					if(sender instanceof Player) {
						Player player = (Player) sender;
						if(Bukkit.getServer().getPlayer(args[0]) != null) {
							Player p = Bukkit.getServer().getPlayer(args[0]);
							if(main.seplayer.getPlayerToggleData(player, "MessageToggle") == true) {
								// return message that CommandExecutor has this toggled ans it has been turned of so that he can can start sending and recieving messages again
								player.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.ToggleSelf"), player, p, ""));
							}
							if(main.seplayer.getPlayerToggleData(p, "MessageToggle") == true) {
								// return that the commandReciever has this toggled and so wishes not to recieve /msg or /r commands
								player.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.ToggleOther"), player, p, ""));
								return true;
							}
							// Set and get /msg message formats
							p.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.Format"), player, p, message));
							player.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.Format"), player, p, message));
//							main.seplayer.getConfig(p).getConfigurationSection(p.getUniqueId().toString()).set("ReturnMessage", "yeheasss");
//							main.seplayer.setPlayerDataString(player, "", "ReturnMessage", "yeheasss");
							
//							main.seplayer.getConfig(player).getConfigurationSection(player.getUniqueId().toString()).set("ReturnMessage", p.getName());
							
							main.seplayer.getConfig(player).getConfigurationSection(player.getUniqueId().toString()).set("ReturnMessage", p.getDisplayName().toString());
							main.seplayer.saveConfig(player);
							String t = main.seplayer.getConfig(player).getConfigurationSection(player.getUniqueId().toString()).getString("ReturnMessage");
							main.functions.tellConsole(t);
							// Check if there if a "Watcher" online
							for(Player watcher : Bukkit.getOnlinePlayers())
								if(watcher.hasPermission(""))
									if(main.seplayer.getPlayerToggleData(watcher, "MessageWatcher") == true)
										watcher.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Msg.Format"), player, p, message));
							
							return true;
						} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
							sender.sendMessage(main.functions.testTCC(main.messages.Get("Messages.ErrorMessages.CantFindOnlinePlayer"), sender, sender, ""));
							return false;
						}
					} else if(sender instanceof ConsoleCommandSender) {
						ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
						if(Bukkit.getServer().getPlayer(args[0]) != null) {
							Player p = Bukkit.getServer().getPlayer(args[0]);
							// Set and get /msg message formats
							p.sendMessage(main.functions.TCC(main.messages.Get(""), consoleSender, p));
							consoleSender.sendMessage(main.functions.TCC(main.messages.Get(""), consoleSender, p));
							main.seplayer.setPlayerDataString(p, p.getUniqueId().toString(), "PreviousMessage", consoleSender.getName());
							// Check if there if a "Watcher" online
							for(Player watcher : Bukkit.getOnlinePlayers())
								if(watcher.hasPermission(""))
									if(main.seplayer.getPlayerToggleData(watcher, "MessageWatcher") == true)
										watcher.sendMessage(main.functions.TCC(main.messages.Get(""), consoleSender, p)); // set message
							
							return true;
						} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
							sender.sendMessage(main.functions.TCC(main.messages.Get("")));
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	
}
