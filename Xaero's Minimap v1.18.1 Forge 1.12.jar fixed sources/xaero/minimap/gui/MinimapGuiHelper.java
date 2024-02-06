//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package xaero.minimap.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiHelper;
import xaero.common.gui.MyOptions;
import xaero.minimap.gui.GuiMinimap;
import xaero.minimap.gui.GuiMinimap5;

public class MinimapGuiHelper
extends GuiHelper {
    public MinimapGuiHelper(IXaeroMinimap modMain) {
        super(modMain);
    }

    @Override
    public void openInterfaceSettings(int i) {
        switch (i) {
            case 4: {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMinimap(this.modMain, Minecraft.getMinecraft().currentScreen));
            }
        }
    }

    @Override
    public void onResetCancel() {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMinimap5(this.modMain, null));
    }

    @Override
    public MyOptions getMyOptions() {
        return new MyOptions("gui.xaero_minimap_settings", new GuiMinimap(this.modMain, Minecraft.getMinecraft().currentScreen), null, Minecraft.getMinecraft().gameSettings);
    }
}

