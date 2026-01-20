package dev.elliot.outpost.command;
import dev.elliot.outpost.outpost.OutpostManager;
import dev.elliot.outpost.util.TimeParser;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
public class SetOutpostCommand implements CommandExecutor {
private final OutpostManager om;
public SetOutpostCommand(OutpostManager om){this.om=om;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){
if(!(s instanceof Player p)||a.length!=3)return true;
om.startOutpost(p.getLocation(),TimeParser.parse(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
return true;
}}