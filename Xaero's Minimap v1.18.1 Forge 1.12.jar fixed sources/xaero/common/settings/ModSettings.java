//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.server.integrated.IntegratedServer
 *  net.minecraft.util.ResourceLocation
 */
package xaero.common.settings;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import xaero.common.IXaeroMinimap;
import xaero.common.events.ForgeEventHandler;
import xaero.common.file.SimpleBackup;
import xaero.common.gui.GuiSlimeSeed;
import xaero.common.interfaces.Interface;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.settings.ModOptions;
import xaero.patreon.Patreon4;

public class ModSettings {
    public static int defaultSettings;
    public static int ignoreUpdate;
    public static final String format = "\u00a7";
    private IXaeroMinimap modMain;
    public static final String[] ENCHANT_COLORS;
    public static final String[] ENCHANT_COLOR_NAMES;
    public static final int[] COLORS;
    public static final String[] MINIMAP_SIZE;
    public static int serverSettings;
    public static KeyBinding keyBindZoom;
    public static KeyBinding keyBindZoom1;
    public static KeyBinding newWaypoint;
    public static KeyBinding keyWaypoints;
    public static KeyBinding keyLargeMap;
    public static KeyBinding keyToggleMap;
    public static KeyBinding keyToggleWaypoints;
    public static KeyBinding keyToggleSlimes;
    public static KeyBinding keyToggleGrid;
    public static KeyBinding keyInstantWaypoint;
    public static KeyBinding keySwitchSet;
    public static KeyBinding keyAllSets;
    private boolean minimap = true;
    public static String minimapItemId;
    public static Item minimapItem;
    public int zoom = 2;
    public float[] zooms = new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
    public int entityAmount = 1;
    private boolean showPlayers = true;
    private boolean showMobs = true;
    private boolean showHostile = true;
    private boolean showItems = true;
    private boolean showOther = true;
    public int caveMaps = 1;
    public int caveZoom = 2;
    private boolean showOtherTeam = true;
    private boolean showWaypoints = true;
    private boolean deathpoints = true;
    private boolean oldDeathpoints = true;
    public int chunkGrid = -5;
    public boolean slimeChunks = false;
    private static HashMap<String, Long> serverSlimeSeeds;
    private boolean showIngameWaypoints = true;
    private boolean showCoords = true;
    private boolean lockNorth = false;
    private boolean antiAliasing = true;
    public boolean displayRedstone = true;
    public boolean mapSafeMode = false;
    public int distance = 1;
    public static final String[] distanceTypes;
    private int blockColours = 0;
    public static final String[] blockColourTypes;
    private boolean lighting = true;
    public boolean compassOverWaypoints = false;
    private int mapSize = -1;
    public int playersColor = 15;
    public int mobsColor = 14;
    public int hostileColor = 14;
    public int itemsColor = 12;
    public int otherColor = 5;
    public int otherTeamColor = -1;
    public float minimapOpacity = 100.0f;
    public float waypointsScale = 1.0f;
    public float dotsScale = 1.0f;
    public static boolean settingsButton;
    public boolean showBiome = false;
    public static boolean updateNotification;
    public boolean showEntityHeight = true;
    public boolean showFlowers = true;
    public boolean keepWaypointNames = false;
    public float waypointsDistance = 0.0f;
    public float waypointsDistanceMin = 0.0f;
    public String waypointTp = "tp";
    public float arrowScale = 1.5f;
    public int arrowColour = 0;
    public String[] arrowColourNames = new String[]{"gui.xaero_red", "gui.xaero_green", "gui.xaero_blue", "gui.xaero_yellow", "gui.xaero_purple", "gui.xaero_white", "gui.xaero_black", "gui.xaero_preset_classic"};
    public float[][] arrowColours = new float[][]{{0.8f, 0.1f, 0.1f, 1.0f}, {0.09f, 0.57f, 0.0f, 1.0f}, {0.0f, 0.55f, 1.0f, 1.0f}, {1.0f, 0.93f, 0.0f, 1.0f}, {0.73f, 0.33f, 0.83f, 1.0f}, {1.0f, 1.0f, 1.0f, 1.0f}, {0.0f, 0.0f, 0.0f, 1.0f}, {0.4588f, 0.0f, 0.0f, 1.0f}};
    public boolean smoothDots = true;
    public boolean playerHeads = false;
    public int heightLimit = 20;
    private boolean worldMap = true;
    private boolean terrainDepth = true;
    private boolean terrainSlopes = true;
    private boolean terrainSlopesExperiment = false;
    public boolean alwaysArrow = false;
    public boolean blockTransparency = true;
    public int waypointOpacityIngame = 80;
    public int waypointOpacityMap = 90;
    public boolean allowWrongWorldTeleportation = false;
    public int hideWorldNames = 1;
    public boolean openSlimeSettings = true;
    public boolean alwaysShowDistance = false;
    private boolean playerNames = true;
    public boolean showLightLevel = false;
    public int renderLayerIndex = 1;

    public ModSettings(IXaeroMinimap modMain) {
        this.modMain = modMain;
        int n = defaultSettings = modMain.getVersionID().endsWith("fair") ? 16188159 : Integer.MAX_VALUE;
        if (serverSettings == 0) {
            serverSettings = defaultSettings;
        }
    }

    public boolean isKeyRepeat(KeyBinding kb) {
        return kb != this.modMain.getSettingsKey() && kb != keyWaypoints && kb != newWaypoint && kb != keyLargeMap && kb != keyToggleMap && kb != keyToggleWaypoints && kb != keyToggleSlimes && kb != keyToggleGrid && kb != keyInstantWaypoint && kb != keySwitchSet && kb != keyAllSets;
    }

    public boolean getMinimap() {
        return this.minimap && !this.minimapDisabled() && (minimapItem == null || Minecraft.getMinecraft().player == null || MinimapProcessor.hasMinimapItem((EntityPlayer)Minecraft.getMinecraft().player));
    }

    public boolean getShowPlayers() {
        return this.showPlayers && !this.minimapDisplayPlayersDisabled();
    }

    public boolean getShowMobs() {
        return this.showMobs && !this.minimapDisplayMobsDisabled();
    }

    public boolean getShowHostile() {
        return this.showHostile && !this.minimapDisplayMobsDisabled();
    }

    public boolean getShowItems() {
        return this.showItems && !this.minimapDisplayItemsDisabled();
    }

    public boolean getShowOther() {
        return this.showOther && !this.minimapDisplayOtherDisabled();
    }

    public boolean getCaveMaps() {
        return this.caveMaps > 0 && !this.caveMapsDisabled();
    }

    public boolean getShowOtherTeam() {
        return this.showOtherTeam && !this.showOtherTeamDisabled();
    }

    public boolean getShowWaypoints() {
        return this.showWaypoints && !this.showWaypointsDisabled();
    }

    public boolean getDeathpoints() {
        return this.deathpoints && !this.deathpointsDisabled();
    }

    public boolean getOldDeathpoints() {
        return this.oldDeathpoints;
    }

    public void setSlimeChunksSeed(long seed) {
        serverSlimeSeeds.put(this.modMain.getWaypointsManager().getCurrentContainerAndWorldID(), seed);
    }

