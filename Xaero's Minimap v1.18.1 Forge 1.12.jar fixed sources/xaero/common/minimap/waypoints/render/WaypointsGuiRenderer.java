//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.minimap.waypoints.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.ServerWaypointStorage;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.MinimapRadar;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.settings.ModSettings;

public class WaypointsGuiRenderer {
    private IXaeroMinimap modMain;
    private Minecraft mc;
    private WaypointsManager waypointsManager;

    public WaypointsGuiRenderer(IXaeroMinimap modMain, Minecraft mc) {
        this.modMain = modMain;
        this.mc = mc;
        this.waypointsManager = modMain.getWaypointsManager();
    }

    public void render(double playerX, double playerZ, int specW, int specH, double ps, double pc, float partial, double zoom) {
        boolean divideBy8 = this.waypointsManager.divideBy8(this.waypointsManager.getCurrentContainerID());
        if (this.modMain.getSettings().compassOverWaypoints) {
            this.drawWaypoints(playerX, playerZ, specW, specH, ps, pc, partial, divideBy8, zoom);
            this.drawCompass(specW, specH, ps, pc, zoom);
        } else {
            this.drawCompass(specW, specH, ps, pc, zoom);
            this.drawWaypoints(playerX, playerZ, specW, specH, ps, pc, partial, divideBy8, zoom);
        }
    }

    private void drawWaypoints(double playerX, double playerZ, int specW, int specH, double ps, double pc, float partial, boolean divideBy8, double zoom) {
        if (this.modMain.getSettings().getShowWaypoints() && this.waypointsManager.getWaypoints() != null) {
            if (this.waypointsManager.renderAllSets) {
                HashMap<String, WaypointSet> sets = this.waypointsManager.getCurrentWorld().getSets();
                for (Map.Entry<String, WaypointSet> setEntry : sets.entrySet()) {
                    this.renderWaypointsList(setEntry.getValue().getList(), playerX, playerZ, specW, specH, ps, pc, divideBy8, zoom);
                }
            } else {
                this.renderWaypointsList(this.waypointsManager.getWaypoints().getList(), playerX, playerZ, specW, specH, ps, pc, divideBy8, zoom);
            }
        }
        if (this.modMain.getSettings().getShowWaypoints() && ServerWaypointStorage.working() && this.waypointsManager.getServerWaypoints() != null) {
            this.renderWaypointsList(this.waypointsManager.getServerWaypoints(), playerX, playerZ, specW, specH, ps, pc, divideBy8, zoom);
        }
    }

    private void renderWaypointsList(List<Waypoint> list, double playerX, double playerZ, int specW, int specH, double ps, double pc, boolean divideBy8, double zoom) {
        for (int i = 0; i < list.size(); ++i) {
            Waypoint w = list.get(i);
            if (w == null || w.isDisabled() || w.getType() == 1 && !this.modMain.getSettings().getDeathpoints() || this.modMain.getSettings().waypointsDistance != 0.0f && w.getLastDistance() > (double)this.modMain.getSettings().waypointsDistance) continue;
            double offx = (double)Math.floorDiv(w.getX(), divideBy8 ? 8 : 1) - playerX;
            double offy = (double)Math.floorDiv(w.getZ(), divideBy8 ? 8 : 1) - playerZ;
            this.translatePosition(specW, specH, ps, pc, offx, offy, zoom);
            GlStateManager.scale((float)2.0f, (float)2.0f, (float)1.0f);
            this.drawIconOnGUI(w, this.modMain.getSettings(), -4, -4);
            GL11.glPopMatrix();
        }
    }

    private void drawCompass(int specW, int specH, double ps, double pc, double zoom) {
        String[] nesw = new String[]{"N", "E", "S", "W"};
        for (int i = 0; i < 4; ++i) {
            double offx;
            double d = i == 0 || i == 2 ? 0.0 : (offx = (double)(i == 1 ? 10000 : -10000));
            double offy = i == 1 || i == 3 ? 0.0 : (double)(i == 2 ? 10000 : -10000);
            this.translatePosition(specW, specH, ps, pc, offx, offy, zoom);
            GlStateManager.scale((float)2.0f, (float)2.0f, (float)1.0f);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(nesw[i], (float)(1 - this.mc.fontRenderer.getStringWidth(nesw[i]) / 2), -3.0f, MinimapRadar.radarPlayers.hashCode());
            GL11.glPopMatrix();
        }
    }

    public void translatePosition(int specW, int specH, double ps, double pc, double offx, double offy, double zoom) {
        double X;
        double Y = (pc * offx + ps * offy) * zoom;
        double borderedX = X = (ps * offx - pc * offy) * zoom;
        double borderedY = Y;
        if (borderedX > (double)specW) {
            borderedX = specW;
            borderedY = Y * (double)specW / X;
        } else if (borderedX < (double)(-specW)) {
            borderedX = -specW;
            borderedY = -Y * (double)specW / X;
        }
        if (borderedY > (double)specH) {
            borderedY = specH;
            borderedX = X * (double)specH / Y;
        } else if (borderedY < (double)(-specH)) {
            borderedY = -specH;
            borderedX = -X * (double)specH / Y;
        }
        GL11.glPushMatrix();
        GlStateManager.translate((double)borderedX, (double)borderedY, (double)0.0);
    }

    public void drawIconOnGUI(Waypoint w, ModSettings settings, int drawX, int drawY) {
        if (w.getType() == 0) {
            int rectX2 = drawX + 9;
            int rectY2 = drawY + 9;
            int c = ModSettings.COLORS[w.getColor()];
            int r = c >> 16 & 0xFF;
            int g = c >> 8 & 0xFF;
            int b = c & 0xFF;
            int a = (int)(255.0f * ((float)settings.waypointOpacityMap / 100.0f));
            c = a << 24 | r << 16 | g << 8 | b;
            Gui.drawRect((int)drawX, (int)drawY, (int)rectX2, (int)rectY2, (int)c);
            int j = Minecraft.getMinecraft().fontRenderer.getStringWidth(w.getSymbol()) / 2;
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(w.getSymbol(), (float)(drawX + 5 - j), (float)(drawY + 1), MinimapRadar.radarPlayers.hashCode());
        } else if (w.getType() == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(InterfaceRenderer.guiTextures);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(drawX, drawY, 0, 78, 9, 9);
        }
    }

    public void drawSetChange(ScaledResolution res) {
        if (this.waypointsManager.getWaypoints() != null && this.waypointsManager.setChanged != 0L) {
            int passed = (int)(System.currentTimeMillis() - this.waypointsManager.setChanged);
            if (passed < 1500) {
                int fadeTime = 300;
                boolean fading = passed > 1500 - fadeTime;
                int alpha = 3 + (int)(252.0f * (fading ? (float)(1500 - passed) / (float)fadeTime : 1.0f));
                Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format((String)this.waypointsManager.getWaypoints().getName(), (Object[])new Object[0]), res.getScaledWidth() / 2, res.getScaledHeight() / 2 + 50, new Color(255, 255, 255, alpha).hashCode());
            } else {
                this.waypointsManager.setChanged = 0L;
            }
        }
    }
}

