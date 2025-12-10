package com.toxic.zuri.checks;

import com.toxic.zuri.Zuri;

import cn.nukkit.Player;

public class PlaceholderCheck extends Check {

    public PlaceholderCheck(Zuri plugin) {
        super(plugin, new CheckInfo(
                "Placeholder",
                "A",
                5,
                true
        ));
    }

    public void handle(Player player, Object data) 
    {
    }
}
