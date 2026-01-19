
package dev.elliot.outpost.outpost;

import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.rewards.RewardStorage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OutpostManager {

    private final OutpostPlugin plugin;

    public OutpostManager(OutpostPlugin plugin, RewardStorage rewards) {
        this.plugin = plugin;
    }

    public boolean isRestricted(Player p) {
        return false;
    }
}
