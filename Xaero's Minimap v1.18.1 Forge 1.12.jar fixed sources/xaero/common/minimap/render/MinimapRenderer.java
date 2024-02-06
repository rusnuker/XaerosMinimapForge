//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.chunk.Chunk
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.minimap.render;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;
import xaero.common.IXaeroMinimap;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.MinimapRadar;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.misc.OptimizedMath;

public abstract class MinimapRenderer {
    public static final Color black = new Color(0, 0, 0, 255);
    public static final Color slime = new Color(82, 241, 64, 128);
    protected IXaeroMinimap modMain;
    protected Minecraft mc;
    protected MinimapRendererHelper helper;
    private WaypointsGuiRenderer waypointsGuiRenderer;
    private int lastMinimapSize;
    private ArrayList<String> underText;
    protected double zoom = 1.0;

    public MinimapRenderer(IXaeroMinimap modMain, Minecraft mc, WaypointsGuiRenderer waypointsGuiRenderer) {
        this.modMain = modMain;
        this.mc = mc;
        this.waypointsGuiRenderer = waypointsGuiRenderer;
        this.underText = new ArrayList();
        this.helper = new MinimapRendererHelper();
    }

    public double getRenderAngle(EntityPlayer player) {
        if (this.modMain.getSettings().getLockNorth()) {
            return 90.0;
        }
        return this.getActualAngle(player);
    }

    public double getActualAngle(EntityPlayer player) {
        double angle;
        double rotation = player.rotationYaw;
        if (rotation < 0.0 || rotation > 360.0) {
            rotation %= 360.0;
        }
        if ((angle = 270.0 - rotation) < 0.0 || angle > 360.0) {
            angle %= 360.0;
        }
        return angle;
    }

    protected abstract void renderChunks(MinimapProcessor var1, int[] var2, int var3, float var4, float var5, int var6, boolean var7);

