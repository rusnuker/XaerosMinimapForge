//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.input.Keyboard
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.MySmallButton;

public class GuiTpCommand
extends GuiScreen {
    public String screenTitle;
    private GuiScreen parentScreen;
    private MySmallButton confirmButton;
    private IXaeroMinimap modMain;
    private GuiTextField commandTextField;
    private String command;

    public GuiTpCommand(IXaeroMinimap modMain, GuiScreen parent) {
        this.parentScreen = parent;
        this.modMain = modMain;
    }

    public void initGui() {
        this.buttonList.clear();
        this.confirmButton = new MySmallButton(200, this.width / 2 - 155, this.height / 6 + 168, I18n.format((String)"gui.xaero_confirm", (Object[])new Object[0]));
        this.buttonList.add(this.confirmButton);
        this.buttonList.add(new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        this.screenTitle = I18n.format((String)"gui.xaero_teleport_command", (Object[])new Object[0]);
        this.commandTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 7 + 68, 200, 20);
        if (this.command == null) {
            this.command = this.modMain.getSettings().waypointTp;
        }
        this.commandTextField.setText(this.command);
        Keyboard.enableRepeatEvents((boolean)true);
    }

    public void drawScreen(int mouseX, int mouseY, float partial) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partial);
        this.commandTextField.drawTextBox();
    }

    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
        this.command = this.commandTextField.getText();
        this.confirmButton.enabled = this.command != null && this.command.length() > 0;
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.commandTextField.mouseClicked(par1, par2, par3);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if (this.commandTextField.isFocused()) {
            this.commandTextField.textboxKeyTyped(par1, par2);
        }
        if (par2 == 28) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        super.keyTyped(par1, par2);
    }

    protected void actionPerformed(GuiButton par1GuiButton) throws IOException {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 200) {
                this.modMain.getSettings().waypointTp = this.command;
                this.modMain.getSettings().saveSettings();
            }
            this.mc.displayGuiScreen(this.parentScreen);
        }
        super.actionPerformed(par1GuiButton);
    }
}

