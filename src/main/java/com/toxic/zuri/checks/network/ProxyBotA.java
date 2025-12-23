package com.toxic.zuri.checks.network;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerLoginEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;

public class ProxyBotA extends Check {

    private static final Gson GSON = new Gson();

    public ProxyBotA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "ProxyBot",
                "A",
                0,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {
        if (!(data instanceof PlayerLoginEvent)) {
            return;
        }

        PlayerLoginEvent event = (PlayerLoginEvent) data;
        String ip = player.getAddress();

        try {
            // decrepated usage, but eh
            URL url = new URL("https://proxycheck.io/v2/" + ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            Map<?, ?> dataMap = GSON.fromJson(response.toString(), Map.class);

            Object status = dataMap.get("status");
            Object ipData = dataMap.get(ip);

            if (status != null && !"error".equals(status.toString()) && ipData instanceof Map) {
                Map<?, ?> ipResult = (Map<?, ?>) ipData;
                Object proxy = ipResult.get("proxy");

                if ("yes".equals(proxy)) {
                    //warn sys aint exist yet
                    //warn(player.getName());
                    event.setKickMessage("Error: Potential proxy, turn it off");
                    event.setCancelled(true);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
