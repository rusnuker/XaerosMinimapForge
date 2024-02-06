/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.gui;

import java.util.ArrayList;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiDropdownHelper;
import xaero.common.minimap.waypoints.WaypointsManager;

public class GuiWaypointContainers
extends GuiDropdownHelper {
    public GuiWaypointContainers(IXaeroMinimap modMain, WaypointsManager waypointsManager, String currentContainer) {
        String c = currentContainer;
        String a = waypointsManager.getAutoContainerID().split("/")[0];
        this.current = -1;
        this.auto = -1;
        ArrayList<String> keysList = new ArrayList<String>();
        ArrayList<String> optionsList = new ArrayList<String>();
        String[] containerKeys = waypointsManager.getWaypointMap().keySet().toArray(new String[0]);
        for (int i = 0; i < containerKeys.length; ++i) {
            String containerKey = containerKeys[i];
            try {
                String[] details;
                String option = "Error";
                if (this.current == -1 && containerKey.equals(c)) {
                    this.current = optionsList.size();
                }
                if (this.auto == -1 && containerKey.equals(a)) {
                    this.auto = optionsList.size();
                }
                option = (details = containerKey.split("_")).length > 1 && details[0].equals("Realms") ? "Realm ID " + details[1].substring(details[1].indexOf(".") + 1) : details[details.length - 1].replace("%us%", "_").replace("%fs%", "/").replace("%bs%", "\\");
                if (modMain.getSettings().hideWorldNames == 2) {
                    option = "hidden " + optionsList.size();
                } else if (modMain.getSettings().hideWorldNames == 1 && details.length > 1 && details[0].equals("Multiplayer")) {
                    String[] dotSplit = option.split("\\.");
                    StringBuilder builder = new StringBuilder();
                    for (int o = 0; o < dotSplit.length; ++o) {
                        if (o < dotSplit.length - 2) {
                            builder.append("-.");
                            continue;
                        }
                        if (o < dotSplit.length - 1) {
                            builder.append(dotSplit[o].charAt(0) + "-.");
                            continue;
                        }
                        builder.append(dotSplit[o]);
                    }
                    option = builder.toString();
                }
                if (this.auto == optionsList.size()) {
                    option = option + " (auto)";
                }
                keysList.add(containerKey);
                optionsList.add(option);
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.keys = keysList.toArray(new String[0]);
        this.options = optionsList.toArray(new String[0]);
    }
}

