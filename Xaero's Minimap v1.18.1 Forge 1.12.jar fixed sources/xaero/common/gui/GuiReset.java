//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;

public class GuiReset
extends GuiYesNo {
    private IXaeroMinimap modMain;

    public GuiReset(IXaeroMinimap modMain) {
        super(null, I18n.format((String)"gui.xaero_reset_message", (Object[])new Object[0]), I18n.format((String)"gui.xaero_reset_message2", (Object[])new Object[0]), 0);
        this.modMain = modMain;
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.modMain.getInterfaces().getMinimap().setToResetImage(true);
                this.modMain.resetSettings();
            }
            case 1: {
                this.modMain.getGuiHelper().onResetCancel();
            }
        }
    }
}

