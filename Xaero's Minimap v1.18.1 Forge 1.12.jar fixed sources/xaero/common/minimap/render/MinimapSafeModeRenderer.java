//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.minimap.render;

import java.nio.ByteBuffer;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xaero.common.IXaeroMinimap;
import xaero.common.graphics.MinimapTexture;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.region.MinimapChunk;
import xaero.common.minimap.region.MinimapTile;
import xaero.common.minimap.render.MinimapRenderer;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.misc.OptimizedMath;
import xaero.common.settings.ModSettings;

public class MinimapSafeModeRenderer
extends MinimapRenderer {
    private static final ResourceLocation mapTextures = new ResourceLocation("xaeromaptexture");
    private byte[] bytes;
    private byte drawYState;
    private final int[] tempColor = new int[3];
    private MinimapTexture mapTexture = new MinimapTexture(mapTextures);

    public MinimapSafeModeRenderer(IXaeroMinimap modMain, Minecraft mc, WaypointsGuiRenderer waypointsGuiRenderer) {
        super(modMain, mc, waypointsGuiRenderer);
    }

    public void updateMapFrameSafeMode(MinimapProcessor minimap, EntityPlayer player, int bufferSize, int mapW, float partial, int level) {
        EntityPlayer p = player;
        long before = System.currentTimeMillis();
        if (minimap.isToResetImage()) {
            this.bytes = new byte[bufferSize * bufferSize * 3];
            minimap.setToResetImage(false);
        }
        boolean motionBlur = Minecraft.getDebugFPS() >= 35;
        int increaseY = motionBlur ? 2 : 1;
        int mapH = mapW;
        int halfW = (mapW + 1) / 2;
        int halfH = (mapH + 1) / 2;
        double halfWZoomed = (double)halfW / this.zoom;
        double halfHZoomed = (double)halfH / this.zoom;
        int currentState = this.drawYState;
        double angle = Math.toRadians(this.getRenderAngle(player));
        double ps = Math.sin(Math.PI - angle);
        double pc = Math.cos(Math.PI - angle);
        double playerX = minimap.getEntityRadar().getEntityX((Entity)player, partial);
        double playerZ = minimap.getEntityRadar().getEntityZ((Entity)player, partial);
        for (int currentX = 0; currentX <= mapW + 1; ++currentX) {
            int currentY;
            double currentXZoomed = (double)currentX / this.zoom;
            double offx = currentXZoomed - halfWZoomed;
            double psx = ps * offx;
            double pcx = pc * offx;
            int n = currentY = motionBlur ? currentState : 0;
            while (currentY <= mapH + 1) {
                double offy = (double)currentY / this.zoom - halfHZoomed;
                this.getLoadedBlockColor(minimap, this.tempColor, OptimizedMath.myFloor(playerX + psx + pc * offy), OptimizedMath.myFloor(playerZ + ps * offy - pcx), level);
                this.helper.putColor(this.bytes, currentX, currentY, this.tempColor[0], this.tempColor[1], this.tempColor[2], bufferSize);
                currentY += increaseY;
            }
            currentState = (byte)(currentState != 1 ? 1 : 0);
        }
        this.renderEntityListSafeMode(minimap, p, minimap.getEntityRadar().getEntitiesIterator(), pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial);
        this.renderEntityListSafeMode(minimap, p, minimap.getEntityRadar().getItemsIterator(), pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial);
        this.renderEntityListSafeMode(minimap, p, minimap.getEntityRadar().getHostileIterator(), pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial);
        this.renderEntityListSafeMode(minimap, p, minimap.getEntityRadar().getLivingIterator(), pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial);
        this.renderEntityListSafeMode(minimap, p, minimap.getEntityRadar().getPlayersIterator(), pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial);
        if (!this.modMain.getSettings().alwaysArrow) {
            this.renderEntityDotSafeMode(minimap, p, (Entity)p, pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial);
        }
        this.drawYState = (byte)(this.drawYState != 1 ? 1 : 0);
        ByteBuffer buffer = this.mapTexture.getBuffer(bufferSize);
        buffer.put(this.bytes);
        buffer.flip();
    }

    public void renderEntityListSafeMode(MinimapProcessor minimap, EntityPlayer p, Iterator<Entity> iter, double pc, double ps, int mapW, int bufferSize, int halfW, int halfH, double playerX, double playerZ, float partial) {
        while (iter.hasNext()) {
            Entity e = iter.next();
            if (p != e && this.renderEntityDotSafeMode(minimap, p, e, pc, ps, mapW, bufferSize, halfW, halfH, playerX, playerZ, partial)) continue;
        }
    }

    public boolean renderEntityDotSafeMode(MinimapProcessor minimap, EntityPlayer p, Entity e, double pc, double ps, int mapW, int bufferSize, int halfW, int halfH, double playerX, double playerZ, float partial) {
        if (!minimap.getEntityRadar().shouldRenderEntity(e)) {
            return false;
        }
        double offx = minimap.getEntityRadar().getEntityX(e, partial) - playerX;
        double offz = minimap.getEntityRadar().getEntityZ(e, partial) - playerZ;
        double offh = p.posY - e.posY;
        double Z = pc * offx + ps * offz;
        double X = ps * offx - pc * offz;
        int drawX = OptimizedMath.myFloor((double)halfW + X * this.zoom);
        int drawY = OptimizedMath.myFloor((double)halfH + Z * this.zoom);
        this.modMain.getSettings();
        int color = minimap.getEntityRadar().getEntityColour(p, e, offh);
        for (int a = drawX - 2; a < drawX + 4; ++a) {
            if (a < 0 || a > mapW) continue;
            for (int b = drawY - 2; b < drawY + 4; ++b) {
                if (b < 0 || b > mapW || (a == drawX - 2 || a == drawX + 3) && (b == drawY - 2 || b == drawY + 3) || a == drawX + 2 && b == drawY - 2 || a == drawX + 3 && b == drawY - 1 || a == drawX - 2 && b == drawY + 2 || a == drawX - 1 && b == drawY + 3) continue;
                if (a == drawX + 3 || b == drawY + 3 || a == drawX + 2 && b == drawY + 2) {
                    this.helper.putColor(this.bytes, a, b, 0, 0, 0, bufferSize);
                    continue;
                }
                this.helper.putColor(this.bytes, a, b, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, bufferSize);
            }
        }
        return true;
    }

    @Override
    protected void renderChunks(MinimapProcessor minimap, int[] mapAndBuffer, int mapW, float sizeFix, float partial, int lightLevel, boolean useWorldMap) {
        this.updateMapFrameSafeMode(minimap, (EntityPlayer)this.mc.player, mapAndBuffer[1], mapW, partial, lightLevel);
        GL11.glScalef((float)sizeFix, (float)sizeFix, (float)1.0f);
        this.helper.bindTextureBuffer(this.mapTexture.getBuffer(mapAndBuffer[1]), mapAndBuffer[1], mapAndBuffer[1], this.mapTexture.getGlTextureId());
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(this.modMain.getSettings().minimapOpacity / 100.0f));
    }

    private void getLoadedBlockColor(MinimapProcessor minimap, int[] result, int par1, int par2, int level) {
        int tileX = par1 >> 4;
        int tileZ = par2 >> 4;
        int chunkX = (tileX >> 2) - minimap.getMinimapWriter().getLoadedMapChunkX();
        int chunkZ = (tileZ >> 2) - minimap.getMinimapWriter().getLoadedMapChunkZ();
        if (minimap.getMinimapWriter().getLoadedBlocks() == null || chunkX < 0 || chunkX >= minimap.getMinimapWriter().getLoadedBlocks().length || chunkZ < 0 || chunkZ >= minimap.getMinimapWriter().getLoadedBlocks().length) {
            result[2] = 1;
            result[1] = 1;
            result[0] = 1;
            return;
        }
        try {
            MinimapTile tile;
            MinimapChunk current = minimap.getMinimapWriter().getLoadedBlocks()[chunkX][chunkZ];
            if (current != null && (tile = current.getTile(tileX & 3, tileZ & 3)) != null) {
                int insideX = par1 & 0xF;
                int insideZ = par2 & 0xF;
                this.chunkOverlay(result, tile.getRed(level, insideX, insideZ), tile.getGreen(level, insideX, insideZ), tile.getBlue(level, insideX, insideZ), tile);
                return;
            }
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            // empty catch block
        }
        result[2] = 1;
        result[1] = 1;
        result[0] = 1;
    }

    private void chunkOverlay(int[] result, int red, int green, int blue, MinimapTile c) {
        if (this.modMain.getSettings().getSlimeChunks() && c.isSlimeChunk()) {
            this.helper.slimeOverlay(result, red, green, blue);
        } else if (this.modMain.getSettings().chunkGrid > -1 && c.isChunkGrid()) {
            this.helper.gridOverlay(result, ModSettings.COLORS[this.modMain.getSettings().chunkGrid], red, green, blue);
        } else {
            result[0] = red;
            result[1] = green;
            result[2] = blue;
        }
    }
}

