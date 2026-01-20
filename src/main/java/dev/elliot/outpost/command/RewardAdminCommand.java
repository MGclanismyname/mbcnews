package dev.elliot.outpost.command;
import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
public class RewardAdminCommand implements CommandExecutor {
private final RewardManager rm;
private final OutpostPlugin plugin;
public RewardAdminCommand(RewardManager rm, OutpostPlugin plugin){this.rm=rm; this.plugin=plugin;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){
if(!(s instanceof Player p)){s.sendMessage("Players only"); return true;}
if(a.length<2){p.sendMessage(plugin.msg("usage-reward")); return true;}
if(a[0].equalsIgnoreCase("add")){
rm.addReward(a[1],p.getInventory().getItemInMainHand().clone());
p.sendMessage(plugin.msg("reward-added"));
}
if(a[0].equalsIgnoreCase("delete")){
rm.deleteReward(a[1]);
p.sendMessage(plugin.msg("reward-deleted"));
}
return true;
}
}