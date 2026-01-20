package dev.elliot.outpost.command;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
public class RewardAdminCommand implements CommandExecutor {
private final RewardManager rm;
public RewardAdminCommand(RewardManager rm){this.rm=rm;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){
if(!(s instanceof Player p)||a.length<2)return true;
if(a[0].equalsIgnoreCase("add")) rm.addReward(a[1],p.getInventory().getItemInMainHand().clone());
if(a[0].equalsIgnoreCase("delete")) rm.deleteReward(a[1]);
return true;
}}