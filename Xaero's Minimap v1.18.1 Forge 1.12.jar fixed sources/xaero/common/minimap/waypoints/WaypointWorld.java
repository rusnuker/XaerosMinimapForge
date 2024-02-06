/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.minimap.waypoints;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorldContainer;

public class WaypointWorld {
    private String id;
    private HashMap<String, WaypointSet> sets;
    private HashMap<Integer, Waypoint> serverWaypoints;
    private HashMap<String, Boolean> serverWaypointsDisabled;
    private String current = "gui.xaero_default";
    private WaypointWorldContainer container;
    private List<String> toRemoveOnSave;

    public WaypointWorld(WaypointWorldContainer container, String id) {
        this.container = container;
        this.id = id;
        this.sets = new HashMap();
        this.serverWaypoints = new HashMap();
        this.serverWaypointsDisabled = new HashMap();
        this.addSet("gui.xaero_default");
        this.toRemoveOnSave = new ArrayList<String>();
    }

    public WaypointSet getCurrentSet() {
        return this.sets.get(this.current);
    }

    public void addSet(String s) {
        this.sets.put(s, new WaypointSet(s));
    }

    public void onSaveCleanup(File worldFile) throws IOException {
        Path folder = worldFile.toPath().getParent();
        for (int i = 0; i < this.toRemoveOnSave.size(); ++i) {
            String s = this.toRemoveOnSave.get(i);
            Path path = folder.resolve(this.id + "_" + s + ".txt");
            Files.deleteIfExists(path);
        }
    }

    public HashMap<String, Boolean> getServerWaypointsDisabled() {
        return this.serverWaypointsDisabled;
    }

    public HashMap<Integer, Waypoint> getServerWaypoints() {
        return this.serverWaypoints;
    }

    public HashMap<String, WaypointSet> getSets() {
        return this.sets;
    }

    public String getCurrent() {
        return this.current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getId() {
        return this.id;
    }

    public String getFullId() {
        return this.container.getKey() + "_" + this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WaypointWorldContainer getContainer() {
        return this.container;
    }

    public void setContainer(WaypointWorldContainer container) {
        this.container = container;
    }

    public void requestRemovalOnSave(String name) {
        this.toRemoveOnSave.add(name);
    }

    public boolean hasSomethingToRemoveOnSave() {
        return !this.toRemoveOnSave.isEmpty();
    }
}

