
package dev.elliot.outpost.outpost;

import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.rewards.RewardStorage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class OutpostManager {

    private final OutpostPlugin plugin;
    private final RewardStorage rewards;

    private Location center;
    private Block banner;
    private int radius;

    private final Map<UUID, Integer> points = new HashMap<>();

    public OutpostManager(OutpostPlugin plugin, RewardStorage rewards) {
        this.plugin = plugin;
        this.rewards = rewards;
    }

    public void startOutpost(Location loc, int time, int radius, int height) {
        this.center = loc;
        this.radius = radius;
        this.banner = loc.getBlock();
    }

    public void stopOutpost(boolean reward) {
        if (banner != null) banner.setType(Material.AIR);
    }

    public boolean isOutpostBlock(Block b) {
        return banner != null && banner.equals(b);
    }

    public boolean isRestricted(Player p) {
        if (center == null) return false;
        return p.getWorld().equals(center.getWorld())
                && p.getLocation().distance(center) <= plugin.getConfig().getInt("restrictions.radius");
    }

    public void addPoint(Player p) {
        points.put(p.getUniqueId(), points.getOrDefault(p.getUniqueId(), 0) + 1);
    }

    public int getPoints(Player p) {
        return points.getOrDefault(p.getUniqueId(), 0);
    }

    public String getLeaderboardName(int rank) {
        return points.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .skip(rank)
                .map(e -> Bukkit.getOfflinePlayer(e.getKey()).getName())
                .findFirst().orElse("");
    }

    public int getLeaderboardAmount(int rank) {
        return points.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .skip(rank)
                .map(Map.Entry::getValue)
                .findFirst().orElse(0);
    }
}
