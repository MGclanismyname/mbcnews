
package dev.elliot.outpost;

import dev.elliot.outpost.command.*;
import dev.elliot.outpost.listener.BlockListener;
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

        RewardStorage rewards = new RewardStorage();
        manager = new OutpostManager(this, rewards);

        getCommand("setoutpost").setExecutor(new SetOutpostCommand(manager));
        getCommand("stopoutpost").setExecutor(new StopOutpostCommand(manager));
        getCommand("rewards").setExecutor(new RewardsCommand(rewards));

        Bukkit.getPluginManager().registerEvents(new BlockListener(manager), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new OutpostExpansion(manager).register();
        }
    }
}
