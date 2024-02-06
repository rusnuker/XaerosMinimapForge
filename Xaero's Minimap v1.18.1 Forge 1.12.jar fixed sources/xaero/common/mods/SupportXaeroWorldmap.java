//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL14
 *  xaero.map.MapProcessor
 *  xaero.map.WorldMap
 *  xaero.map.gui.GuiMap
 *  xaero.map.gui.GuiWorldMapSettings
 *  xaero.map.region.MapRegion
 *  xaero.map.region.MapTileChunk
 */
package xaero.common.mods;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL14;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.region.MinimapTile;
import xaero.common.settings.ModSettings;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.gui.GuiMap;
import xaero.map.gui.GuiWorldMapSettings;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTileChunk;

public class SupportXaeroWorldmap {
    private static final HashMap<MapTileChunk, Long> seedsUsed = new HashMap();
    public static final Color black = new Color(0, 0, 0, 255);
    public static final Color slime = new Color(82, 241, 64, 128);
    private IXaeroMinimap modMain;

    public SupportXaeroWorldmap(IXaeroMinimap modMain) {
        this.modMain = modMain;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void drawMinimap(int xFloored, int zFloored, int radius, boolean zooming, double zoom) {
        Gui.drawRect((int)-256, (int)-256, (int)256, (int)256, (int)black.hashCode());
        Object object = MapProcessor.instance.renderThreadPauseSync;
        synchronized (object) {
            if (!MapProcessor.instance.isRenderingPaused()) {
                if (MapProcessor.instance.getCurrentDimension() == null) {
                    return;
                }
                String worldString = MapProcessor.instance.getCurrentWorldString();
                if (worldString == null) {
                    return;
                }
                int mapX = xFloored >> 4;
                int mapZ = zFloored >> 4;
                int chunkX = mapX >> 2;
                int chunkZ = mapZ >> 2;
                int tileX = mapX & 3;
                int tileZ = mapZ & 3;
                int insideX = xFloored & 0xF;
                int insideZ = zFloored & 0xF;
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.enableBlend();
                int minX = (mapX >> 2) - 4;
                int maxX = (mapX >> 2) + 4;
                int minZ = (mapZ >> 2) - 4;
                int maxZ = (mapZ >> 2) + 4;
                int minViewX = (mapX >> 2) - radius - 1;
                int maxViewX = (mapX >> 2) + radius + 1;
                int minViewZ = (mapZ >> 2) - radius - 1;
                int maxViewZ = (mapZ >> 2) + radius + 1;
                for (int i = minX; i < maxX + 1; ++i) {
                    for (int j = minZ; j < maxZ + 1; ++j) {
                        boolean newSeed;
                        MapTileChunk chunk;
                        MapRegion region = MapProcessor.instance.getMapRegion(i >> 3, j >> 3, MapProcessor.instance.regionExists(i >> 3, j >> 3));
                        if (region == null) continue;
                        MapRegion mapRegion = region;
                        synchronized (mapRegion) {
                            if (!region.recacheHasBeenRequested() && !region.reloadHasBeenRequested() && (region.getVersion() != MapProcessor.instance.getGlobalVersion() || region.getLoadState() == 4 && region.shouldCache())) {
                                if (region.isBeingWritten() && region.getLoadState() == 2) {
                                    region.requestRefresh();
                                } else if (region.getLoadState() == 0 || region.getLoadState() == 4) {
                                    MapProcessor.instance.getMapSaveLoad().requestLoad(region, "Minimap");
                                    MapProcessor.instance.getMapSaveLoad().setNextToLoadByViewing(region);
                                }
                            }
                        }
                        if (!MapProcessor.instance.isUploadingPaused()) {
                            List regions = MapProcessor.instance.getCurrentMapList(MapProcessor.instance.getCurrentDimension());
                            regions.remove(region);
                            regions.add(region);
                        }
                        if (i < minViewX || i > maxViewX || j < minViewZ || j > maxViewZ || (chunk = region.getChunk(i & 7, j & 7)) == null || chunk.getGlColorTexture() == -1) continue;
                        GuiMap.bindMapTextureWithLighting((MapTileChunk)chunk, (int)(zooming ? 9729 : 9728), (int)0);
                        int drawX = 64 * (chunk.getX() - chunkX) - 16 * tileX - insideX;
                        int drawZ = 64 * (chunk.getZ() - chunkZ) - 16 * tileZ - insideZ - 1;
                        GL14.glBlendFuncSeparate((int)770, (int)771, (int)1, (int)771);
                        GuiMap.renderTexturedModalRectWithLighting((float)drawX, (float)drawZ, (int)0, (int)0, (float)64.0f, (float)64.0f);
                        int r = 0;
                        int g = 0;
                        int b = 0;
                        if (this.modMain.getSettings().chunkGrid > -1) {
                            int grid = ModSettings.COLORS[this.modMain.getSettings().chunkGrid];
                            r = grid >> 16 & 0xFF;
                            g = grid >> 8 & 0xFF;
                            b = grid & 0xFF;
                        }
                        Long seed = this.modMain.getSettings().getSlimeChunksSeed();
                        Long savedSeed = seedsUsed.get(chunk);
                        boolean bl = newSeed = seed == null && savedSeed != null || seed != null && !seed.equals(savedSeed);
                        if (newSeed) {
                            seedsUsed.put(chunk, seed);
                        }
                        GuiMap.restoreTextureStates();
                        for (int t = 0; t < 16; ++t) {
                            if (newSeed || (chunk.getTileGridsCache()[t % 4][t / 4] & 1) == 0) {
                                chunk.getTileGridsCache()[t % 4][t / 4] = (byte)(1 | (MinimapTile.isSlimeChunk(this.modMain.getSettings(), chunk.getX() * 4 + t % 4, chunk.getZ() * 4 + t / 4, seed) ? 2 : 0) | ((t % 4 & 1) == (t / 4 & 1) ? 4 : 0));
                            }
                            if (this.modMain.getSettings().getSlimeChunks() && (chunk.getTileGridsCache()[t % 4][t / 4] & 2) != 0) {
                                int slimeDrawX = drawX + 16 * (t % 4);
                                int slimeDrawZ = drawZ + 16 * (t / 4);
                                Gui.drawRect((int)slimeDrawX, (int)slimeDrawZ, (int)(slimeDrawX + 16), (int)(slimeDrawZ + 16), (int)slime.hashCode());
                            }
                            if (this.modMain.getSettings().chunkGrid <= -1 || (chunk.getTileGridsCache()[t % 4][t / 4] & 4) == 0) continue;
                            int gridDrawX = drawX + 16 * (t % 4);
                            int gridDrawZ = drawZ + 16 * (t / 4);
                            Gui.drawRect((int)gridDrawX, (int)gridDrawZ, (int)(gridDrawX + 16), (int)(gridDrawZ + 16), (int)new Color(r, g, b, 64).hashCode());
                        }
                        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        GlStateManager.enableBlend();
                    }
                }
                GlStateManager.disableBlend();
            }
        }
    }

    public int getWorldMapColours() {
        return WorldMap.settings.colours;
    }

    public boolean getWorldMapLighting() {
        return WorldMap.settings.lighting;
    }

    public boolean getWorldMapTerrainDepth() {
        return WorldMap.settings.terrainDepth;
    }

    public boolean getWorldMapTerrainSlopes() {
        return WorldMap.settings.terrainSlopes;
    }

    public void openSettings() {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiWorldMapSettings(Minecraft.getMinecraft().currentScreen));
    }
}

