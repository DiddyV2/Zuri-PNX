package com.toxic.zuri.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleSprintEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;

public class CheckListener implements Listener {

    private final CheckRegistry registry;

    public CheckListener(CheckRegistry registry) {
        this.registry = registry;
    }

    private void runChecks(Player player, Object... data) {
        for (Check check : registry.getChecks()) {
            if (!check.enabled()) continue;

            check.handle(player, data);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        runChecks(player, event);
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        runChecks(player, event);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        runChecks(player, event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        runChecks(player, event);
    }
}
