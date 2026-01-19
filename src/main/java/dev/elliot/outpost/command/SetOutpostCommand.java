
package dev.elliot.outpost.command;

import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.util.TimeParser;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SetOutpostCommand implements CommandExecutor {

    private final OutpostManager manager;

    public SetOutpostCommand(OutpostManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (args.length != 3) return true;

        manager.startOutpost(
            p.getLocation(),
            TimeParser.parse(args[0]),
            Integer.parseInt(args[1]),
            Integer.parseInt(args[2])
        );
        return true;
    }
}
