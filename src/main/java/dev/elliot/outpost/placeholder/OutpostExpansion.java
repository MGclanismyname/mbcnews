package dev.elliot.outpost.placeholder;
import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.points.PointsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import java.util.*;
public class OutpostExpansion extends PlaceholderExpansion {
private final OutpostPlugin plugin;
private final PointsManager points;
public OutpostExpansion(OutpostPlugin plugin, PointsManager points){
this.plugin=plugin; this.points=points;
}
@Override public String getIdentifier(){ return "points_captured"; }
@Override public String getAuthor(){ return "Elliot"; }
@Override public String getVersion(){ return plugin.getDescription().getVersion(); }
@Override
public String onRequest(OfflinePlayer p, String params){
if(params.equals("")){
return String.valueOf(points.get(p.getUniqueId()));
}
if(params.startsWith("amount_")){
int r=parse(params.substring(7));
var lb=points.leaderboard();
return r<lb.size()?String.valueOf(lb.get(r).getValue()):"";
}
if(params.startsWith("name_")){
int r=parse(params.substring(5));
var lb=points.leaderboard();
return r<lb.size()?plugin.getServer().getOfflinePlayer(lb.get(r).getKey()).getName():"";
}
return "";
}
private int parse(String s){
try{return Integer.parseInt(s);}catch(Exception e){return -1;}
}
}