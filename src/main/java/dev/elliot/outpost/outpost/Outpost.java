
package dev.elliot.outpost.outpost;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Outpost {

    private final Location center;
    private final int radius;
    private final int height;
    private Player owner;
    private int progress;

    public Outpost(Location center, int radius, int height) {
        this.center = center;
        this.radius = radius;
        this.height = height;
    }

    public boolean isInside(Player p) {
        Location l = p.getLocation();
        return l.getWorld().equals(center.getWorld())
            && Math.abs(l.getX() - center.getX()) <= radius
            && Math.abs(l.getZ() - center.getZ()) <= radius
            && Math.abs(l.getY() - center.getY()) <= height;
    }

    public Player getOwner() { return owner; }
    public void setOwner(Player p) { owner = p; }
    public int getProgress() { return progress; }
    public void resetProgress() { progress = 0; }
    public void addProgress() { progress++; }
}