    public Long getSlimeChunksSeed() {
        IntegratedServer sp = Minecraft.getMinecraft().getIntegratedServer();
        if (sp == null) {
            return serverSlimeSeeds.get(this.modMain.getWaypointsManager().getCurrentContainerAndWorldID());
        }
        try {
            if (sp.getEntityWorld().provider.getDimension() != 0) {
                return null;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        long seed = sp.getEntityWorld().getSeed();
        return seed;
    }

    public boolean customSlimeSeedNeeded() {
        return !(Minecraft.getMinecraft().currentScreen instanceof GuiSlimeSeed) && Minecraft.getMinecraft().getIntegratedServer() == null && Minecraft.getMinecraft().world != null;
    }

    public boolean getSlimeChunks() {
        return this.slimeChunks && (Minecraft.getMinecraft().getIntegratedServer() != null || this.getSlimeChunksSeed() != null);
    }

    public boolean getShowIngameWaypoints() {
        return this.showIngameWaypoints && !this.showWaypointsDisabled() && (minimapItem == null || Minecraft.getMinecraft().player == null || MinimapProcessor.hasMinimapItem((EntityPlayer)Minecraft.getMinecraft().player));
    }

    public boolean waypointsGUI() {
        return Minecraft.getMinecraft().player != null && this.modMain.getWaypointsManager().getWaypoints() != null && (minimapItem == null || Minecraft.getMinecraft().player == null || MinimapProcessor.hasMinimapItem((EntityPlayer)Minecraft.getMinecraft().player));
    }

    public boolean getShowCoords() {
        return this.showCoords;
    }

    public boolean getLockNorth() {
        return this.lockNorth || this.modMain.getInterfaces().getMinimap().isEnlargedMap();
    }

    public boolean getAntiAliasing() {
        return this.antiAliasing && (!this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isTriedFBO() && !this.mapSafeMode || this.modMain.getInterfaces().getMinimap().usingFBO());
    }

    public int getBlockColours() {
        if (this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
            return this.modMain.getSupportMods().worldmapSupport.getWorldMapColours();
        }
        return this.blockColours;
    }

    public boolean getLighting() {
        if (this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
            return this.modMain.getSupportMods().worldmapSupport.getWorldMapLighting();
        }
        return this.lighting;
    }

    public int getMinimapSize() {
        if (this.mapSize > -1) {
            return this.mapSize;
        }
        int height = Minecraft.getMinecraft().displayHeight;
        int width = Minecraft.getMinecraft().displayWidth;
        int size = (int)((float)(height <= width ? height : width) / this.getMinimapScale());
        if (size <= 480) {
            return 0;
        }
        if (size <= 720) {
            return 1;
        }
        if (size <= 1080) {
            return 2;
        }
        return 3;
    }

    public float getMinimapScale() {
        int size;
        int height = Minecraft.getMinecraft().displayHeight;
        int width = Minecraft.getMinecraft().displayWidth;
        int n = size = height <= width ? height : width;
        if (size > 1500) {
            return 2.0f;
        }
        return 1.0f;
    }

    public boolean getSmoothDots() {
        return this.smoothDots && (!this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isTriedFBO() && !this.mapSafeMode || this.modMain.getInterfaces().getMinimap().usingFBO());
    }

    public boolean getPlayerHeads() {
        return this.playerHeads && (!this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isTriedFBO() && !this.mapSafeMode || this.modMain.getInterfaces().getMinimap().usingFBO());
    }

    public boolean getUseWorldMap() {
        return this.worldMap && (!this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isTriedFBO() && !this.mapSafeMode || this.modMain.getInterfaces().getMinimap().usingFBO());
    }

    public boolean getTerrainDepth() {
        if (this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
            return this.modMain.getSupportMods().worldmapSupport.getWorldMapTerrainDepth();
        }
        return this.terrainDepth;
    }

    public boolean getTerrainSlopes() {
        if (this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
            return this.modMain.getSupportMods().worldmapSupport.getWorldMapTerrainSlopes();
        }
        return this.terrainSlopes;
    }

    public void convertWaypointFilesToFolders() throws IOException {
        Stream<Path> files = Files.list(this.modMain.getWaypointsFolder().toPath());
        Path backupFolder = this.modMain.getWaypointsFolder().toPath().resolve("backup");
        Files.createDirectories(backupFolder, new FileAttribute[0]);
        if (files != null) {
            Object[] fileArray = files.toArray();
            for (int i = 0; i < fileArray.length; ++i) {
                String fileName;
                Path filePath = (Path)fileArray[i];
                if (filePath.toFile().isDirectory() || !(fileName = filePath.getFileName().toString()).endsWith(".txt") || !fileName.contains("_")) continue;
                int lastUnderscore = fileName.lastIndexOf("_");
                if (!fileName.startsWith("Multiplayer") && !fileName.startsWith("Realms")) {
                    fileName = fileName.substring(0, lastUnderscore).replace("_", "%us%") + fileName.substring(lastUnderscore);
                }
                String noExtension = fileName.substring(0, fileName.lastIndexOf("."));
                Path folderPath = filePath.getParent().resolve(noExtension);
                Path fixedFilePath = folderPath.resolve("waypoints.txt");
                Path backupFilePath = backupFolder.resolve(fileName);
                Files.createDirectories(folderPath, new FileAttribute[0]);
                if (!backupFilePath.toFile().exists()) {
                    Files.copy(filePath, backupFilePath, new CopyOption[0]);
                }
                try {
                    Files.move(filePath, fixedFilePath, new CopyOption[0]);
                    continue;
                }
                catch (FileAlreadyExistsException e) {
                    if (!backupFilePath.toFile().exists()) continue;
                    Files.deleteIfExists(filePath);
                }
            }
            files.close();
        }
    }

    public void convertWaypointFoldersToSingleFolder() throws IOException {
        Stream<Path> folders = Files.list(this.modMain.getWaypointsFolder().toPath());
        if (folders != null) {
            Object[] folderArray = folders.toArray();
            for (int i = 0; i < folderArray.length; ++i) {
                Stream<Path> deleteCheck;
                String dimensionName;
                String lastArg;
                String folderName;
                String[] folderArgs;
                Path folderPath = (Path)folderArray[i];
                if (!folderPath.toFile().isDirectory() || (folderArgs = (folderName = folderPath.getFileName().toString()).split("_")).length <= 1 || !(lastArg = folderArgs[folderArgs.length - 1]).equals("null") && (!lastArg.startsWith("DIM") || lastArg.length() <= 3)) continue;
                int dimensionId = lastArg.equals("null") ? 0 : Integer.parseInt(lastArg.substring(3));
                try {
                    dimensionName = this.modMain.getWaypointsManager().getDimensionDirectoryName(dimensionId);
                }
                catch (IllegalArgumentException iae) {
                    continue;
                }
                Path correctFolder = folderPath.getParent().resolve(folderName.substring(0, folderName.lastIndexOf("_"))).resolve(dimensionName);
                Files.createDirectories(correctFolder, new FileAttribute[0]);
                Stream<Path> files = Files.list(folderPath);
                if (files != null) {
                    Object[] filesArray = files.toArray();
                    for (int j = 0; j < filesArray.length; ++j) {
                        Path filePath = (Path)filesArray[j];
                        if (filePath.toFile().isDirectory()) continue;
                        Path correctFilePath = correctFolder.resolve(filePath.getFileName());
                        Files.move(filePath, correctFilePath, new CopyOption[0]);
                    }
                    files.close();
                }
                if ((deleteCheck = Files.list(folderPath)).count() == 0L) {
                    Files.deleteIfExists(folderPath);
                }
                deleteCheck.close();
            }
            folders.close();
        }
    }

    public static void copyTempFilesBack(Path folder) throws IOException {
        Stream<Path> tempFiles = Files.list(folder);
        if (tempFiles != null) {
            Iterator tempFilesIter = tempFiles.iterator();
            while (tempFilesIter.hasNext()) {
                Path tempFile = (Path)tempFilesIter.next();
                Path newLocation = folder.getParent().resolve(tempFile.getFileName());
                if (!Files.exists(newLocation, new LinkOption[0]) || Files.size(newLocation) == 0L) {
                    Files.move(tempFile, newLocation, StandardCopyOption.REPLACE_EXISTING);
                    continue;
                }
                Path backupPath = newLocation.resolveSibling("backup").resolve(tempFile.getFileName());
                Files.createDirectories(backupPath, new FileAttribute[0]);
                Files.move(tempFile, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }
            tempFiles.close();
        }
        Files.delete(folder);
    }

    public void loadAllWaypoints() throws IOException {
        Path waypointsFolderPath = this.modMain.getWaypointsFolder().toPath();
        if (!Files.exists(waypointsFolderPath, new LinkOption[0])) {
            Files.createDirectories(waypointsFolderPath, new FileAttribute[0]);
        }
        this.convertWaypointFilesToFolders();
        this.convertWaypointFoldersToSingleFolder();
        Stream<Path> folders = Files.list(this.modMain.getWaypointsFolder().toPath());
        if (folders != null) {
            Object[] paths = folders.toArray();
            for (int i = 0; i < paths.length; ++i) {
                Stream<Path> filesOrFolders;
                Path folderPath = (Path)paths[i];
                Path tempToAdd = folderPath.resolve("temp_to_add");
                if (Files.exists(tempToAdd, new LinkOption[0])) {
                    ModSettings.copyTempFilesBack(tempToAdd);
                }
                if (!folderPath.toFile().isDirectory() || (filesOrFolders = Files.list(folderPath)) == null) continue;
                Object[] fileArray = filesOrFolders.toArray();
                String folderName = folderPath.getFileName().toString();
                if (folderName.equals("backup")) continue;
                for (int j = 0; j < fileArray.length; ++j) {
                    String fileOrFolderName;
                    Path fileOrFolderPath = (Path)fileArray[j];
                    Path tempToAdd2 = fileOrFolderPath.resolve("temp_to_add");
                    if (Files.exists(tempToAdd2, new LinkOption[0])) {
                        ModSettings.copyTempFilesBack(tempToAdd2);
                    }
                    if ((fileOrFolderName = fileOrFolderPath.getFileName().toString()).equals("backup")) continue;
                    if (fileOrFolderPath.toFile().isDirectory()) {
                        if (fileOrFolderName.startsWith("backup")) continue;
                        String dimensionName = fileOrFolderName;
                        String fixedDimensionName = this.fixDimensionName(dimensionName);
                        boolean toDeleteOld = !fixedDimensionName.equals(dimensionName);
                        String containerKey = folderName + "/" + fixedDimensionName;
                        WaypointWorldContainer wc = this.modMain.getWaypointsManager().addWorldContainer(containerKey);
                        Stream<Path> files = Files.list(fileOrFolderPath);
                        if (files != null) {
                            Object[] filesArray = files.toArray();
                            if (filesArray.length == 0) {
                                this.modMain.getWaypointsManager().removeContainer(containerKey);
                            } else {
                                for (int k = 0; k < filesArray.length; ++k) {
                                    Path filePath = (Path)filesArray[k];
                                    String fileName = filePath.getFileName().toString();
                                    this.loadWaypointsFile(wc, fileName, filePath.toFile());
                                }
                            }
                            files.close();
                        }
                        if (this.modMain.getWaypointsManager().getWorldContainer(folderName).isEmpty()) {
                            this.modMain.getWaypointsManager().removeContainer(folderName);
                        }
                        if (!toDeleteOld) continue;
                        SimpleBackup.moveToBackup(fileOrFolderPath);
                        this.saveWorlds(wc.getAllWorlds());
                        continue;
                    }
                    if (!fileOrFolderName.contains("_")) continue;
                    WaypointWorldContainer wc = this.modMain.getWaypointsManager().addWorldContainer(folderName);
                    this.loadWaypointsFile(wc, fileOrFolderName, null);
                }
                filesOrFolders.close();
            }
            folders.close();
        }
    }

    private String fixDimensionName(String savedDimName) {
        if (savedDimName.equals("Overworld")) {
            return "dim%0";
        }
        if (savedDimName.equals("Nether")) {
            return "dim%-1";
        }
        if (savedDimName.equals("The End")) {
            return "dim%1";
        }
        return savedDimName;
    }

    private boolean loadWaypointsFile(WaypointWorldContainer wc, String fileName, File file) throws IOException {
        WaypointWorld w;
        String noExtension;
        if (!fileName.endsWith(".txt")) {
            return false;
        }
        String multiworldId = noExtension = fileName.substring(0, fileName.lastIndexOf("."));
        if (!noExtension.equals("waypoints")) {
            String[] multiworld = noExtension.split("_");
            multiworldId = multiworld[0];
            String multiworldName = multiworld[1].replace("%us%", "_");
            wc.addName(multiworldId, multiworldName);
        }
        if ((w = wc.addWorld(multiworldId)) != null) {
            this.loadWaypoints(w, file);
        }
        return true;
    }

    public void saveAllWaypoints() throws IOException {
        String[] keys = this.modMain.getWaypointsManager().getWaypointMap().keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length; ++i) {
            String key = keys[i];
            WaypointWorldContainer wc = this.modMain.getWaypointsManager().getWaypointMap().get(key);
            this.saveWorlds(wc.getAllWorlds());
        }
    }

    public void saveWorlds(ArrayList<WaypointWorld> worlds) throws IOException {
        for (int j = 0; j < worlds.size(); ++j) {
            WaypointWorld w = worlds.get(j);
            if (w == null) continue;
            this.saveWaypoints(w);
        }
    }

    public void saveWaypoints(WaypointWorld wpw) throws IOException {
        this.saveWaypoints(wpw, true);
    }

    public File getWaypointsFile(WaypointWorld w) throws IOException {
        File containerFolder = w.getContainer().getDirectory();
        Files.createDirectories(containerFolder.toPath(), new FileAttribute[0]);
        String filePath = containerFolder.getPath() + "/" + w.getId();
        String name = w.getContainer().getName(w.getId());
        if (name != null) {
            filePath = filePath + "_" + name.replace("_", "%us%").replace(":", "\u00a7\u00a7");
        }
        return new File(filePath + ".txt");
    }

    public void saveWaypoints(WaypointWorld wpw, boolean overwrite) throws IOException {
        String name;
        int i;
        if (wpw == null) {
            return;
        }
        File worldFile = this.getWaypointsFile(wpw);
        if (worldFile.exists() && !overwrite) {
            return;
        }
        OutputStreamWriter output = new OutputStreamWriter((OutputStream)new FileOutputStream(worldFile), StandardCharsets.UTF_8);
        Object[] keys = wpw.getSets().keySet().toArray();
        if (keys.length > 1) {
            output.write("sets:" + wpw.getCurrent());
            for (i = 0; i < keys.length; ++i) {
                name = (String)keys[i];
                if (name.equals(wpw.getCurrent())) continue;
                output.write(":" + (String)keys[i]);
            }
            output.write("\n");
        }
        for (i = 0; i < keys.length; ++i) {
            name = (String)keys[i];
            WaypointSet set = wpw.getSets().get(name);
            if (set == null) continue;
            ArrayList<Waypoint> list = set.getList();
            for (int j = 0; j < list.size(); ++j) {
                Waypoint w = list.get(j);
                if (w.isTemporary()) continue;
                output.write("waypoint:" + w.getNameSafe("\u00a7\u00a7") + ":" + w.getSymbolSafe("\u00a7\u00a7") + ":" + w.getX() + ":" + w.getY() + ":" + w.getZ() + ":" + w.getColor() + ":" + w.isDisabled() + ":" + w.getType() + ":" + name + ":" + w.isRotation() + ":" + w.getYaw() + "\n");
            }
        }
        ArrayList<Map.Entry<String, Boolean>> serverWaypointsDisabled = new ArrayList<Map.Entry<String, Boolean>>(wpw.getServerWaypointsDisabled().entrySet());
        for (int i2 = 0; i2 < serverWaypointsDisabled.size(); ++i2) {
            Map.Entry<String, Boolean> e = serverWaypointsDisabled.get(i2);
            output.write("server_waypoint:" + e.getKey() + ":" + e.getValue() + "\n");
        }
        output.close();
        if (wpw.hasSomethingToRemoveOnSave()) {
            wpw.onSaveCleanup(worldFile);
        }
    }

    public boolean checkWaypointsLine(String[] args, WaypointWorld wpw) {
        if (args[0].equalsIgnoreCase("sets")) {
            wpw.setCurrent(args[1]);
            for (int i = 1; i < args.length; ++i) {
                if (wpw.getSets().get(args[i]) != null) continue;
                wpw.getSets().put(args[i], new WaypointSet(args[i]));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("waypoint")) {
            String setName = args[9];
            WaypointSet waypoints = wpw.getSets().get(setName);
            if (waypoints == null) {
                waypoints = new WaypointSet(setName);
                wpw.getSets().put(setName, waypoints);
            }
            Waypoint loadWaypoint = new Waypoint(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Waypoint.getStringFromStringSafe(args[1], "\u00a7\u00a7"), Waypoint.getStringFromStringSafe(args[2], "\u00a7\u00a7"), Integer.parseInt(args[6]));
            if (args.length > 7) {
                loadWaypoint.setDisabled(args[7].equals("true"));
            }
            if (args.length > 8) {
                loadWaypoint.setType(Integer.parseInt(args[8]));
            }
            if (args.length > 10) {
                loadWaypoint.setRotation(args[10].equals("true"));
            }
            if (args.length > 11) {
                loadWaypoint.setYaw(Integer.parseInt(args[11]));
            }
            waypoints.getList().add(loadWaypoint);
            return true;
        }
        if (args[0].equalsIgnoreCase("server_waypoint")) {
            wpw.getServerWaypointsDisabled().put(args[1], args[2].equals("true"));
        }
        return false;
    }

    public void loadWaypoints(WaypointWorld wpw, File file) throws IOException {
        String s;
        if (file == null) {
            file = this.getWaypointsFile(wpw);
        }
        if (!file.exists()) {
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), "UTF8"));
        while ((s = reader.readLine()) != null) {
            Object[] args = s.split(":");
            try {
                this.checkWaypointsLine((String[])args, wpw);
            }
            catch (Exception e) {
                System.out.println("Skipping:" + Arrays.toString(args));
                e.printStackTrace();
            }
        }
        reader.close();
        if (wpw.getSets().size() == 1 && wpw.getCurrentSet().getList().isEmpty()) {
            this.modMain.getWaypointsManager().getWaypointMap().remove(wpw.getId());
        }
    }

    public String convertToNewConteinerID(String oldID) {
        String parentContainer = oldID.substring(0, oldID.lastIndexOf("_"));
        String dimension = oldID.substring(oldID.lastIndexOf("_") + 1);
        if (dimension.equals("null")) {
            dimension = "Overworld";
        } else if (dimension.startsWith("DIM")) {
            dimension = this.modMain.getWaypointsManager().getDimensionDirectoryName(Integer.parseInt(dimension.substring(3)));
        }
        return parentContainer + "/" + this.fixDimensionName(dimension);
    }

    public boolean checkWaypointsLineOLD(String[] args) {
        if (args[0].equalsIgnoreCase("world")) {
            if (!args[1].contains("_")) {
                args[1] = args[1] + "_null";
            }
            WaypointWorldContainer wc = this.modMain.getWaypointsManager().addWorldContainer(this.convertToNewConteinerID(args[1]));
            WaypointWorld map = wc.addWorld("waypoints");
            map.setCurrent(args[2]);
            for (int i = 2; i < args.length; ++i) {
                if (map.getSets().get(args[i]) != null) continue;
                map.getSets().put(args[i], new WaypointSet(args[i]));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("waypoint")) {
            WaypointSet waypoints;
            if (!args[1].contains("_")) {
                args[1] = args[1] + "_null";
            }
            WaypointWorldContainer wc = this.modMain.getWaypointsManager().addWorldContainer(this.convertToNewConteinerID(args[1]));
            WaypointWorld map = wc.addWorld("waypoints");
            String setName = "gui.xaero_default";
            if (args.length > 10) {
                setName = args[10];
            }
            if ((waypoints = map.getSets().get(setName)) == null) {
                waypoints = new WaypointSet(setName);
                map.getSets().put(setName, waypoints);
            }
            Waypoint loadWaypoint = new Waypoint(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), args[2].replace("\u00a7\u00a7", ":"), args[3].replace("\u00a7\u00a7", ":"), Integer.parseInt(args[7]));
            if (args.length > 8) {
                loadWaypoint.setDisabled(args[8].equals("true"));
            }
            if (args.length > 9) {
                loadWaypoint.setType(Integer.parseInt(args[9]));
            }
            if (args.length > 11) {
                loadWaypoint.setRotation(args[11].equals("true"));
            }
            if (args.length > 12) {
                loadWaypoint.setYaw(Integer.parseInt(args[12]));
            }
            waypoints.getList().add(loadWaypoint);
            return true;
        }
        return false;
    }

    public void loadOldWaypoints(File file) throws IOException {
        String s;
        if (!file.exists()) {
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((s = reader.readLine()) != null) {
            String[] args = s.split(":");
            try {
                this.checkWaypointsLineOLD(args);
            }
            catch (Exception e) {
                System.out.println("Skipping setting:" + args[0]);
            }
        }
        reader.close();
        File backupFile = new File(file.getAbsolutePath() + ".backup");
        if (backupFile.exists()) {
            System.out.println("Waypoints old file backup already exists!");
            return;
        }
        Files.move(file.toPath(), backupFile.toPath(), new CopyOption[0]);
    }

    public void writeSettings(PrintWriter writer) {
        writer.println("#CONFIG ONLY OPTIONS");
        writer.println("ignoreUpdate:" + ignoreUpdate);
        writer.println("updateNotification:" + updateNotification);
        writer.println("settingsButton:" + settingsButton);
        if (minimapItemId != null) {
            writer.println("minimapItemId:" + minimapItemId);
        }
        writer.println("allowWrongWorldTeleportation:" + this.allowWrongWorldTeleportation);
        writer.println("#INGAME SETTINGS (DO NOT EDIT!)");
        writer.println("minimap:" + this.minimap);
        writer.println("caveMaps:" + this.caveMaps);
        writer.println("caveZoom:" + this.caveZoom);
        writer.println("showPlayers:" + this.showPlayers);
        writer.println("showHostile:" + this.showHostile);
        writer.println("showMobs:" + this.showMobs);
        writer.println("showItems:" + this.showItems);
        writer.println("showOther:" + this.showOther);
        writer.println("showOtherTeam:" + this.showOtherTeam);
        writer.println("showWaypoints:" + this.showWaypoints);
        writer.println("showIngameWaypoints:" + this.showIngameWaypoints);
        writer.println("displayRedstone:" + this.displayRedstone);
        writer.println("deathpoints:" + this.deathpoints);
        writer.println("oldDeathpoints:" + this.oldDeathpoints);
        writer.println("distance:" + this.distance);
        writer.println("showCoords:" + this.showCoords);
        writer.println("lockNorth:" + this.lockNorth);
        writer.println("zoom:" + this.zoom);
        writer.println("mapSize:" + this.mapSize);
        writer.println("entityAmount:" + this.entityAmount);
        writer.println("chunkGrid:" + this.chunkGrid);
        writer.println("slimeChunks:" + this.slimeChunks);
        writer.println("playersColor:" + this.playersColor);
        writer.println("mobsColor:" + this.mobsColor);
        writer.println("hostileColor:" + this.hostileColor);
        writer.println("itemsColor:" + this.itemsColor);
        writer.println("otherColor:" + this.otherColor);
        writer.println("otherTeamColor:" + this.otherTeamColor);
        writer.println("mapSafeMode:" + this.mapSafeMode);
        writer.println("minimapOpacity:" + this.minimapOpacity);
        writer.println("waypointsScale:" + this.waypointsScale);
        writer.println("antiAliasing:" + this.antiAliasing);
        writer.println("blockColours:" + this.blockColours);
        writer.println("lighting:" + this.lighting);
        writer.println("dotsScale:" + this.dotsScale);
        writer.println("compassOverWaypoints:" + this.compassOverWaypoints);
        writer.println("showBiome:" + this.showBiome);
        writer.println("showEntityHeight:" + this.showEntityHeight);
        writer.println("showFlowers:" + this.showFlowers);
        writer.println("keepWaypointNames:" + this.keepWaypointNames);
        writer.println("waypointsDistance:" + this.waypointsDistance);
        writer.println("waypointsDistanceMin:" + this.waypointsDistanceMin);
        writer.println("waypointTp:" + this.waypointTp);
        writer.println("arrowScale:" + this.arrowScale);
        writer.println("arrowColour:" + this.arrowColour);
        writer.println("smoothDots:" + this.smoothDots);
        writer.println("playerHeads:" + this.playerHeads);
        writer.println("heightLimit:" + this.heightLimit);
        writer.println("worldMap:" + this.worldMap);
        writer.println("terrainDepth:" + this.terrainDepth);
        writer.println("terrainSlopes:" + this.terrainSlopes);
        writer.println("terrainSlopesExperiment:" + this.terrainSlopesExperiment);
        writer.println("alwaysArrow:" + this.alwaysArrow);
        writer.println("blockTransparency:" + this.blockTransparency);
        writer.println("waypointOpacityIngame:" + this.waypointOpacityIngame);
        writer.println("waypointOpacityMap:" + this.waypointOpacityMap);
        writer.println("hideWorldNames:" + this.hideWorldNames);
        writer.println("openSlimeSettings:" + this.openSlimeSettings);
        writer.println("alwaysShowDistance:" + this.alwaysShowDistance);
        writer.println("playerNames:" + this.playerNames);
        writer.println("showLightLevel:" + this.showLightLevel);
        writer.println("renderLayerIndex:" + this.renderLayerIndex);
    }

    public void saveSettings() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(this.modMain.getConfigFile()));
        this.writeSettings(writer);
        Object[] keys = serverSlimeSeeds.keySet().toArray();
        Object[] values = serverSlimeSeeds.values().toArray();
        for (int i = 0; i < keys.length; ++i) {
            writer.println("seed:" + keys[i] + ":" + values[i]);
        }
        Iterator<Interface> iter = this.modMain.getInterfaces().getInterfaceIterator();
        while (iter.hasNext()) {
            Interface l = iter.next();
            writer.println("interface:" + l.getIname() + ":" + l.getActualx() + ":" + l.getActualy() + ":" + l.isCentered() + ":" + l.isFlipped() + ":" + l.isFromRight() + ":" + l.isFromBottom());
        }
        writer.println("#WAYPOINTS HAVE BEEN MOVED TO xaerowaypoints.txt!");
        writer.close();
    }

