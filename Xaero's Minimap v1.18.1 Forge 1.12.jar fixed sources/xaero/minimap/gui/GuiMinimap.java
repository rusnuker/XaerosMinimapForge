//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 */
package xaero.minimap.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiSettings;
import xaero.common.gui.GuiWaypoints;
import xaero.common.gui.MySmallButton;
import xaero.common.gui.MyTinyButton;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.settings.ModOptions;
import xaero.common.settings.ModSettings;
import xaero.minimap.gui.GuiMinimap2;

public class GuiMinimap
extends GuiSettings {
    private MySmallButton waypointsButton;
    private MyTinyButton nextButton;
    private MyTinyButton prevButton;
    private String title;
    private MinimapProcessor minimap;

    public GuiMinimap(IXaeroMinimap modMain, GuiScreen par1GuiScreen) {
        super(modMain, par1GuiScreen);
        this.minimap = modMain.getInterfaces().getMinimap();
        this.options = new ModOptions[]{ModOptions.MINIMAP, ModOptions.ZOOM, ModOptions.SIZE, ModOptions.DOTS, ModOptions.CAVE_MAPS, ModOptions.NORTH, ModOptions.COORDS, ModOptions.WAYPOINTS, ModOptions.CHUNK_GRID, ModOptions.INGAME_WAYPOINTS, ModOptions.SLIME_CHUNKS, ModOptions.EDIT};
    }

    @Override
    public void initGui() {
        super.initGui();
        this.screenTitle = this.modMain.getMessage();
        this.title = I18n.format((String)"gui.xaero_minimap_settings", (Object[])new Object[0]);
        if (ModSettings.serverSettings != ModSettings.defaultSettings) {
            this.title = "\u00a7e" + I18n.format((String)"gui.xaero_server_disabled", (Object[])new Object[0]);
        }
        this.waypointsButton = new MySmallButton(201, this.width / 2 - 75, this.height / 7 + 144, I18n.format((String)"gui.xaero_waypoints", (Object[])new Object[0]));
        this.buttonList.add(this.waypointsButton);
        this.nextButton = new MyTinyButton(202, this.width / 2 + 80, this.height / 7 + 144, I18n.format((String)"gui.xaero_next", (Object[])new Object[0]));
        this.buttonList.add(this.nextButton);
        this.prevButton = new MyTinyButton(203, this.width / 2 - 155, this.height / 7 + 144, I18n.format((String)"gui.xaero_previous", (Object[])new Object[0]));
        this.buttonList.add(this.prevButton);
        this.prevButton.enabled = false;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 201) {
                this.mc.displayGuiScreen((GuiScreen)new GuiWaypoints(this.modMain, this));
            }
            if (par1GuiButton.id == 202) {
                this.mc.displayGuiScreen((GuiScreen)new GuiMinimap2(this.modMain, this.parentGuiScreen));
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.waypointsButton.enabled = Minecraft.getMinecraft().player != null && this.modMain.getWaypointsManager().getWaypoints() != null;
        super.drawScreen(par1, par2, par3);
        if (!this.modMain.getSettings().mapSafeMode && this.minimap.getMinimapFBORenderer().isTriedFBO() && !this.minimap.getMinimapFBORenderer().isLoadedFBO()) {
            this.drawCenteredString(this.fontRenderer, "\u00a74You've been forced into safe mode! :(", this.width / 2, 2, 0xFFFFFF);
            this.drawCenteredString(this.fontRenderer, "\u00a7cTurn off Video Settings - Performance - Fast Render.", this.width / 2, 11, 0xFFFFFF);
        } else {
            this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 5, 0xFFFFFF);
        }
    }
}

