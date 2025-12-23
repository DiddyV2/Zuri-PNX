package com.toxic.zuri.checks.inventory;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.InventoryMoveItemEvent;

import java.util.HashMap;
import java.util.Map;

public class ChestStealerA extends Check {

    private final Map<Player, Integer> ticksN = new HashMap<>();
    private final Map<Player, Double> lastTimeN = new HashMap<>();

    public ChestStealerA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "ChestStealer",
                "A",
                0,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {

        if (!(data instanceof InventoryMoveItemEvent)) {
            return;
        }
        
        double now = System.nanoTime() / 1_000_000_000D;

        Integer ticks = ticksN.get(player);
        Double lastTime = lastTimeN.get(player);

        if (ticks != null && lastTime != null) {
            double diff = now - lastTime;

            if (diff > 0.1) {
                if (ticks > 1) {
                    flag(player, 1.0f, "Chest stealer pattern");
                }
                ticksN.remove(player);
                lastTimeN.remove(player);
            } else {
                ticksN.put(player, ticks + 1);
            }
        } else {
            ticksN.put(player, 0);
            lastTimeN.put(player, now);
        }
    }
}
