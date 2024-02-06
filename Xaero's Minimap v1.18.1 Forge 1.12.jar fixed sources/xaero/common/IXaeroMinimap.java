/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
 */
package xaero.common;

import java.io.File;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import xaero.common.controls.ControlsHandler;
import xaero.common.events.ForgeEventHandler;
import xaero.common.gui.GuiHelper;
import xaero.common.interfaces.InterfaceManager;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.waypoints.WaypointSharingHandler;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.mods.SupportMods;
import xaero.common.settings.ModSettings;
import xaero.common.validator.FieldValidatorHolder;
import xaero.patreon.PatreonMod2;

public interface IXaeroMinimap {
    public static final File old_waypointsFile = new File("xaerowaypoints.txt");
    public static final File wrongWaypointsFile = new File("config/xaerowaypoints.txt");
    public static final File wrongWaypointsFolder = new File("mods/XaeroWaypoints");

    public File getOldOptionsFile();

    public File getOldConfigFile();

    public String getVersionID();

    public String getFileLayoutID();

    public File getConfigFile();

    public File getModJAR();

    public ModSettings getSettings();

    public void setSettings(ModSettings var1);

    public ControlsHandler getControls();

    public SupportMods getSupportMods();

    public InterfaceManager getInterfaces();

    public InterfaceRenderer getInterfaceRenderer();

    public ForgeEventHandler getEvents();

    public boolean isOutdated();

    public void setOutdated(boolean var1);

    public String getMessage();

    public void setMessage(String var1);

    public String getLatestVersion();

    public void setLatestVersion(String var1);

    public int getNewestUpdateID();

    public void setNewestUpdateID(int var1);

    public PatreonMod2 getPatreon();

    public GuiHelper getGuiHelper();

    public String getVersionsURL();

    public void resetSettings();

    public String getUpdateLink();

    public KeyBinding getSettingsKey();

    public SimpleNetworkWrapper getNetwork();

    public File getWaypointsFile();

    public File getWaypointsFolder();

    public WaypointsManager getWaypointsManager();

    public WaypointSharingHandler getWaypointSharing();

    public FieldValidatorHolder getFieldValidators();
}

