/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.minimap.waypoints;

import java.util.ArrayList;
import xaero.common.minimap.waypoints.Waypoint;

public class WaypointSet {
    private String name;
    private ArrayList<Waypoint> list;

    public WaypointSet(String name) {
        this.name = name;
        this.list = new ArrayList();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Waypoint> getList() {
        return this.list;
    }
}

