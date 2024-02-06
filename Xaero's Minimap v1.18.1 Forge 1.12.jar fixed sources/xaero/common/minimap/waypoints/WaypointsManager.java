//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.dto.RealmsServer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.DimensionType
 *  net.minecraft.world.World
 */
package xaero.common.minimap.waypoints;

import com.mojang.realmsclient.dto.RealmsServer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.ServerWaypointStorage;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.misc.OptimizedMath;

public class WaypointsManager {
    private IXaeroMinimap modMain;
    private Minecraft mc;
    private HashMap<String, WaypointWorldContainer> waypointMap = new HashMap();
    private WaypointSet waypoints = null;
    private List<Waypoint> serverWaypoints = null;
    private String containerID = null;
    private String customContainerID = null;
    private String worldID = null;
    private String customWorldID = null;
    private BlockPos currentSpawn;
    private RealmsServer latestRealm;
    public long setChanged;
    public boolean renderAllSets;

    public WaypointsManager(IXaeroMinimap modMain, Minecraft mc) {
        this.modMain = modMain;
        this.mc = mc;
    }

    public boolean divideBy8(String worldContainerID) {
        return worldContainerID != null && Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.provider.getDimensionType() == DimensionType.NETHER && worldContainerID.endsWith(this.getDimensionDirectoryName(0));
    }

    public String getDimensionDirectoryName(int dim) {
        return "dim%" + dim;
    }

    public DimensionType findDimensionType(String validatedName) {
        DimensionType[] allDimensionTypes;
        for (DimensionType dt : allDimensionTypes = DimensionType.values()) {
            if (!validatedName.equals(dt.getName().replaceAll("[^a-zA-Z0-9_]+", ""))) continue;
            return dt;
        }
        return null;
    }

    public Integer getDimensionForDirectoryName(String dirName) {
        int dimId;
        String dimIdPart = dirName.substring(4);
        if (!dimIdPart.matches("-{0,1}[0-9]+")) {
            return null;
        }
        try {
            dimId = Integer.parseInt(dimIdPart);
        }
        catch (NumberFormatException nfe) {
            return null;
        }
        return dimId;
    }

    private String getContainer(World world) {
        String dim = this.getDimensionDirectoryName(world.provider.getDimension());
        if (this.mc.getIntegratedServer() != null) {
            return this.mc.getIntegratedServer().getFolderName().replace("_", "%us%").replace("/", "%fs%").replace("\\", "%bs%") + "/" + dim;
        }
        if (this.mc.getCurrentServerData() != null && this.currentSpawn != null) {
            String serverIP = this.mc.getCurrentServerData().serverIP;
            if (serverIP.contains(":")) {
                serverIP = serverIP.substring(0, serverIP.indexOf(":"));
            }
            if (this.mc.getCurrentServerData() != null && ServerWaypointStorage.autoWorldUID != null) {
                return ServerWaypointStorage.getAutoContainer();
            }
            return "Multiplayer_" + serverIP.replace(":", "\u00a7").replace("_", "%us%").replace("/", "%fs%").replace("\\", "%bs%") + "/" + dim;
        }
        if (this.mc.isConnectedToRealms() && this.latestRealm != null && this.currentSpawn != null) {
            return "Realms_" + this.latestRealm.ownerUUID + "." + this.latestRealm.id + "/" + dim;
        }
        return "Unknown";
    }

    public String getCurrentContainerAndWorldID() {
        return this.getCurrentContainerID() + "_" + this.getCurrentWorldID();
    }

    private String getWorld(World world) {
        if (this.mc.getIntegratedServer() != null) {
            return "waypoints";
        }
        if (this.currentSpawn != null) {
            if (this.mc.getCurrentServerData() != null && ServerWaypointStorage.autoWorldUID != null) {
                return ServerWaypointStorage.getAutoWorld();
            }
            return "mw" + (this.currentSpawn.getX() >> 6) + "," + (this.currentSpawn.getY() >> 6) + "," + (this.currentSpawn.getZ() >> 6);
        }
        return "null";
    }

    public String getCurrentContainerID() {
        if (this.customContainerID == null) {
            return this.containerID;
        }
        return this.customContainerID;
    }

    public String getCurrentWorldID() {
        if (this.customWorldID == null) {
            return this.worldID;
        }
        return this.customWorldID;
    }

    public WaypointWorld getCurrentWorld() {
        return this.getWorld(this.getCurrentContainerID(), this.getCurrentWorldID());
    }

    public WaypointWorld getAutoWorld() {
        return this.getWorld(this.getAutoContainerID(), this.getAutoWorldID());
    }

    public String getCurrentOriginContainerID() {
        if (this.getCurrentContainerID() == null) {
            return null;
        }
        return this.getCurrentContainerID().split("/")[0];
    }

    public String getAutoRootContainerID() {
        if (this.containerID == null) {
            return null;
        }
        return this.containerID.split("/")[0];
    }

    public String getAutoContainerID() {
        return this.containerID;
    }

    public String getAutoWorldID() {
        return this.worldID;
    }

    public WaypointWorld getWorld(String container, String world) {
        return this.addWorld(container, world);
    }

    public WaypointWorld addWorld(String container, String world) {
        if (container == null) {
            return null;
        }
        WaypointWorldContainer wc = this.addWorldContainer(container);
        return wc.addWorld(world);
    }

    public WaypointWorldContainer getWorldContainer(String id) {
        return this.addWorldContainer(id);
    }

