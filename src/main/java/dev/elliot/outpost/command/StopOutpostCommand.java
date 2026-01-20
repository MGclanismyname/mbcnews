package dev.elliot.outpost.command;
import dev.elliot.outpost.OutpostPlugin;
import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.command.*;
public class StopOutpostCommand implements CommandExecutor {
private final OutpostManager om; private final OutpostPlugin plugin;
public StopOutpostCommand(OutpostManager om, OutpostPlugin plugin){this.om=om; this.plugin=plugin;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){
om.stopOutpost(false);
s.sendMessage(plugin.color(plugin.getConfig().getString("messages.prefix")+"Outpost stopped."));
return true;
}
}