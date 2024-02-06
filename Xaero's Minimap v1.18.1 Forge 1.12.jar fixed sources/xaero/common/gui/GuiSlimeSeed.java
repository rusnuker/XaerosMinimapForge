//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  org.apache.commons.lang3.StringUtils
 *  org.lwjgl.input.Keyboard
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiSettings;
import xaero.common.settings.ModOptions;

public class GuiSlimeSeed
extends GuiSettings {
    public GuiTextField seedTextField;
    private IXaeroMinimap modMain;

    public GuiSlimeSeed(IXaeroMinimap modMain, GuiScreen parent) {
        super(modMain, parent);
        this.modMain = modMain;
        this.options = new ModOptions[]{ModOptions.SLIME_CHUNKS, ModOptions.OPEN_SLIME_SETTINGS};
    }

    @Override
    public void initGui() {
        super.initGui();
        this.screenTitle = I18n.format((String)"gui.xaero_slime_chunks", (Object[])new Object[0]);
        this.seedTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 7 + 68, 200, 20);
        this.modMain.getSettings();
        this.modMain.getSettings();
        this.seedTextField.setText("" + (this.modMain.getSettings().getSlimeChunksSeed() == null ? "" : this.modMain.getSettings().getSlimeChunksSeed()));
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partial) {
        super.drawScreen(mouseX, mouseY, partial);
        this.seedTextField.drawTextBox();
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_used_seed", (Object[])new Object[0]), this.width / 2, this.height / 7 + 55, 0xFFFFFF);
    }

    public void updateScreen() {
        this.seedTextField.updateCursorCounter();
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.seedTextField.mouseClicked(par1, par2, par3);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        String s;
        if (this.seedTextField.isFocused()) {
            this.seedTextField.textboxKeyTyped(par1, par2);
        }
        if (par2 == 28) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        if (!StringUtils.isEmpty((CharSequence)(s = this.seedTextField.getText()))) {
            try {
                long j = Long.parseLong(s);
                this.modMain.getSettings().setSlimeChunksSeed(j);
            }
            catch (NumberFormatException numberformatexception) {
                this.modMain.getSettings().setSlimeChunksSeed(s.hashCode());
            }
        }
        this.modMain.getSettings().saveSettings();
        super.keyTyped(par1, par2);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled && par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentGuiScreen);
        }
        super.actionPerformed(par1GuiButton);
    }
}

