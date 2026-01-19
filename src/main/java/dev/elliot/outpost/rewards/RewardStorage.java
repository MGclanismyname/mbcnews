
package dev.elliot.outpost.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RewardStorage {

    private final Map<UUID, List<ItemStack>> overflow = new HashMap<>();

    public void addOverflow(Player p, ItemStack item) {
        overflow.computeIfAbsent(p.getUniqueId(), k -> new ArrayList<>()).add(item);
    }

    public List<ItemStack> getOverflow(Player p) {
        return overflow.getOrDefault(p.getUniqueId(), new ArrayList<>());
    }

    public void clear(Player p) {
        overflow.remove(p.getUniqueId());
    }
}
