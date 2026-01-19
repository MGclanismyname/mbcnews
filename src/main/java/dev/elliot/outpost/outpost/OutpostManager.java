
package dev.elliot.outpost.outpost;

import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.rewards.RewardStorage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OutpostManager {

    private final OutpostPlugin plugin;
    private final RewardStorage rewards;

    private Location center;
    private Block banner;
    private UUID owner;
    private int radius;

    private BukkitRunnable task;

    private final Map<UUID, Integer> points = new HashMap<>();

    public OutpostManager(OutpostPlugin plugin, RewardStorage rewards) {
        this.plugin = plugin;
        this.rewards = rewards;
    }

    public void startOutpost(Location loc, int duration, int radius, int height) {
        stopOutpost(false);

        this.center = loc;
        this.radius = radius;
        this.owner = null;

        banner = loc.getBlock();
        banner.setType(Material.BLACK_BANNER);

        task = new BukkitRunnable() {
            int timeLeft = duration;

            @Override
            public void run() {
                if (timeLeft-- <= 0) {
                    finishOutpost();
                    cancel();
                    return;
                }

                Player capturer = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (isInside(p.getLocation())) {
                        capturer = p;
                        break;
                    }
                }

                if (capturer != null && (owner == null || !owner.equals(capturer.getUniqueId()))) {
                    owner = capturer.getUniqueId();
                    Bukkit.broadcastMessage(
                        plugin.getConfig().getString("messages.captured")
                            .replace("&", "§")
                            .replace("%player%", capturer.getName())
                    );
                }
            }
        };
        task.runTaskTimer(plugin, 20, 20);
    }

    private void finishOutpost() {
        if (owner != null) {
            Player p = Bukkit.getPlayer(owner);
            if (p != null) {
                points.put(owner, points.getOrDefault(owner, 0) + 1);
                Bukkit.broadcastMessage(
                    plugin.getConfig().getString("messages.ended")
                        .replace("&", "§")
                        .replace("%player%", p.getName())
                );
            }
        }
        if (banner != null) banner.setType(Material.AIR);
    }

    public void stopOutpost(boolean reward) {
        if (task != null) task.cancel();
        if (banner != null) banner.setType(Material.AIR);
    }

    public boolean isOutpostBlock(Block b) {
        return banner != null && banner.equals(b);
    }

    private boolean isInside(Location l) {
        return l.getWorld().equals(center.getWorld())
            && l.distance(center) <= radius;
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
