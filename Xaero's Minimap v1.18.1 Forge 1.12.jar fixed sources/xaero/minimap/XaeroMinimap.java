//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  net.minecraftforge.fml.common.network.NetworkRegistry
 *  net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
 *  net.minecraftforge.fml.relauncher.Side
 */
package xaero.minimap;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.message.in.InMessageWaypoint;
import xaero.common.api.spigot.message.out.OutMessageHandshake;
import xaero.common.api.spigot.message.out.OutMessageWaypoint;
import xaero.common.controls.ControlsHandler;
import xaero.common.controls.event.KeyEventHandler;
import xaero.common.events.FMLEventHandler;
import xaero.common.events.ForgeEventHandler;
import xaero.common.gui.GuiHelper;
import xaero.common.interfaces.InterfaceManager;
import xaero.common.interfaces.render.InterfaceRenderer;
import xaero.common.minimap.waypoints.WaypointSharingHandler;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.misc.Internet;
import xaero.common.mods.SupportMods;
import xaero.common.settings.ModSettings;
import xaero.common.validator.FieldValidatorHolder;
import xaero.common.validator.NumericFieldValidator;
import xaero.minimap.controls.MinimapControlsHandler;
import xaero.minimap.gui.MinimapGuiHelper;
import xaero.minimap.interfaces.MinimapInterfaceLoader;
import xaero.patreon.Patreon4;
import xaero.patreon.PatreonMod2;

