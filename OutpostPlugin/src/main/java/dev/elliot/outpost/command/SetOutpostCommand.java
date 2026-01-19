
package dev.elliot.outpost.command;

import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.Location;
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
            player.sendMessage("§cUsage: /setoutpost <time> <radius> <height>");
            return true;
        }

        int time = Integer.parseInt(args[0]);
        int radius = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

        Location loc = player.getLocation();
        manager.createOutpost(loc, time, radius, height);
        player.sendMessage("§aOutpost created!");

        return true;
    }
}
