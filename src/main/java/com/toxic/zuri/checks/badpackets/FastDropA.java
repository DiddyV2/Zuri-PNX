package com.toxic.zuri.checks.badpackets;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.Map;

public class FastDropA extends Check {

    private final Map<Player, Double> lastTick = new HashMap<>();

    public FastDropA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "FastDrop",
                "A",
                5,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof PlayerDropItemEvent)) {
            return;
        }

        PlayerDropItemEvent event = (PlayerDropItemEvent) data;
        double currentTick = System.nanoTime() / 1_000_000_000D; 

        Double last = lastTick.get(player);

        if (last != null) {
            double diff = currentTick - last;
            int ping = player.getPing();

            if (diff < 0.06 && ping < 200) {
                event.setCancelled(true);
                flag(player, 1.0f, "potential FastDrop violation");
            }

            // ...still cant use debug, soon tho
            //debug(player, "lastTick=" + last + ", diff=" + diff);
        }

        lastTick.put(player, currentTick);
    }
}
