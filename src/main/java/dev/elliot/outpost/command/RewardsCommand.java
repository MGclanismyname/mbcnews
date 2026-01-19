
package dev.elliot.outpost.command;

import dev.elliot.outpost.rewards.RewardStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RewardsCommand implements CommandExecutor {

    private final RewardStorage storage;

    public RewardsCommand(RewardStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        Inventory inv = Bukkit.createInventory(null, 54, "Outpost Rewards");
        for (ItemStack item : storage.getOverflow(p)) {
            inv.addItem(item);
        }
        storage.clear(p);
        p.openInventory(inv);
        return true;
    }
}
