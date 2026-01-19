
package dev.elliot.outpost.outpost;

import dev.elliot.outpost.OutpostPlugin;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OutpostManager {

    private final OutpostPlugin plugin;
    private Location center;
    private UUID owner;
    private int radius, height;

    private BossBar timerBar;
    private Map<UUID, BossBar> captureBars = new HashMap<>();

    private BukkitRunnable task;
    private Block bannerBlock;

    public OutpostManager(OutpostPlugin plugin) {
        this.plugin = plugin;
    }

    public void startOutpost(Location loc, int duration, int radius, int height) {
        stopOutpost(true);

        this.center = loc;
        this.radius = radius;
        this.height = height;
        this.owner = null;

        bannerBlock = loc.getBlock();
        bannerBlock.setType(Material.BLACK_BANNER);

        Banner banner = (Banner) bannerBlock.getState();
        banner.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_CENTER));
        banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
        banner.update();

        timerBar = Bukkit.createBossBar("Outpost", BarColor.YELLOW, BarStyle.SEGMENTED_20);
        Bukkit.getOnlinePlayers().forEach(timerBar::addPlayer);

        task = new BukkitRunnable() {
            int timeLeft = duration;
            UUID capturer = null;
            int progress = 0;
            int needed = plugin.getConfig().getInt("capture.capture-time-seconds");

            @Override
            public void run() {
                if (timeLeft-- <= 0) {
                    stopOutpost(true);
                    return;
                }

                timerBar.setProgress((double) timeLeft / duration);

                Set<Player> inside = new HashSet<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (isInside(p.getLocation())) inside.add(p);
                }

                if (inside.size() == 1) {
                    Player p = inside.iterator().next();

                    if (owner != null && owner.equals(p.getUniqueId())) {
                        clearCaptureBars();
                        return;
                    }

                    if (capturer == null || !capturer.equals(p.getUniqueId())) {
                        capturer = p.getUniqueId();
                        progress = 0;
                    }

                    progress++;
                    showCaptureBar(p, (double) progress / needed);

                    if (progress >= needed) {
                        owner = p.getUniqueId();
                        capturer = null;
                        progress = 0;
                        clearCaptureBars();
                        Bukkit.broadcastMessage(
                            plugin.getConfig().getString("messages.captured")
                                .replace("&", "§")
                                .replace("%player%", p.getName())
                        );
                    }
                } else {
                    capturer = null;
                    progress = 0;
                    clearCaptureBars();
                }
            }
        };
        task.runTaskTimer(plugin, 20, 20);
    }

    public void stopOutpost(boolean reward) {
        if (task != null) task.cancel();
        clearCaptureBars();

        if (timerBar != null) timerBar.removeAll();

        if (!reward && center != null) {
            Bukkit.broadcastMessage(plugin.getConfig().getString("messages.stopped").replace("&", "§"));
        }

        if (bannerBlock != null) bannerBlock.setType(Material.AIR);

        owner = null;
        center = null;
    }

    public boolean isOutpostBlock(Block b) {
        return bannerBlock != null && bannerBlock.equals(b);
    }

    private boolean isInside(Location l) {
        return l.getWorld().equals(center.getWorld())
            && Math.abs(l.getX() - center.getX()) <= radius
            && Math.abs(l.getZ() - center.getZ()) <= radius
            && Math.abs(l.getY() - center.getY()) <= height;
    }

    private void showCaptureBar(Player p, double progress) {
        BossBar bar = captureBars.computeIfAbsent(
            p.getUniqueId(),
            k -> Bukkit.createBossBar(
                plugin.getConfig().getString("bossbars.capture-title").replace("&", "§"),
                BarColor.GREEN,
                BarStyle.SEGMENTED_10
            )
        );
        bar.addPlayer(p);
        bar.setProgress(Math.min(1.0, progress));
    }

    private void clearCaptureBars() {
        captureBars.values().forEach(BossBar::removeAll);
        captureBars.clear();
    }
}
