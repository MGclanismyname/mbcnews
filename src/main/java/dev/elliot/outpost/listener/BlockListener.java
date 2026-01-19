
package dev.elliot.outpost.listener;

import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

    private final OutpostManager manager;

    public BlockListener(OutpostManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (manager.isOutpostBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }
}
