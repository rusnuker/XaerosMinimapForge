//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.GameSettings
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import xaero.common.gui.GuiSettings;

public class MyOptions
extends GuiOptions {
    private GuiSettings settingsScreen;
    private String buttonName;
    Minecraft mc = Minecraft.getMinecraft();

    public MyOptions(String buttonName, GuiSettings settingsScreen, GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
        super(par1GuiScreen, par2GameSettings);
        this.buttonName = buttonName;
        this.settingsScreen = settingsScreen;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(300, this.width / 2 - 100, this.height / 6 + 10, I18n.format((String)this.buttonName, (Object[])new Object[0])));
    }

    protected void actionPerformed(GuiButton par1GuiButton) throws IOException {
        super.actionPerformed(par1GuiButton);
        if (par1GuiButton.enabled && par1GuiButton.id == 300) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen((GuiScreen)this.settingsScreen);
        }
    }
}

