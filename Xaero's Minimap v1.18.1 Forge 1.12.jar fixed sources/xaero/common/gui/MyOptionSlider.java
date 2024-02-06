//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.math.MathHelper
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import xaero.common.IXaeroMinimap;
import xaero.common.settings.ModOptions;

public class MyOptionSlider
extends GuiButton {
    private float sliderValue;
    private boolean dragging;
    private ModOptions options;
    private IXaeroMinimap modMain;

    public MyOptionSlider(IXaeroMinimap modMain, int buttonId, int x, int y, ModOptions optionIn) {
        this(modMain, buttonId, x, y, optionIn, 0.0f, 1.0f);
    }

    public MyOptionSlider(IXaeroMinimap modMain, int buttonId, int x, int y, ModOptions optionIn, float minValueIn, float maxValue) {
        super(buttonId, x, y, 150, 20, "");
        this.modMain = modMain;
        this.sliderValue = 1.0f;
        this.options = optionIn;
        this.sliderValue = optionIn.normalizeValue(modMain.getSettings().getOptionFloatValue(optionIn));
        this.displayString = modMain.getSettings().getKeyBinding(optionIn);
    }

    protected int getHoverState(boolean mouseOver) {
        return 0;
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.updateValue(mouseX);
            }
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.updateValue(mouseX);
            this.dragging = true;
            return true;
        }
        return false;
    }

    private void updateValue(double mouseX) {
        this.sliderValue = (float)(mouseX - (double)(this.x + 4)) / (float)(this.width - 8);
        this.sliderValue = MathHelper.clamp((float)this.sliderValue, (float)0.0f, (float)1.0f);
        float f = this.options.denormalizeValue(this.sliderValue);
        try {
            this.modMain.getSettings().setOptionFloatValue(this.options, f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.sliderValue = this.options.normalizeValue(f);
        this.displayString = this.modMain.getSettings().getKeyBinding(this.options);
    }

    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }
}