    public void readSetting(String[] args) {
        if (args[0].equalsIgnoreCase("ignoreUpdate")) {
            ignoreUpdate = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("updateNotification")) {
            updateNotification = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("settingsButton")) {
            settingsButton = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("minimapItemId")) {
            minimapItemId = args[1] + ":" + args[2];
            minimapItem = (Item)Item.REGISTRY.getObject((Object)new ResourceLocation(args[1], args[2]));
        } else if (args[0].equalsIgnoreCase("allowWrongWorldTeleportation")) {
            this.allowWrongWorldTeleportation = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("minimap")) {
            this.minimap = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("caveMaps")) {
            this.caveMaps = args[1].equals("true") ? 1 : (args[1].equals("false") ? 0 : Integer.parseInt(args[1]));
        } else if (args[0].equalsIgnoreCase("caveZoom")) {
            this.caveZoom = args[1].equals("true") ? 2 : (args[1].equals("false") ? 0 : Integer.parseInt(args[1]));
        } else if (args[0].equalsIgnoreCase("showPlayers")) {
            this.showPlayers = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showHostile")) {
            this.showHostile = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showMobs")) {
            this.showMobs = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showItems")) {
            this.showItems = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showOther")) {
            this.showOther = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showOtherTeam")) {
            this.showOtherTeam = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showWaypoints")) {
            this.showWaypoints = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("deathpoints")) {
            this.deathpoints = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("oldDeathpoints")) {
            this.oldDeathpoints = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showIngameWaypoints")) {
            this.showIngameWaypoints = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("displayRedstone")) {
            this.displayRedstone = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("distance")) {
            this.distance = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("showCoords")) {
            this.showCoords = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("lockNorth")) {
            this.lockNorth = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("zoom")) {
            this.zoom = Integer.parseInt(args[1]);
            if (this.zoom >= this.zooms.length) {
                this.zoom = this.zooms.length - 1;
            }
        } else if (args[0].equalsIgnoreCase("mapSize")) {
            this.mapSize = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("entityAmount")) {
            this.entityAmount = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("chunkGrid")) {
            this.chunkGrid = args[1].equals("true") ? 4 : (args[1].equals("false") ? -5 : Integer.parseInt(args[1]));
        } else if (args[0].equalsIgnoreCase("slimeChunks")) {
            this.slimeChunks = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("playersColor")) {
            this.playersColor = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("mobsColor")) {
            this.mobsColor = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("hostileColor")) {
            this.hostileColor = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("itemsColor")) {
            this.itemsColor = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("otherColor")) {
            this.otherColor = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("otherTeamColor")) {
            this.otherTeamColor = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("mapSafeMode")) {
            this.mapSafeMode = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("minimapOpacity")) {
            this.minimapOpacity = Float.valueOf(args[1]).floatValue();
        } else if (args[0].equalsIgnoreCase("waypointsScale")) {
            this.waypointsScale = Float.valueOf(args[1]).floatValue();
        } else if (args[0].equalsIgnoreCase("antiAliasing")) {
            this.antiAliasing = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("blockColours")) {
            this.blockColours = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("lighting")) {
            this.lighting = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("dotsScale")) {
            this.dotsScale = Float.valueOf(args[1]).floatValue();
        } else if (args[0].equalsIgnoreCase("compassOverWaypoints")) {
            this.compassOverWaypoints = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showBiome")) {
            this.showBiome = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showEntityHeight")) {
            this.showEntityHeight = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showFlowers")) {
            this.showFlowers = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("keepWaypointNames")) {
            this.keepWaypointNames = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("waypointsDistance")) {
            this.waypointsDistance = Float.valueOf(args[1]).floatValue();
        } else if (args[0].equalsIgnoreCase("waypointsDistanceMin")) {
            this.waypointsDistanceMin = Float.valueOf(args[1]).floatValue();
        } else if (args[0].equalsIgnoreCase("waypointTp")) {
            this.waypointTp = args[1];
        } else if (args[0].equalsIgnoreCase("arrowScale")) {
            this.arrowScale = Float.valueOf(args[1]).floatValue();
        } else if (args[0].equalsIgnoreCase("arrowColour")) {
            this.arrowColour = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("seed")) {
            serverSlimeSeeds.put(args[1], Long.parseLong(args[2]));
        } else if (args[0].equalsIgnoreCase("smoothDots")) {
            this.smoothDots = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("playerHeads")) {
            this.playerHeads = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("heightLimit")) {
            this.heightLimit = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("worldMap")) {
            this.worldMap = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("terrainDepth")) {
            this.terrainDepth = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("terrainSlopes")) {
            this.terrainSlopes = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("terrainSlopesExperiment")) {
            this.terrainSlopesExperiment = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("alwaysArrow")) {
            this.alwaysArrow = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("blockTransparency")) {
            this.blockTransparency = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("waypointOpacityIngame")) {
            this.waypointOpacityIngame = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("waypointOpacityMap")) {
            this.waypointOpacityMap = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("hideWorldNames")) {
            this.hideWorldNames = args[1].equals("true") ? 2 : (args[1].equals("false") ? 1 : Integer.parseInt(args[1]));
        } else if (args[0].equalsIgnoreCase("openSlimeSettings")) {
            this.openSlimeSettings = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("alwaysShowDistance")) {
            this.alwaysShowDistance = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("playerNames")) {
            this.playerNames = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("showLightLevel")) {
            this.showLightLevel = args[1].equals("true");
        } else if (args[0].equalsIgnoreCase("renderLayerIndex")) {
            this.renderLayerIndex = Integer.parseInt(args[1]);
            if (this.renderLayerIndex >= ForgeEventHandler.OVERLAY_LAYERS.length) {
                this.renderLayerIndex = ForgeEventHandler.OVERLAY_LAYERS.length - 1;
            }
        }
    }

