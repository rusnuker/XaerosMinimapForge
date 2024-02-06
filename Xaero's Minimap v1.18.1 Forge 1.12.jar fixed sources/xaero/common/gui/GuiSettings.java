//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.MyOptionSlider;
import xaero.common.gui.MySmallButton;
import xaero.common.settings.ModOptions;

public class GuiSettings
extends GuiScreen {
    protected GuiScreen parentGuiScreen;
    protected String screenTitle;
    protected ModOptions[] options;
    protected IXaeroMinimap modMain;

    public GuiSettings(IXaeroMinimap modMain, GuiScreen par1GuiScreen) {
        this.modMain = modMain;
        this.parentGuiScreen = par1GuiScreen;
    }

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format((String)"gui.xaero_back", (Object[])new Object[0])));
        int var8 = 0;
        if (this.options == null) {
            return;
        }
        for (ModOptions option : this.options) {
            if (!option.getEnumFloat()) {
                this.buttonList.add(new MySmallButton(option.returnEnumOrdinal(), this.width / 2 - 155 + var8 % 2 * 160, this.height / 7 + 24 * (var8 >> 1), option, this.modMain.getSettings().getKeyBinding(option)));
            } else {
                this.buttonList.add(new MyOptionSlider(this.modMain, option.returnEnumOrdinal(), this.width / 2 - 155 + var8 % 2 * 160, this.height / 7 + 24 * (var8 >> 1), option));
            }
            ++var8;
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.mc.gameSettings.guiScale;
            try {
                if (par1GuiButton instanceof MySmallButton) {
                    this.modMain.getGuiHelper().openSettingsGui(((MySmallButton)par1GuiButton).returnModOptions());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                try {
                    this.modMain.getSettings().setOptionValue(((MySmallButton)par1GuiButton).returnModOptions(), 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                par1GuiButton.displayString = this.modMain.getSettings().getKeyBinding(ModOptions.getModOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                try {
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.mc.gameSettings.guiScale != var2) {
                ScaledResolution var3 = new ScaledResolution(this.mc);
                int var4 = var3.getScaledWidth();
                int var5 = var3.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(par1, par2, par3);
    }
}

