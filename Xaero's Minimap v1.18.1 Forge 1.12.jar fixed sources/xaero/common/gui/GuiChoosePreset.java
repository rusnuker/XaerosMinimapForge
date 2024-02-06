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

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiEditMode;
import xaero.common.gui.MySmallButton;
import xaero.common.interfaces.Preset;

public class GuiChoosePreset
extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private IXaeroMinimap modMain;

    public GuiChoosePreset(IXaeroMinimap modMain, GuiScreen par1GuiScreen) {
        this.modMain = modMain;
        this.parentGuiScreen = par1GuiScreen;
    }

    public void initGui() {
        this.screenTitle = I18n.format((String)"gui.xaero_choose_a_preset", (Object[])new Object[0]);
        this.modMain.getInterfaces().setSelectedId(-1);
        this.modMain.getInterfaces().setDraggingId(-1);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        Iterator<Preset> iterator = this.modMain.getInterfaces().getPresetsIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Preset var7 = iterator.next();
            this.buttonList.add(new MySmallButton(i, this.width / 2 - 155 + i % 2 * 160, this.height / 7 + 24 * (i >> 1), var7.getName()));
            ++i;
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.mc.gameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                ((GuiEditMode)this.parentGuiScreen).applyPreset(par1GuiButton.id);
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 200) {
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

    public List getButtons() {
        return this.buttonList;
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(par1, par2, par3);
    }
}