    public void loadSettings() throws IOException {
        String s;
        new File("./config").mkdirs();
        if (!this.modMain.getConfigFile().exists()) {
            this.saveSettings();
            return;
        }
        this.modMain.getWaypointsManager().getWaypointMap().clear();
        BufferedReader reader = new BufferedReader(new FileReader(this.modMain.getConfigFile()));
        boolean saveWaypoints = false;
        block2: while ((s = reader.readLine()) != null) {
            String[] args = s.split(":");
            try {
                this.readSetting(args);
                if (args[0].equalsIgnoreCase("interface")) {
                    Iterator<Interface> iter = this.modMain.getInterfaces().getInterfaceIterator();
                    while (iter.hasNext()) {
                        Interface l = iter.next();
                        if (!args[1].equals(l.getIname())) continue;
                        l.setX(Integer.parseInt(args[2]));
                        l.setY(Integer.parseInt(args[3]));
                        l.setActualx(l.getX());
                        l.setActualy(l.getY());
                        l.setCentered(args[4].equals("true"));
                        l.setFlipped(args[5].equals("true"));
                        l.setFromRight(args[6].equals("true"));
                        if (args.length > 7) {
                            l.setFromBottom(args[7].equals("true"));
                        }
                        l.backup();
                        continue block2;
                    }
                    continue;
                }
                if (!this.checkWaypointsLineOLD(args)) continue;
                saveWaypoints = true;
            }
            catch (Exception e) {
                System.out.println("Skipping setting:" + args[0]);
            }
        }
        reader.close();
        if (this.modMain.getWaypointsFile().exists()) {
            this.loadOldWaypoints(this.modMain.getWaypointsFile());
            saveWaypoints = true;
        }
        this.loadAllWaypoints();
        if (saveWaypoints) {
            this.saveAllWaypoints();
            this.saveSettings();
        }
    }

