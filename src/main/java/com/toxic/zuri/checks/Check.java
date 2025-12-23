package com.toxic.zuri.checks;

import cn.nukkit.Player;
import com.toxic.zuri.Zuri;

public abstract class Check {

    public static final String PREFIX = "§7(§e§lZuri§r§7) §8>§r ";

    protected final Zuri plugin;
    protected final CheckInfo info;

    public Check(Zuri plugin, CheckInfo info) {
        this.plugin = plugin;
        this.info = info;
    }

    public String getName() {
        return info.name();
    }

    public String getType() {
        return info.type();
    }

    public int getMaxVl() {
        return info.maxVl();
    }

    public boolean enabled() {
        return info.enabled();
    }

    public abstract void handle(Player player, Object data);

    public void flag(Player player, float amount, String reason) {
        String msg = PREFIX + player.getName() +
                " failed " + getName() + getType() +
                " (+" + amount + ") Reason: " + reason;

        player.sendMessage(msg);
        plugin.getLogger().warning(msg);
    }

    public void reward(Player player, float amount, String reason) {
        String msg = PREFIX + player.getName() +
                " rewarded on " + getName() + getType() +
                " (-" + amount + ") Reason: " + reason;

        player.sendMessage(msg);
        plugin.getLogger().info(msg);
    }
}
