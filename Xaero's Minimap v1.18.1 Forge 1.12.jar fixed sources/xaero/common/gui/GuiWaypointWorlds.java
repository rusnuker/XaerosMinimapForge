/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.gui;

import java.util.ArrayList;
import xaero.common.gui.GuiDropdownHelper;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.minimap.waypoints.WaypointsManager;

public class GuiWaypointWorlds
extends GuiDropdownHelper {
    public GuiWaypointWorlds(WaypointWorldContainer wc, WaypointsManager waypointsManager, String currentWorld) {
        String a = waypointsManager.getAutoContainerID() + "_" + waypointsManager.getAutoWorldID();
        this.current = -1;
        this.auto = -1;
        ArrayList<String> keysList = new ArrayList<String>();
        ArrayList<String> optionsList = new ArrayList<String>();
        this.addWorlds(wc, currentWorld, a, keysList, optionsList);
        if (this.current == -1) {
            this.current = 0;
        }
        this.keys = keysList.toArray(new String[0]);
        this.options = optionsList.toArray(new String[0]);
    }

    private void addWorlds(WaypointWorldContainer wc, String c, String a, ArrayList<String> keysList, ArrayList<String> optionsList) {
        String[] worldKeys = wc.worlds.keySet().toArray(new String[0]);
        for (int j = 0; j < worldKeys.length; ++j) {
            String worldKey = worldKeys[j];
            String worldName = wc.getFullName(worldKey);
            String fullKey = wc.getKey() + "_" + worldKey;
            try {
                String option = "Error";
                if (this.current == -1 && fullKey.equals(c)) {
                    this.current = optionsList.size();
                }
                if (this.auto == -1 && fullKey.equals(a)) {
                    this.auto = optionsList.size();
                }
                option = worldName;
                if (this.auto == optionsList.size()) {
                    option = option + " (auto)";
                }
                keysList.add(fullKey);
                optionsList.add(option);
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        WaypointWorldContainer[] subContainers = wc.subContainers.values().toArray(new WaypointWorldContainer[0]);
        for (int i = 0; i < subContainers.length; ++i) {
            this.addWorlds(subContainers[i], c, a, keysList, optionsList);
        }
    }

    public String[] getCurrentKeys() {
        String fullKey = this.getCurrentKey();
        return new String[]{fullKey.substring(0, fullKey.lastIndexOf("_")), fullKey.substring(fullKey.lastIndexOf("_") + 1)};
    }
}

