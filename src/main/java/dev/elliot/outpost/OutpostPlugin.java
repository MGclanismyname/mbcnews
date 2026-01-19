
package dev.elliot.outpost;

import dev.elliot.outpost.listener.RestrictionListener;
import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.rewards.RewardStorage;
import org.bukkit.plugin.java.JavaPlugin;

public class OutpostPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        OutpostManager manager = new OutpostManager(this, new RewardStorage());
        getServer().getPluginManager().registerEvents(new RestrictionListener(manager), this);
    }
}
