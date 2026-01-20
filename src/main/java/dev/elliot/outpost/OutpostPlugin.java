package dev.elliot.outpost;
import dev.elliot.outpost.command.*;
import dev.elliot.outpost.listener.BlockListener;
import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.points.PointsManager;
import dev.elliot.outpost.placeholder.OutpostExpansion;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public class OutpostPlugin extends JavaPlugin {
private PointsManager points;
@Override public void onEnable() {
saveDefaultConfig();
saveResource("rewards.yml", false);
RewardManager rm = new RewardManager(this);
points = new PointsManager();
OutpostManager om = new OutpostManager(this, rm, points);
getCommand("setoutpost").setExecutor(new SetOutpostCommand(om, this));
getCommand("stopoutpost").setExecutor(new StopOutpostCommand(om, this));
getCommand("rewards").setExecutor(new RewardsCommand(rm));
getCommand("reward").setExecutor(new RewardAdminCommand(rm, this));
getServer().getPluginManager().registerEvents(new BlockListener(om), this);
if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
new OutpostExpansion(this, points).register();
}
}
public String color(String s){ return s == null ? "" : s.replace("&","§"); }
}