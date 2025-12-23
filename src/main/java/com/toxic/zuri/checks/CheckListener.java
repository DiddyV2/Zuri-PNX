package com.toxic.zuri.checks;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleSprintEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryMoveItemEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;

public class CheckListener implements Listener {

    private final CheckRegistry registry;

    public CheckListener(CheckRegistry registry) {
        this.registry = registry;
    }

    private void runChecks(Player player, Object data) {
        for (Check check : registry.getChecks()) {
            if (!check.enabled()) continue;
            check.handle(player, data);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        runChecks(event.getPlayer(), event);
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {
        runChecks(event.getPlayer(), event);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        runChecks((Player) event.getDamager(), event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        runChecks(event.getPlayer(), event);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        runChecks(event.getPlayer(), event);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        runChecks(event.getPlayer(), event);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        runChecks((Player) event.getPlayer(), event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        runChecks((Player) event.getPlayer(), event);
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (event.getInventory().getHolder() instanceof Player) {
            runChecks((Player) event.getInventory().getHolder(), event);
        }
        if (event.getTargetInventory().getHolder() instanceof Player) {
            runChecks((Player) event.getTargetInventory().getHolder(), event);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        runChecks(event.getPlayer(), event);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getShooter() instanceof Player) {
            runChecks((Player) event.getShooter(), event);
        }
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            runChecks((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        runChecks(event.getPlayer(), event);
    }
}
