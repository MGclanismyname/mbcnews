
package dev.elliot.outpost;

import dev.elliot.outpost.command.OutpostCommand;
import dev.elliot.outpost.command.SetOutpostCommand;
import dev.elliot.outpost.command.StopOutpostCommand;
import dev.elliot.outpost.listener.BlockListener;
import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OutpostPlugin extends JavaPlugin {

    private OutpostManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        manager = new OutpostManager(this);

        getCommand("setoutpost").setExecutor(new SetOutpostCommand(manager));
        getCommand("stopoutpost").setExecutor(new StopOutpostCommand(manager));
        getCommand("outpost").setExecutor(new OutpostCommand(this));

        getServer().getPluginManager().registerEvents(new BlockListener(manager), this);
    }
}
