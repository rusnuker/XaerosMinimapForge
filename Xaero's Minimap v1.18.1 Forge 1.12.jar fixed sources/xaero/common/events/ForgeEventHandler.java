//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.dto.RealmsServer
 *  com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen
 *  com.mojang.realmsclient.util.RealmsTasks$RealmsGetServerDetailsTask
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiScreenRealmsProxy
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.client.event.GuiScreenEvent$ActionPerformedEvent
 *  net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Pre
 *  net.minecraftforge.client.event.RenderPlayerEvent$Post
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.client.event.TextureStitchEvent$Post
 *  net.minecraftforge.event.entity.player.PlayerSetSpawnEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.events;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.util.RealmsTasks;
import java.io.IOException;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xaero.common.IXaeroMinimap;
import xaero.common.anim.OldAnimation;
import xaero.common.gui.GuiEditMode;
import xaero.common.gui.GuiUpdate;
import xaero.common.settings.ModSettings;
import xaero.patreon.GuiUpdateAll;
import xaero.patreon.Patreon4;

public class ForgeEventHandler {
    public static final RenderGameOverlayEvent.ElementType[] OVERLAY_LAYERS = new RenderGameOverlayEvent.ElementType[]{RenderGameOverlayEvent.ElementType.ALL, RenderGameOverlayEvent.ElementType.HELMET, RenderGameOverlayEvent.ElementType.HOTBAR, RenderGameOverlayEvent.ElementType.CROSSHAIRS, RenderGameOverlayEvent.ElementType.BOSSHEALTH, RenderGameOverlayEvent.ElementType.TEXT, RenderGameOverlayEvent.ElementType.POTION_ICONS, RenderGameOverlayEvent.ElementType.SUBTITLES, RenderGameOverlayEvent.ElementType.CHAT};
    private IXaeroMinimap modMain;
    private Object lastGuiOpen;
    private long died;
    GuiScreen lastClickOn;
    private int deathCounter;
    private Field realmsTaskField;
    private Field realmsTaskServerField;
    private boolean askedToUpdate;

    public ForgeEventHandler(IXaeroMinimap modMain) {
        this.modMain = modMain;
        this.died = -1L;
    }

