package com.toxic.zuri.checks.badpackets;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashMap;
import java.util.Map;

public class FastThrowA extends Check {

    private final Map<Player, Double> lastUse = new HashMap<>();

    public FastThrowA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "FastThrow",
                "A",
                5,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof ProjectileLaunchEvent)) {
            return;
        }

        ProjectileLaunchEvent event = (ProjectileLaunchEvent) data;
        if (!(event.getShooter() instanceof Player)) {
            return;
        }

        Player p = (Player) event.getShooter();
        if (event.getEntity() instanceof EntityArrow) {
            return;
        }

        double currentTime = System.nanoTime() / 1_000_000_000D; // uhm
        Double last = lastUse.get(p);

        if (last != null) {
            double diff = currentTime - last;
            if (diff < 0.2 && p.getPing() < 200) {
                event.setCancelled(true);
                flag(p, 1.0f, "FastThrow violation");
                lastUse.remove(p);
            }
        } else {
            lastUse.put(p, currentTime);
        }
    }
}
