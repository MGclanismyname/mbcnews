
package dev.elliot.outpost.command;

import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.command.*;

public class StopOutpostCommand implements CommandExecutor {

    private final OutpostManager manager;

    public StopOutpostCommand(OutpostManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        manager.stopOutpost(false);
        return true;
    }
}
