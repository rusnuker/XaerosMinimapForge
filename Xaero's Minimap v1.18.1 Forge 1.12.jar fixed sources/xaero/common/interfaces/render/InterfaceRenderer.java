//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 */
package xaero.common.interfaces.render;

import java.awt.Color;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiEditMode;
import xaero.common.interfaces.Interface;
import xaero.common.interfaces.InterfaceManager;

public class InterfaceRenderer {
    public static final ResourceLocation guiTextures = new ResourceLocation("xaerobetterpvp", "gui/guis.png");
    private IXaeroMinimap modMain;
    private final Color disabled = new Color(189, 189, 189, 80);
    private final Color enabled = new Color(255, 255, 255, 100);
    private final Color selected = new Color(255, 255, 255, 130);
    private ScaledResolution scaledresolution;

    public InterfaceRenderer(IXaeroMinimap modMain) {
        this.modMain = modMain;
    }

    public void renderInterfaces(float partial) {
        this.scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = this.scaledresolution.getScaledWidth();
        int height = this.scaledresolution.getScaledHeight();
        int scale = this.scaledresolution.getScaleFactor();
        int mouseX = Mouse.getX() * width / Minecraft.getMinecraft().displayWidth;
        int mouseY = height - Mouse.getY() * height / Minecraft.getMinecraft().displayHeight - 1;
        this.modMain.getInterfaces().updateInterfaces(mouseX, mouseY, width, height, scale);
        Iterator<Interface> iter = this.modMain.getInterfaces().getInterfaceIterator();
        while (iter.hasNext()) {
            Interface l = iter.next();
            if (!this.modMain.getSettings().getBooleanValue(l.getOption())) continue;
            try {
                l.drawInterface(width, height, scale, partial);
            }
            catch (ConcurrentModificationException concurrentModificationException) {}
        }
        GlStateManager.enableDepth();
    }

    public void renderBoxes(int mouseX, int mouseY, int width, int height, int scale) {
        if (this.modMain.getEvents().getLastGuiOpen() instanceof GuiEditMode) {
            int mouseOverId = this.modMain.getInterfaces().getInterfaceId(mouseX, mouseY, width, height, scale);
            InterfaceManager interfaces = this.modMain.getInterfaces();
            Iterator<Interface> iter = interfaces.getInterfaceIterator();
            int i = -1;
            while (iter.hasNext()) {
                ++i;
                Interface l = iter.next();
                if (!this.modMain.getSettings().getBooleanValue(l.getOption())) continue;
                int x = l.getX();
                if (l.isFromRight()) {
                    x = width - x;
                }
                int y = l.getY();
                if (l.isFromBottom()) {
                    y = height - y;
                }
                int w = l.getW(scale);
                int h = l.getH(scale);
                int x2 = x + w;
                int y2 = y + h;
                if (interfaces.getSelectedId() == i || !interfaces.overAButton(mouseX, mouseY) && mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2 || i == interfaces.getDraggingId()) {
                    Gui.drawRect((int)x, (int)y, (int)x2, (int)y2, (int)(interfaces.getSelectedId() == i ? this.selected.hashCode() : this.enabled.hashCode()));
                    if (interfaces.getDraggingId() != -1 || i != mouseOverId) continue;
                    l.getcBox().drawBox(mouseX, mouseY, width, height);
                    continue;
                }
                Gui.drawRect((int)x, (int)y, (int)x2, (int)y2, (int)this.disabled.hashCode());
            }
        }
    }
}

