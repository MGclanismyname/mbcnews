
package dev.elliot.outpost.listener;

import dev.elliot.outpost.outpost.OutpostManager;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class RestrictionListener implements Listener {

    private final OutpostManager manager;

    public RestrictionListener(OutpostManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onGlide(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof Player p && manager.isRestricted(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL
                && manager.isRestricted(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (manager.isRestricted(e.getEntity())) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }
}
