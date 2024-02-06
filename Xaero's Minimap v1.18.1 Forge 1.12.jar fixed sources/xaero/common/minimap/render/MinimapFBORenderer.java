//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.settings.GameSettings$Options
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL14
 *  org.lwjgl.opengl.GLContext
 */
package xaero.common.minimap.render;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import xaero.common.IXaeroMinimap;
import xaero.common.graphics.ImprovedFramebuffer;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.region.MinimapChunk;
import xaero.common.minimap.render.MinimapRenderer;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.minimap.write.MinimapWriter;
import xaero.common.misc.OptimizedMath;
import xaero.common.settings.ModSettings;

public class MinimapFBORenderer
extends MinimapRenderer {
    private ImprovedFramebuffer scalingFramebuffer;
    private ImprovedFramebuffer rotationFramebuffer;
    private boolean triedFBO;
    private boolean loadedFBO;

    public MinimapFBORenderer(IXaeroMinimap modMain, Minecraft mc, WaypointsGuiRenderer waypointsGuiRenderer) {
        super(modMain, mc, waypointsGuiRenderer);
    }

    public void loadFrameBuffer() {
        if (!(GLContext.getCapabilities().GL_EXT_framebuffer_object || GLContext.getCapabilities().GL_ARB_framebuffer_object || GLContext.getCapabilities().OpenGL30)) {
            System.out.println("FBO not supported! Using minimap safe mode.");
        } else {
            if (!Minecraft.getMinecraft().gameSettings.fboEnable) {
                Minecraft.getMinecraft().gameSettings.setOptionValue(GameSettings.Options.FBO_ENABLE, 0);
                System.out.println("FBO is supported but off. Turning it on.");
            }
            this.scalingFramebuffer = new ImprovedFramebuffer(512, 512, false);
            this.rotationFramebuffer = new ImprovedFramebuffer(512, 512, false);
            this.loadedFBO = this.scalingFramebuffer.framebufferObject != -1 && this.rotationFramebuffer.framebufferObject != -1;
        }
        this.triedFBO = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void renderChunks(MinimapProcessor minimap, int[] mapAndBuffer, int mapW, float sizeFix, float partial, int lightLevel, boolean useWorldMap) {
        MinimapWriter minimapWriter = MinimapProcessor.instance.getMinimapWriter();
        synchronized (minimapWriter) {
            this.renderChunksToFBO(minimap, (EntityPlayer)this.mc.player, mapAndBuffer[1], mapW, sizeFix, partial, lightLevel, true, useWorldMap);
        }
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
        GlStateManager.viewport((int)0, (int)0, (int)Minecraft.getMinecraft().getFramebuffer().framebufferWidth, (int)Minecraft.getMinecraft().getFramebuffer().framebufferHeight);
        this.rotationFramebuffer.bindFramebufferTexture();
    }

    public void renderChunksToFBO(MinimapProcessor minimap, EntityPlayer player, int bufferSize, int viewW, float sizeFix, float partial, int level, boolean retryIfError, boolean useWorldMap) {
        double zInsidePixel;
        int radius = (int)((double)viewW / Math.sqrt(2.0) / 2.0 / this.zoom) / 64 + 1;
        double playerX = minimap.getEntityRadar().getEntityX((Entity)player, partial);
        double playerZ = minimap.getEntityRadar().getEntityZ((Entity)player, partial);
        int xFloored = OptimizedMath.myFloor(playerX);
        int zFloored = OptimizedMath.myFloor(playerZ);
        int playerChunkX = xFloored >> 6;
        int playerChunkZ = zFloored >> 6;
        int offsetX = xFloored & 0x3F;
        int offsetZ = zFloored & 0x3F;
        boolean zooming = (double)((int)this.zoom) != this.zoom;
        this.scalingFramebuffer.bindFramebuffer(true);
        GL11.glClear((int)16640);
        GL11.glEnable((int)3553);
        RenderHelper.disableStandardItemLighting();
        long before = System.currentTimeMillis();
        GlStateManager.clear((int)256);
        GlStateManager.matrixMode((int)5889);
        GL11.glPushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.ortho((double)0.0, (double)512.0, (double)512.0, (double)0.0, (double)1000.0, (double)3000.0);
        GlStateManager.matrixMode((int)5888);
        GL11.glPushMatrix();
        GlStateManager.loadIdentity();
        before = System.currentTimeMillis();
        double xInsidePixel = minimap.getEntityRadar().getEntityX((Entity)player, partial) - (double)xFloored;
        if (xInsidePixel < 0.0) {
            xInsidePixel += 1.0;
        }
        if ((zInsidePixel = minimap.getEntityRadar().getEntityZ((Entity)player, partial) - (double)zFloored) < 0.0) {
            zInsidePixel += 1.0;
        }
        zInsidePixel = 1.0 - zInsidePixel;
        float halfWView = (float)viewW / 2.0f;
        float angle = (float)(90.0 - this.getRenderAngle(player));
        GlStateManager.enableBlend();
        GlStateManager.translate((float)256.0f, (float)256.0f, (float)-2000.0f);
        GlStateManager.scale((double)this.zoom, (double)this.zoom, (double)1.0);
        if (useWorldMap) {
            this.modMain.getSupportMods().worldmapSupport.drawMinimap(xFloored, zFloored, radius, zooming, this.zoom);
        } else if (MinimapProcessor.instance.getMinimapWriter().getLoadedBlocks() != null) {
            Gui.drawRect((int)-256, (int)-256, (int)256, (int)256, (int)black.hashCode());
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int minX = playerChunkX - radius - 1;
            int minZ = playerChunkZ - radius - 1;
            int maxX = playerChunkX + radius + 1;
            int maxZ = playerChunkZ + radius + 1;
            for (int X = minX; X <= maxX; ++X) {
                int canvasX = X - MinimapProcessor.instance.getMinimapWriter().getLoadedMapChunkX();
                if (canvasX < 0 || canvasX >= MinimapProcessor.instance.getMinimapWriter().getLoadedBlocks().length) continue;
                for (int Z = minZ; Z <= maxZ; ++Z) {
                    MinimapChunk mchunk;
                    int canvasZ = Z - MinimapProcessor.instance.getMinimapWriter().getLoadedMapChunkZ();
                    if (canvasZ < 0 || canvasZ >= MinimapProcessor.instance.getMinimapWriter().getLoadedBlocks().length || (mchunk = MinimapProcessor.instance.getMinimapWriter().getLoadedBlocks()[canvasX][canvasZ]) == null) continue;
                    mchunk.bindTexture(level);
                    if (!mchunk.isHasSomething() || level >= mchunk.getLevelsBuffered() || mchunk.getGlTexture(level) == 0) continue;
                    if (!zooming) {
                        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
                    } else {
                        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
                    }
                    int drawX = (mchunk.getX() - playerChunkX) * 64 - offsetX;
                    int drawZ = (mchunk.getZ() - playerChunkZ) * 64 - offsetZ - 1;
                    GlStateManager.enableBlend();
                    GL14.glBlendFuncSeparate((int)770, (int)771, (int)1, (int)771);
                    this.helper.drawMyTexturedModalRect(drawX, drawZ, 0, 0, 64.0f, 64.0f, 64.0f);
                    GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    if (this.modMain.getSettings().chunkGrid > -1) {
                        int grid = ModSettings.COLORS[this.modMain.getSettings().chunkGrid];
                        r = grid >> 16 & 0xFF;
                        g = grid >> 8 & 0xFF;
                        b = grid & 0xFF;
                    }
                    for (int t = 0; t < 16; ++t) {
                        if (mchunk.getTile(t % 4, t / 4) == null) continue;
                        if (this.modMain.getSettings().getSlimeChunks() && mchunk.getTile(t % 4, t / 4).isSlimeChunk()) {
                            int slimeDrawX = drawX + 16 * (t % 4);
                            int slimeDrawZ = drawZ + 16 * (t / 4);
                            Gui.drawRect((int)slimeDrawX, (int)slimeDrawZ, (int)(slimeDrawX + 16), (int)(slimeDrawZ + 16), (int)slime.hashCode());
                        }
                        if (this.modMain.getSettings().chunkGrid <= -1 || !mchunk.getTile(t % 4, t / 4).isChunkGrid()) continue;
                        int gridDrawX = drawX + 16 * (t % 4);
                        int gridDrawZ = drawZ + 16 * (t / 4);
                        Gui.drawRect((int)gridDrawX, (int)gridDrawZ, (int)(gridDrawX + 16), (int)(gridDrawZ + 16), (int)new Color(r, g, b, 64).hashCode());
                    }
                    GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                }
            }
        }
        this.scalingFramebuffer.unbindFramebuffer();
        this.rotationFramebuffer.bindFramebuffer(false);
        GL11.glClear((int)16640);
        this.scalingFramebuffer.bindFramebufferTexture();
        GlStateManager.loadIdentity();
        if (this.modMain.getSettings().getAntiAliasing()) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        } else {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        }
        GlStateManager.translate((float)(halfWView + 0.5f), (float)(511.5f - halfWView), (float)-2000.0f);
        if (!this.modMain.getSettings().getLockNorth()) {
            GL11.glRotatef((float)angle, (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GL11.glPushMatrix();
        GlStateManager.translate((double)(-xInsidePixel * this.zoom), (double)(-zInsidePixel * this.zoom), (double)0.0);
        GlStateManager.disableBlend();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(this.modMain.getSettings().minimapOpacity / 100.0f));
        this.helper.drawMyTexturedModalRect(-256.0f, -256.0f, 0, 0, 512.0f, 512.0f, 512.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
        before = System.currentTimeMillis();
        this.mc.getTextureManager().bindTexture(InterfaceRenderer.guiTextures);
        if (this.modMain.getSettings().getSmoothDots()) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        } else {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        }
        GlStateManager.enableBlend();
        GL14.glBlendFuncSeparate((int)770, (int)771, (int)1, (int)771);
        EntityPlayer p = player;
        this.renderEntityListToFBO(minimap, p, MinimapProcessor.instance.getEntityRadar().getEntitiesIterator(), angle, playerX, playerZ, partial);
        this.renderEntityListToFBO(minimap, p, MinimapProcessor.instance.getEntityRadar().getItemsIterator(), angle, playerX, playerZ, partial);
        this.renderEntityListToFBO(minimap, p, MinimapProcessor.instance.getEntityRadar().getLivingIterator(), angle, playerX, playerZ, partial);
        this.renderEntityListToFBO(minimap, p, MinimapProcessor.instance.getEntityRadar().getHostileIterator(), angle, playerX, playerZ, partial);
        this.renderEntityListToFBO(minimap, p, MinimapProcessor.instance.getEntityRadar().getPlayersIterator(), angle, playerX, playerZ, partial);
        this.mc.getTextureManager().bindTexture(InterfaceRenderer.guiTextures);
        if (!this.modMain.getSettings().alwaysArrow) {
            this.renderEntityDotToFBO(minimap, p, (Entity)p, angle, playerX, playerZ, partial);
        }
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        this.rotationFramebuffer.unbindFramebuffer();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.disableBlend();
        GlStateManager.matrixMode((int)5889);
        GL11.glPopMatrix();
        GlStateManager.matrixMode((int)5888);
        GL11.glPopMatrix();
    }

    public void renderEntityListToFBO(MinimapProcessor minimap, EntityPlayer p, Iterator<Entity> iter, float angle, double playerX, double playerZ, float partial) {
        while (iter.hasNext()) {
            Entity e = iter.next();
            if (p == e) continue;
            if ((Keyboard.isKeyDown((int)15) || this.modMain.getSettings().getPlayerHeads()) && e instanceof AbstractClientPlayer) {
                this.renderPlayerHeadToFBO(minimap, p, (AbstractClientPlayer)e, angle, playerX, playerZ, partial);
                continue;
            }
            this.renderEntityDotToFBO(minimap, p, e, angle, playerX, playerZ, partial);
        }
    }

    public void renderPlayerHeadToFBO(MinimapProcessor minimap, EntityPlayer mainPlayer, AbstractClientPlayer e, float angle, double playerX, double playerZ, float partial) {
        if (!minimap.getEntityRadar().shouldRenderEntity((Entity)e)) {
            return;
        }
        double offx = minimap.getEntityRadar().getEntityX((Entity)e, partial) - playerX;
        double offz = playerZ - minimap.getEntityRadar().getEntityZ((Entity)e, partial);
        double offh = mainPlayer.posY - e.posY;
        GL11.glPushMatrix();
        GlStateManager.translate((double)(offx * this.zoom), (double)(offz * this.zoom), (double)0.0);
        if (!this.modMain.getSettings().getLockNorth()) {
            GL11.glRotatef((float)(-angle), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.scale((double)2.0, (double)2.0, (double)1.0);
        double brightness = minimap.getEntityRadar().getEntityBrightness(offh);
        GL11.glColor3d((double)brightness, (double)brightness, (double)brightness);
        boolean flag1 = e != null && e.isWearing(EnumPlayerModelParts.CAPE) && (e.getGameProfile().getName().equals("Dinnerbone") || e.getGameProfile().getName().equals("Grumm"));
        Minecraft.getMinecraft().getTextureManager().bindTexture(e.getLocationSkin());
        int l2 = 8 + (flag1 ? 8 : 0);
        int i3 = 8 * (flag1 ? -1 : 1);
        Gui.drawScaledCustomSizeModalRect((int)-4, (int)-4, (float)8.0f, (float)((float)l2 + (float)i3), (int)8, (int)(-i3), (int)8, (int)8, (float)64.0f, (float)64.0f);
        if (this.modMain.getSettings().isPlayerNames()) {
            GlStateManager.pushMatrix();
            GlStateManager.scale((float)0.5f, (float)-0.5f, (float)1.0f);
            GlStateManager.disableCull();
            this.mc.fontRenderer.drawString(e.getDisplayNameString(), (float)(-this.mc.fontRenderer.getStringWidth(e.getDisplayNameString()) / 2), 11.0f, -1, true);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
        if (e != null && e.isWearing(EnumPlayerModelParts.HAT)) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(e.getLocationSkin());
            int j3 = 8 + (flag1 ? 8 : 0);
            int k3 = 8 * (flag1 ? -1 : 1);
            Gui.drawScaledCustomSizeModalRect((int)-4, (int)-4, (float)40.0f, (float)((float)j3 + (float)k3), (int)8, (int)(-k3), (int)8, (int)8, (float)64.0f, (float)64.0f);
        }
        GL11.glPopMatrix();
    }

    public void renderEntityDotToFBO(MinimapProcessor minimap, EntityPlayer p, Entity e, float angle, double playerX, double playerZ, float partial) {
        if (!minimap.getEntityRadar().shouldRenderEntity(e)) {
            return;
        }
        double offx = minimap.getEntityRadar().getEntityX(e, partial) - playerX;
        double offz = playerZ - minimap.getEntityRadar().getEntityZ(e, partial);
        double offh = p.posY - e.posY;
        GL11.glPushMatrix();
        GlStateManager.translate((double)(offx * this.zoom), (double)(offz * this.zoom), (double)0.0);
        if (!this.modMain.getSettings().getLockNorth()) {
            GL11.glRotatef((float)(-angle), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.translate((float)-0.5f, (float)-0.5f, (float)0.0f);
        int color = minimap.getEntityRadar().getEntityColour(p, e, offh);
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f((float)f, (float)f1, (float)f2, (float)1.0f);
        GL11.glScalef((float)this.modMain.getSettings().dotsScale, (float)this.modMain.getSettings().dotsScale, (float)1.0f);
        this.helper.drawMyTexturedModalRect(-2.0f, -3.0f, this.modMain.getSettings().getSmoothDots() ? 2 : 10, this.modMain.getSettings().getSmoothDots() ? 89 : 78, 6.0f, 6.0f, 256.0f);
        GL11.glPopMatrix();
    }

    public void deleteFramebuffers() {
        this.scalingFramebuffer.deleteFramebuffer();
        this.rotationFramebuffer.deleteFramebuffer();
    }

    public boolean isLoadedFBO() {
        return this.loadedFBO;
    }

    public void setLoadedFBO(boolean loadedFBO) {
        this.loadedFBO = loadedFBO;
    }

    public boolean isTriedFBO() {
        return this.triedFBO;
    }
}