    public String getMoreKeybindings(String s, ModOptions par1EnumOptions) {
        boolean clientSetting = this.getClientBooleanValue(par1EnumOptions);
        boolean serverSetting = this.getBooleanValue(par1EnumOptions);
        s = s + ModSettings.getTranslation(clientSetting) + (serverSetting != clientSetting ? "\u00a7e (" + ModSettings.getTranslation(serverSetting) + ")" : "");
        return s;
    }

    public String getKeyBinding(ModOptions par1EnumOptions) {
        String s = par1EnumOptions.getEnumString() + ": ";
        if (par1EnumOptions == ModOptions.DOTS_SCALE && (this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isTriedFBO() && !this.modMain.getInterfaces().getMinimap().usingFBO() || this.mapSafeMode)) {
            return s + "\u00a7e" + ModSettings.getTranslation(false);
        }
        if (par1EnumOptions.getEnumFloat()) {
            return this.getEnumFloatSliderText(s, par1EnumOptions);
        }
        if (par1EnumOptions == ModOptions.CHUNK_GRID) {
            s = s + (this.chunkGrid > -1 ? format + ENCHANT_COLORS[this.chunkGrid] + I18n.format((String)ENCHANT_COLOR_NAMES[this.chunkGrid], (Object[])new Object[0]) : I18n.format((String)"gui.xaero_off", (Object[])new Object[0]));
        } else if (par1EnumOptions == ModOptions.EDIT) {
            s = par1EnumOptions.getEnumString();
        } else if (par1EnumOptions == ModOptions.DOTS) {
            s = par1EnumOptions.getEnumString();
        } else if (par1EnumOptions == ModOptions.RESET) {
            s = par1EnumOptions.getEnumString();
        } else if (par1EnumOptions == ModOptions.WAYPOINTS_TP) {
            s = par1EnumOptions.getEnumString();
        } else if (par1EnumOptions == ModOptions.ZOOM) {
            s = s + this.zooms[this.zoom] + "x";
        } else if (par1EnumOptions == ModOptions.COLOURS) {
            s = s + (this.modMain.getSupportMods().shouldUseWorldMapChunks() ? "\u00a7e" + I18n.format((String)"gui.xaero_world_map", (Object[])new Object[0]) : I18n.format((String)blockColourTypes[this.getBlockColours()], (Object[])new Object[0]));
        } else if (par1EnumOptions == ModOptions.DISTANCE) {
            s = s + I18n.format((String)distanceTypes[this.distance], (Object[])new Object[0]);
        } else if (par1EnumOptions == ModOptions.SLIME_CHUNKS && this.customSlimeSeedNeeded()) {
            s = par1EnumOptions.getEnumString();
        } else if (par1EnumOptions == ModOptions.SIZE) {
            s = s + I18n.format((String)(this.mapSize > -1 ? MINIMAP_SIZE[this.mapSize] : "gui.xaero_auto_map_size"), (Object[])new Object[0]);
        } else if (par1EnumOptions == ModOptions.EAMOUNT) {
            s = this.entityAmount == 0 ? s + I18n.format((String)"gui.xaero_unlimited", (Object[])new Object[0]) : s + 100 * this.entityAmount;
        } else if (par1EnumOptions == ModOptions.CAVE_MAPS) {
            if (this.caveMaps == 0) {
                s = s + I18n.format((String)"gui.xaero_off", (Object[])new Object[0]);
            } else {
                int roofSideSize = (this.caveMaps - 1) * 2 + 1;
                s = s + roofSideSize + "x" + roofSideSize + " " + I18n.format((String)"gui.xaero_roof", (Object[])new Object[0]);
                if (!this.getCaveMaps()) {
                    s = s + "\u00a7e (" + ModSettings.getTranslation(false) + ")";
                }
            }
        } else if (par1EnumOptions == ModOptions.CAVE_ZOOM) {
            s = this.caveZoom == 0 ? s + I18n.format((String)"gui.xaero_off", (Object[])new Object[0]) : s + (1 + this.caveZoom) + "x";
        } else if (par1EnumOptions == ModOptions.HIDE_WORLD_NAMES) {
            s = s + (this.hideWorldNames == 0 ? I18n.format((String)"gui.xaero_off", (Object[])new Object[0]) : (this.hideWorldNames == 1 ? I18n.format((String)"gui.xaero_partial", (Object[])new Object[0]) : I18n.format((String)"gui.xaero_full", (Object[])new Object[0])));
        } else if (par1EnumOptions == ModOptions.ARROW_COLOUR) {
            String colourName = "gui.xaero_team";
            if (this.arrowColour != -1) {
                colourName = this.arrowColourNames[this.arrowColour];
            }
            s = s + I18n.format((String)colourName, (Object[])new Object[0]);
        } else {
            s = (par1EnumOptions == ModOptions.LIGHT || par1EnumOptions == ModOptions.TERRAIN_DEPTH || par1EnumOptions == ModOptions.TERRAIN_SLOPES) && this.modMain.getSupportMods().shouldUseWorldMapChunks() ? s + "\u00a7e" + I18n.format((String)"gui.xaero_world_map", (Object[])new Object[0]) : (par1EnumOptions == ModOptions.TERRAIN_SLOPES && this.terrainSlopesExperiment ? s + "Experimental" : (par1EnumOptions == ModOptions.RENDER_LAYER ? s + this.renderLayerIndex : this.getMoreKeybindings(s, par1EnumOptions)));
        }
        return s;
    }

