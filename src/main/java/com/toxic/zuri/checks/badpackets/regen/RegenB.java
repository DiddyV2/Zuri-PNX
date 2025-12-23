package com.toxic.zuri.checks.badpackets.regen;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.entity.EntityRegainHealthEvent;

import java.util.HashMap;
import java.util.Map;

public class RegenB extends Check {

    private final Map<Player, Double> lastHealthTickB = new HashMap<>();
    private final Map<Player, Float> healCountB = new HashMap<>();
    private final Map<Player, Float> healTimeB = new HashMap<>();

    public RegenB(Zuri plugin) {
        super(plugin, new CheckInfo(
                "Regen",
                "B",
                3,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof EntityRegainHealthEvent)) return;

        EntityRegainHealthEvent event = (EntityRegainHealthEvent) data;
        if (!(event.getEntity() instanceof Player)) return;

        int reason = event.getRegainReason();
        if (reason == EntityRegainHealthEvent.CAUSE_MAGIC || reason == EntityRegainHealthEvent.CAUSE_CUSTOM) return;

        double tick = Server.getInstance().getTick();
        double tps = Server.getInstance().getTicksPerSecond();
        double lastTick = lastHealthTickB.getOrDefault(player, 0.0);
        float healAmount = event.getAmount();

        if (tps > 0.0 && lastTick != -1.0) {
            double diffTicks = tick - lastTick;
            double delta = diffTicks / tps;

            float count = healCountB.getOrDefault(player, 0f);
            float time = healTimeB.getOrDefault(player, 0f);

            if (delta < 10) {
                count += healAmount;
                time += delta;

                if (count >= 5) {
                    float healRate = count / time;
                    if (healRate > 0.5) {
                        flag(player, 1.0f, "RegenB violation, healRate=" + healRate);
                    }
                    count = 0;
                    time = 0;
                }

                healCountB.put(player, count);
                healTimeB.put(player, time);
            }
        }

        lastHealthTickB.put(player, tick);
    }
}
