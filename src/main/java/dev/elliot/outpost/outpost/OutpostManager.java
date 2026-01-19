
package dev.elliot.outpost.outpost;

import dev.elliot.outpost.OutpostPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OutpostManager {

    private final OutpostPlugin plugin;
    private Outpost outpost;

    public OutpostManager(OutpostPlugin plugin) {
        this.plugin = plugin;
    }

    public void createOutpost(Location loc, int durationSeconds, int radius, int height) {
        Block b = loc.getBlock();
        b.setType(Material.BLACK_BANNER);
        outpost = new Outpost(loc, radius, height);

        new BukkitRunnable() {
            int timeLeft = durationSeconds;

            @Override
            public void run() {
                if (timeLeft-- <= 0) {
                    cancel();
                    if (outpost.getOwner() != null) {
                        for (String cmd : plugin.getConfig().getStringList("end-commands")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                cmd.replace("%player%", outpost.getOwner().getName()));
                        }
                    }
                    return;
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!outpost.isInside(p)) continue;

                    if (outpost.getOwner() == null || !outpost.getOwner().equals(p)) {
                        outpost.addProgress();
                        int need = plugin.getConfig().getInt("capture.capture-time-seconds");
                        if (outpost.getProgress() >= need) {
                            outpost.setOwner(p);
                            outpost.resetProgress();
                            Bukkit.broadcastMessage(
                                plugin.getConfig().getString("messages.captured")
                                    .replace("&", "§")
                                    .replace("%player%", p.getName())
                            );
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }
}