    public WaypointWorldContainer addWorldContainer(String id) {
        WaypointWorldContainer container = null;
        String[] subs = id.split("/");
        for (int i = 0; i < subs.length; ++i) {
            if (i == 0) {
                container = this.waypointMap.get(subs[i]);
                if (container != null) continue;
                container = new WaypointWorldContainer(this.modMain, subs[i]);
                this.waypointMap.put(subs[i], container);
                continue;
            }
            container = container.addSubContainer(subs[i]);
        }
        return container;
    }

    public void removeContainer(String id) {
        WaypointWorldContainer container = null;
        String[] subs = id.split("/");
        for (int i = 0; i < subs.length; ++i) {
            if (i == 0) {
                container = this.waypointMap.get(subs[i]);
                if (container == null) {
                    return;
                }
                if (i != subs.length - 1) continue;
                this.waypointMap.remove(subs[i]);
                return;
            }
            if (container.containsSub(subs[i])) {
                if (i == subs.length - 1) {
                    container.deleteSubContainer(subs[i]);
                    return;
                }
                container = container.addSubContainer(subs[i]);
                continue;
            }
            return;
        }
    }

    public boolean containerExists(String id) {
        WaypointWorldContainer container = null;
        String[] subs = id.split("/");
        for (int i = 0; i < subs.length; ++i) {
            if (i == 0) {
                container = this.waypointMap.get(subs[i]);
                if (container == null) {
                    return false;
                }
                if (i != subs.length - 1) continue;
                return true;
            }
            if (container.containsSub(subs[i])) {
                if (i == subs.length - 1) {
                    return true;
                }
                container = container.addSubContainer(subs[i]);
                continue;
            }
            return false;
        }
        return false;
    }

    public void updateWorldIds() {
        String oldContainerID = this.containerID;
        String oldWorldID = this.worldID;
        this.containerID = this.getContainer((World)this.mc.world);
        if (this.containerID.equalsIgnoreCase(oldContainerID)) {
            this.containerID = oldContainerID;
        } else {
            Set<Map.Entry<String, WaypointWorldContainer>> entries = this.waypointMap.entrySet();
            for (Map.Entry<String, WaypointWorldContainer> e : entries) {
                String containerSearch = e.getValue().getEqualIgnoreCaseSub(this.containerID);
                if (containerSearch == null) continue;
                this.containerID = containerSearch;
                break;
            }
            String rootContainerId = this.getAutoRootContainerID();
            WaypointWorldContainer rootContainer = this.getWorldContainer(rootContainerId);
            rootContainer.renameOldContainer(this.containerID);
        }
        WaypointWorldContainer wwc = this.getWorldContainer(this.containerID);
        this.worldID = wwc.applyPointer(this.getWorld((World)this.mc.world));
        if (this.containerID != null && this.containerID.equals(oldContainerID) && this.worldID != null && !this.worldID.equals(oldWorldID) && this.modMain.getEvents().getDied() != -1L && System.currentTimeMillis() - this.modMain.getEvents().getDied() <= 1000L) {
            wwc.addPointer(this.worldID, oldWorldID);
            this.worldID = oldWorldID;
        }
    }

    public void updateWaypoints() {
        this.addWorld(this.containerID, this.worldID);
        WaypointWorld world = this.getCurrentWorld();
        this.waypoints = world.getCurrentSet();
        this.serverWaypoints = !world.getServerWaypoints().isEmpty() ? new ArrayList<Waypoint>(world.getServerWaypoints().values()) : null;
    }

    public void createDeathpoint(EntityPlayer p) {
        boolean disabled = false;
        if (this.waypoints == null) {
            return;
        }
        ArrayList<Waypoint> list = this.waypoints.getList();
        for (int i = 0; i < list.size(); ++i) {
            Waypoint w = (Waypoint)list.get(i);
            if (w.getType() != 1) continue;
            disabled = w.isDisabled();
            if (!this.modMain.getSettings().getDeathpoints() || !this.modMain.getSettings().getOldDeathpoints()) {
                list.remove(w);
                break;
            }
            w.setType(0);
            w.setName("gui.xaero_deathpoint_old");
            break;
        }
        boolean divideBy8 = this.divideBy8(this.getCurrentContainerID());
        if (this.modMain.getSettings().getDeathpoints()) {
            Waypoint deathpoint = new Waypoint(OptimizedMath.myFloor(p.posX) * (divideBy8 ? 8 : 1), OptimizedMath.myFloor(p.posY), OptimizedMath.myFloor(p.posZ) * (divideBy8 ? 8 : 1), "gui.xaero_deathpoint", "D", 0, 1);
            deathpoint.setDisabled(disabled);
            list.add(deathpoint);
        }
        try {
            this.modMain.getSettings().saveWaypoints(this.getCurrentWorld());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WaypointSet getWaypoints() {
        return this.waypoints;
    }

    public void setWaypoints(WaypointSet waypoints) {
        this.waypoints = waypoints;
    }

    public List<Waypoint> getServerWaypoints() {
        return this.serverWaypoints;
    }

    public HashMap<String, WaypointWorldContainer> getWaypointMap() {
        return this.waypointMap;
    }

    public RealmsServer getLatestRealm() {
        return this.latestRealm;
    }

    public void setLatestRealm(RealmsServer latestRealm) {
        this.latestRealm = latestRealm;
    }

    public void setCurrentSpawn(BlockPos currentSpawn) {
        this.currentSpawn = currentSpawn;
    }

    public String getCustomContainerID() {
        return this.customContainerID;
    }

    public void setCustomContainerID(String customContainerID) {
        this.customContainerID = customContainerID;
    }

    public String getCustomWorldID() {
        return this.customWorldID;
    }

    public void setCustomWorldID(String customWorldID) {
        this.customWorldID = customWorldID;
    }
}

