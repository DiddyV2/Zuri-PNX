package com.toxic.zuri.checks;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.badpackets.FastDropA;
import com.toxic.zuri.checks.badpackets.FastEatA;
import com.toxic.zuri.checks.badpackets.FastThrowA;
import com.toxic.zuri.checks.badpackets.SelfHitA;
import com.toxic.zuri.checks.badpackets.regen.RegenA;
import com.toxic.zuri.checks.badpackets.regen.RegenB;
import com.toxic.zuri.checks.blockbreak.InstaBreakA;
import com.toxic.zuri.checks.blockplace.scaffold.ScaffoldA;
import com.toxic.zuri.checks.chat.SpamA;
import com.toxic.zuri.checks.chat.SpamB;
import com.toxic.zuri.checks.combat.FastBow;
import com.toxic.zuri.checks.combat.ImpossibleHit;
import com.toxic.zuri.checks.inventory.ChestAuraA;
import com.toxic.zuri.checks.inventory.ChestStealerA;
import com.toxic.zuri.checks.inventory.InventoryCleanerA;
import com.toxic.zuri.checks.inventory.InventoryMoveA;
import com.toxic.zuri.checks.network.ProxyBotA;
import com.toxic.zuri.checks.network.editionfaker.EditionFakerA;

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
        add(new ChestAuraA(plugin));
        add(new ChestStealerA(plugin));
        add(new FastBow(plugin));
        add(new ImpossibleHit(plugin));
        add(new InventoryCleanerA(plugin));
        add(new InventoryMoveA(plugin));
        add(new ChestStealerA(plugin));
        add(new ChestAuraA(plugin));
        //soon
        //add(new SpamA(plugin));
        add(new SpamB(plugin));
        add(new InstaBreakA(plugin));
        add(new SelfHitA(plugin));
        add(new FastThrowA(plugin));
        add(new FastEatA(plugin));
        add(new FastDropA(plugin));
        add(new RegenA(plugin));
        add(new RegenB(plugin));

        add(new EditionFakerA(plugin));
        add(new ProxyBotA(plugin));

        plugin.getLogger().info("[!] Loaded " + checks.size() + " Zuri checks.");
    }

    public List<Check> getChecks() {
        return checks;
    }
}
