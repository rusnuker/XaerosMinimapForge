//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.input.Mouse
 */
package xaero.common.gui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiDropDown;
import xaero.common.gui.GuiWaypointContainers;
import xaero.common.gui.GuiWaypointWorlds;
import xaero.common.gui.GuiWaypoints;
import xaero.common.gui.IDropDownCallback;
import xaero.common.gui.MySmallButton;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;

public class GuiTransfer
extends GuiScreen
implements IDropDownCallback {
    private GuiScreen parentScreen;
    private MySmallButton transferButton;
    private ArrayList<GuiDropDown> dropDowns = new ArrayList();
    private GuiWaypointContainers containers1;
    private GuiWaypointWorlds worlds1;
    private GuiWaypointContainers containers2;
    private GuiWaypointWorlds worlds2;
    private GuiDropDown containers1DD;
    private GuiDropDown worlds1DD;
    private GuiDropDown containers2DD;
    private GuiDropDown worlds2DD;
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;
    private boolean dropped = false;

    public GuiTransfer(IXaeroMinimap modMain, GuiScreen par1) {
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
        this.parentScreen = par1;
        String currentContainer = this.waypointsManager.getCurrentContainerID().split("/")[0];
        String currentWorld = this.waypointsManager.getCurrentContainerAndWorldID();
        this.containers1 = new GuiWaypointContainers(modMain, this.waypointsManager, currentContainer);
        this.containers2 = new GuiWaypointContainers(modMain, this.waypointsManager, currentContainer);
        this.worlds1 = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers1.getCurrentKey()), this.waypointsManager, currentWorld);
        this.worlds2 = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers2.getCurrentKey()), this.waypointsManager, currentWorld);
    }

    public void initGui() {
        this.buttonList.clear();
        this.transferButton = new MySmallButton(5, this.width / 2 - 155, this.height / 7 + 120, I18n.format((String)"gui.xaero_transfer", (Object[])new Object[0]));
        this.buttonList.add(this.transferButton);
        this.transferButton.enabled = false;
        this.buttonList.add(new MySmallButton(6, this.width / 2 + 5, this.height / 7 + 120, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        this.dropDowns.clear();
        this.worlds1DD = new GuiDropDown(this.worlds1.options, this.width / 2 + 2, this.height / 7 + 20, 200, this.worlds1.current, this);
        this.dropDowns.add(this.worlds1DD);
        this.worlds2DD = new GuiDropDown(this.worlds2.options, this.width / 2 + 2, this.height / 7 + 50, 200, this.worlds2.current, this);
        this.dropDowns.add(this.worlds2DD);
        this.containers1DD = new GuiDropDown(this.containers1.options, this.width / 2 - 202, this.height / 7 + 20, 200, this.containers1.current, this);
        this.dropDowns.add(this.containers1DD);
        this.containers2DD = new GuiDropDown(this.containers2.options, this.width / 2 - 202, this.height / 7 + 50, 200, this.containers2.current, this);
        this.dropDowns.add(this.containers2DD);
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        for (GuiDropDown d : this.dropDowns) {
            if (!d.isClosed() && d.onDropDown(par1, par2, this.height)) {
                d.mouseClicked(par1, par2, par3, this.height);
                return;
            }
            d.setClosed(true);
        }
        for (GuiDropDown d : this.dropDowns) {
            if (d.onDropDown(par1, par2, this.height)) {
                d.mouseClicked(par1, par2, par3, this.height);
                return;
            }
            d.setClosed(true);
        }
        if (this.dropped) {
            return;
        }
        super.mouseClicked(par1, par2, par3);
    }

    protected void mouseReleased(int par1, int par2, int par3) {
        super.mouseReleased(par1, par2, par3);
        for (GuiDropDown d : this.dropDowns) {
            d.mouseReleased(par1, par2, par3, this.height);
        }
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 5: {
                    this.transfer();
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.parentScreen);
                }
            }
        }
    }

    public void transfer() {
        try {
            String[] keys1 = this.worlds1.getCurrentKeys();
            String[] keys2 = this.worlds2.getCurrentKeys();
            WaypointWorld from = this.waypointsManager.getWorld(keys1[0], keys1[1]);
            WaypointWorld to = this.waypointsManager.getWorld(keys2[0], keys2[1]);
            Object[] keys = from.getSets().keySet().toArray();
            Object[] values = from.getSets().values().toArray();
            for (int i = 0; i < keys.length; ++i) {
                String setName = (String)keys[i];
                WaypointSet fromSet = (WaypointSet)values[i];
                WaypointSet toSet = to.getSets().get(setName);
                if (toSet == null) {
                    toSet = new WaypointSet(setName);
                }
                ArrayList<Waypoint> list = fromSet.getList();
                for (int j = 0; j < list.size(); ++j) {
                    Waypoint w = list.get(j);
                    Waypoint copy = new Waypoint(w.getX(), w.getY(), w.getZ(), w.getName(), w.getSymbol(), w.getColor(), w.getType());
                    copy.setRotation(w.isRotation());
                    copy.setDisabled(w.isDisabled());
                    copy.setYaw(w.getYaw());
                    toSet.getList().add(copy);
                }
                to.getSets().put(setName, toSet);
            }
            if (keys2[0] != null && !keys2[0].equals(this.waypointsManager.getCustomContainerID())) {
                this.waypointsManager.setCustomContainerID(keys2[0]);
            }
            if (keys2[1] != null && !keys2[1].equals(this.waypointsManager.getCustomWorldID())) {
                this.waypointsManager.setCustomWorldID(keys2[1]);
            }
            this.waypointsManager.updateWaypoints();
            if (this.parentScreen instanceof GuiWaypoints) {
                this.mc.displayGuiScreen((GuiScreen)new GuiWaypoints(this.modMain, ((GuiWaypoints)this.parentScreen).getParentScreen()));
            }
            this.modMain.getSettings().saveWaypoints(to);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        int k;
        super.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_transfer_all", (Object[])new Object[0]), this.width / 2, 5, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_from", (Object[])new Object[0]).replace("\u00a7\u00a7", ":") + ":", this.width / 2, this.height / 7 + 10, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_to", (Object[])new Object[0]).replace("\u00a7\u00a7", ":") + ":", this.width / 2, this.height / 7 + 40, 0xFFFFFF);
        if (this.dropped) {
            super.drawScreen(0, 0, par3);
        } else {
            super.drawScreen(par1, par2, par3);
        }
        this.dropped = false;
        for (k = 0; k < this.dropDowns.size(); ++k) {
            if (this.dropDowns.get(k).isClosed()) {
                this.dropDowns.get(k).drawButton(par1, par2, this.height);
                continue;
            }
            this.dropped = true;
        }
        for (k = 0; k < this.dropDowns.size(); ++k) {
            if (this.dropDowns.get(k).isClosed()) continue;
            this.dropDowns.get(k).drawButton(par1, par2, this.height);
        }
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getEventDWheel() / 120;
        if (wheel != 0) {
            ScaledResolution var3 = new ScaledResolution(this.mc);
            int mouseXScaled = Mouse.getX() / var3.getScaleFactor();
            int mouseYScaled = var3.getScaledHeight() - 1 - Mouse.getY() / var3.getScaleFactor();
            for (GuiDropDown d : this.dropDowns) {
                if (d.isClosed() || !d.onDropDown(mouseXScaled, mouseYScaled, this.height)) continue;
                d.mouseScrolled(wheel, mouseXScaled, mouseYScaled, this.height);
                return;
            }
        }
    }

    @Override
    public void onSelected(GuiDropDown menu, int selected) {
        if (menu == this.containers1DD) {
            this.containers1.current = selected;
            this.worlds1 = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers1.getCurrentKey()), this.waypointsManager, this.waypointsManager.getCurrentContainerAndWorldID());
            this.worlds1DD = new GuiDropDown(this.worlds1.options, this.width / 2 + 2, this.height / 7 + 20, 200, this.worlds1.current, this);
            this.dropDowns.set(0, this.worlds1DD);
        } else if (menu == this.containers2DD) {
            this.containers2.current = selected;
            this.worlds2 = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers2.getCurrentKey()), this.waypointsManager, this.waypointsManager.getCurrentContainerAndWorldID());
            this.worlds2DD = new GuiDropDown(this.worlds2.options, this.width / 2 + 2, this.height / 7 + 50, 200, this.worlds2.current, this);
            this.dropDowns.set(1, this.worlds2DD);
        } else if (menu == this.worlds1DD) {
            this.worlds1.current = selected;
        } else if (menu == this.worlds2DD) {
            this.worlds2.current = selected;
        }
        this.transferButton.enabled = this.containers1.current != this.containers2.current || this.worlds1.current != this.worlds2.current;
    }
}

