package com.toxic.zuri.checks.combat;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.entity.EntityShootBowEvent;

import java.util.HashMap;
import java.util.Map;

public class FastBow extends Check {

    private final Map<Player, Double> shootFirstTickA = new HashMap<>();
    private final Map<Player, Float> hsTimeSum = new HashMap<>();
    private final Map<Player, Integer> currentHsIndex = new HashMap<>();
    private final Map<Player, float[]> hsTimeList = new HashMap<>();

    public FastBow(Zuri plugin) {
        super(plugin, new CheckInfo(
                "FastBow",
                "A",
                1,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof EntityShootBowEvent)) return;

        double tick = Server.getInstance().getTick();
        double tps = Server.getInstance().getTicksPerSecond();

        double firstTick = shootFirstTickA.getOrDefault(player, -1.0);
        float sum = hsTimeSum.getOrDefault(player, 0f);
        int index = currentHsIndex.getOrDefault(player, 0);
        float[] list = hsTimeList.getOrDefault(player, new float[5]);

        if (firstTick == -1) firstTick = tick - 30;

        double tickDiff = tick - firstTick;
        double delta = tickDiff / tps;

        list[index] = (float) delta;
        sum = sum - list[index] + (float) delta;

        index++;
        if (index >= 5) index = 0;

        float hsHitTime = sum / 5f;

        shootFirstTickA.put(player, firstTick);
        hsTimeSum.put(player, sum);
        currentHsIndex.put(player, index);
        hsTimeList.put(player, list);

        // no
        //debug(player, "tick=" + tick + ", tickDiff=" + tickDiff + ", tps=" + tps + ", firstTick=" + firstTick + ", hsHitTime=" + hsHitTime);

        if (hsHitTime < 0.65) {
            flag(player, 1.0f, "FastBow violation");
        }
    }
}
