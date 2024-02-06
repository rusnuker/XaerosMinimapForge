//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;
import xaero.common.settings.ModSettings;

public class GuiUpdate
extends GuiYesNo {
    private IXaeroMinimap modMain;

    public GuiUpdate(IXaeroMinimap modMain, String message1) {
        super(null, message1, "Would you like to update it (open the mod page)?", 0);
        this.modMain = modMain;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 144, I18n.format((String)"Don't show again for this update", (Object[])new Object[0])));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (this.modMain.getModJAR() == null) {
                    return;
                }
                try {
                    Desktop d = Desktop.getDesktop();
                    d.browse(new URI(this.modMain.getUpdateLink()));
                    d.open(this.modMain.getModJAR().getParentFile());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                Minecraft.getMinecraft().shutdown();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(null);
                break;
            }
            case 200: {
                ModSettings.ignoreUpdate = this.modMain.getNewestUpdateID();
                this.modMain.getSettings().saveSettings();
                this.mc.displayGuiScreen(null);
                break;
            }
            case 201: {
                try {
                    Desktop d = Desktop.getDesktop();
                    d.browse(new URI(this.modMain.getPatreon().changelogLink));
                    break;
                }
                catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

