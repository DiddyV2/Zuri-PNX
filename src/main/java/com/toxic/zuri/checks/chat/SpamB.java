package com.toxic.zuri.checks.chat;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class SpamB extends Check {

    private final Map<Player, String> lastMessage = new HashMap<>();

    public SpamB(Zuri plugin) {
        super(plugin, new CheckInfo(
                "Spam",
                "B",
                0,
                true
        ));
    }

    @Override
    public void handle(Player player, Object data) {

        if (!(data instanceof PlayerChatEvent)) {
            return;
        }

        PlayerChatEvent event = (PlayerChatEvent) data;

        if (event.isCancelled()) {
            return;
        }

        String message = event.getMessage();
        String last = lastMessage.get(player);

        if (!player.isOnline()) {
            return;
        }

        boolean violation = false;

        if (last != null) {
            String[] splitCurrent = message.split(" ");
            String[] splitLast = last.split(" ");

            if (splitCurrent.length == splitLast.length && splitCurrent.length == 1) {
                char[] charsCurrent = splitCurrent[0].toLowerCase().toCharArray();
                char[] charsLast = splitLast[0].toLowerCase().toCharArray();
                int count = 0;
                for (int i = 0; i < charsCurrent.length && i < charsLast.length; i++) {
                    if (charsCurrent[i] == charsLast[i]) {
                        count++;
                    }
                }
                if (charsLast.length - count <= count) {
                    violation = true;
                }
            }

            int countWords = 0;
            Map<String, String> charsMap = new HashMap<>();
            for (String s : splitCurrent) {
                charsMap.put(s.toLowerCase(), s.toLowerCase());
            }
            for (String s : splitLast) {
                if (charsMap.containsKey(s.toLowerCase())) {
                    countWords++;
                }
            }
            if (charsMap.size() - countWords <= countWords) {
                violation = true;
            }
        }

        if (violation) {
            // TODO: Make this configurable
            player.sendMessage(TextFormat.RED + "Continously repeating text.");
            event.setCancelled(true);
        }

        lastMessage.put(player, message);
    }
}