    protected String getEnumFloatSliderText(String s, ModOptions par1EnumOptions) {
        String f1 = String.format("%.1f", Float.valueOf(this.getOptionFloatValue(par1EnumOptions)));
        if (par1EnumOptions == ModOptions.WAYPOINTS_DISTANCE) {
            f1 = this.waypointsDistance == 0.0f ? I18n.format((String)"gui.xaero_unlimited", (Object[])new Object[0]) : (int)this.waypointsDistance + "m";
        } else if (par1EnumOptions == ModOptions.WAYPOINTS_DISTANCE_MIN) {
            f1 = this.waypointsDistanceMin == 0.0f ? I18n.format((String)"gui.xaero_off", (Object[])new Object[0]) : (int)this.waypointsDistanceMin + "m";
        } else if (par1EnumOptions == ModOptions.ARROW_SCALE) {
            f1 = f1 + "x";
        }
        return s + f1;
    }

    public boolean getBooleanValue(ModOptions o) {
        if (o == ModOptions.MINIMAP) {
            return this.getMinimap();
        }
        if (o == ModOptions.CAVE_MAPS) {
            return this.getCaveMaps();
        }
        if (o == ModOptions.DISPLAY_OTHER_TEAM) {
            return this.getShowOtherTeam();
        }
        if (o == ModOptions.WAYPOINTS) {
            return this.getShowWaypoints();
        }
        if (o == ModOptions.DEATHPOINTS) {
            return this.getDeathpoints();
        }
        if (o == ModOptions.OLD_DEATHPOINTS) {
            return this.getOldDeathpoints();
        }
        if (o == ModOptions.INGAME_WAYPOINTS) {
            return this.getShowIngameWaypoints();
        }
        if (o == ModOptions.COORDS) {
            return this.getShowCoords();
        }
        if (o == ModOptions.NORTH) {
            return this.getLockNorth();
        }
        if (o == ModOptions.PLAYERS) {
            return this.getShowPlayers();
        }
        if (o == ModOptions.HOSTILE) {
            return this.getShowHostile();
        }
        if (o == ModOptions.MOBS) {
            return this.getShowMobs();
        }
        if (o == ModOptions.ITEMS) {
            return this.getShowItems();
        }
        if (o == ModOptions.ENTITIES) {
            return this.getShowOther();
        }
        if (o == ModOptions.SAFE_MAP) {
            return this.mapSafeMode || this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isTriedFBO() && !this.modMain.getInterfaces().getMinimap().getMinimapFBORenderer().isLoadedFBO();
        }
        if (o == ModOptions.AA) {
            return this.getAntiAliasing();
        }
        if (o == ModOptions.SMOOTH_DOTS) {
            return this.getSmoothDots();
        }
        if (o == ModOptions.PLAYER_HEADS) {
            return this.getPlayerHeads();
        }
        if (o == ModOptions.WORLD_MAP) {
            return this.getUseWorldMap();
        }
        if (o == ModOptions.TERRAIN_DEPTH) {
            return this.getTerrainDepth();
        }
        if (o == ModOptions.TERRAIN_SLOPES) {
            return this.getTerrainSlopes();
        }
        return this.getClientBooleanValue(o);
    }

