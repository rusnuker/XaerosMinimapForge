//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.gui.GuiYesNoCallback
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraft.util.text.event.ClickEvent
 *  net.minecraft.util.text.event.ClickEvent$Action
 *  net.minecraft.util.text.event.HoverEvent
 *  net.minecraft.util.text.event.HoverEvent$Action
 *  net.minecraft.world.DimensionType
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.common.DimensionManager
 */
package xaero.common.minimap.waypoints;

import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.DimensionManager;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiAddWaypoint;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointWorldContainer;

public class WaypointSharingHandler
implements GuiYesNoCallback {
    public static final String WAYPOINT_SHARE_PREFIX = "xaero_waypoint:";
    public static final String WAYPOINT_ADD_PREFIX = "xaero_waypoint_add:";
    private IXaeroMinimap modMain;
    private GuiScreen parent;
    private Waypoint w;
    private WaypointWorld wWorld;

    public WaypointSharingHandler(IXaeroMinimap modMain) {
        this.modMain = modMain;
    }

    public void shareWaypoint(GuiScreen parent, Waypoint w, WaypointWorld wWorld) {
        this.parent = parent;
        this.w = w;
        this.wWorld = wWorld;
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, I18n.format((String)"gui.xaero_share_msg1", (Object[])new Object[0]), I18n.format((String)"gui.xaero_share_msg2", (Object[])new Object[0]), 0));
    }

    public void onWaypointReceived(String text, ClientChatReceivedEvent e) {
        String[] args = text.substring(text.indexOf(WAYPOINT_SHARE_PREFIX)).split(":");
        TextComponentString component = null;
        if (args.length < 9) {
            System.out.println("Incorrect format of the shared waypoint! Error: 0");
        } else {
            String newText;
            block11: {
                String playerName = text.substring(0, text.indexOf(WAYPOINT_SHARE_PREFIX));
                int lastGreater = playerName.lastIndexOf(">");
                if (lastGreater != -1) {
                    playerName = playerName.substring(0, lastGreater).replaceFirst("<", "");
                }
                newText = playerName + " shared a waypoint called \"" + I18n.format((String)Waypoint.getStringFromStringSafe(args[1], "^col^"), (Object[])new Object[0]) + "\"";
                if (args.length > 9 && args[9].startsWith("Internal_")) {
                    try {
                        String details = args[9].substring(9, args[9].lastIndexOf("_")).replace("^col^", ":");
                        newText = newText + " from ";
                        if (details.startsWith("dim%")) {
                            if (details.length() == 4) {
                                newText = newText + "an unknown dimension";
                                break block11;
                            }
                            Integer dimId = this.modMain.getWaypointsManager().getDimensionForDirectoryName(details);
                            if (dimId == null) {
                                newText = newText + "an unknown dimension";
                                break block11;
                            }
                            DimensionType dt = null;
                            try {
                                dt = DimensionManager.getProviderType((int)dimId);
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                // empty catch block
                            }
                            newText = dt == null ? newText + "an unknown dimension" : newText + dt.getName();
                            break block11;
                        }
                        newText = newText + details;
                    }
                    catch (IndexOutOfBoundsException details) {
                        // empty catch block
                    }
                }
            }
            newText = newText + "! \u00a72\u00a7n[Add]";
            newText = newText.replaceAll("\u00a7r", "\u00a7r\u00a77").replaceAll("\u00a7f", "\u00a77");
            component = new TextComponentString(newText);
            TextComponentString hoverComponent = new TextComponentString(args[3] + ", " + args[4] + ", " + args[5]);
            component.getStyle().setColor(TextFormatting.GRAY).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, WAYPOINT_ADD_PREFIX + text.substring(text.indexOf(WAYPOINT_SHARE_PREFIX) + WAYPOINT_SHARE_PREFIX.length()))).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)hoverComponent));
        }
        e.setMessage(component);
    }

    public void onWaypointAdd(String[] args) {
        String waypointName = Waypoint.getStringFromStringSafe(args[1], "^col^");
        if (waypointName.length() < 1 || waypointName.length() > 32) {
            System.out.println("Incorrect format of the shared waypoint! Error: 1");
            return;
        }
        String waypointSymbol = Waypoint.getStringFromStringSafe(args[2], "^col^");
        if (waypointSymbol.length() < 1 || waypointSymbol.length() > 2) {
            System.out.println("Incorrect format of the shared waypoint! Error: 2");
            return;
        }
        try {
            int x = Integer.parseInt(args[3]);
            int y = Integer.parseInt(args[4]);
            int z = Integer.parseInt(args[5]);
            int color = Integer.parseInt(args[6]);
            String yawString = args[8];
            if (yawString.length() > 4) {
                System.out.println("Incorrect format of the shared waypoint! Error: 4");
                return;
            }
            int yaw = Integer.parseInt(yawString);
            boolean rotation = args[7].equals("true");
            Waypoint w = new Waypoint(x, y, z, waypointName, waypointSymbol, color, 0);
            w.setRotation(rotation);
            w.setYaw(yaw);
            String externalContainerId = this.modMain.getWaypointsManager().getCurrentContainerID().split("/")[0];
            WaypointWorld externalWorld = this.modMain.getWaypointsManager().getCurrentWorld();
            String parentContainerId = externalContainerId;
            WaypointWorld currentWorld = externalWorld;
            if (args.length > 9) {
                String worldDetails = args[9];
                if (worldDetails.length() > 9 && worldDetails.startsWith("Internal_")) {
                    String subContainers;
                    int divider = worldDetails.lastIndexOf(95);
                    if (divider < 1 || divider == worldDetails.length() - 1) {
                        System.out.println("Incorrect format of the shared waypoint! Error: 5");
                        return;
                    }
                    String worldId = worldDetails.substring(divider + 1);
                    if (!worldId.replaceAll("[^a-zA-Z0-9,-]+", "").equals(worldId)) {
                        System.out.println("Incorrect format of the shared waypoint! Error: 7");
                        return;
                    }
                    boolean destinationDimensionExists = true;
                    try {
                        subContainers = worldDetails.substring(9, divider);
                    }
                    catch (IndexOutOfBoundsException eoobe) {
                        subContainers = null;
                    }
                    parentContainerId = this.modMain.getWaypointsManager().getAutoRootContainerID();
                    String containerId = null;
                    if (subContainers != null) {
                        CharSequence[] subContainersArgs = (subContainers = subContainers.replace("^col^", ":")).split("/");
                        if (subContainersArgs.length > 1) {
                            System.out.println("Incorrect format of the shared waypoint! Error: 8");
                            return;
                        }
                        for (int i = 0; i < subContainersArgs.length; ++i) {
                            String s = subContainersArgs[i];
                            if (!s.isEmpty()) continue;
                            System.out.println("Incorrect format of the shared waypoint! Error: 11");
                            return;
                        }
                        String dimContainer = subContainersArgs[0];
                        Integer dimId = null;
                        if (!dimContainer.startsWith("dim%")) {
                            int[] dims;
                            if (!dimContainer.replaceAll("[^a-zA-Z0-9_]+", "").equals(dimContainer)) {
                                System.out.println("Incorrect format of the shared waypoint! Error: 18");
                                return;
                            }
                            DimensionType dt = this.modMain.getWaypointsManager().findDimensionType(dimContainer);
                            if (dt != null && (dims = DimensionManager.getDimensions((DimensionType)dt)).length > 0) {
                                dimId = dims[0];
                            }
                        } else {
                            dimId = this.modMain.getWaypointsManager().getDimensionForDirectoryName(dimContainer);
                        }
                        if (dimId == null) {
                            System.out.println("Destination dimension doesn't exists! Handling waypoint as external.");
                            parentContainerId = externalContainerId;
                            currentWorld = externalWorld;
                            destinationDimensionExists = false;
                        } else {
                            subContainersArgs[0] = this.modMain.getWaypointsManager().getDimensionDirectoryName(dimId);
                            subContainers = String.join((CharSequence)"/", subContainersArgs);
                            containerId = parentContainerId + "/" + subContainers;
                            WaypointWorldContainer rootContainer = this.modMain.getWaypointsManager().getWorldContainer(parentContainerId);
                            rootContainer.renameOldContainer(containerId);
                        }
                    } else {
                        containerId = parentContainerId;
                    }
                    if (destinationDimensionExists) {
                        String autoWorldId = this.modMain.getWaypointsManager().getAutoWorldID();
                        if (worldId.equals("waypoints")) {
                            worldId = autoWorldId;
                        } else if (autoWorldId.equals("waypoints")) {
                            worldId = "waypoints";
                        }
                        File securityTest = new File(this.modMain.getWaypointsFolder(), containerId + "/" + worldId + (worldId.equals("waypoints") ? "" : "_1") + ".txt");
                        try {
                            if (!securityTest.getPath().equals(securityTest.getCanonicalPath())) {
                                System.out.println("Dangerously incorrect format of the shared waypoint! Error: 10");
                                return;
                            }
                        }
                        catch (IOException e) {
                            System.out.println("IO error adding a shared waypoint!");
                            return;
                        }
                        currentWorld = this.modMain.getWaypointsManager().getWorld(containerId, worldId);
                    }
                } else if (!worldDetails.equals("External")) {
                    System.out.println("Incorrect format of the shared waypoint! Error: 12");
                    return;
                }
            }
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiAddWaypoint(this.modMain, null, w, parentContainerId, currentWorld));
        }
        catch (NumberFormatException nfe) {
            System.out.println("Incorrect format of the shared waypoint! Error: 3");
            return;
        }
    }

    public void confirmClicked(boolean p_confirmResult_1_, int p_confirmResult_2_) {
        switch (p_confirmResult_2_) {
            case 0: {
                String worldDetails;
                WaypointWorldContainer autoRootContainer;
                if (!p_confirmResult_1_) break;
                WaypointWorldContainer rootContainer = this.wWorld.getContainer().getRootContainer();
                if (rootContainer == (autoRootContainer = this.modMain.getWaypointsManager().getAutoWorld().getContainer().getRootContainer())) {
                    String details;
                    String containerId = this.wWorld.getContainer().getKey();
                    int firstSlashIndex = containerId.indexOf("/");
                    if (firstSlashIndex != -1) {
                        String subContainers = containerId.substring(firstSlashIndex + 1);
                        CharSequence[] subContainersSplit = subContainers.split("/");
                        if (subContainersSplit[0].equals("dim%0")) {
                            subContainersSplit[0] = "overworld";
                        } else if (subContainersSplit[0].equals("dim%-1")) {
                            subContainersSplit[0] = "the_nether";
                        } else if (((String)subContainersSplit[0]).equals("dim%1")) {
                            subContainersSplit[0] = "the_end";
                        }
                        subContainers = String.join((CharSequence)"/", subContainersSplit);
                        details = subContainers.replace(":", "^col^") + "_" + this.wWorld.getId();
                    } else {
                        details = this.wWorld.getId();
                    }
                    worldDetails = "Internal_" + details;
                } else {
                    worldDetails = "External";
                }
                Minecraft.getMinecraft().currentScreen.sendChatMessage(WAYPOINT_SHARE_PREFIX + this.w.getNameSafe("^col^") + ":" + this.w.getSymbolSafe("^col^") + ":" + this.w.getX() + ":" + this.w.getY() + ":" + this.w.getZ() + ":" + this.w.getColor() + ":" + this.w.isRotation() + ":" + this.w.getYaw() + ":" + worldDetails, true);
                Minecraft.getMinecraft().displayGuiScreen(null);
                return;
            }
        }
        Minecraft.getMinecraft().displayGuiScreen(this.parent);
    }
}

