package com.toxic.zuri.checks.badpackets;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;

public class SelfHitA extends Check {

    public SelfHitA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "SelfHit",
                "A",
                2,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) data;

        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player entity = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (entity.getId() == damager.getId()) {
                flag(player, 1.0f, "SelfHit violation");
            }
        }
    }
}
