//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.DimensionType
 *  net.minecraftforge.common.DimensionManager
 */
package xaero.common.minimap.waypoints;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import xaero.common.IXaeroMinimap;
import xaero.common.file.SimpleBackup;
import xaero.common.minimap.waypoints.WaypointWorld;

public class WaypointWorldContainer {
    private IXaeroMinimap modMain;
    private String key;
    public HashMap<String, WaypointWorldContainer> subContainers;
    public HashMap<String, WaypointWorld> worlds;
    private HashMap<String, String> multiworldNames;
    private HashMap<String, String> pointers;

    public WaypointWorldContainer(IXaeroMinimap modMain, String key) {
        this.modMain = modMain;
        this.key = key;
        this.worlds = new HashMap();
        this.subContainers = new HashMap();
        this.multiworldNames = new HashMap();
        this.pointers = new HashMap();
    }

    public void setKey(String key) {
        this.key = key;
        for (WaypointWorldContainer s : this.subContainers.values()) {
            String[] subKeySplit = s.getKey().split("/");
            s.setKey(key + "/" + subKeySplit[subKeySplit.length - 1]);
        }
    }

    public WaypointWorldContainer addSubContainer(String subID) {
        WaypointWorldContainer c = this.subContainers.get(subID);
        if (c == null) {
            c = new WaypointWorldContainer(this.modMain, this.key + "/" + subID);
            this.subContainers.put(subID, c);
        }
        return c;
    }

    public boolean containsSub(String subId) {
        return this.subContainers.containsKey(subId);
    }

    public void deleteSubContainer(String subId) {
        this.subContainers.remove(subId);
    }

    public boolean isEmpty() {
        return this.subContainers.isEmpty() && this.worlds.isEmpty();
    }

