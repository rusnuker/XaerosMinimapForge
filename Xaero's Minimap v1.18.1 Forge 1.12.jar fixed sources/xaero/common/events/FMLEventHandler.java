//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.events;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
import xaero.common.IXaeroMinimap;
import xaero.common.api.spigot.ServerWaypointStorage;
import xaero.common.controls.event.KeyEventHandler;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.waypoints.WaypointsManager;

public class FMLEventHandler {
    private IXaeroMinimap modMain;
    private MinimapProcessor minimap;
    protected WaypointsManager waypointsManager;
    private KeyEventHandler keyEventHandler;

    public FMLEventHandler(IXaeroMinimap modMain, KeyEventHandler keyEventHandler) {
        this.modMain = modMain;
        this.minimap = modMain.getInterfaces().getMinimap();
        this.waypointsManager = modMain.getWaypointsManager();
        this.keyEventHandler = keyEventHandler;
    }

    @SubscribeEvent
    public void handleClientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ServerWaypointStorage.update(this.modMain, this.waypointsManager);
            this.minimap.onClientTick();
        }
    }

    @SubscribeEvent
    public void handlePlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT && event.player == Minecraft.getMinecraft().player && event.phase == TickEvent.Phase.START) {
            this.waypointsManager.updateWorldIds();
            this.minimap.onPlayerTick();
            if (this.modMain.getSettings() != null && (this.modMain.getSettings().getDeathpoints() || this.modMain.getSettings().getShowWaypoints() || this.modMain.getSettings().getShowIngameWaypoints())) {
                this.waypointsManager.updateWaypoints();
            } else if (this.waypointsManager.getWaypoints() != null) {
                this.waypointsManager.setWaypoints(null);
            }
            Minecraft mc = Minecraft.getMinecraft();
            this.keyEventHandler.handleEvents(mc, this.modMain);
            this.playerTickPostOverridable();
        }
    }

    protected void playerTickPostOverridable() {
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().player != null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                this.keyEventHandler.onKeyInput(mc, this.modMain);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SubscribeEvent
    public void handleRenderTickEvent(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ArrayList<Integer> arrayList = this.minimap.getTexturesToDelete();
            synchronized (arrayList) {
                if (!this.minimap.getTexturesToDelete().isEmpty()) {
                    int toDelete = this.minimap.getTexturesToDelete().get(0);
                    GL11.glDeleteTextures((int)toDelete);
                    this.minimap.getTexturesToDelete().remove(0);
                }
            }
            this.minimap.setMainValues();
        }
    }
}

