package dev.elliot.outpost.command;
import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.command.*;
public class StopOutpostCommand implements CommandExecutor {
private final OutpostManager om;
public StopOutpostCommand(OutpostManager om){this.om=om;}
@Override public boolean onCommand(CommandSender s, Command c, String l, String[] a){om.stopOutpost(false); return true;}
}