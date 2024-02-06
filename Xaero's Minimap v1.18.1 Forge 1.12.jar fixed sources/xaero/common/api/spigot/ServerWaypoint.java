/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.api.spigot;

import xaero.common.api.spigot.ServerWaypointStorage;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorld;

public class ServerWaypoint
extends Waypoint {
    private String worldUID;
    private int ID;

    public ServerWaypoint(String worldUID, int ID, int x, int y, int z, String name, String symbol, int color, boolean rotation, int yaw) {
        super(x, y, z, name, symbol, color, 0);
        this.worldUID = worldUID;
        this.ID = ID;
        this.setRotation(rotation);
        this.setYaw(yaw);
    }

    public String getWorldUID() {
        return this.worldUID;
    }

    public int getID() {
        return this.ID;
    }

    @Override
    public boolean isDisabled() {
        WaypointWorld w = ServerWaypointStorage.getWorld(this.worldUID);
        Boolean d = w.getServerWaypointsDisabled().get(this.getX() + "_" + this.getY() + "_" + this.getZ());
        return d != null && d != false;
    }

    @Override
    public void setDisabled(boolean b) {
        ServerWaypointStorage.getWorld(this.worldUID).getServerWaypointsDisabled().put(this.getX() + "_" + this.getY() + "_" + this.getZ(), b);
    }
}

