package dev.elliot.outpost.listener;
import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
public class BlockListener implements Listener {
private final OutpostManager om;
public BlockListener(OutpostManager om){this.om=om;}
@EventHandler public void onBreak(BlockBreakEvent e){ if(om.isOutpostBlock(e.getBlock())) e.setCancelled(true);}
}