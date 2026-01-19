
package dev.elliot.outpost.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOutpostCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length != 3) {
            player.sendMessage("§cUsage: /setoutpost <time> <radius> <height>");
            return true;
        }

        player.sendMessage("§aOutpost created (logic coming next).");
        return true;
    }
}
