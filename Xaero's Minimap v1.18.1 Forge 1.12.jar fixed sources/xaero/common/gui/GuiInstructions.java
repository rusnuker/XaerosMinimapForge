//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiInstructions
extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle;

    public GuiInstructions(GuiScreen par1GuiScreen) {
        this.parentGuiScreen = par1GuiScreen;
    }

    public void initGui() {
        this.screenTitle = I18n.format((String)"gui.xaero_instructions", (Object[])new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format((String)"gui.xaero_OK", (Object[])new Object[0])));
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled && par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentGuiScreen);
        }
    }

    public List getButtons() {
        return this.buttonList;
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_select", (Object[])new Object[0]), this.width / 2, this.height / 7 + 10, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_drag", (Object[])new Object[0]), this.width / 2, this.height / 7 + 21, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_deselect", (Object[])new Object[0]), this.width / 2, this.height / 7 + 32, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_center", (Object[])new Object[0]), this.width / 2, this.height / 7 + 43, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_different_centered", (Object[])new Object[0]), this.width / 2, this.height / 7 + 54, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_flip", (Object[])new Object[0]), this.width / 2, this.height / 7 + 65, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_settings", (Object[])new Object[0]), this.width / 2, this.height / 7 + 76, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_preset", (Object[])new Object[0]), this.width / 2, this.height / 7 + 87, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_save", (Object[])new Object[0]), this.width / 2, this.height / 7 + 98, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_howto_cancel", (Object[])new Object[0]), this.width / 2, this.height / 7 + 109, 0xFFFFFF);
        super.drawScreen(par1, par2, par3);
    }
}

