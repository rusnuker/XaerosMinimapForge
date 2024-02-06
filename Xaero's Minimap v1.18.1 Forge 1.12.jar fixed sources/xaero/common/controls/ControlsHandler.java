//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 */
package xaero.common.controls;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiAddWaypoint;
import xaero.common.gui.GuiSlimeSeed;
import xaero.common.gui.GuiWaypoints;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.misc.OptimizedMath;
import xaero.common.settings.ModOptions;
import xaero.common.settings.ModSettings;

public class ControlsHandler {
    protected IXaeroMinimap modMain;
    protected MinimapProcessor minimap;
    protected WaypointsManager waypointsManager;
    public static final KeyBinding[] toAdd = new KeyBinding[]{ModSettings.keySwitchSet, ModSettings.keyInstantWaypoint, ModSettings.keyToggleSlimes, ModSettings.keyToggleGrid, ModSettings.keyToggleWaypoints, ModSettings.keyToggleMap, ModSettings.keyLargeMap, ModSettings.keyWaypoints, ModSettings.keyBindZoom, ModSettings.keyBindZoom1, ModSettings.newWaypoint, ModSettings.keyAllSets};

    public ControlsHandler(IXaeroMinimap modMain) {
        this.modMain = modMain;
        this.minimap = modMain.getInterfaces().getMinimap();
        this.waypointsManager = modMain.getWaypointsManager();
        for (KeyBinding kb : toAdd) {
            ClientRegistry.registerKeyBinding((KeyBinding)kb);
        }
    }

    public void setKeyState(KeyBinding kb, boolean pressed) {
        KeyBinding.setKeyBindState((int)kb.getKeyCode(), (boolean)pressed);
    }

    public boolean isDown(KeyBinding kb) {
        return GameSettings.isKeyDown((KeyBinding)kb);
    }

    public void keyDownPre(KeyBinding kb) {
    }

    public void keyDownPost(KeyBinding kb) {
    }

    public void keyDown(KeyBinding kb, boolean tickEnd, boolean isRepeat) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!tickEnd) {
            WaypointWorld currentWorld;
            this.keyDownPre(kb);
            if (kb == ModSettings.newWaypoint && this.modMain.getSettings().waypointsGUI()) {
                mc.displayGuiScreen((GuiScreen)new GuiAddWaypoint(this.modMain, null, null, this.waypointsManager.getCurrentContainerID().split("/")[0], this.waypointsManager.getCurrentWorld()));
            }
            if (kb == ModSettings.keyWaypoints && this.modMain.getSettings().waypointsGUI()) {
                mc.displayGuiScreen((GuiScreen)new GuiWaypoints(this.modMain, null));
            }
            if (kb == ModSettings.keyLargeMap) {
                this.minimap.setEnlargedMap(true);
                this.minimap.setToResetImage(true);
            }
            if (kb == ModSettings.keyToggleMap) {
                try {
                    this.modMain.getSettings().setOptionValue(ModOptions.MINIMAP, 0);
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyToggleWaypoints) {
                try {
                    this.modMain.getSettings().setOptionValue(ModOptions.INGAME_WAYPOINTS, 0);
                    this.modMain.getSettings().setOptionValue(ModOptions.WAYPOINTS, 0);
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyToggleSlimes) {
                try {
                    if (this.modMain.getSettings().customSlimeSeedNeeded() && this.modMain.getSettings().getBooleanValue(ModOptions.OPEN_SLIME_SETTINGS)) {
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiSlimeSeed(this.modMain, Minecraft.getMinecraft().currentScreen));
                    } else {
                        this.modMain.getSettings().slimeChunks = !this.modMain.getSettings().slimeChunks;
                        this.modMain.getSettings().saveSettings();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyToggleGrid) {
                try {
                    this.modMain.getSettings().chunkGrid = -this.modMain.getSettings().chunkGrid - 1;
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyInstantWaypoint && this.modMain.getSettings().waypointsGUI() && this.waypointsManager.getWaypoints() != null) {
                boolean divideBy8 = this.waypointsManager.divideBy8(this.waypointsManager.getCurrentContainerID());
                int x = OptimizedMath.myFloor(mc.player.posX) * (divideBy8 ? 8 : 1);
                int z = OptimizedMath.myFloor(mc.player.posZ) * (divideBy8 ? 8 : 1);
                Waypoint instant = new Waypoint(x, OptimizedMath.myFloor(mc.player.posY), z, "Waypoint", "X", (int)(Math.random() * (double)ModSettings.ENCHANT_COLORS.length), 0, true);
                this.waypointsManager.getWaypoints().getList().add(instant);
                try {
                    this.modMain.getSettings().saveWaypoints(this.waypointsManager.getCurrentWorld());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keySwitchSet && (currentWorld = this.waypointsManager.getCurrentWorld()) != null) {
                String[] keys = currentWorld.getSets().keySet().toArray(new String[0]);
                for (int i = 0; i < keys.length; ++i) {
                    if (keys[i] == null || !keys[i].equals(currentWorld.getCurrent())) continue;
                    currentWorld.setCurrent(keys[(i + 1) % keys.length]);
                    break;
                }
                this.waypointsManager.updateWaypoints();
                this.waypointsManager.setChanged = System.currentTimeMillis();
                try {
                    this.modMain.getSettings().saveWaypoints(currentWorld);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyAllSets) {
                this.waypointsManager.renderAllSets = !this.waypointsManager.renderAllSets;
            }
            this.keyDownPost(kb);
        }
    }

    public void keyUpPre(KeyBinding kb) {
    }

    public void keyUpPost(KeyBinding kb) {
    }

    public void keyUp(KeyBinding kb, boolean tickEnd) {
        if (!tickEnd) {
            this.keyUpPre(kb);
            if (kb == ModSettings.keyBindZoom) {
                try {
                    this.modMain.getSettings().setOptionValue(ModOptions.ZOOM, 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyBindZoom1) {
                --this.modMain.getSettings().zoom;
                if (this.modMain.getSettings().zoom == -1) {
                    this.modMain.getSettings().zoom = this.modMain.getSettings().zooms.length - 1;
                }
                try {
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (kb == ModSettings.keyLargeMap) {
                this.minimap.setEnlargedMap(false);
                this.minimap.setToResetImage(true);
            }
            this.keyUpPost(kb);
        }
    }
}

