
package dev.elliot.outpost.placeholder;

import dev.elliot.outpost.outpost.OutpostManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class OutpostExpansion extends PlaceholderExpansion {

    private final OutpostManager manager;

    public OutpostExpansion(OutpostManager manager) {
        this.manager = manager;
    }

    @Override
    public String getIdentifier() {
        return "points_captured";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (params.equalsIgnoreCase("points")) {
            return String.valueOf(manager.getPoints(p));
        }
        if (params.startsWith("name_")) {
            return manager.getLeaderboardName(Integer.parseInt(params.substring(5)));
        }
        if (params.startsWith("amount_")) {
            return String.valueOf(manager.getLeaderboardAmount(Integer.parseInt(params.substring(7))));
        }
        return "";
    }
}