    public boolean getClientBooleanValue(ModOptions o) {
        if (o == ModOptions.MINIMAP) {
            return this.minimap;
        }
        if (o == ModOptions.DISPLAY_OTHER_TEAM) {
            return this.showOtherTeam;
        }
        if (o == ModOptions.WAYPOINTS) {
            return this.showWaypoints;
        }
        if (o == ModOptions.DEATHPOINTS) {
            return this.deathpoints;
        }
        if (o == ModOptions.OLD_DEATHPOINTS) {
            return this.oldDeathpoints;
        }
        if (o == ModOptions.INGAME_WAYPOINTS) {
            return this.showIngameWaypoints;
        }
        if (o == ModOptions.REDSTONE) {
            return this.displayRedstone;
        }
        if (o == ModOptions.COORDS) {
            return this.showCoords;
        }
        if (o == ModOptions.NORTH) {
            return this.lockNorth;
        }
        if (o == ModOptions.PLAYERS) {
            return this.showPlayers;
        }
        if (o == ModOptions.HOSTILE) {
            return this.showHostile;
        }
        if (o == ModOptions.MOBS) {
            return this.showMobs;
        }
        if (o == ModOptions.ITEMS) {
            return this.showItems;
        }
        if (o == ModOptions.ENTITIES) {
            return this.showOther;
        }
        if (o == ModOptions.SLIME_CHUNKS) {
            return this.slimeChunks;
        }
        if (o == ModOptions.SAFE_MAP) {
            return this.mapSafeMode;
        }
        if (o == ModOptions.AA) {
            return this.antiAliasing;
        }
        if (o == ModOptions.LIGHT) {
            return this.lighting;
        }
        if (o == ModOptions.COMPASS) {
            return this.compassOverWaypoints;
        }
        if (o == ModOptions.BIOME) {
            return this.showBiome;
        }
        if (o == ModOptions.ENTITY_HEIGHT) {
            return this.showEntityHeight;
        }
        if (o == ModOptions.FLOWERS) {
            return this.showFlowers;
        }
        if (o == ModOptions.KEEP_WP_NAMES) {
            return this.keepWaypointNames;
        }
        if (o == ModOptions.SMOOTH_DOTS) {
            return this.smoothDots;
        }
        if (o == ModOptions.PLAYER_HEADS) {
            return this.playerHeads;
        }
        if (o == ModOptions.WORLD_MAP) {
            return this.worldMap;
        }
        if (o == ModOptions.CAPES) {
            return Patreon4.showCapes;
        }
        if (o == ModOptions.TERRAIN_DEPTH) {
            return this.terrainDepth;
        }
        if (o == ModOptions.TERRAIN_SLOPES) {
            return this.terrainSlopes;
        }
        if (o == ModOptions.ALWAYS_ARROW) {
            return this.alwaysArrow;
        }
        if (o == ModOptions.BLOCK_TRANSPARENCY) {
            return this.blockTransparency;
        }
        if (o == ModOptions.OPEN_SLIME_SETTINGS) {
            return this.openSlimeSettings;
        }
        if (o == ModOptions.ALWAYS_SHOW_DISTANCE) {
            return this.alwaysShowDistance;
        }
        if (o == ModOptions.PLAYER_NAMES) {
            return this.playerNames;
        }
        if (o == ModOptions.SHOW_LIGHT_LEVEL) {
            return this.showLightLevel;
        }
        return false;
    }

    public static String getTranslation(boolean o) {
        return I18n.format((String)("gui.xaero_" + (o ? "on" : "off")), (Object[])new Object[0]);
    }

