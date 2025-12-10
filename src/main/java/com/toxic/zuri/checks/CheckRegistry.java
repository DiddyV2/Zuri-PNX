package com.toxic.zuri.checks;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.blockplace.scaffold.ScaffoldA;

import java.util.ArrayList;
import java.util.List;

public class CheckRegistry {

    private final Zuri plugin;
    private final List<Check> checks = new ArrayList<>();

    public CheckRegistry(Zuri plugin) {
        this.plugin = plugin;
    }


    public void add(Check check) {
        checks.add(check);
    }

    public void registerAll() {
        add(new PlaceholderCheck(plugin));
        add(new ScaffoldA(plugin));

        plugin.getLogger().info("[!] Loaded " + checks.size() + " Zuri checks.");
    }

    public List<Check> getChecks() {
        return checks;
    }
}