    public void renderMinimap(MinimapProcessor minimap, int x, int y, int width, int height, int scale, int size, float partial) {
        int specW;
        if (this.modMain.getSettings().getMinimapSize() != this.lastMinimapSize) {
            this.lastMinimapSize = this.modMain.getSettings().getMinimapSize();
            minimap.setToResetImage(true);
        }
        long before = System.currentTimeMillis();
        int[] mapAndBuffer = minimap.getMinimapWidthAndBuffer();
        if (minimap.usingFBO()) {
            mapAndBuffer[1] = minimap.getFBOBufferSize();
        }
        float mapScale = (float)scale / 2.0f / this.modMain.getSettings().getMinimapScale();
        int mapW = minimap.setMinimapWidth(mapAndBuffer[0]);
        int mapH = minimap.setMinimapHeight(mapW);
        minimap.updateZoom();
        this.zoom = minimap.getMinimapZoom();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.glPixelStorei((int)3317, (int)4);
        GlStateManager.glPixelStorei((int)3316, (int)0);
        GlStateManager.glPixelStorei((int)3315, (int)0);
        GlStateManager.glPixelStorei((int)3314, (int)0);
        float sizeFix = (float)mapAndBuffer[1] / 512.0f;
        boolean useWorldMap = this.modMain.getSupportMods().shouldUseWorldMapChunks() && MinimapProcessor.instance.getMinimapWriter().getLoadedCaving() == -1;
        int lightLevel = (int)((1.0f - Math.min(1.0f, this.mc.world.getSunBrightnessFactor(1.0f))) * (float)(minimap.getMinimapWriter().getLoadedLevels() - 1));
        if (useWorldMap || lightLevel >= 0) {
            this.renderChunks(minimap, mapAndBuffer, mapW, sizeFix, partial, lightLevel, useWorldMap);
        }
        if (minimap.usingFBO()) {
            sizeFix = 1.0f;
        }
        GL11.glEnable((int)3008);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
        GL11.glScalef((float)(1.0f / mapScale), (float)(1.0f / mapScale), (float)1.0f);
        int scaledX = (int)((float)x * mapScale);
        int scaledY = (int)((float)y * mapScale);
        this.mc.ingameGUI.drawTexturedModalRect((int)((float)(scaledX + 9) / sizeFix), (int)((float)(scaledY + 9) / sizeFix), 0, 0, (int)((float)(mapW / 2 + 1) / sizeFix), (int)((float)(mapH / 2 + 1) / sizeFix));
        if (!minimap.usingFBO()) {
            GL11.glScalef((float)(1.0f / sizeFix), (float)(1.0f / sizeFix), (float)1.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        int specH = specW = mapW / 2 + 6;
        double angle = Math.toRadians(this.getRenderAngle((EntityPlayer)this.mc.player));
        double ps = Math.sin(Math.PI - angle);
        double pc = Math.cos(Math.PI - angle);
        this.mc.getTextureManager().bindTexture(InterfaceRenderer.guiTextures);
        if (this.modMain.getSettings().getLockNorth() || this.modMain.getSettings().alwaysArrow) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
            double arrowX = (double)(2 * scaledX + 18 + (mapW + 1) / 2) + 0.5;
            double arrowY = (double)(2 * scaledY + 18 + (mapH + 1) / 2) + 0.5;
            GL11.glPushMatrix();
            GL11.glScalef((float)0.5f, (float)0.5f, (float)1.0f);
            float arrowAngle = this.modMain.getSettings().getLockNorth() ? this.mc.player.rotationYaw : 180.0f;
            this.drawArrow(arrowAngle, arrowX, arrowY + 1.0, new float[]{0.0f, 0.0f, 0.0f, 0.5f});
            float[] c = new float[4];
            if (this.modMain.getSettings().arrowColour != -1) {
                c = this.modMain.getSettings().arrowColours[this.modMain.getSettings().arrowColour];
            } else {
                int rgb = minimap.getEntityRadar().getPlayerTeamColour((EntityPlayer)this.mc.player);
                if (rgb != -1) {
                    c[0] = (float)(rgb >> 16 & 0xFF) / 255.0f;
                    c[1] = (float)(rgb >> 8 & 0xFF) / 255.0f;
                    c[2] = (float)(rgb & 0xFF) / 255.0f;
                    c[3] = 1.0f;
                } else {
                    c = this.modMain.getSettings().arrowColours[0];
                }
            }
            this.drawArrow(arrowAngle, arrowX, arrowY, c);
            GL11.glPopMatrix();
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        }
        this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4, scaledY + 9 - 4, 0, 0, 17, 15);
        this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4 + mapW / 2 - 8, scaledY + 9 - 4, 0, 15, 17, 15);
        this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4, scaledY + 9 - 4 + mapH / 2 - 6, 0, 30, 17, 15);
        this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4 + mapW / 2 - 8, scaledY + 9 - 4 + mapH / 2 - 6, 0, 45, 17, 15);
        int horLineLength = (mapW / 2 - 16) / 16;
        for (int i = 0; i < horLineLength; ++i) {
            this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4 + 17 + i * 16, scaledY + 9 - 4, 0, 60, 16, 4);
            this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4 + 17 + i * 16, scaledY + 9 - 4 + mapH / 2 + 9 - 4, 0, 64, 16, 4);
        }
        int vertLineLength = (mapH / 2 - 14) / 5;
        for (int i = 0; i < vertLineLength; ++i) {
            this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4, scaledY + 9 - 4 + 15 + i * 5, 0, 68, 4, 5);
            this.mc.ingameGUI.drawTexturedModalRect(scaledX + 9 - 4 + mapW / 2 + 9 - 4, scaledY + 9 - 4 + 15 + i * 5, 0, 73, 4, 5);
        }
        GL11.glPushMatrix();
        GlStateManager.scale((float)0.5f, (float)0.5f, (float)1.0f);
        GlStateManager.translate((float)(2 * scaledX + specW - 6 + 18), (float)(2 * scaledY + specH - 6 + 18), (float)0.0f);
        double playerX = minimap.getEntityRadar().getEntityX((Entity)this.mc.player, partial);
        double playerZ = minimap.getEntityRadar().getEntityZ((Entity)this.mc.player, partial);
        this.waypointsGuiRenderer.render(playerX, playerZ, specW, specH, ps, pc, partial, this.zoom);
        GL11.glPopMatrix();
        if (this.modMain.getSettings().getShowCoords()) {
            int interfaceSize = size / 2;
            String coords = OptimizedMath.myFloor(this.mc.player.posX) + ", " + OptimizedMath.myFloor(this.mc.player.posY) + ", " + OptimizedMath.myFloor(this.mc.player.posZ);
            if (this.mc.fontRenderer.getStringWidth(coords) >= interfaceSize) {
                String stringLevel = "" + OptimizedMath.myFloor(this.mc.player.posY);
                coords = OptimizedMath.myFloor(this.mc.player.posX) + ", " + OptimizedMath.myFloor(this.mc.player.posZ);
                this.underText.add(coords);
                this.underText.add(stringLevel);
            } else {
                this.underText.add(coords);
            }
        }
        int playerBlockX = OptimizedMath.myFloor(this.mc.getRenderViewEntity().posX);
        int playerBlockY = OptimizedMath.myFloor(this.mc.getRenderViewEntity().getEntityBoundingBox().minY);
        int playerBlockZ = OptimizedMath.myFloor(this.mc.getRenderViewEntity().posZ);
        BlockPos pos = new BlockPos(playerBlockX, playerBlockY, playerBlockZ);
        Chunk chunk = this.mc.world.getChunk(pos);
        if (this.modMain.getSettings().showBiome) {
            this.underText.add(chunk.getBiome(pos, this.mc.world.getBiomeProvider()).getBiomeName());
        }
        if (this.modMain.getSettings().showLightLevel) {
            int playerBlockLightLevel = 15;
            if (playerBlockY >= 0 && playerBlockY < 256) {
                playerBlockLightLevel = Math.max(chunk.getLightFor(EnumSkyBlock.SKY, pos), chunk.getLightFor(EnumSkyBlock.BLOCK, pos));
            }
            this.underText.add(String.format("Light: %d", playerBlockLightLevel));
        }
        this.drawTextUnderMinimap(scaledX, scaledY, height, size, mapScale);
        GL11.glScalef((float)mapScale, (float)mapScale, (float)1.0f);
    }

    private void drawArrow(float angle, double arrowX, double arrowY, float[] colour) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)arrowX, (double)arrowY, (double)0.0);
        GlStateManager.rotate((float)angle, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glScalef((float)(0.5f * this.modMain.getSettings().arrowScale), (float)(0.5f * this.modMain.getSettings().arrowScale), (float)1.0f);
        GL11.glTranslated((double)-13.0, (double)-6.0, (double)0.0);
        GL11.glColor4f((float)colour[0], (float)colour[1], (float)colour[2], (float)colour[3]);
        this.mc.ingameGUI.drawTexturedModalRect(0, 0, 49, 0, 26, 27);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public void drawTextUnderMinimap(int scaledX, int scaledY, int height, int size, float mapScale) {
        int interfaceSize = size / 2;
        int scaledHeight = (int)((float)height * mapScale);
        for (int i = 0; i < this.underText.size(); ++i) {
            String s = this.underText.get(i);
            int stringWidth = this.mc.fontRenderer.getStringWidth(s);
            boolean under = scaledY + interfaceSize / 2 < scaledHeight / 2;
            int stringY = scaledY + (under ? interfaceSize : -9) + i * 9 * (under ? 1 : -1);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, (float)(scaledX + interfaceSize / 2 - stringWidth / 2), (float)stringY, MinimapRadar.radarPlayers.hashCode());
        }
        this.underText.clear();
    }

    public double getZoom() {
        return this.zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}

