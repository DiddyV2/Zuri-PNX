package com.toxic.zuri.checks.inventory;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.InventoryMoveItemEvent;

import java.util.HashMap;
import java.util.Map;

public class InventoryCleanerA extends Check {

    private final Map<Player, Double> ticksTransaction = new HashMap<>();
    private final Map<Player, Integer> transaction = new HashMap<>();

    public InventoryCleanerA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "InventoryCleaner",
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
        Double ticks = ticksTransaction.get(player);
        Integer transCount = transaction.get(player);

        if (ticks != null && transCount != null) {
            double diff = now - ticks;

            if (diff > 2) {
                if (transCount > 20) {
                    flag(player, 1.0f, "InventoryCleaner violation");
                    player.kick("Potential Inventory Cleaner");
                }
                ticksTransaction.remove(player);
                transaction.remove(player);
            } else {
                transaction.put(player, transCount + 1);
            }
        } else {
            ticksTransaction.put(player, now);
            transaction.put(player, 0);
        }
    }
}