    public WaypointWorld addWorld(String multiworldId) {
        WaypointWorld world = this.worlds.get(multiworldId);
        if (world == null) {
            WaypointWorld defaultWorld = this.worlds.get("waypoints");
            if (defaultWorld == null) {
                world = new WaypointWorld(this, multiworldId);
                this.worlds.put(multiworldId, world);
            } else {
                this.worlds.put(multiworldId, defaultWorld);
                try {
                    File defaultFile = this.modMain.getSettings().getWaypointsFile(defaultWorld);
                    defaultWorld.setId(multiworldId);
                    File fixedFile = this.modMain.getSettings().getWaypointsFile(defaultWorld);
                    Files.move(defaultFile.toPath(), fixedFile.toPath(), new CopyOption[0]);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.worlds.remove("waypoints");
                world = defaultWorld;
            }
        }
        return world;
    }

    public void addName(String id, String name) {
        String current = this.multiworldNames.get(id);
        if (current != null && !current.equals(name)) {
            this.worlds.get(id).requestRemovalOnSave(current);
        }
        this.multiworldNames.put(id, name);
    }

    public String getName(String id) {
        if (id.equals("waypoints")) {
            return null;
        }
        String name = this.multiworldNames.get(id);
        if (name == null) {
            int numericName = this.multiworldNames.size() + 1;
            while (this.multiworldNames.containsValue(name = "" + numericName++)) {
            }
            this.addName(id, name);
        }
        return name;
    }

    public void removeName(String id) {
        this.multiworldNames.remove(id);
    }

    public String getSubId() {
        return this.key.contains("/") ? this.key.substring(this.key.lastIndexOf("/") + 1) : "";
    }

    public String getFullName(String id) {
        String subID;
        String name = this.getName(id);
        String containerName = subID = this.getSubId();
        if (subID.startsWith("dim%")) {
            int dimId = Integer.parseInt(subID.substring(4));
            containerName = "Dim. " + dimId;
            if (this.getRootContainer().getKey().equals(this.modMain.getWaypointsManager().getAutoRootContainerID())) {
                DimensionType dimType;
                try {
                    dimType = DimensionManager.getProviderType((int)dimId);
                }
                catch (IllegalArgumentException iae) {
                    dimType = null;
                }
                if (dimType != null) {
                    containerName = containerName + " (" + dimType.getName() + ")";
                }
            }
        }
        if (name == null || this.worlds.size() < 2 && !containerName.isEmpty()) {
            return containerName;
        }
        return containerName.length() > 0 ? name + " - " + containerName : name;
    }

    public String getKey() {
        return this.key;
    }

    public WaypointWorld getFirstWorld() {
        if (!this.worlds.isEmpty()) {
            return this.worlds.values().toArray(new WaypointWorld[0])[0];
        }
        WaypointWorldContainer[] subs = this.subContainers.values().toArray(new WaypointWorldContainer[0]);
        for (int i = 0; i < subs.length; ++i) {
            WaypointWorld subFirst = subs[i].getFirstWorld();
            if (subFirst == null) continue;
            return subFirst;
        }
        return null;
    }

    public String toString() {
        return this.key + " sc:" + this.subContainers.size() + " w:" + this.worlds.size();
    }

    public ArrayList<WaypointWorld> getAllWorlds() {
        ArrayList<WaypointWorld> allWorlds = new ArrayList<WaypointWorld>(this.worlds.values());
        WaypointWorldContainer[] subs = this.subContainers.values().toArray(new WaypointWorldContainer[0]);
        for (int i = 0; i < subs.length; ++i) {
            allWorlds.addAll(subs[i].getAllWorlds());
        }
        return allWorlds;
    }

    public String applyPointer(String world) {
        if (this.pointers.containsKey(world)) {
            return this.pointers.get(world);
        }
        return world;
    }

    public void addPointer(String from, String to) {
        this.pointers.put(from, to);
    }

    public String getEqualIgnoreCaseSub(String cId) {
        CharSequence[] otherKeyArgs;
        if (cId.equalsIgnoreCase(this.key)) {
            return this.key;
        }
        if (this.subContainers.isEmpty()) {
            return null;
        }
        Set<Map.Entry<String, WaypointWorldContainer>> entries = this.subContainers.entrySet();
        for (Map.Entry<String, WaypointWorldContainer> entry : entries) {
            String subSearch = entry.getValue().getEqualIgnoreCaseSub(cId);
            if (subSearch == null) continue;
            return subSearch;
        }
        String[] keyArgs = this.key.split("/");
        if (keyArgs.length >= (otherKeyArgs = cId.split("/")).length) {
            return null;
        }
        for (int i = 0; i < keyArgs.length; ++i) {
            if (!otherKeyArgs[i].equalsIgnoreCase(keyArgs[i])) {
                return null;
            }
            otherKeyArgs[i] = keyArgs[i];
        }
        return String.join((CharSequence)"/", otherKeyArgs);
    }

    public WaypointWorldContainer getRootContainer() {
        if (!this.key.contains("/")) {
            return this;
        }
        return this.modMain.getWaypointsManager().getWorldContainer(this.key.substring(0, this.key.indexOf("/")));
    }

    public void renameOldContainer(String containerID) {
        if (this.subContainers.isEmpty()) {
            return;
        }
        String dimensionPart = containerID.split("/")[1];
        if (this.subContainers.containsKey(dimensionPart)) {
            return;
        }
        Integer dimId = this.modMain.getWaypointsManager().getDimensionForDirectoryName(dimensionPart);
        if (dimId == null) {
            return;
        }
        DimensionType dt = null;
        try {
            dt = DimensionManager.getProviderType((int)dimId);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (dt == null) {
            return;
        }
        for (Map.Entry<String, WaypointWorldContainer> subContainerEntry : this.subContainers.entrySet()) {
            String subKey = subContainerEntry.getKey();
            if (!subKey.equals(dt.getName().replaceAll("[^a-zA-Z0-9_]+", ""))) continue;
            WaypointWorldContainer subContainer = subContainerEntry.getValue();
            this.subContainers.put(dimensionPart, subContainer);
            this.subContainers.remove(subKey);
            SimpleBackup.moveToBackup(subContainer.getDirectory().toPath());
            subContainer.setKey(this.key + "/" + dimensionPart);
            try {
                this.modMain.getSettings().saveWorlds(this.getAllWorlds());
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to rename a dimension! Can't continue.", e);
            }
            return;
        }
    }

    public File getDirectory() {
        return new File(this.modMain.getWaypointsFolder(), this.key);
    }
}

