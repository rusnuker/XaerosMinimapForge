//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointsManager;

public class GuiClearSet
extends GuiYesNo {
    private String container;
    private String world;
    private String name;
    private GuiScreen parentScreen;
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;

    public GuiClearSet(IXaeroMinimap modMain, String setName, String container, String world, String name, GuiScreen parent) {
        super(null, I18n.format((String)"gui.xaero_clear_set_message", (Object[])new Object[0]) + ": " + setName.replace("\u00a7\u00a7", ":") + "?", I18n.format((String)"gui.xaero_clear_set_message2", (Object[])new Object[0]), 0);
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
        this.parentScreen = parent;
        this.container = container;
        this.world = world;
        this.name = name;
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                WaypointSet set = this.waypointsManager.getWorld(this.container, this.world).getSets().get(this.name);
                if (set != null) {
                    set.getList().clear();
                }
                this.modMain.getSettings().saveWaypoints(this.waypointsManager.getWorld(this.container, this.world));
            }
            case 1: {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}

