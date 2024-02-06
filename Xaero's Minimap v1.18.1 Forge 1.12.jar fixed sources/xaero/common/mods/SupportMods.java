/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.mods;

import xaero.common.IXaeroMinimap;
import xaero.common.mods.SupportXaeroWorldmap;

public class SupportMods {
    public SupportXaeroWorldmap worldmapSupport = null;
    private IXaeroMinimap modMain;

    public boolean worldmap() {
        return this.worldmapSupport != null;
    }

    public boolean shouldUseWorldMapChunks() {
        return this.worldmap() && this.modMain.getSettings().getUseWorldMap();
    }

    public SupportMods(IXaeroMinimap modMain) {
        this.modMain = modMain;
        try {
            this.worldmapSupport = new SupportXaeroWorldmap(modMain);
            System.out.println("Xaero's Minimap: World Map found!");
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
        }
    }
}

