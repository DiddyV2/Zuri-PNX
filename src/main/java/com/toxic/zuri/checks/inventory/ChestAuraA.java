package com.toxic.zuri.checks.inventory;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryMoveItemEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.inventory.ChestInventory;
import cn.nukkit.inventory.CrafterInventory;
import cn.nukkit.inventory.HumanInventory;
import cn.nukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class ChestAuraA extends Check {

    private final Map<Player, Integer> countTransaction = new HashMap<>();
    private final Map<Player, Double> timeOpenChest = new HashMap<>();

    public ChestAuraA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "ChestAura",
                "A",
                0,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {

        if (data instanceof InventoryOpenEvent) {
            InventoryOpenEvent event = (InventoryOpenEvent) data;
            Inventory inv = event.getInventory();

            if (!timeOpenChest.containsKey(player) && !(inv instanceof CrafterInventory)) {
                timeOpenChest.put(player, System.nanoTime() / 1_000_000_000D);
            }

            return;
        }

        if (data instanceof InventoryCloseEvent) {
            if (timeOpenChest.containsKey(player) && countTransaction.containsKey(player)) {
                double timeDiff = (System.nanoTime() / 1_000_000_000D) - timeOpenChest.get(player);

                int count = countTransaction.get(player);
                double limit = count / 3D;

                if (timeDiff < limit) {
                    flag(player, 1.0f, "Potential chest aura");
                }
            }

            timeOpenChest.remove(player);
            countTransaction.remove(player);
            return;
        }

        if (data instanceof InventoryMoveItemEvent) {
            InventoryMoveItemEvent event = (InventoryMoveItemEvent) data;

            Inventory from = event.getInventory();
            Inventory to = event.getTargetInventory();

            if (from instanceof ChestInventory || to instanceof HumanInventory) {
                if (countTransaction.containsKey(player) && timeOpenChest.containsKey(player)) {
                    countTransaction.put(player, countTransaction.get(player) + 1);
                } else {
                    countTransaction.put(player, 0);
                }
            }
        }
    }
}
