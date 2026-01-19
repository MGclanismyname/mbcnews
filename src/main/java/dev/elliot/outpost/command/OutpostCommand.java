
package dev.elliot.outpost.command;

import dev.elliot.outpost.OutpostPlugin;
import org.bukkit.command.*;

public class OutpostCommand implements CommandExecutor {

    private final OutpostPlugin plugin;

    public OutpostCommand(OutpostPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("§aOutpost config reloaded.");
        }
        return true;
    }
}
