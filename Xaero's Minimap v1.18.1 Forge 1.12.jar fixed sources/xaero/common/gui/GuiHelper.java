//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package xaero.common.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiDotColors;
import xaero.common.gui.GuiEditMode;
import xaero.common.gui.GuiReset;
import xaero.common.gui.GuiTpCommand;
import xaero.common.gui.MyOptions;
import xaero.common.settings.ModOptions;

public abstract class GuiHelper {
    protected IXaeroMinimap modMain;

    public GuiHelper(IXaeroMinimap modMain) {
        this.modMain = modMain;
    }

    public void openSettingsGui(ModOptions returnModOptions) {
        GuiScreen current = Minecraft.getMinecraft().currentScreen;
        if (returnModOptions == ModOptions.EDIT) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiEditMode(this.modMain, current, "gui.xaero_minimap_guide", false));
        } else if (returnModOptions == ModOptions.RESET) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiReset(this.modMain));
        } else if (returnModOptions == ModOptions.DOTS) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiDotColors(this.modMain, current));
        } else if (returnModOptions == ModOptions.WAYPOINTS_TP) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiTpCommand(this.modMain, current));
        }
    }

    public abstract void openInterfaceSettings(int var1);

    public abstract void onResetCancel();

    public abstract MyOptions getMyOptions();
}

