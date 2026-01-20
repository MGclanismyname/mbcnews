package dev.elliot.outpost.command;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
public class RewardsCommand implements CommandExecutor {
private final RewardManager rm;
public RewardsCommand(RewardManager rm){this.rm=rm;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){
if(!(s instanceof Player p)) return true;
Inventory inv=Bukkit.createInventory(null,54,"Outpost Rewards");
rm.pending(p).forEach(inv::addItem);
p.openInventory(inv);
return true;
}
}