    @SubscribeEvent
    public void handleGuiOpenEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiOptions) {
            if (!ModSettings.settingsButton) {
                return;
            }
            event.setGui((GuiScreen)this.modMain.getGuiHelper().getMyOptions());
            try {
                this.modMain.getSettings().saveSettings();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getGui() instanceof GuiMainMenu || event.getGui() instanceof GuiMultiplayer) {
            this.modMain.getSettings().resetServerSettings();
        }
        if (event.getGui() instanceof GuiGameOver) {
            ++this.deathCounter;
            if ((this.deathCounter & 1) == 0) {
                this.modMain.getWaypointsManager().createDeathpoint((EntityPlayer)Minecraft.getMinecraft().player);
            }
        }
        if (event.getGui() instanceof GuiScreenRealmsProxy && ((GuiScreenRealmsProxy)event.getGui()).getProxy() instanceof RealmsLongRunningMcoTaskScreen) {
            try {
                RealmsTasks.RealmsGetServerDetailsTask realmsTask;
                RealmsServer realm;
                RealmsLongRunningMcoTaskScreen realmsTaskScreen;
                Object task;
                if (this.realmsTaskField == null) {
                    this.realmsTaskField = RealmsLongRunningMcoTaskScreen.class.getDeclaredField("task");
                    this.realmsTaskField.setAccessible(true);
                }
                if (this.realmsTaskServerField == null) {
                    this.realmsTaskServerField = RealmsTasks.RealmsGetServerDetailsTask.class.getDeclaredField("server");
                    this.realmsTaskServerField.setAccessible(true);
                }
                if ((task = this.realmsTaskField.get(realmsTaskScreen = (RealmsLongRunningMcoTaskScreen)((GuiScreenRealmsProxy)event.getGui()).getProxy())) instanceof RealmsTasks.RealmsGetServerDetailsTask && (realm = (RealmsServer)this.realmsTaskServerField.get(realmsTask = (RealmsTasks.RealmsGetServerDetailsTask)task)) != null && (this.modMain.getWaypointsManager().getLatestRealm() == null || realm.id != this.modMain.getWaypointsManager().getLatestRealm().id)) {
                    this.modMain.getWaypointsManager().setLatestRealm(realm);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.lastGuiOpen = event.getGui();
    }

    protected void handleRenderGameOverlayEventPreOverridable(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == OVERLAY_LAYERS[this.modMain.getSettings().renderLayerIndex]) {
            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.modMain.getInterfaceRenderer().renderInterfaces(event.getPartialTicks());
            this.modMain.getInterfaces().getMinimapInterface().getWaypointsGuiRenderer().drawSetChange(event.getResolution());
            OldAnimation.tick();
        }
    }

    @SubscribeEvent
    public void handleRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
        if (Keyboard.isKeyDown((int)1)) {
            GuiEditMode.cancel(this.modMain.getInterfaces());
        }
        this.handleRenderGameOverlayEventPreOverridable(event);
    }

    @SubscribeEvent
    public void handleGuiScreenActionPerformedEvent(GuiScreenEvent.ActionPerformedEvent event) {
        if (event.getGui() instanceof GuiGameOver || event.getGui() instanceof GuiYesNo && this.lastClickOn instanceof GuiGameOver) {
            this.died = System.currentTimeMillis();
        }
        this.lastClickOn = event.getGui();
    }

    @SubscribeEvent
    public void handleClientSendChatEvent(ClientChatEvent e) {
        if (e.getMessage().startsWith("xaero_waypoint_add:")) {
            String[] args = e.getMessage().replaceAll("\u00a7.", "").split(":");
            e.setMessage("");
            this.modMain.getWaypointSharing().onWaypointAdd(args);
        }
    }

    @SubscribeEvent
    public void handleClientChatReceivedEvent(ClientChatReceivedEvent e) {
        String text = e.getMessage().getFormattedText();
        if (text.contains("xaero_waypoint:")) {
            this.modMain.getWaypointSharing().onWaypointReceived(text, e);
        }
        if (text.contains("\u00a7c \u00a7r\u00a75 \u00a7r\u00a71 \u00a7r\u00a7f")) {
            String code = text.substring(text.indexOf("f") + 1);
            code = code.replace("\u00a7", "").replace("r", "").replace(" ", "");
            this.modMain.getSettings().resetServerSettings();
            this.modMain.getSettings();
            ModSettings.serverSettings &= Integer.parseInt(code);
            System.out.println("Code: " + code);
        }
    }

    @SubscribeEvent
    public void handleRenderWorldLastEvent(RenderWorldLastEvent event) {
        if (Minecraft.getMinecraft().world == this.modMain.getInterfaces().getMinimap().mainWorld) {
            this.modMain.getInterfaces().getMinimapInterface().getWaypointsIngameRenderer().render(event.getPartialTicks());
        }
    }

    protected void onOutdatedOverridable() {
        if (Patreon4.patronPledge >= 5) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiUpdateAll());
        } else {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiUpdate(this.modMain, "A newer version of Xaero's Minimap is available!"));
        }
        System.out.println("Minimap is outdated!");
    }

    @SubscribeEvent
    public void handleDrawScreenEventPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiUpdate) {
            this.askedToUpdate = true;
        } else if (!this.askedToUpdate && this.modMain.isOutdated() && event.getGui() instanceof GuiMainMenu) {
            this.onOutdatedOverridable();
        } else if (this.modMain.isOutdated()) {
            this.modMain.setOutdated(false);
        }
    }

    @SubscribeEvent
    public void handleTextureStitchEventPost(TextureStitchEvent.Post event) {
        this.modMain.getInterfaces().getMinimap().getMinimapWriter().setClearBlockColours(true);
    }

    @SubscribeEvent
    public void handleRenderPlayerEventPost(RenderPlayerEvent.Post event) {
        Patreon4.renderCape(this.modMain.getFileLayoutID(), event);
    }

    @SubscribeEvent
    public void handlePlayerSetSpawnEvent(PlayerSetSpawnEvent event) {
        this.modMain.getWaypointsManager().setCurrentSpawn(event.getNewSpawn());
    }

    public Object getLastGuiOpen() {
        return this.lastGuiOpen;
    }

    public long getDied() {
        return this.died;
    }
}

