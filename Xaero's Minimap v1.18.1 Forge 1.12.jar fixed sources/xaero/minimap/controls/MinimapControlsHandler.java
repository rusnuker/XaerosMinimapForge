//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.settings.KeyBinding
 */
package xaero.minimap.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import xaero.common.IXaeroMinimap;
import xaero.common.controls.ControlsHandler;
import xaero.minimap.XaeroMinimap;
import xaero.minimap.gui.GuiMinimap;

public class MinimapControlsHandler
extends ControlsHandler {
    public MinimapControlsHandler(IXaeroMinimap modMain) {
        super(modMain);
    }

    @Override
    public void keyDownPre(KeyBinding kb) {
        if (kb == XaeroMinimap.keyBindSettings) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMinimap(this.modMain, null));
        }
    }
}

