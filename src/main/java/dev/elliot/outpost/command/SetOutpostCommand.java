
package dev.elliot.outpost.command;

import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.util.TimeParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOutpostCommand implements CommandExecutor {

    private final OutpostManager manager;

    public SetOutpostCommand(OutpostManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length != 3) {
            player.sendMessage("§c/setoutpost <time(1d|1h|1m)> <radius> <height>");
            return true;
        }

        int seconds = TimeParser.parse(args[0]);
        int radius = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

        manager.createOutpost(player.getLocation(), seconds, radius, height);
        player.sendMessage("§aOutpost created!");
        return true;
    }
}
