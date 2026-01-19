
package dev.elliot.outpost.outpost;

import dev.elliot.outpost.OutpostPlugin;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class OutpostManager {

    private final OutpostPlugin plugin;
    private Outpost outpost;

    public OutpostManager(OutpostPlugin plugin) {
        this.plugin = plugin;
    }

    public void createOutpost(Location loc, int duration, int radius, int height) {
        Block b = loc.getBlock();
        b.setType(Material.BLACK_BANNER);

        Banner banner = (Banner) b.getState();
        banner.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_DOWNRIGHT));
        banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
        banner.update();

        outpost = new Outpost(loc, radius, height);

        new BukkitRunnable() {
            int timeLeft = duration;

            @Override
            public void run() {
                if (timeLeft-- <= 0) {
                    cancel();
                    if (outpost.owner != null) {
                        Player p = Bukkit.getPlayer(outpost.owner);
                        if (p != null) {
                            for (String cmd : plugin.getConfig().getStringList("end-commands")) {
                                Bukkit.dispatchCommand(
                                    Bukkit.getConsoleSender(),
                                    cmd.replace("%player%", p.getName())
                                );
                            }
                        }
                    }
                    return;
                }

                drawCircle(loc, radius);

                Set<Player> inside = new HashSet<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (isInside(p.getLocation())) inside.add(p);
                }

                if (inside.size() == 1) {
                    Player p = inside.iterator().next();
                    outpost.progress++;
                    if (outpost.progress >= plugin.getConfig().getInt("capture.capture-time-seconds")) {
                        outpost.owner = p.getUniqueId();
                        outpost.progress = 0;
                        Bukkit.broadcastMessage(
                            plugin.getConfig().getString("messages.captured")
                                .replace("&", "§")
                                .replace("%player%", p.getName())
                        );
                    }
                } else if (inside.size() > 1) {
                    outpost.progress = 0;
                    Bukkit.broadcastMessage(
                        plugin.getConfig().getString("messages.contested").replace("&", "§")
                    );
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    private boolean isInside(Location l) {
        return l.getWorld().equals(outpost.center.getWorld())
            && Math.abs(l.getX() - outpost.center.getX()) <= outpost.radius
            && Math.abs(l.getZ() - outpost.center.getZ()) <= outpost.radius
            && Math.abs(l.getY() - outpost.center.getY()) <= outpost.height;
    }

    private void drawCircle(Location center, int radius) {
        World w = center.getWorld();
        for (int i = 0; i < 24; i++) {
            double angle = 2 * Math.PI * i / 24;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            w.spawnParticle(Particle.END_ROD, x, center.getY() + 0.2, z, 1, 0, 0, 0, 0);
        }
    }
}