    public void setOptionValue(ModOptions par1EnumOptions, int par2) throws IOException {
        if (par1EnumOptions == ModOptions.ZOOM) {
            this.zoom = (this.zoom + 1) % this.zooms.length;
        } else if (par1EnumOptions == ModOptions.SIZE) {
            this.mapSize = this.mapSize == 3 ? -1 : (this.mapSize + 1) % 4;
        } else if (par1EnumOptions == ModOptions.EAMOUNT) {
            this.entityAmount = (this.entityAmount + 1) % 11;
        } else if (par1EnumOptions == ModOptions.MINIMAP) {
            this.minimap = !this.minimap;
        } else if (par1EnumOptions == ModOptions.CAVE_MAPS) {
            this.caveMaps = (this.caveMaps + 1) % 4;
        } else if (par1EnumOptions == ModOptions.CAVE_ZOOM) {
            this.caveZoom = (this.caveZoom + 1) % 4;
        } else if (par1EnumOptions == ModOptions.DISPLAY_OTHER_TEAM) {
            this.showOtherTeam = !this.showOtherTeam;
        } else if (par1EnumOptions == ModOptions.WAYPOINTS) {
            this.showWaypoints = !this.showWaypoints;
        } else if (par1EnumOptions == ModOptions.DEATHPOINTS) {
            this.deathpoints = !this.deathpoints;
        } else if (par1EnumOptions == ModOptions.OLD_DEATHPOINTS) {
            this.oldDeathpoints = !this.oldDeathpoints;
        } else if (par1EnumOptions == ModOptions.INGAME_WAYPOINTS) {
            this.showIngameWaypoints = !this.showIngameWaypoints;
        } else if (par1EnumOptions == ModOptions.REDSTONE) {
            this.displayRedstone = !this.displayRedstone;
        } else if (par1EnumOptions == ModOptions.DISTANCE) {
            this.distance = (this.distance + 1) % distanceTypes.length;
        } else if (par1EnumOptions == ModOptions.COORDS) {
            this.showCoords = !this.showCoords;
        } else if (par1EnumOptions == ModOptions.NORTH) {
            this.lockNorth = !this.lockNorth;
        } else if (par1EnumOptions == ModOptions.PLAYERS) {
            this.showPlayers = !this.showPlayers;
        } else if (par1EnumOptions == ModOptions.HOSTILE) {
            this.showHostile = !this.showHostile;
        } else if (par1EnumOptions == ModOptions.MOBS) {
            this.showMobs = !this.showMobs;
        } else if (par1EnumOptions == ModOptions.ITEMS) {
            this.showItems = !this.showItems;
        } else if (par1EnumOptions == ModOptions.ENTITIES) {
            this.showOther = !this.showOther;
        } else if (par1EnumOptions == ModOptions.CHUNK_GRID) {
            if (this.chunkGrid == COLORS.length - 1) {
                this.chunkGrid = -4;
            } else {
                if (this.chunkGrid < -1) {
                    this.chunkGrid = -1;
                }
                ++this.chunkGrid;
            }
        } else if (par1EnumOptions == ModOptions.SLIME_CHUNKS) {
            if (this.customSlimeSeedNeeded()) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiSlimeSeed(this.modMain, Minecraft.getMinecraft().currentScreen));
                return;
            }
            this.slimeChunks = !this.slimeChunks;
        } else if (par1EnumOptions == ModOptions.SAFE_MAP) {
            this.mapSafeMode = !this.mapSafeMode;
            this.modMain.getInterfaces().getMinimap().setToResetImage(true);
        } else if (par1EnumOptions == ModOptions.AA) {
            this.antiAliasing = !this.antiAliasing;
        } else if (par1EnumOptions == ModOptions.COLOURS) {
            if (!this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
                this.blockColours = (this.blockColours + 1) % blockColourTypes.length;
            } else {
                this.modMain.getSupportMods().worldmapSupport.openSettings();
            }
        } else if (par1EnumOptions == ModOptions.LIGHT) {
            if (!this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
                this.lighting = !this.lighting;
            } else {
                this.modMain.getSupportMods().worldmapSupport.openSettings();
            }
        } else if (par1EnumOptions == ModOptions.COMPASS) {
            this.compassOverWaypoints = !this.compassOverWaypoints;
        } else if (par1EnumOptions == ModOptions.BIOME) {
            this.showBiome = !this.showBiome;
        } else if (par1EnumOptions == ModOptions.ENTITY_HEIGHT) {
            this.showEntityHeight = !this.showEntityHeight;
        } else if (par1EnumOptions == ModOptions.FLOWERS) {
            this.showFlowers = !this.showFlowers;
        } else if (par1EnumOptions == ModOptions.KEEP_WP_NAMES) {
            this.keepWaypointNames = !this.keepWaypointNames;
        } else if (par1EnumOptions == ModOptions.ARROW_COLOUR) {
            ++this.arrowColour;
            if (this.arrowColour == this.arrowColours.length) {
                this.arrowColour = -1;
            }
        } else if (par1EnumOptions == ModOptions.SMOOTH_DOTS) {
            this.smoothDots = !this.smoothDots;
        } else if (par1EnumOptions == ModOptions.PLAYER_HEADS) {
            this.playerHeads = !this.playerHeads;
        } else if (par1EnumOptions == ModOptions.WORLD_MAP) {
            this.worldMap = !this.worldMap;
        } else if (par1EnumOptions == ModOptions.CAPES) {
            Patreon4.showCapes = !Patreon4.showCapes;
            Patreon4.saveSettings();
        } else if (par1EnumOptions == ModOptions.TERRAIN_DEPTH) {
            if (!this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
                this.terrainDepth = !this.terrainDepth;
            } else {
                this.modMain.getSupportMods().worldmapSupport.openSettings();
            }
        } else if (par1EnumOptions == ModOptions.TERRAIN_SLOPES) {
            if (!this.modMain.getSupportMods().shouldUseWorldMapChunks()) {
                if (this.terrainSlopes && !this.terrainSlopesExperiment) {
                    this.terrainSlopesExperiment = true;
                } else {
                    this.terrainSlopes = !this.terrainSlopes;
                    this.terrainSlopesExperiment = false;
                }
            } else {
                this.modMain.getSupportMods().worldmapSupport.openSettings();
            }
        } else if (par1EnumOptions == ModOptions.ALWAYS_ARROW) {
            this.alwaysArrow = !this.alwaysArrow;
        } else if (par1EnumOptions == ModOptions.BLOCK_TRANSPARENCY) {
            this.blockTransparency = !this.blockTransparency;
            this.modMain.getInterfaces().getMinimap().setToResetImage(true);
        } else if (par1EnumOptions == ModOptions.HIDE_WORLD_NAMES) {
            this.hideWorldNames = (this.hideWorldNames + 1) % 3;
        } else if (par1EnumOptions == ModOptions.OPEN_SLIME_SETTINGS) {
            this.openSlimeSettings = !this.openSlimeSettings;
        } else if (par1EnumOptions == ModOptions.ALWAYS_SHOW_DISTANCE) {
            this.alwaysShowDistance = !this.alwaysShowDistance;
        } else if (par1EnumOptions == ModOptions.PLAYER_NAMES) {
            this.playerNames = !this.playerNames;
        } else if (par1EnumOptions == ModOptions.SHOW_LIGHT_LEVEL) {
            this.showLightLevel = !this.showLightLevel;
        } else if (par1EnumOptions == ModOptions.RENDER_LAYER) {
            this.renderLayerIndex = (this.renderLayerIndex + 1) % ForgeEventHandler.OVERLAY_LAYERS.length;
        }
        this.saveSettings();
        if (Minecraft.getMinecraft().currentScreen != null) {
            Minecraft.getMinecraft().currentScreen.initGui();
        }
    }

    public void setOptionFloatValue(ModOptions options, float f) throws IOException {
        if (options == ModOptions.OPACITY) {
            this.minimapOpacity = f;
        }
        if (options == ModOptions.WAYPOINTS_SCALE) {
            this.waypointsScale = f;
        }
        if (options == ModOptions.DOTS_SCALE) {
            this.dotsScale = f;
        }
        if (options == ModOptions.WAYPOINTS_DISTANCE) {
            this.waypointsDistance = (int)f;
        }
        if (options == ModOptions.WAYPOINTS_DISTANCE_MIN) {
            this.waypointsDistanceMin = (int)f;
        }
        if (options == ModOptions.ARROW_SCALE) {
            this.arrowScale = f;
        }
        if (options == ModOptions.HEIGHT_LIMIT) {
            this.heightLimit = (int)f;
        }
        if (options == ModOptions.WAYPOINT_OPACITY_INGAME) {
            this.waypointOpacityIngame = (int)f;
        }
        if (options == ModOptions.WAYPOINT_OPACITY_MAP) {
            this.waypointOpacityMap = (int)f;
        }
        this.saveSettings();
    }

    public float getOptionFloatValue(ModOptions options) {
        if (options == ModOptions.OPACITY) {
            return this.minimapOpacity;
        }
        if (options == ModOptions.WAYPOINTS_SCALE) {
            return this.waypointsScale;
        }
        if (options == ModOptions.DOTS_SCALE) {
            return this.dotsScale;
        }
        if (options == ModOptions.WAYPOINTS_DISTANCE) {
            return this.waypointsDistance;
        }
        if (options == ModOptions.WAYPOINTS_DISTANCE_MIN) {
            return this.waypointsDistanceMin;
        }
        if (options == ModOptions.ARROW_SCALE) {
            return this.arrowScale;
        }
        if (options == ModOptions.HEIGHT_LIMIT) {
            return this.heightLimit;
        }
        if (options == ModOptions.WAYPOINT_OPACITY_INGAME) {
            return this.waypointOpacityIngame;
        }
        if (options == ModOptions.WAYPOINT_OPACITY_MAP) {
            return this.waypointOpacityMap;
        }
        return 1.0f;
    }

    public boolean minimapDisabled() {
        return (serverSettings & 1) != 1;
    }

    public boolean minimapDisplayPlayersDisabled() {
        return (serverSettings & 0x400) != 1024;
    }

    public boolean minimapDisplayMobsDisabled() {
        return (serverSettings & 0x800) != 2048;
    }

    public boolean minimapDisplayItemsDisabled() {
        return (serverSettings & 0x1000) != 4096;
    }

    public boolean minimapDisplayOtherDisabled() {
        return (serverSettings & 0x2000) != 8192;
    }

    public boolean caveMapsDisabled() {
        return (serverSettings & 0x4000) != 16384;
    }

    public boolean showOtherTeamDisabled() {
        return (serverSettings & 0x8000) != 32768;
    }

    public boolean showWaypointsDisabled() {
        return (serverSettings & 0x10000) != 65536;
    }

    public boolean deathpointsDisabled() {
        return (serverSettings & 0x200000) == 0;
    }

    public void resetServerSettings() {
        serverSettings = defaultSettings;
    }

    public static void setServerSettings() {
    }

    public boolean isPlayerNames() {
        return this.playerNames;
    }

    public boolean getTerrainSlopesExperiment() {
        return this.terrainSlopesExperiment;
    }

    static {
        ENCHANT_COLORS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        ENCHANT_COLOR_NAMES = new String[]{"gui.xaero_black", "gui.xaero_dark_blue", "gui.xaero_dark_green", "gui.xaero_dark_aqua", "gui.xaero_dark_red", "gui.xaero_dark_purple", "gui.xaero_gold", "gui.xaero_gray", "gui.xaero_dark_gray", "gui.xaero_blue", "gui.xaero_green", "gui.xaero_aqua", "gui.xaero_red", "gui.xaero_purple", "gui.xaero_yellow", "gui.xaero_white"};
        COLORS = new int[]{new Color(0, 0, 0, 255).hashCode(), new Color(0, 0, 170, 255).hashCode(), new Color(0, 170, 0, 255).hashCode(), new Color(0, 170, 170, 255).hashCode(), new Color(170, 0, 0, 255).hashCode(), new Color(170, 0, 170, 255).hashCode(), new Color(255, 170, 0, 255).hashCode(), new Color(170, 170, 170, 255).hashCode(), new Color(85, 85, 85, 255).hashCode(), new Color(85, 85, 255, 255).hashCode(), new Color(85, 255, 85, 255).hashCode(), new Color(85, 255, 255, 255).hashCode(), new Color(255, 0, 0, 255).hashCode(), new Color(255, 85, 255, 255).hashCode(), new Color(255, 255, 85, 255).hashCode(), new Color(255, 255, 255, 255).hashCode()};
        MINIMAP_SIZE = new String[]{"gui.xaero_tiny", "gui.xaero_small", "gui.xaero_medium", "gui.xaero_large"};
        keyBindZoom = new KeyBinding("gui.xaero_zoom_in", 23, "Xaero's Minimap");
        keyBindZoom1 = new KeyBinding("gui.xaero_zoom_out", 24, "Xaero's Minimap");
        newWaypoint = new KeyBinding("gui.xaero_new_waypoint", 48, "Xaero's Minimap");
        keyWaypoints = new KeyBinding("gui.xaero_waypoints_key", 22, "Xaero's Minimap");
        keyLargeMap = new KeyBinding("gui.xaero_enlarge_map", 44, "Xaero's Minimap");
        keyToggleMap = new KeyBinding("gui.xaero_toggle_map", 35, "Xaero's Minimap");
        keyToggleWaypoints = new KeyBinding("gui.xaero_toggle_waypoints", 0, "Xaero's Minimap");
        keyToggleSlimes = new KeyBinding("gui.xaero_toggle_slime", 0, "Xaero's Minimap");
        keyToggleGrid = new KeyBinding("gui.xaero_toggle_grid", 0, "Xaero's Minimap");
        keyInstantWaypoint = new KeyBinding("gui.xaero_instant_waypoint", 78, "Xaero's Minimap");
        keySwitchSet = new KeyBinding("gui.xaero_switch_waypoint_set", 0, "Xaero's Minimap");
        keyAllSets = new KeyBinding("gui.xaero_display_all_sets", 0, "Xaero's Minimap");
        minimapItemId = null;
        minimapItem = null;
        serverSlimeSeeds = new HashMap();
        distanceTypes = new String[]{"gui.xaero_off", "gui.xaero_looking_at", "gui.xaero_all"};
        blockColourTypes = new String[]{"gui.xaero_accurate", "gui.xaero_vanilla"};
        settingsButton = false;
        updateNotification = true;
    }
}

