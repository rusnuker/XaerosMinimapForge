//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.minimap.waypoints.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.ServerWaypointStorage;
import xaero.common.gui.GuiMisc;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.settings.ModSettings;

public class WaypointsIngameRenderer {
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;

    public WaypointsIngameRenderer(IXaeroMinimap modMain, Minecraft mc) {
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
    }

    public void render(float partial) {
        if (this.modMain.getSettings().getShowIngameWaypoints() && this.waypointsManager.getWaypoints() != null) {
            Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partial;
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partial;
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partial;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            boolean divideBy8 = this.waypointsManager.divideBy8(this.waypointsManager.getCurrentContainerID());
            if (this.waypointsManager.renderAllSets) {
                HashMap<String, WaypointSet> sets = this.waypointsManager.getCurrentWorld().getSets();
                for (Map.Entry<String, WaypointSet> setEntry : sets.entrySet()) {
                    this.renderWaypointsList(setEntry.getValue().getList(), d3, d4, d5, entity, bufferbuilder, tessellator, divideBy8);
                }
            } else {
                this.renderWaypointsList(this.waypointsManager.getWaypoints().getList(), d3, d4, d5, entity, bufferbuilder, tessellator, divideBy8);
            }
            if (ServerWaypointStorage.working() && this.waypointsManager.getServerWaypoints() != null) {
                this.renderWaypointsList(this.waypointsManager.getServerWaypoints(), d3, d4, d5, entity, bufferbuilder, tessellator, divideBy8);
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
        }
    }

    private void renderWaypointsList(List<Waypoint> list, double d3, double d4, double d5, Entity entity, BufferBuilder bufferbuilder, Tessellator tessellator, boolean divideBy8) {
        for (int i = 0; i < list.size(); ++i) {
            this.renderWaypointIngame(list.get(i), this.modMain, 12.0, d3, d4, d5, entity, bufferbuilder, tessellator, divideBy8);
        }
    }

    private void renderWaypointIngame(Waypoint w, IXaeroMinimap modMain, double radius, double d3, double d4, double d5, Entity entity, BufferBuilder bufferBuilder, Tessellator tessellator, boolean divideBy8) {
        if (w.isDisabled() || w.getType() == 1 && !modMain.getSettings().getDeathpoints()) {
            return;
        }
        float offX = (float)Math.floorDiv(w.getX(), divideBy8 ? 8 : 1) - (float)d3 + 0.5f;
        float offY = (float)w.getY() - (float)d4 + 1.0f;
        float offZ = (float)Math.floorDiv(w.getZ(), divideBy8 ? 8 : 1) - (float)d5 + 0.5f;
        double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
        double correctDistance = Math.sqrt(offX * offX + (offY - 1.0f) * (offY - 1.0f) + offZ * offZ);
        w.setLastDistance(distance);
        if (modMain.getSettings().waypointsDistance != 0.0f && distance > (double)modMain.getSettings().waypointsDistance || modMain.getSettings().waypointsDistanceMin != 0.0f && distance < (double)modMain.getSettings().waypointsDistanceMin) {
            return;
        }
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        FontRenderer fontrenderer = renderManager.getFontRenderer();
        if (fontrenderer == null) {
            return;
        }
        float f = 1.6f;
        float f1 = 0.016666668f * f;
        GlStateManager.pushMatrix();
        float textSize = 1.0f;
        String name = w.getLocalizedName();
        String distanceText = "";
        boolean showDistance = false;
        float zoomer2 = 1.0f;
        if (modMain.getSettings().keepWaypointNames) {
            textSize = 1.6f;
        }
        if (distance > radius) {
            double maxDistance = (double)Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16.0;
            if (distance > maxDistance) {
                zoomer2 = (float)(maxDistance / radius);
                float zoomer = (float)(maxDistance / distance);
                offX *= zoomer;
                offY *= zoomer;
                offY += entity.getEyeHeight() * (1.0f - zoomer);
                offZ *= zoomer;
            } else {
                zoomer2 = (float)(distance / radius);
            }
        }
        if (correctDistance > 20.0 || modMain.getSettings().alwaysShowDistance) {
            textSize = 1.6f;
            if (modMain.getSettings().distance == 1) {
                float cameraAngle;
                float offset;
                float Z = (float)(offZ == 0.0f ? 0.001 : (double)offZ);
                float angle = (float)Math.toDegrees(Math.atan(-offX / Z));
                if (offZ < 0.0f) {
                    angle = offX < 0.0f ? (angle += 180.0f) : (angle -= 180.0f);
                }
                showDistance = (offset = MathHelper.wrapDegrees((float)(angle - (cameraAngle = MathHelper.wrapDegrees((float)entity.rotationYaw))))) > -20.0f && offset < 20.0f;
            } else if (modMain.getSettings().distance == 2) {
                showDistance = true;
            }
            if (showDistance) {
                distanceText = GuiMisc.simpleFormat.format(correctDistance) + "m";
                if (!modMain.getSettings().keepWaypointNames) {
                    name = "";
                }
            } else {
                name = "";
            }
        }
        GlStateManager.translate((float)offX, (float)offY, (float)offZ);
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)(-f1), (float)(-f1), (float)f1);
        GlStateManager.scale((float)zoomer2, (float)zoomer2, (float)1.0f);
        GlStateManager.disableLighting();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        this.drawIconInWorld(w, modMain.getSettings(), bufferBuilder, tessellator, fontrenderer, name, distanceText, textSize, showDistance);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }

    public void drawIconInWorld(Waypoint w, ModSettings settings, BufferBuilder vertexBuffer, Tessellator tessellator, FontRenderer fontrenderer, String name, String distance, float textSize, boolean showDistance) {
        GlStateManager.scale((float)settings.waypointsScale, (float)settings.waypointsScale, (float)1.0f);
        if (w.getType() == 0) {
            int c = ModSettings.COLORS[w.getColor()];
            float l = (float)(c >> 16 & 0xFF) / 255.0f;
            float i1 = (float)(c >> 8 & 0xFF) / 255.0f;
            float j1 = (float)(c & 0xFF) / 255.0f;
            int s = fontrenderer.getStringWidth(w.getSymbol()) / 2;
            GlStateManager.disableTexture2D();
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION);
            GlStateManager.color((float)l, (float)i1, (float)j1, (float)(133.3f * ((float)settings.waypointOpacityIngame / 100.0f) / 255.0f));
            vertexBuffer.pos(-5.0, -9.0, 0.0).endVertex();
            vertexBuffer.pos(-5.0, 0.0, 0.0).endVertex();
            vertexBuffer.pos(4.0, 0.0, 0.0).endVertex();
            vertexBuffer.pos(4.0, -9.0, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(w.getSymbol(), -s, -8, 0x20FFFFFF);
            fontrenderer.drawString(w.getSymbol(), -s, -8, -1);
        } else if (w.getType() == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(InterfaceRenderer.guiTextures);
            float f = 0.00390625f;
            float f1 = 0.00390625f;
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)(250.0f * ((float)settings.waypointOpacityIngame / 100.0f) / 255.0f));
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexBuffer.pos(-5.0, -9.0, 0.0).tex(0.0, (double)(78.0f * f1)).endVertex();
            vertexBuffer.pos(-5.0, 0.0, 0.0).tex(0.0, (double)(87.0f * f1)).endVertex();
            vertexBuffer.pos(4.0, 0.0, 0.0).tex((double)(9.0f * f), (double)(87.0f * f1)).endVertex();
            vertexBuffer.pos(4.0, -9.0, 0.0).tex((double)(9.0f * f), (double)(78.0f * f1)).endVertex();
            tessellator.draw();
            if (!showDistance) {
                name = w.getLocalizedName();
                if (!settings.keepWaypointNames) {
                    textSize = 1.0f;
                }
            }
        }
        if (Minecraft.getMinecraft().isUnicode()) {
            textSize *= 1.5f;
        }
        boolean showingName = name.length() > 0;
        GlStateManager.translate((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.scale((float)(textSize / 2.0f), (float)(textSize / 2.0f), (float)1.0f);
        if (distance.length() > 0) {
            int t = fontrenderer.getStringWidth(distance) / 2;
            GlStateManager.disableTexture2D();
            GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f, (float)0.27450982f);
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION);
            vertexBuffer.pos((double)(-t) - 1.0, (double)(showingName ? 10 : 0), 0.0).endVertex();
            vertexBuffer.pos((double)(-t) - 1.0, 9.0 + (double)(showingName ? 10 : 0), 0.0).endVertex();
            vertexBuffer.pos((double)t, 9.0 + (double)(showingName ? 10 : 0), 0.0).endVertex();
            vertexBuffer.pos((double)t, (double)(showingName ? 10 : 0), 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(distance, -t, 1 + (showingName ? 10 : 0), 0x20FFFFFF);
            fontrenderer.drawString(distance, -t, 1 + (showingName ? 10 : 0), -1);
        }
        if (showingName) {
            int t = fontrenderer.getStringWidth(name) / 2;
            fontrenderer.drawString(name, -t, 1, 0x20FFFFFF);
            fontrenderer.drawString(name, -t, 1, -1);
        }
    }
}

