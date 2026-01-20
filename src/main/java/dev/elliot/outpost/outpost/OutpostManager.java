package dev.elliot.outpost.outpost;
import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;
public class OutpostManager {
private final OutpostPlugin plugin;
private final RewardManager rm;
private Location center;
private Block banner;
private UUID owner;
private int radius;
private BukkitRunnable task;
public OutpostManager(OutpostPlugin p, RewardManager rm){this.plugin=p;this.rm=rm;}
public void startOutpost(Location l,int d,int r,int h){
stopOutpost(false); center=l; radius=r; owner=null;
banner=l.getBlock(); banner.setType(Material.BLACK_BANNER);
task=new BukkitRunnable(){int t=d; public void run(){
if(t--<=0){finish(); cancel();}
for(Player p:Bukkit.getOnlinePlayers())
if(p.getWorld().equals(center.getWorld())&&p.getLocation().distance(center)<=radius){
if(owner==null||!owner.equals(p.getUniqueId())){
owner=p.getUniqueId();
Bukkit.broadcastMessage(plugin.getConfig().getString("messages.captured").replace("&","§").replace("%player%",p.getName()));
}
break;
}}};
task.runTaskTimer(plugin,20,20);
}
private void finish(){
if(owner!=null){
Player p=Bukkit.getPlayer(owner);
if(p!=null){
rm.queue(p);
Bukkit.broadcastMessage(plugin.getConfig().getString("messages.ended").replace("&","§").replace("%player%",p.getName()));
for(String c:plugin.getConfig().getStringList("end-commands"))
Bukkit.dispatchCommand(Bukkit.getConsoleSender(),c.replace("%player%",p.getName()));
}}
if(banner!=null) banner.setType(Material.AIR);
}
public void stopOutpost(boolean r){ if(task!=null)task.cancel(); if(banner!=null)banner.setType(Material.AIR);}
public boolean isOutpostBlock(Block b){return banner!=null&&banner.equals(b);}
}