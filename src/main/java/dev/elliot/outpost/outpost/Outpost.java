
package dev.elliot.outpost.outpost;

import org.bukkit.Location;
import java.util.UUID;

public class Outpost {

    public final Location center;
    public final int radius;
    public final int height;

    public UUID owner;
    public UUID capturer;
    public int progress;

    public Outpost(Location center, int radius, int height) {
        this.center = center;
        this.radius = radius;
        this.height = height;
    }
}