@Mod(modid="xaerominimap", name="Xaero's Minimap", version="1.18.1", clientSideOnly=true, acceptedMinecraftVersions="[1.12,1.12.2]")
public class XaeroMinimap
implements IXaeroMinimap {
    @Mod.Instance(value="xaerominimap")
    public static XaeroMinimap instance;
    private static final String versionID = "1.12_1.18.1";
    private int newestUpdateID;
    private boolean isOutdated = true;
    private String fileLayoutID = "1.12_1.18.1".endsWith("fair") ? "minimapfair" : "minimap";
    private String latestVersion;
    private static final File old_optionsFile;
    private static final File oldConfigFile;
    public static KeyBinding keyBindSettings;
    private ModSettings settings;
    private String message = "";
    private ControlsHandler controls;
    private ForgeEventHandler events;
    private FMLEventHandler fmlEvents;
    private InterfaceManager interfaces;
    private InterfaceRenderer interfaceRenderer;
    private GuiHelper guiHelper;
    private SupportMods supportMods;
    private WaypointsManager waypointsManager;
    private WaypointSharingHandler waypointSharing;
    private FieldValidatorHolder fieldValidators;
    private File modJAR = null;
    private File configFile;
    public File waypointsFile;
    public File waypointsFolder;
    private SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        Path waypointTempToAddFolder;
        if (event.getSourceFile().getName().endsWith(".jar")) {
            this.modJAR = event.getSourceFile();
        }
        Path config = event.getModConfigurationDirectory().toPath();
        this.waypointsFile = config.resolve("xaerowaypoints.txt").toFile();
        Path wrongWaypointsFolder3 = config.resolve("XaeroWaypoints");
        Path wrongWaypointsFolder2 = this.modJAR != null ? this.modJAR.toPath().getParent().resolve("XaeroWaypoints") : config.getParent().resolve("mods").resolve("XaeroWaypoints");
        this.waypointsFolder = config.getParent().resolve("XaeroWaypoints").toFile();
        if (wrongWaypointsFile.exists() && !this.waypointsFile.exists()) {
            Files.move(wrongWaypointsFile.toPath(), this.waypointsFile.toPath(), new CopyOption[0]);
        }
        if (wrongWaypointsFolder.exists() && !this.waypointsFolder.exists()) {
            Files.move(wrongWaypointsFolder.toPath(), this.waypointsFolder.toPath(), new CopyOption[0]);
        } else if (wrongWaypointsFolder2.toFile().exists() && !this.waypointsFolder.exists()) {
            Files.move(wrongWaypointsFolder2, this.waypointsFolder.toPath(), new CopyOption[0]);
        } else if (wrongWaypointsFolder3.toFile().exists() && !this.waypointsFolder.exists()) {
            Files.move(wrongWaypointsFolder3, this.waypointsFolder.toPath(), new CopyOption[0]);
        }
        this.configFile = config.resolve("xaerominimap.txt").toFile();
        if (oldConfigFile.exists() && !this.configFile.getAbsolutePath().equals(oldConfigFile.getAbsolutePath())) {
            Files.move(oldConfigFile.toPath(), this.configFile.toPath(), new CopyOption[0]);
        }
        if (Files.exists(waypointTempToAddFolder = this.waypointsFolder.toPath().resolve("temp_to_add"), new LinkOption[0])) {
            ModSettings.copyTempFilesBack(waypointTempToAddFolder);
        }
    }

    @Override
    public String getVersionsURL() {
        return "http://data.chocolateminecraft.com/Versions/Minimap.txt";
    }

    @Override
    public String getUpdateLink() {
        return "http://goo.gl/DsWDI5";
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) throws IOException {
        ClientRegistry.registerKeyBinding((KeyBinding)keyBindSettings);
        Patreon4.checkPatreon();
        Patreon4.rendersCapes = this.fileLayoutID;
        this.waypointsManager = new WaypointsManager(this, Minecraft.getMinecraft());
        this.waypointSharing = new WaypointSharingHandler(this);
        this.fieldValidators = new FieldValidatorHolder(new NumericFieldValidator());
        this.interfaceRenderer = new InterfaceRenderer(this);
        MinimapInterfaceLoader interfaceLoader = new MinimapInterfaceLoader();
        this.interfaces = new InterfaceManager(this, interfaceLoader);
        this.settings = new ModSettings(this){

            @Override
            public boolean isKeyRepeat(KeyBinding kb) {
                return kb != keyBindSettings && super.isKeyRepeat(kb);
            }
        };
        this.controls = new MinimapControlsHandler(this);
        if (old_optionsFile.exists() && !this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            Files.move(old_optionsFile.toPath(), this.configFile.toPath(), new CopyOption[0]);
        }
        if (old_waypointsFile.exists() && !this.waypointsFile.exists()) {
            this.waypointsFile.getParentFile().mkdirs();
            Files.move(old_waypointsFile.toPath(), this.waypointsFile.toPath(), new CopyOption[0]);
        }
    }

    @Mod.EventHandler
    public void load(FMLPostInitializationEvent event) throws IOException {
        this.settings.loadSettings();
        this.events = new ForgeEventHandler(this);
        KeyEventHandler keyEventHandler = new KeyEventHandler();
        this.fmlEvents = new FMLEventHandler(this, keyEventHandler);
        Internet.checkModVersion(this);
        if (Patreon4.patronPledge >= 5 && this.isOutdated) {
            this.getPatreon().modJar = this.modJAR;
            this.getPatreon().currentVersion = versionID;
            this.getPatreon().latestVersion = this.latestVersion;
            Patreon4.addOutdatedMod(this.getPatreon());
        }
        MinecraftForge.EVENT_BUS.register((Object)this.events);
        MinecraftForge.EVENT_BUS.register((Object)this.fmlEvents);
        this.guiHelper = new MinimapGuiHelper(this);
        this.supportMods = new SupportMods(this);
        this.network = NetworkRegistry.INSTANCE.newSimpleChannel("XaeroMinimap");
        this.network.registerMessage(InMessageWaypoint.Handler.class, InMessageWaypoint.class, 0, Side.CLIENT);
        this.network.registerMessage(OutMessageWaypoint.Handler.class, OutMessageWaypoint.class, 1, Side.SERVER);
        this.network.registerMessage(OutMessageHandshake.Handler.class, OutMessageHandshake.class, 2, Side.SERVER);
    }

    @Override
    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }

    @Override
    public File getOldOptionsFile() {
        return old_optionsFile;
    }

    @Override
    public File getOldConfigFile() {
        return oldConfigFile;
    }

    @Override
    public String getFileLayoutID() {
        return this.fileLayoutID;
    }

    @Override
    public File getConfigFile() {
        return this.configFile;
    }

    @Override
    public File getModJAR() {
        return this.modJAR;
    }

    @Override
    public ModSettings getSettings() {
        return this.settings;
    }

    @Override
    public void setSettings(ModSettings minimapSettings) {
        this.settings = minimapSettings;
    }

    @Override
    public void resetSettings() {
        this.settings = new ModSettings(this);
    }

    @Override
    public ControlsHandler getControls() {
        return this.controls;
    }

    @Override
    public SupportMods getSupportMods() {
        return this.supportMods;
    }

    @Override
    public InterfaceManager getInterfaces() {
        return this.interfaces;
    }

    @Override
    public ForgeEventHandler getEvents() {
        return this.events;
    }

    @Override
    public boolean isOutdated() {
        return this.isOutdated;
    }

    @Override
    public void setOutdated(boolean value) {
        this.isOutdated = value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String string) {
        this.message = string;
    }

    @Override
    public String getLatestVersion() {
        return this.latestVersion;
    }

    @Override
    public void setLatestVersion(String string) {
        this.latestVersion = string;
    }

    @Override
    public int getNewestUpdateID() {
        return this.newestUpdateID;
    }

    @Override
    public void setNewestUpdateID(int parseInt) {
        this.newestUpdateID = parseInt;
    }

    @Override
    public PatreonMod2 getPatreon() {
        return Patreon4.mods.get(this.fileLayoutID);
    }

    @Override
    public GuiHelper getGuiHelper() {
        return this.guiHelper;
    }

    @Override
    public String getVersionID() {
        return versionID;
    }

    @Override
    public KeyBinding getSettingsKey() {
        return keyBindSettings;
    }

    @Override
    public File getWaypointsFile() {
        return this.waypointsFile;
    }

    @Override
    public File getWaypointsFolder() {
        return this.waypointsFolder;
    }

    @Override
    public WaypointsManager getWaypointsManager() {
        return this.waypointsManager;
    }

    @Override
    public FieldValidatorHolder getFieldValidators() {
        return this.fieldValidators;
    }

    @Override
    public InterfaceRenderer getInterfaceRenderer() {
        return this.interfaceRenderer;
    }

    @Override
    public WaypointSharingHandler getWaypointSharing() {
        return this.waypointSharing;
    }

    static {
        old_optionsFile = new File("xaerominimap.txt");
        oldConfigFile = new File("config/xaerominimap.txt");
        keyBindSettings = new KeyBinding("gui.xaero_minimap_settings", 21, "Xaero's Minimap");
    }
}

