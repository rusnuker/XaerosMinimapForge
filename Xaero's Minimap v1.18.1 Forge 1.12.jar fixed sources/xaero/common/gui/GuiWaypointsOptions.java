//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.gui.GuiYesNoCallback
 *  net.minecraft.client.resources.I18n
 *  org.apache.commons.io.FileUtils
 */
package xaero.common.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import org.apache.commons.io.FileUtils;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiTransfer;
import xaero.common.gui.MySmallButton;
import xaero.common.gui.MyTinyButton;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.minimap.waypoints.WaypointsManager;

public class GuiWaypointsOptions
extends GuiScreen
implements GuiYesNoCallback {
    private GuiScreen parent;
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;
    private MySmallButton automaticButton;
    private MySmallButton subAutomaticButton;
    private MySmallButton deleteButton;
    private MySmallButton subDeleteButton;
    private boolean buttonTest;
    private WaypointWorld waypointWorld;

    public GuiWaypointsOptions(IXaeroMinimap modMain, GuiScreen parent, WaypointWorld waypointWorld) {
        this.parent = parent;
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
        this.waypointWorld = waypointWorld;
    }

    public void initGui() {
        this.parent.setWorldAndResolution(this.mc, this.width, this.height);
        this.buttonList.clear();
        this.buttonList.add(new MyTinyButton(5, this.width / 2 - 203, 32, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        this.buttonList.add(new MySmallButton(6, this.width / 2 - 203, 57, I18n.format((String)"gui.xaero_transfer", (Object[])new Object[0])));
        this.automaticButton = new MySmallButton(7, this.width / 2 - 203, 82, I18n.format((String)"gui.xaero_make_automatic", (Object[])new Object[0]));
        this.buttonList.add(this.automaticButton);
        this.subAutomaticButton = new MySmallButton(8, this.width / 2 - 203, 107, I18n.format((String)"gui.xaero_make_multi_automatic", (Object[])new Object[0]));
        this.buttonList.add(this.subAutomaticButton);
        this.deleteButton = new MySmallButton(9, this.width / 2 - 203, 132, I18n.format((String)"gui.xaero_delete_world", (Object[])new Object[0]));
        this.buttonList.add(this.deleteButton);
        this.subDeleteButton = new MySmallButton(10, this.width / 2 - 203, 157, I18n.format((String)"gui.xaero_delete_multi_world", (Object[])new Object[0]));
        this.buttonList.add(this.subDeleteButton);
        this.buttonList.add(new MySmallButton(11, this.width / 2 - 203, 182, I18n.format((String)"gui.xaero_multiply_all_by_8", (Object[])new Object[0])));
        this.buttonList.add(new MySmallButton(12, this.width / 2 - 203, 207, I18n.format((String)"gui.xaero_divide_all_by_8", (Object[])new Object[0])));
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        this.buttonTest = false;
        super.mouseClicked(par1, par2, par3);
        if (!this.buttonTest) {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    protected void actionPerformed(GuiButton button) {
        this.buttonTest = true;
        if (button.enabled) {
            switch (button.id) {
                case 5: {
                    this.mc.displayGuiScreen(this.parent);
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiTransfer(this.modMain, this.parent));
                    break;
                }
                case 7: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_make_automatic_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_make_automatic_msg2", (Object[])new Object[0]), button.id));
                    break;
                }
                case 8: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_make_multi_automatic_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_make_multi_automatic_msg2", (Object[])new Object[0]), button.id));
                    break;
                }
                case 9: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_delete_world_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_delete_world_msg2", (Object[])new Object[0]), button.id));
                    break;
                }
                case 10: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_delete_multi_world_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_delete_multi_world_msg2", (Object[])new Object[0]), button.id));
                    break;
                }
                case 11: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_multiply_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_multiply_msg2", (Object[])new Object[0]), button.id));
                    break;
                }
                case 12: {
                    this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_multiply_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_divide_msg2", (Object[])new Object[0]), button.id));
                }
            }
        }
    }

    public void confirmClicked(boolean result, int id) {
        block25: {
            if (!result) break block25;
            switch (id) {
                case 7: {
                    WaypointWorldContainer selected = this.waypointWorld.getContainer().getRootContainer();
                    WaypointWorldContainer auto = this.waypointsManager.getWaypointMap().get(this.waypointsManager.getAutoRootContainerID());
                    if (selected == null || auto == null) break;
                    String buKey = selected.getKey();
                    this.waypointsManager.getWaypointMap().put(auto.getKey(), selected);
                    this.waypointsManager.getWaypointMap().put(buKey, auto);
                    selected.setKey(auto.getKey());
                    auto.setKey(buKey);
                    Path selectedPath = selected.getDirectory().toPath();
                    Path autoPath = auto.getDirectory().toPath();
                    Path tempFolder = this.modMain.getWaypointsFolder().toPath().resolve("temp_to_add");
                    try {
                        Files.createDirectories(tempFolder, new FileAttribute[0]);
                        Path selectedTemp = tempFolder.resolve(selectedPath.getFileName());
                        Path autoTemp = tempFolder.resolve(autoPath.getFileName());
                        if (Files.exists(selectedPath, new LinkOption[0])) {
                            Files.move(selectedPath, selectedTemp, new CopyOption[0]);
                        }
                        if (Files.exists(autoPath, new LinkOption[0])) {
                            Files.move(autoPath, autoTemp, new CopyOption[0]);
                        }
                        if (Files.exists(selectedTemp, new LinkOption[0])) {
                            Files.move(selectedTemp, autoPath, new CopyOption[0]);
                        }
                        if (Files.exists(autoTemp, new LinkOption[0])) {
                            Files.move(autoTemp, selectedPath, new CopyOption[0]);
                        }
                        Files.deleteIfExists(tempFolder);
                    }
                    catch (Throwable e) {
                        this.modMain.getInterfaces().getMinimap().setCrashedWith(e);
                    }
                    this.waypointsManager.setCustomWorldID(null);
                    this.waypointsManager.setCustomContainerID(null);
                    this.waypointsManager.updateWaypoints();
                    break;
                }
                case 8: {
                    WaypointWorld autoWorld = this.waypointsManager.getAutoWorld();
                    WaypointWorld selectedWorld = this.waypointWorld;
                    try {
                        Path autoFile = this.modMain.getSettings().getWaypointsFile(autoWorld).toPath();
                        Path selectedFile = this.modMain.getSettings().getWaypointsFile(selectedWorld).toPath();
                        Path autoTempFile = autoFile.getParent().resolve("temp_to_add").resolve(autoFile.getFileName());
                        Path selectedTempFile = selectedFile.getParent().resolve("temp_to_add").resolve(selectedFile.getFileName());
                        Files.createDirectories(autoTempFile.getParent(), new FileAttribute[0]);
                        Files.createDirectories(selectedTempFile.getParent(), new FileAttribute[0]);
                        if (!Files.exists(autoFile, new LinkOption[0])) {
                            Files.createFile(autoFile, new FileAttribute[0]);
                        }
                        Files.move(autoFile, autoTempFile, new CopyOption[0]);
                        if (!Files.exists(selectedFile, new LinkOption[0])) {
                            Files.createFile(selectedFile, new FileAttribute[0]);
                        }
                        Files.move(selectedFile, selectedTempFile, new CopyOption[0]);
                        if (Files.exists(autoTempFile, new LinkOption[0])) {
                            Files.move(autoTempFile, selectedFile, new CopyOption[0]);
                        }
                        if (Files.exists(selectedTempFile, new LinkOption[0])) {
                            Files.move(selectedTempFile, autoFile, new CopyOption[0]);
                        }
                        Files.deleteIfExists(autoTempFile.getParent());
                        Files.deleteIfExists(selectedTempFile.getParent());
                    }
                    catch (Throwable e) {
                        this.modMain.getInterfaces().getMinimap().setCrashedWith(e);
                        break;
                    }
                    WaypointWorldContainer autoWc = autoWorld.getContainer();
                    WaypointWorldContainer selectedWc = selectedWorld.getContainer();
                    autoWorld.setContainer(selectedWc);
                    selectedWorld.setContainer(autoWc);
                    selectedWc.worlds.put(selectedWorld.getId(), autoWorld);
                    autoWc.worlds.put(autoWorld.getId(), selectedWorld);
                    String buSelected = selectedWorld.getId();
                    selectedWorld.setId(autoWorld.getId());
                    autoWorld.setId(buSelected);
                    this.waypointsManager.setCustomWorldID(null);
                    this.waypointsManager.setCustomContainerID(null);
                    this.waypointsManager.updateWaypoints();
                    break;
                }
                case 9: {
                    String selectedRootContainerId = this.waypointWorld.getContainer().getRootContainer().getKey();
                    try {
                        File directory = this.modMain.getWaypointsFolder().toPath().resolve(selectedRootContainerId).toFile();
                        if (directory.exists()) {
                            FileUtils.deleteDirectory((File)directory);
                        }
                    }
                    catch (Throwable e) {
                        this.modMain.getInterfaces().getMinimap().setCrashedWith(e);
                        break;
                    }
                    this.waypointsManager.getWaypointMap().remove(selectedRootContainerId);
                    this.waypointsManager.setCustomWorldID(null);
                    this.waypointsManager.setCustomContainerID(null);
                    this.waypointsManager.updateWaypoints();
                    break;
                }
                case 10: {
                    WaypointWorld selectedWorld = this.waypointWorld;
                    try {
                        Files.deleteIfExists(this.modMain.getSettings().getWaypointsFile(selectedWorld).toPath());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    selectedWorld.getContainer().worlds.remove(selectedWorld.getId());
                    selectedWorld.getContainer().removeName(selectedWorld.getId());
                    this.waypointsManager.setCustomWorldID(null);
                    this.waypointsManager.setCustomContainerID(null);
                    this.waypointsManager.updateWaypoints();
                    break;
                }
                case 11: {
                    this.multiplyWaypoints(this.waypointWorld, 8.0);
                    break;
                }
                case 12: {
                    this.multiplyWaypoints(this.waypointWorld, 0.125);
                }
            }
        }
        this.mc.displayGuiScreen(this.parent);
    }

    private void multiplyWaypoints(WaypointWorld world, double factor) {
        HashMap<String, WaypointSet> sets = world.getSets();
        Iterator<WaypointSet> iter = sets.values().iterator();
        while (iter.hasNext()) {
            ArrayList<Waypoint> wpList = iter.next().getList();
            for (int i = 0; i < wpList.size(); ++i) {
                Waypoint wp = wpList.get(i);
                wp.setX((int)Math.floor((double)wp.getX() * factor));
                wp.setZ((int)Math.floor((double)wp.getZ() * factor));
            }
        }
        try {
            this.modMain.getSettings().saveWaypoints(world);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.deleteButton.enabled = this.waypointsManager.getAutoRootContainerID() != null && !this.waypointsManager.getAutoRootContainerID().equals(this.waypointWorld.getContainer().getRootContainer().getKey());
        this.automaticButton.enabled = this.deleteButton.enabled;
        this.subDeleteButton.enabled = !this.automaticButton.enabled && this.waypointWorld != this.waypointsManager.getAutoWorld();
        this.subAutomaticButton.enabled = this.subDeleteButton.enabled;
        this.parent.drawScreen(0, 0, par3);
        this.drawDefaultBackground();
        super.drawScreen(par1, par2, par3);
    }
}

