package dev.elliot.outpost.rewards;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.*;
public class RewardManager {
private final File file;
private final FileConfiguration cfg;
private final Map<UUID,List<ItemStack>> pending=new HashMap<>();
public RewardManager(JavaPlugin plugin){
file=new File(plugin.getDataFolder(),"rewards.yml");
cfg=YamlConfiguration.loadConfiguration(file);
}
public void addReward(String id, ItemStack item){
cfg.set("rewards."+id,item); save();
}
public void deleteReward(String id){
cfg.set("rewards."+id,null); save();
}
public Map<String,ItemStack> all(){
Map<String,ItemStack> m=new HashMap<>();
if(cfg.isConfigurationSection("rewards"))
for(String k:cfg.getConfigurationSection("rewards").getKeys(false))
m.put(k,cfg.getItemStack("rewards."+k));
return m;
}
public void queue(Player p){
pending.computeIfAbsent(p.getUniqueId(),k->new ArrayList<>()).addAll(all().values());
}
public List<ItemStack> pending(Player p){return pending.getOrDefault(p.getUniqueId(),new ArrayList<>());}
public void remove(Player p, ItemStack i){pending(p).remove(i);}
private void save(){try{cfg.save(file);}catch(Exception e){}}
}