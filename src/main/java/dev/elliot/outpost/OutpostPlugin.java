package dev.elliot.outpost;
import dev.elliot.outpost.command.*;
import dev.elliot.outpost.listener.BlockListener;
import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.plugin.java.JavaPlugin;
public class OutpostPlugin extends JavaPlugin {
@Override public void onEnable() {
saveDefaultConfig();
saveResource("rewards.yml", false);
RewardManager rm = new RewardManager(this);
OutpostManager om = new OutpostManager(this, rm);
getCommand("setoutpost").setExecutor(new SetOutpostCommand(om, this));
getCommand("stopoutpost").setExecutor(new StopOutpostCommand(om, this));
getCommand("rewards").setExecutor(new RewardsCommand(rm));
getCommand("reward").setExecutor(new RewardAdminCommand(rm, this));
getServer().getPluginManager().registerEvents(new BlockListener(om), this);
}
public String msg(String key){
return getConfig().getString("messages.prefix")+getConfig().getString("messages."+key);
}
}