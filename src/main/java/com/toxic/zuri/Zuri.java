package com.toxic.zuri;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import com.toxic.zuri.checks.CheckListener;
import com.toxic.zuri.checks.CheckRegistry;

import cn.nukkit.event.Listener;

public class Zuri extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        CheckRegistry registry = new CheckRegistry(this);
        registry.registerAll();
        this.getLogger().info(TextFormat.GREEN + "Zuri is loading...");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new CheckListener(registry), this);
        this.getLogger().info(TextFormat.GREEN + "Zuri has been enabled!");
        this.getLogger().info(TextFormat.GOLD + "Warning: This plugin is currently in alpha and isnt 1 to 1 to the actual zuri, some improvements have been made.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.RED + "Zuri has been disabled.");
    }
}
