package com.toxic.zuri.checks.badpackets.regen;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityRegainHealthEvent;

public class RegenA extends Check {

    public RegenA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "Regen",
                "A",
                3,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof EntityRegainHealthEvent)) {
            return;
        }

        EntityRegainHealthEvent event = (EntityRegainHealthEvent) data;

        if (event.getEntity() instanceof Player) {
            int reason = event.getRegainReason();
            if (reason != EntityRegainHealthEvent.CAUSE_MAGIC && reason != EntityRegainHealthEvent.CAUSE_CUSTOM) {
                float healAmount = event.getAmount();
                if (healAmount > 3) {
                    flag(player, 1.0f, "Regen violation, healAmount=" + healAmount);
                }
            }
        }
    }
}
