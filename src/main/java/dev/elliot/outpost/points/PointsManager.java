package dev.elliot.outpost.points;
import java.util.*;
public class PointsManager {
private final Map<UUID,Integer> points = new HashMap<>();
public void add(UUID id){ points.put(id, points.getOrDefault(id,0)+1); }
public int get(UUID id){ return points.getOrDefault(id,0); }
public List<Map.Entry<UUID,Integer>> leaderboard(){
List<Map.Entry<UUID,Integer>> list = new ArrayList<>(points.entrySet());
list.sort((a,b)->Integer.compare(b.getValue(), a.getValue()));
return list;
}
}