package com.toxic.zuri.checks.blockbreak;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.effect.Effect;
import cn.nukkit.entity.effect.EffectType;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class InstaBreakA extends Check {

    private final Map<Player, Long> breakTimes = new HashMap<>();

    public InstaBreakA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "InstaBreak",
                "A",
                5,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {

        if (data instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) data;
            if (interact.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                breakTimes.put(player, System.nanoTime() / 50_000_000L); // should be mb microtime * 20
            }
            return;
        }

        if (data instanceof BlockBreakEvent) {
            BlockBreakEvent event = (BlockBreakEvent) data;
            Block block = event.getBlock();

            if (block.getId() == BlockID.BAMBOO) {
                return;
            }

            Long lastBreak = breakTimes.get(player);
            if (lastBreak == null) {
                event.setCancelled(true);
                return;
            }

            if (!player.isOnline()) {
                return;
            }

            double expectedTime = Math.ceil(block.getBreakTime(event.getItem()) * 20);

            Effect haste = player.getEffect(EffectType.HASTE);
            if (haste != null) {
                expectedTime *= 1 - (0.2 * haste.getAmplifier());
            }

            Effect miningFatigue = player.getEffect(EffectType.MINING_FATIGUE);
            if (miningFatigue != null) {
                expectedTime *= 1 + (0.3 * miningFatigue.getAmplifier());
            }

            expectedTime -= 1;
            double actualTime = (System.nanoTime() / 50_000_000L) - lastBreak;

            if (actualTime < expectedTime) {
                flag(player, 1.0f, "InstaBreak violation");
                event.setCancelled(true);
            }

            breakTimes.remove(player);
        }
    }
}
