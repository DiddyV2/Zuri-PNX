package com.toxic.zuri.checks.badpackets;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemFood;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.EntityEventPacket;

import java.util.HashMap;
import java.util.Map;

public class FastEatA extends Check {

    private final Map<Player, Double> lastTick = new HashMap<>();

    public FastEatA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "FastEat",
                "A",
                5,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        double currentTick = System.nanoTime() / 1_000_000_000D;

        if (data instanceof DataPacketReceiveEvent) {
            DataPacketReceiveEvent event = (DataPacketReceiveEvent) data;
            DataPacket packet = event.getPacket();

            if (packet instanceof EntityEventPacket) {
                EntityEventPacket actorEvent = (EntityEventPacket) packet;
                if (actorEvent.event == EntityEventPacket.EATING_ITEM) {
                    lastTick.putIfAbsent(player, currentTick);
                    // EH
                    //debug(player, "lastTick=" + lastTick.get(player));
                }
            }
        }

        if (data instanceof PlayerItemConsumeEvent) {
            PlayerItemConsumeEvent event = (PlayerItemConsumeEvent) data;
            if (event.getItem() instanceof ItemFood) {
                Double last = lastTick.get(player);
                if (last != null) {
                    double diff = currentTick - last;
                    int ping = player.getPing();

                    if (diff < 1.5 && ping < 200) {
                        event.setCancelled(true);
                        flag(player, 1.0f, "FastEat violation");
                        lastTick.remove(player);
                    }

                    //...yk
                    //debug(player, "lastTick=" + last + ", diff=" + diff);
                }
            }
        }
    }
}
