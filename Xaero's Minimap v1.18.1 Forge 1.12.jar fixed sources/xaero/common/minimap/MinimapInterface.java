//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xaero.common.minimap;

import net.minecraft.client.Minecraft;
import xaero.common.IXaeroMinimap;
import xaero.common.interfaces.Interface;
import xaero.common.interfaces.InterfaceManager;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.MinimapRadar;
import xaero.common.minimap.render.MinimapFBORenderer;
import xaero.common.minimap.render.MinimapSafeModeRenderer;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.minimap.waypoints.render.WaypointsIngameRenderer;
import xaero.common.minimap.write.MinimapWriter;
import xaero.common.settings.ModOptions;

public class MinimapInterface
extends Interface {
    private IXaeroMinimap modMain;
    private MinimapProcessor minimap;
    private Minecraft mc = Minecraft.getMinecraft();
    private WaypointsGuiRenderer waypointsGuiRenderer;
    private WaypointsIngameRenderer waypointsIngameRenderer;

    public MinimapInterface(IXaeroMinimap modMain, int id, InterfaceManager interfaces) {
        super(interfaces, "gui.xaero_minimap", id, 128, 128, ModOptions.MINIMAP);
        this.modMain = modMain;
        MinimapWriter minimapWriter = new MinimapWriter(this.modMain);
        MinimapRadar entityRadar = new MinimapRadar(this.modMain);
        this.waypointsGuiRenderer = new WaypointsGuiRenderer(modMain, this.mc);
        this.waypointsIngameRenderer = new WaypointsIngameRenderer(modMain, this.mc);
        MinimapFBORenderer minimapFBORenderer = new MinimapFBORenderer(modMain, this.mc, this.waypointsGuiRenderer);
        MinimapSafeModeRenderer minimapSafeModeRenderer = new MinimapSafeModeRenderer(modMain, this.mc, this.waypointsGuiRenderer);
        this.minimap = new MinimapProcessor(modMain, minimapWriter, minimapFBORenderer, minimapSafeModeRenderer, entityRadar);
        new Thread(minimapWriter).start();
    }

    @Override
    public void drawInterface(int width, int height, int scale, float partial) {
        this.minimap.onRender(this.getX(), this.getY(), width, height, scale, this.getSize(), partial);
        super.drawInterface(width, height, scale, partial);
    }

    public MinimapProcessor getMinimap() {
        return this.minimap;
    }

    @Override
    public int getW(int scale) {
        return (int)((float)(this.getSize() / scale) * this.modMain.getSettings().getMinimapScale());
    }

    @Override
    public int getH(int scale) {
        return this.getW(scale);
    }

    @Override
    public int getWC(int scale) {
        return this.getW(scale);
    }

    @Override
    public int getHC(int scale) {
        return this.getH(scale);
    }

    @Override
    public int getW0(int scale) {
        return this.getW(scale);
    }

    @Override
    public int getH0(int scale) {
        return this.getH(scale);
    }

    @Override
    public int getSize() {
        return this.minimap.getMinimapWidthAndBuffer()[0] + 36 + 2;
    }

    public WaypointsGuiRenderer getWaypointsGuiRenderer() {
        return this.waypointsGuiRenderer;
    }

    public WaypointsIngameRenderer getWaypointsIngameRenderer() {
        return this.waypointsIngameRenderer;
    }
}

