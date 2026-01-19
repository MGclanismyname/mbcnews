
package dev.elliot.outpost;

import dev.elliot.outpost.command.SetOutpostCommand;
import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OutpostPlugin extends JavaPlugin {

    private OutpostManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        manager = new OutpostManager(this);
        getCommand("setoutpost").setExecutor(new SetOutpostCommand(manager));
    }
}
