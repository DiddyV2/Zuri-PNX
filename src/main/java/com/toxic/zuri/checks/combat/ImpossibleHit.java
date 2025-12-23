package com.toxic.zuri.checks.combat;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;

public class ImpossibleHit extends Check {

    private final Map<String, Boolean> inventoryOpen = new HashMap<>();
    private final Map<String, Boolean> eating = new HashMap<>();

    public ImpossibleHit(Zuri plugin) {
        super(plugin, new CheckInfo(
                "ImpossibleHit",
                "A",
                5,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (data instanceof InventoryOpenEvent openEvent) {
            if (openEvent.getPlayer() instanceof Player p) {
                inventoryOpen.put(p.getName(), true);
            }
            return;
        }

        if (data instanceof InventoryCloseEvent closeEvent) {
            if (closeEvent.getPlayer() instanceof Player p) {
                inventoryOpen.put(p.getName(), false);
            }
            return;
        }

        if (data instanceof DataPacketReceiveEvent packetEvent) {
            if (packetEvent.getPacket() instanceof EntityEventPacket packet) {
                if (packet.event == EntityEventPacket.EATING_ITEM) {
                    eating.put(player.getName(), true);
                } else {
                    eating.remove(player.getName());
                }
            }
            return;
        }

        if (data instanceof EntityDamageByEntityEvent event) {
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
                boolean isInvOpen = inventoryOpen.getOrDefault(player.getName(), false);
                boolean isEating = eating.getOrDefault(player.getName(), false);

                if (isInvOpen || isEating) {
                    this.flag(player, 1.0f, "Impossible hit detected while inventory open or eating");
                }
                //this.debug(player, "isInventoryOpen=" + isInvOpen + ", isEating=" + isEating);
            }
        }
    }
}
