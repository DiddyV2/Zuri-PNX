package com.toxic.zuri.checks.network.editionfaker;

import com.toxic.zuri.Zuri;
import com.toxic.zuri.checks.Check;
import com.toxic.zuri.checks.CheckInfo;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.utils.LoginChainData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EditionFakerA extends Check {

    public static final int UNKNOWN = -1;
    public static final int ANDROID = 1;
    public static final int IOS = 2;
    public static final int OSX = 3;
    public static final int AMAZON = 4;
    public static final int GEAR_VR = 5;
    public static final int HOLOLENS = 6;
    public static final int WINDOWS_10 = 7;
    public static final int WIN32 = 8;
    public static final int DEDICATED = 9;
    public static final int TVOS = 10;
    public static final int PLAYSTATION = 11;
    public static final int NINTENDO = 12;
    public static final int XBOX = 13;
    public static final int WINDOWS_PHONE = 14;

    private static final Set<Integer> NULL_MODELS = new HashSet<>(Arrays.asList(
            ANDROID,
            OSX,
            WINDOWS_10,
            WIN32,
            DEDICATED
    ));

    private static final Set<Integer> DEVICE_OS_LIST = new HashSet<>(Arrays.asList(
            ANDROID,
            IOS,
            AMAZON,
            WINDOWS_10,
            WIN32,
            PLAYSTATION,
            NINTENDO,
            XBOX
    ));

    public EditionFakerA(Zuri plugin) {
        super(plugin, new CheckInfo(
                "EditionFaker",
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
        LoginChainData client = player.getLoginChainData();
        int deviceOS = client.getDeviceOS();
        String deviceModel = client.getDeviceModel();
        String deviceId = client.getDeviceId();

        if (!DEVICE_OS_LIST.contains(deviceOS)) {
            //no warn sys yet
            //warn(nickname);
            event.setKickMessage("Fake edition");
            event.setCancelled(true);
            return;
        }

        if (!NULL_MODELS.contains(deviceOS) && (deviceModel == null || deviceModel.isEmpty())) {
            //no warn sys yet
            //warn(nickname);
            event.setKickMessage("Fake edition");
            event.setCancelled(true);
            return;
        }

        if (deviceOS == IOS) {
            //no warn sys yet
            //warn(nickname);
            if (deviceId != null && !deviceId.equals(deviceId.toUpperCase())) {
                event.setKickMessage("Fake edition");
                event.setCancelled(true);
            }
        }
    }
}
