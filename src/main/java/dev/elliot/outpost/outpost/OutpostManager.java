package dev.elliot.outpost.outpost;
import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.rewards.RewardManager;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;
public class OutpostManager {
private final OutpostPlugin plugin;
private final RewardManager rm;
private Location center;
private Block banner;
private Block bedrock;
private UUID owner;
private int radius;
private BukkitRunnable task;
private BossBar timerBar;
private BossBar captureBar;
private int captureTime;
private final Map<UUID,Integer> captureProgress = new HashMap<>();
public OutpostManager(OutpostPlugin p, RewardManager rm){this.plugin=p;this.rm=rm; this.captureTime=p.getConfig().getInt("capture.time-seconds");}
public void startOutpost(Location l,int duration,int r,int h){
stopOutpost(false);
center=l; radius=r; owner=null; captureProgress.clear();
bedrock=l.clone().subtract(0,1,0).getBlock();
bedrock.setType(Material.BEDROCK);
banner=l.getBlock();
banner.setType(Material.BLACK_BANNER);
BlockState state=banner.getState();
if(state instanceof Banner b){
b.setPatterns(List.of(
new org.bukkit.block.banner.Pattern(org.bukkit.DyeColor.RED, org.bukkit.block.banner.PatternType.CROSS),
new org.bukkit.block.banner.Pattern(org.bukkit.DyeColor.WHITE, org.bukkit.block.banner.PatternType.BORDER)
));
b.update();
}
timerBar=Bukkit.createBossBar(color(plugin.getConfig().getString("bossbars.timer")), BarColor.YELLOW, BarStyle.SOLID);
captureBar=Bukkit.createBossBar(color(plugin.getConfig().getString("bossbars.capture")), BarColor.GREEN, BarStyle.SEGMENTED_10);
task=new BukkitRunnable(){int t=duration;
public void run(){
timerBar.setProgress(Math.max(0,(double)t/duration));
timerBar.setTitle(color(plugin.getConfig().getString("bossbars.timer").replace("%time%",String.valueOf(t))));
if(t--<=0){finish(); cancel(); return;}
for(Player p:Bukkit.getOnlinePlayers()){
timerBar.addPlayer(p);
if(p.getWorld().equals(center.getWorld())&&p.getLocation().distance(center)<=radius){
int prog=captureProgress.getOrDefault(p.getUniqueId(),0)+1;
captureProgress.put(p.getUniqueId(),prog);
double percent=(prog*100.0)/captureTime;
captureBar.addPlayer(p);
captureBar.setProgress(Math.min(1,prog/(double)captureTime));
captureBar.setTitle(color(plugin.getConfig().getString("bossbars.capture")
.replace("%progress%",String.valueOf((int)percent))));
if(prog>=captureTime && (owner==null||!owner.equals(p.getUniqueId()))){
owner=p.getUniqueId();
captureProgress.clear();
Bukkit.broadcastMessage(color(plugin.getConfig().getString("messages.captured").replace("%player%",p.getName())));
}
}}
}};
task.runTaskTimer(plugin,20,20);
}
private void finish(){
timerBar.removeAll();
captureBar.removeAll();
if(owner!=null){
Player p=Bukkit.getPlayer(owner);
if(p!=null){
rm.queue(p);
Bukkit.broadcastMessage(color(plugin.getConfig().getString("messages.ended").replace("%player%",p.getName())));
for(String c:plugin.getConfig().getStringList("end-commands"))
Bukkit.dispatchCommand(Bukkit.getConsoleSender(),c.replace("%player%",p.getName()));
}}
cleanup();
}
private void cleanup(){
if(banner!=null) banner.setType(Material.AIR);
if(bedrock!=null) bedrock.setType(Material.AIR);
}
public void stopOutpost(boolean r){
if(task!=null) task.cancel();
if(timerBar!=null) timerBar.removeAll();
if(captureBar!=null) captureBar.removeAll();
cleanup();
}
public boolean isOutpostBlock(Block b){return b!=null&&(b.equals(banner)||b.equals(bedrock));}
private String color(String s){return s==null?"":s.replace("&","§");}
}