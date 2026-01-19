
package dev.elliot.outpost;

import dev.elliot.outpost.command.*;
import dev.elliot.outpost.listener.RestrictionListener;
import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.placeholder.OutpostExpansion;
import dev.elliot.outpost.rewards.RewardStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OutpostPlugin extends JavaPlugin {

    private OutpostManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        RewardStorage storage = new RewardStorage();
        manager = new OutpostManager(this, storage);

        getCommand("rewards").setExecutor(new RewardsCommand(storage));

        Bukkit.getPluginManager().registerEvents(new RestrictionListener(manager), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new OutpostExpansion(manager).register();
        }
    }

    public OutpostManager getManager() {
        return manager;
    }
}
