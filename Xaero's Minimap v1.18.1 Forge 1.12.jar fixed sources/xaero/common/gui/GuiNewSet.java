//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.input.Keyboard
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiWaypoints;
import xaero.common.gui.MySmallButton;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;

public class GuiNewSet
extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private GuiTextField nameTextField;
    private String nameText = "";
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;
    private WaypointWorld waypointWorld;

    public GuiNewSet(IXaeroMinimap modMain, GuiScreen par1GuiScreen, WaypointWorld waypointWorld) {
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
        this.parentGuiScreen = par1GuiScreen;
        this.waypointWorld = waypointWorld;
    }

    public void initGui() {
        this.screenTitle = I18n.format((String)"gui.xaero_create_set", (Object[])new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(new MySmallButton(200, this.width / 2 - 155, this.height / 6 + 168, I18n.format((String)"gui.xaero_confirm", (Object[])new Object[0])));
        this.buttonList.add(new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        this.nameTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
        this.nameTextField.setText(this.nameText);
        this.nameTextField.setFocused(true);
        Keyboard.enableRepeatEvents((boolean)true);
        this.updateConfirmButton();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    private boolean canConfirm() {
        return this.nameTextField.getText().length() > 0 && this.waypointWorld.getSets().get(this.nameTextField.getText()) == null;
    }

    private void updateConfirmButton() {
        ((GuiButton)this.buttonList.get((int)0)).enabled = this.canConfirm();
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if (this.nameTextField.isFocused()) {
            this.nameTextField.textboxKeyTyped(par1, par2);
            this.nameText = this.nameTextField.getText();
            this.nameTextField.setText(this.nameText);
            this.updateConfirmButton();
        }
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        super.keyTyped(par1, par2);
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.nameTextField.mouseClicked(par1, par2, par3);
    }

    public void updateScreen() {
        this.nameTextField.updateCursorCounter();
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.mc.gameSettings.guiScale;
            if (par1GuiButton.id == 200) {
                String setName = this.nameTextField.getText().replace(":", "\u00a7\u00a7");
                this.waypointWorld.setCurrent(setName);
                this.waypointWorld.addSet(setName);
                this.waypointsManager.updateWaypoints();
                try {
                    this.modMain.getSettings().saveWaypoints(this.waypointWorld);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.mc.displayGuiScreen((GuiScreen)new GuiWaypoints(this.modMain, ((GuiWaypoints)this.parentGuiScreen).getParentScreen()));
            }
            if (par1GuiButton.id == 201) {
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
        this.nameTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}

