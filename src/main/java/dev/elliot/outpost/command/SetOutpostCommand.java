package dev.elliot.outpost.command;
import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.util.TimeParser;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
public class SetOutpostCommand implements CommandExecutor {
private final OutpostManager om;
private final OutpostPlugin plugin;
public SetOutpostCommand(OutpostManager om, OutpostPlugin plugin){this.om=om; this.plugin=plugin;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){
if(!(s instanceof Player p)){s.sendMessage("Players only"); return true;}
if(a.length!=3){p.sendMessage(color(plugin.msg("usage-set"))); return true;}
try{
om.startOutpost(p.getLocation(),TimeParser.parse(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
}catch(Exception e){p.sendMessage(color(plugin.msg("usage-set")));}
return true;
}
private String color(String s){return s.replace("&","§");}
}