//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 */
package xaero.common.api.spigot;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.message.out.OutMessageHandshake;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.minimap.waypoints.WaypointsManager;

public class ServerWaypointStorage {
    public static final String CHANNEL_NAME = "XaeroMinimap";
    private static WaypointsManager waypointsManager;
    private static String containerID;
    public static String autoWorldUID;
    public static List<String[]> worldsToAdd;
    private static boolean handshakeSent;

    public static boolean working() {
        return containerID != null;
    }

    public static void update(IXaeroMinimap modMain, WaypointsManager wm) {
        waypointsManager = wm;
        String realContainer = ServerWaypointStorage.getWorldContainerId();
        if (!(realContainer != null && realContainer.equals(containerID) || containerID == null)) {
            WaypointWorldContainer container = waypointsManager.getWorldContainer(containerID);
            if (container != null) {
                ArrayList<WaypointWorld> worlds = new ArrayList<WaypointWorld>(container.worlds.values());
                for (int i = 0; i < worlds.size(); ++i) {
                    worlds.get(i).getServerWaypoints().clear();
                }
            }
            handshakeSent = false;
        }
        if ((containerID = realContainer) == null) {
            return;
        }
        if (!handshakeSent) {
            modMain.getNetwork().sendToServer((IMessage)new OutMessageHandshake());
            handshakeSent = true;
        }
        while (worldsToAdd.size() > 0) {
            String[] args = worldsToAdd.remove(0);
            String multiworldID = ServerWaypointStorage.getMultiWorldId(args[0]);
            WaypointWorldContainer container = waypointsManager.addWorldContainer(containerID);
            container.addWorld(multiworldID);
            container.addName(multiworldID, args[1]);
        }
    }

    private static String getWorldContainerId() {
        if (Minecraft.getMinecraft().getConnection() != null && Minecraft.getMinecraft().getCurrentServerData() != null) {
            String serverIP = Minecraft.getMinecraft().getCurrentServerData().serverIP;
            return "Multiplayer_" + serverIP.replace(":", "\u00a7").replace("_", "%us%");
        }
        return null;
    }

    private static String getMultiWorldId(String UID) {
        return "plugin" + UID;
    }

    public static String getAutoContainer() {
        return containerID;
    }

    public static String getAutoWorld() {
        return ServerWaypointStorage.getMultiWorldId(autoWorldUID);
    }

    public static void addWorld(String worldUID, String name) {
        worldsToAdd.add(new String[]{worldUID, name});
    }

    public static WaypointWorld getWorld(String worldUID) {
        return waypointsManager.getWorld(containerID, ServerWaypointStorage.getMultiWorldId(worldUID));
    }

    public static void removeWaypoint(int id) {
        WaypointWorldContainer container = waypointsManager.getWaypointMap().get(containerID);
        if (container == null) {
            return;
        }
        ArrayList<WaypointWorld> worlds = new ArrayList<WaypointWorld>(container.worlds.values());
        for (int i = 0; i < worlds.size(); ++i) {
            WaypointWorld w = worlds.get(i);
            if (w.getServerWaypoints().isEmpty() || w.getServerWaypoints().remove(id) == null) continue;
            return;
        }
    }

    static {
        containerID = null;
        autoWorldUID = null;
        handshakeSent = false;
        worldsToAdd = new ArrayList<String[]>();
    }
}

