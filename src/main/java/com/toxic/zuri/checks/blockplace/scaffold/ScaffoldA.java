package com.toxic.zuri.checks.blockplace.scaffold;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.math.Vector3;

public class ScaffoldA extends Check {

    public ScaffoldA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "Scaffold",
                "A",
                5,
                true));
    }

    public void handle(Player player, Object data) {
        if (data instanceof BlockPlaceEvent ev) {
            Block block = ev.getBlock();
            Vector3 playerPos = player.getPosition();
            Vector3 blockPos = block.getPosition();

            Vector3 directionVector = blockPos.subtract(playerPos.x, playerPos.y, playerPos.z).normalize();

            float playerYaw = (float)player.getLocation().getYaw();
            float playerPitch = (float)player.getLocation().getPitch();

            double yawRad = Math.toRadians(playerYaw);
            double pitchRad = Math.toRadians(playerPitch);

            Vector3 playerDirection = new Vector3(
                    -Math.sin(yawRad) * Math.cos(pitchRad),
                    -Math.sin(pitchRad),
                    Math.cos(yawRad) * Math.cos(pitchRad));

            double dotProduct = playerDirection.dot(directionVector);

            if (playerPos.y > blockPos.y) {
                if (dotProduct < 0.8
                        && Math.abs(playerPitch) < 45) {

                    ev.setCancelled(true);
                    this.flag(player, 1.0f, "product=" + dotProduct +
                            " pitch=" + playerPitch);
                }
            }
        }
    }
}
