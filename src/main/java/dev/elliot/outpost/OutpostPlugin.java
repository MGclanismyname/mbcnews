
package dev.elliot.outpost;

import dev.elliot.outpost.command.SetOutpostCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class OutpostPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("setoutpost").setExecutor(new SetOutpostCommand());
    }
}
