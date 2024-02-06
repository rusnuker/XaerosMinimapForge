//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSlot
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Mouse
 */
package xaero.common.gui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.ServerWaypoint;
import xaero.common.gui.GuiAddWaypoint;
import xaero.common.gui.GuiClearSet;
import xaero.common.gui.GuiDeleteSet;
import xaero.common.gui.GuiDropDown;
import xaero.common.gui.GuiNewSet;
import xaero.common.gui.GuiWaypointContainers;
import xaero.common.gui.GuiWaypointSets;
import xaero.common.gui.GuiWaypointWorlds;
import xaero.common.gui.GuiWaypointsOptions;
import xaero.common.gui.IDropDownCallback;
import xaero.common.gui.MyTinyButton;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;

@SideOnly(value=Side.CLIENT)
public class GuiWaypoints
extends GuiScreen
implements IDropDownCallback {
    private static final int FRAME_TOP_SIZE = 58;
    private static final int FRAME_BOTTOM_SIZE = 61;
    private GuiScreen parentScreen;
    private List list;
    private WaypointWorld displayedWorld;
    private Waypoint selected;
    private ArrayList<GuiDropDown> dropDowns;
    private GuiWaypointContainers containers;
    private GuiWaypointWorlds worlds;
    private GuiWaypointSets sets;
    private GuiDropDown containersDD;
    private GuiDropDown worldsDD;
    private GuiDropDown setsDD;
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;
    private int draggingFromX;
    private int draggingFromY;
    private int draggingFromSlot;
    private Waypoint draggingWaypoint;
    private boolean dropped = false;
    private boolean displayingTeleportableWorld;

    public GuiWaypoints(IXaeroMinimap modMain, GuiScreen par1GuiScreen) {
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
        this.parentScreen = par1GuiScreen;
        this.dropDowns = new ArrayList();
        this.draggingFromX = -1;
        this.draggingFromY = -1;
        this.draggingFromSlot = -1;
    }

    public void initGui() {
        this.displayedWorld = this.waypointsManager.getCurrentWorld();
        String currentContainer = this.displayedWorld.getContainer().getRootContainer().getKey();
        this.containers = new GuiWaypointContainers(this.modMain, this.waypointsManager, currentContainer);
        this.worlds = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers.getCurrentKey()), this.waypointsManager, this.displayedWorld.getFullId());
        this.sets = new GuiWaypointSets(true, this.displayedWorld);
        this.displayingTeleportableWorld = this.isWorldTeleportable();
        this.buttonList.clear();
        this.buttonList.add(new MyTinyButton(5, this.width / 2 + 129, this.height - 53, I18n.format((String)"gui.xaero_delete", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height - 29, I18n.format((String)"gui.done", (Object[])new Object[0])));
        this.buttonList.add(new MyTinyButton(7, this.width / 2 - 203, this.height - 53, I18n.format((String)"gui.xaero_add_edit", (Object[])new Object[0])));
        this.buttonList.add(new MyTinyButton(8, this.width / 2 - 120, this.height - 53, I18n.format((String)"gui.xaero_waypoint_teleport", (Object[])new Object[0]) + " (T)"));
        this.buttonList.add(new MyTinyButton(9, this.width / 2 + 46, this.height - 53, I18n.format((String)"gui.xaero_disable_enable", (Object[])new Object[0])));
        this.buttonList.add(new MyTinyButton(10, this.width / 2 + 130, 32, I18n.format((String)"gui.xaero_clear", (Object[])new Object[0])));
        this.buttonList.add(new MyTinyButton(11, this.width / 2 - 203, 32, I18n.format((String)"gui.xaero_options", (Object[])new Object[0])));
        this.buttonList.add(new MyTinyButton(12, this.width / 2 - 37, this.height - 53, I18n.format((String)"gui.xaero_share", (Object[])new Object[0])));
        this.list = new List();
        this.list.registerScrollButtons(7, 8);
        this.dropDowns.clear();
        this.containersDD = new GuiDropDown(this.containers.options, this.width / 2 - 202, 17, 200, this.containers.current, this);
        this.dropDowns.add(this.containersDD);
        this.worldsDD = new GuiDropDown(this.worlds.options, this.width / 2 + 2, 17, 200, this.worlds.current, this);
        this.dropDowns.add(this.worldsDD);
        this.setsDD = new GuiDropDown(this.sets.getOptions(), this.width / 2 - 100, 33, 200, this.sets.getCurrentSet(), this);
        this.dropDowns.add(this.setsDD);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 5: {
                    this.undrag();
                    this.displayedWorld.getCurrentSet().getList().remove(this.selected);
                    this.list.setSelected(null);
                    try {
                        this.modMain.getSettings().saveWaypoints(this.displayedWorld);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.parentScreen);
                    break;
                }
                case 7: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiAddWaypoint(this.modMain, this, this.selected, this.displayedWorld.getContainer().getRootContainer().getKey(), this.displayedWorld));
                    this.list.setSelected(null);
                    break;
                }
                case 8: {
                    this.displayingTeleportableWorld = this.isWorldTeleportable();
                    if (this.selected == null || !this.modMain.getSettings().allowWrongWorldTeleportation && !this.displayingTeleportableWorld) break;
                    boolean divideBy8 = this.waypointsManager.divideBy8(this.displayedWorld.getContainer().getKey());
                    int x = Math.floorDiv(this.selected.getX(), divideBy8 ? 8 : 1);
                    int z = Math.floorDiv(this.selected.getZ(), divideBy8 ? 8 : 1);
                    if (!this.selected.isRotation()) {
                        this.sendChatMessage("/" + this.modMain.getSettings().waypointTp + " " + x + " " + this.selected.getY() + " " + z, false);
                    } else {
                        this.sendChatMessage("/" + this.modMain.getSettings().waypointTp + " " + x + " " + this.selected.getY() + " " + z + " " + this.selected.getYaw() + " ~", false);
                    }
                    this.mc.displayGuiScreen(null);
                    break;
                }
                case 9: {
                    this.selected.setDisabled(!this.selected.isDisabled());
                    try {
                        this.modMain.getSettings().saveWaypoints(this.displayedWorld);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 10: {
                    String[] worldKeys = this.worlds.getCurrentKeys();
                    if (this.shouldDeleteSet()) {
                        this.mc.displayGuiScreen((GuiScreen)new GuiDeleteSet(this.modMain, I18n.format((String)this.sets.getOptions()[this.sets.getCurrentSet()], (Object[])new Object[0]), worldKeys[0], worldKeys[1], this.sets.getOptions()[this.sets.getCurrentSet()], this));
                        break;
                    }
                    this.mc.displayGuiScreen((GuiScreen)new GuiClearSet(this.modMain, I18n.format((String)this.sets.getOptions()[this.sets.getCurrentSet()], (Object[])new Object[0]), worldKeys[0], worldKeys[1], this.sets.getOptions()[this.sets.getCurrentSet()], this));
                    break;
                }
                case 11: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiWaypointsOptions(this.modMain, this, this.displayedWorld));
                    break;
                }
                case 12: {
                    if (this.selected == null) break;
                    this.modMain.getWaypointSharing().shareWaypoint(this, this.selected, this.displayedWorld);
                }
            }
        }
    }

    public boolean shouldDeleteSet() {
        return !this.sets.getOptions()[this.sets.getCurrentSet()].equals("gui.xaero_default") && this.displayedWorld.getCurrentSet().getList().isEmpty();
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getEventDWheel() / 120;
        if (wheel != 0) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            int mouseXScaled = Mouse.getX() / scaledResolution.getScaleFactor();
            int mouseYScaled = scaledResolution.getScaledHeight() - 1 - Mouse.getY() / scaledResolution.getScaleFactor();
            for (GuiDropDown d : this.dropDowns) {
                if (d.isClosed() || !d.onDropDown(mouseXScaled, mouseYScaled, this.height)) continue;
                d.mouseScrolled(wheel, mouseXScaled, mouseYScaled, this.height);
                return;
            }
        }
        this.list.handleMouseInput();
    }

    private void undrag() {
        this.draggingFromX = -1;
        this.draggingFromY = -1;
        this.draggingFromSlot = -1;
        this.draggingWaypoint = null;
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
        if (par3 == 0) {
            if (par2 >= 58 && par2 < this.height - 61) {
                this.draggingFromX = par1;
                this.draggingFromY = par2;
                this.draggingFromSlot = this.list.getSlotIndexFromScreenCoords(par1, par2);
                if (this.draggingFromSlot >= this.displayedWorld.getCurrentSet().getList().size()) {
                    this.draggingFromSlot = -1;
                }
            }
        } else {
            this.list.setSelected(null);
        }
        super.mouseClicked(par1, par2, par3);
    }

    protected void mouseReleased(int par1, int par2, int par3) {
        try {
            if (this.draggingWaypoint != null) {
                this.modMain.getSettings().saveWaypoints(this.displayedWorld);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.undrag();
        super.mouseReleased(par1, par2, par3);
        for (GuiDropDown d : this.dropDowns) {
            d.mouseReleased(par1, par2, par3, this.height);
        }
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        switch (par2) {
            case 211: {
                this.actionPerformed((GuiButton)this.buttonList.get(0));
                break;
            }
            case 20: {
                this.actionPerformed((GuiButton)this.buttonList.get(3));
            }
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        int k;
        if (this.mc.player == null) {
            this.mc.displayGuiScreen(null);
            return;
        }
        this.updateButtons();
        this.list.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_world_server", (Object[])new Object[0]), this.width / 2 - 102, 5, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_subworld_dimension", (Object[])new Object[0]), this.width / 2 + 102, 5, 0xFFFFFF);
        if (this.draggingFromSlot != -1) {
            int distance = (int)Math.sqrt(Math.pow(par1 - this.draggingFromX, 2.0) + Math.pow(par2 - this.draggingFromY, 2.0));
            int toSlot = Math.min(this.displayedWorld.getCurrentSet().getList().size() - 1, this.list.getSlotIndexFromScreenCoords(par1, par2));
            if (distance > 4 && this.draggingWaypoint == null) {
                this.draggingWaypoint = this.displayedWorld.getCurrentSet().getList().get(this.draggingFromSlot);
                this.list.setSelected(null);
            }
            if (this.draggingWaypoint != null && this.draggingFromSlot != toSlot && toSlot != -1) {
                int direction = toSlot > this.draggingFromSlot ? 1 : -1;
                for (int i = this.draggingFromSlot; i != toSlot; i += direction) {
                    this.displayedWorld.getCurrentSet().getList().set(i, this.displayedWorld.getCurrentSet().getList().get(i + direction));
                }
                this.displayedWorld.getCurrentSet().getList().set(toSlot, this.draggingWaypoint);
                this.draggingFromSlot = toSlot;
            }
            int fromCenter = this.draggingFromX - this.list.width / 2;
            this.list.drawWaypointSlot(this.draggingWaypoint, par1 - 108 - fromCenter, par2 - this.list.slotHeight / 4);
        }
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

    private void updateButtons() {
        ((GuiButton)this.buttonList.get((int)0)).enabled = this.selected != null && !(this.selected instanceof ServerWaypoint);
        ((GuiButton)this.buttonList.get((int)7)).enabled = this.selected != null;
        ((GuiButton)this.buttonList.get((int)4)).enabled = ((GuiButton)this.buttonList.get((int)7)).enabled;
        ((GuiButton)this.buttonList.get((int)3)).enabled = this.selected != null && (this.modMain.getSettings().allowWrongWorldTeleportation || this.displayingTeleportableWorld);
        ((GuiButton)this.buttonList.get((int)2)).enabled = !(this.selected instanceof ServerWaypoint) && (this.mc.player != null || this.selected != null);
        ((GuiButton)this.buttonList.get((int)5)).displayString = I18n.format((String)(this.shouldDeleteSet() ? "gui.xaero_delete_set" : "gui.xaero_clear"), (Object[])new Object[0]);
        String[] enabledisable = I18n.format((String)"gui.xaero_disable_enable", (Object[])new Object[0]).split("/");
        ((GuiButton)this.buttonList.get((int)4)).displayString = enabledisable[this.selected == null || !this.selected.isDisabled() ? 0 : 1];
    }

    public GuiScreen getParentScreen() {
        return this.parentScreen;
    }

    @Override
    public void onSelected(GuiDropDown menu, int selectedIndex) {
        if (menu == this.containersDD || menu == this.worldsDD) {
            if (menu == this.containersDD) {
                this.containers.current = selectedIndex;
                if (this.containers.current != this.containers.auto) {
                    WaypointWorld firstWorld = this.waypointsManager.getWorldContainer(this.containers.getCurrentKey()).getFirstWorld();
                    this.waypointsManager.setCustomContainerID(firstWorld.getContainer().getKey());
                    this.waypointsManager.setCustomWorldID(firstWorld.getId());
                } else {
                    this.waypointsManager.setCustomContainerID(null);
                    this.waypointsManager.setCustomWorldID(null);
                }
                this.displayedWorld = this.waypointsManager.getCurrentWorld();
                this.worlds = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers.getCurrentKey()), this.waypointsManager, this.displayedWorld.getFullId());
                this.worldsDD = new GuiDropDown(this.worlds.options, this.width / 2 + 2, 17, 200, this.worlds.current, this);
                this.dropDowns.set(1, this.worldsDD);
            } else if (menu == this.worldsDD) {
                this.worlds.current = selectedIndex;
                if (this.worlds.current != this.worlds.auto) {
                    String[] keys = this.worlds.getCurrentKeys();
                    this.waypointsManager.setCustomContainerID(keys[0]);
                    this.waypointsManager.setCustomWorldID(keys[1]);
                } else {
                    this.waypointsManager.setCustomContainerID(null);
                    this.waypointsManager.setCustomWorldID(null);
                }
                this.displayedWorld = this.waypointsManager.getCurrentWorld();
            }
            this.displayingTeleportableWorld = this.isWorldTeleportable();
            this.waypointsManager.updateWaypoints();
            this.list.setSelected(null);
            this.sets = new GuiWaypointSets(true, this.displayedWorld);
            this.setsDD = new GuiDropDown(this.sets.getOptions(), this.width / 2 - 100, 33, 200, this.sets.getCurrentSet(), this);
            this.dropDowns.set(2, this.setsDD);
        } else if (menu == this.setsDD) {
            if (selectedIndex == menu.size() - 1) {
                System.out.println("New waypoint set gui");
                menu.selectValue(this.sets.getCurrentSet());
                this.mc.displayGuiScreen((GuiScreen)new GuiNewSet(this.modMain, this, this.displayedWorld));
                return;
            }
            this.sets.setCurrentSet(selectedIndex);
            this.displayedWorld.setCurrent(this.sets.getCurrentSetKey());
            this.waypointsManager.updateWaypoints();
            this.list.setSelected(null);
            try {
                this.modMain.getSettings().saveWaypoints(this.displayedWorld);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isWorldTeleportable() {
        return this.containers.getCurrentKey().equals(this.waypointsManager.getAutoRootContainerID()) && this.displayedWorld == this.waypointsManager.getAutoWorld();
    }

    @SideOnly(value=Side.CLIENT)
    class List
    extends GuiSlot {
        public List() {
            super(GuiWaypoints.this.mc, GuiWaypoints.this.width, GuiWaypoints.this.height, 58, Math.max(62, GuiWaypoints.this.height - 61), 18);
        }

        protected int getSize() {
            int size = GuiWaypoints.this.displayedWorld.getCurrentSet().getList().size();
            if (GuiWaypoints.this.waypointsManager.getServerWaypoints() != null) {
                size += GuiWaypoints.this.waypointsManager.getServerWaypoints().size();
            }
            return size;
        }

        private Waypoint getWaypoint(int slotIndex) {
            int serverWPIndex;
            Waypoint waypoint = null;
            if (slotIndex < GuiWaypoints.this.displayedWorld.getCurrentSet().getList().size()) {
                waypoint = GuiWaypoints.this.displayedWorld.getCurrentSet().getList().get(slotIndex);
            } else if (GuiWaypoints.this.waypointsManager.getServerWaypoints() != null && (serverWPIndex = slotIndex - GuiWaypoints.this.displayedWorld.getCurrentSet().getList().size()) < GuiWaypoints.this.waypointsManager.getServerWaypoints().size()) {
                waypoint = GuiWaypoints.this.waypointsManager.getServerWaypoints().get(serverWPIndex);
            }
            return waypoint;
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            Waypoint waypoint = this.getWaypoint(slotIndex);
            if (GuiWaypoints.this.selected != waypoint) {
                GuiWaypoints.this.selected = waypoint;
            }
        }

        public void setSelected(Waypoint w) {
            GuiWaypoints.this.selected = w;
        }

        protected boolean isSelected(int slotIndex) {
            return GuiWaypoints.this.selected != null && GuiWaypoints.this.selected == this.getWaypoint(slotIndex);
        }

        protected int getContentHeight() {
            return this.getSize() * 18;
        }

        protected void drawBackground() {
            GuiWaypoints.this.drawDefaultBackground();
        }

        public void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
            Waypoint w = this.getWaypoint(slotIndex);
            if (w == GuiWaypoints.this.draggingWaypoint) {
                return;
            }
            this.drawWaypointSlot(w, xPos, yPos);
        }

        public void drawWaypointSlot(Waypoint w, int p_180791_2_, int p_180791_3_) {
            if (w == null) {
                return;
            }
            GuiWaypoints.this.drawCenteredString(GuiWaypoints.this.fontRenderer, w.getLocalizedName() + (w.isDisabled() ? " \u00a74" + I18n.format((String)"gui.xaero_disabled", (Object[])new Object[0]) : ""), p_180791_2_ + 110, p_180791_3_ + 1, 0xFFFFFF);
            int rectX = p_180791_2_ + 8;
            int rectY = p_180791_3_;
            GuiWaypoints.this.modMain.getInterfaces().getMinimapInterface().getWaypointsGuiRenderer().drawIconOnGUI(w, GuiWaypoints.this.modMain.getSettings(), rectX, rectY);
        }

        public boolean getEnabled() {
            if (GuiWaypoints.this.dropped || GuiWaypoints.this.draggingWaypoint != null) {
                return false;
            }
            return super.getEnabled();
        }
    }
}

