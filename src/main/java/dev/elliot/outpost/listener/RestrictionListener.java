
package dev.elliot.outpost.listener;

import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleGlideEvent;

public class RestrictionListener implements Listener {

    private final OutpostManager manager;

    public RestrictionListener(OutpostManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onGlide(PlayerToggleGlideEvent e) {
        if (manager.isRestricted(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL
                && manager.isRestricted(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (manager.isRestricted(e.getEntity())) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }
}
