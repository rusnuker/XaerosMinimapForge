//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import net.minecraft.client.resources.I18n;
import xaero.common.minimap.waypoints.WaypointWorld;

public class GuiWaypointSets {
    private int currentSet;
    private String[] options;

    public GuiWaypointSets(boolean canCreate, WaypointWorld currentWorld) {
        String c = currentWorld.getCurrent();
        int size = currentWorld.getSets().size() + (canCreate ? 1 : 0);
        this.options = new String[size];
        Object[] keys = currentWorld.getSets().keySet().toArray();
        for (int i = 0; i < keys.length; ++i) {
            this.options[i] = (String)keys[i];
            if (!this.options[i].equals(c)) continue;
            this.currentSet = i;
        }
        if (canCreate) {
            this.options[this.options.length - 1] = "\u00a78" + I18n.format((String)"gui.xaero_create_set", (Object[])new Object[0]);
        }
    }

    public String getCurrentSetKey() {
        return this.options[this.currentSet];
    }

    public int getCurrentSet() {
        return this.currentSet;
    }

    public void setCurrentSet(int currentSet) {
        this.currentSet = currentSet;
    }

    public String[] getOptions() {
        return this.options;
    }
}

