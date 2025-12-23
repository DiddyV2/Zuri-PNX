package com.toxic.zuri.checks.inventory;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class InventoryMoveA extends Check {

    private final Map<Player, Boolean> inventoryOpen = new HashMap<>();

    public InventoryMoveA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "InventoryMove",
                "A",
                5,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {

        if (data instanceof InventoryOpenEvent) {
            inventoryOpen.put(player, true);
            return;
        }

        if (data instanceof InventoryCloseEvent) {
            inventoryOpen.remove(player);
            return;
        }

        if (data instanceof PlayerMoveEvent) {
            if (inventoryOpen.getOrDefault(player, false)) {
                PlayerMoveEvent moveEvent = (PlayerMoveEvent) data;
                double dx = moveEvent.getTo().getX() - moveEvent.getFrom().getX();
                double dz = moveEvent.getTo().getZ() - moveEvent.getFrom().getZ();
                double moveSensitivity = dx * dx + dz * dz;

                if (moveSensitivity > 0.1) {
                    flag(player, 1.0f, "Moved while inventory open");
                }
            }
        }
    }
}
