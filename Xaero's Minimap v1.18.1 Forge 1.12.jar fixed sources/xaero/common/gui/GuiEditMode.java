//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiChoosePreset;
import xaero.common.gui.GuiInstructions;
import xaero.common.gui.MySmallButton;
import xaero.common.interfaces.Interface;
import xaero.common.interfaces.InterfaceManager;
import xaero.common.settings.ModOptions;

public class GuiEditMode
extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private IXaeroMinimap modMain;
    private String message;
    private boolean instructions;

    public GuiEditMode(IXaeroMinimap modMain, GuiScreen par1GuiScreen, String message, boolean instructions) {
        this.modMain = modMain;
        this.parentGuiScreen = par1GuiScreen;
        this.message = message;
        this.instructions = instructions;
    }

    public void initGui() {
        this.screenTitle = I18n.format((String)"gui.xaero_edit_mode", (Object[])new Object[0]);
        this.modMain.getInterfaces().setSelectedId(-1);
        this.modMain.getInterfaces().setDraggingId(-1);
        this.buttonList.clear();
        this.buttonList.add(new MySmallButton(200, this.width / 2 - 155, this.height / 6 + 143, I18n.format((String)"gui.xaero_confirm", (Object[])new Object[0])));
        this.buttonList.add(new MySmallButton(202, this.width / 2 + 5, this.height / 6 + 143, I18n.format((String)"gui.xaero_choose_a_preset", (Object[])new Object[0])));
        if (this.instructions) {
            this.buttonList.add(new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
            this.buttonList.add(new MySmallButton(203, this.width / 2 - 155, this.height / 6 + 168, I18n.format((String)"gui.xaero_instructions", (Object[])new Object[0])));
        } else {
            this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.mc.gameSettings.guiScale;
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
                    this.confirm();
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 201) {
                GuiEditMode.cancel(this.modMain.getInterfaces());
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 202) {
                this.mc.displayGuiScreen((GuiScreen)new GuiChoosePreset(this.modMain, this));
            }
            if (par1GuiButton.id == 203) {
                this.mc.displayGuiScreen((GuiScreen)new GuiInstructions(this));
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
        if (this.modMain.getInterfaces().getDraggingId() == -1) {
            if (this.mc.player == null) {
                this.drawDefaultBackground();
                this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_not_ingame", (Object[])new Object[0]), this.width / 2, this.height / 6 + 128, 0xFFFFFF);
            } else {
                this.drawCenteredString(this.fontRenderer, I18n.format((String)this.message, (Object[])new Object[0]), this.width / 2, this.height / 6 + 128, 0xFFFFFF);
            }
            super.drawScreen(par1, par2, par3);
        }
        if (this.mc.player != null) {
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();
            int scale = scaledresolution.getScaleFactor();
            this.modMain.getInterfaceRenderer().renderBoxes(par1, par2, width, height, scale);
        }
    }

    public void confirm() {
        Iterator<Interface> iter = this.modMain.getInterfaces().getInterfaceIterator();
        while (iter.hasNext()) {
            iter.next().backup();
        }
    }

    public static void cancel(InterfaceManager interfaces) {
        Iterator<Interface> iter = interfaces.getInterfaceIterator();
        while (iter.hasNext()) {
            iter.next().restore();
        }
    }

    public void applyPreset(int id) {
        Iterator<Interface> iter = this.modMain.getInterfaces().getInterfaceIterator();
        this.modMain.getInterfaces().setActionTimer(10);
        while (iter.hasNext()) {
            iter.next().applyPreset(this.modMain.getInterfaces().getPreset(id));
        }
    }
}

