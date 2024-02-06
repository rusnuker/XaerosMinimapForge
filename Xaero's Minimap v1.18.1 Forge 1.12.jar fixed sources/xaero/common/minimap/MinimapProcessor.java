//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GLContext
 */
package xaero.common.minimap;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GLContext;
import xaero.common.IXaeroMinimap;
import xaero.common.anim.OldAnimation;
import xaero.common.minimap.MinimapRadar;
import xaero.common.minimap.render.MinimapFBORenderer;
import xaero.common.minimap.render.MinimapSafeModeRenderer;
import xaero.common.minimap.write.MinimapWriter;
import xaero.common.settings.ModSettings;

public class MinimapProcessor {
    public static final boolean DEBUG = false;
    public static final int FRAME = 9;
    private IXaeroMinimap modMain;
    private Throwable crashedWith;
    public static MinimapProcessor instance;
    private MinimapWriter minimapWriter;
    private MinimapFBORenderer minimapFBORenderer;
    private MinimapSafeModeRenderer minimapSafeModeRenderer;
    private MinimapRadar entityRadar;
    private ArrayList<Integer> texturesToDelete;
    private double minimapZoom;
    private boolean enlargedMap = false;
    protected final int[] minimapSizes;
    protected final int[] bufferSizes;
    private int minimapWidth = 0;
    private int minimapHeight = 0;
    private boolean toResetImage;
    public final Object mainStuffSync;
    public World mainWorld;
    public double mainPlayerX;
    public double mainPlayerY;
    public double mainPlayerZ;

    public MinimapProcessor(IXaeroMinimap modMain, MinimapWriter minimapWriter, MinimapFBORenderer minimapFBORenderer, MinimapSafeModeRenderer minimapSafeModeRenderer, MinimapRadar entityRadar) {
        this.modMain = modMain;
        this.minimapWriter = minimapWriter;
        this.minimapFBORenderer = minimapFBORenderer;
        this.minimapSafeModeRenderer = minimapSafeModeRenderer;
        this.entityRadar = entityRadar;
        this.texturesToDelete = new ArrayList();
        this.minimapZoom = 1.0;
        this.minimapSizes = new int[]{112, 168, 224, 336};
        this.bufferSizes = new int[]{128, 256, 256, 512};
        instance = this;
        this.toResetImage = true;
        this.mainStuffSync = new Object();
    }

    public boolean usingFBO() {
        return this.minimapFBORenderer.isLoadedFBO() && !this.modMain.getSettings().mapSafeMode;
    }

    public Throwable getCrashedWith() {
        return this.crashedWith;
    }

    public void setCrashedWith(Throwable crashedWith) {
        if (this.crashedWith == null) {
            this.crashedWith = crashedWith;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void requestTextureDelete(int texture) {
        ArrayList<Integer> arrayList = this.texturesToDelete;
        synchronized (arrayList) {
            this.texturesToDelete.add(texture);
        }
    }

    public double getMinimapZoom() {
        return this.minimapZoom;
    }

    public void updateZoom() {
        double off;
        double target = this.modMain.getSettings().zooms[this.modMain.getSettings().zoom] * (this.modMain.getSettings().caveZoom > 0 && this.minimapWriter.getLoadedCaving() != -1 ? (float)(1 + this.modMain.getSettings().caveZoom) : 1.0f);
        if (target > (double)this.modMain.getSettings().zooms[this.modMain.getSettings().zooms.length - 1]) {
            target = this.modMain.getSettings().zooms[this.modMain.getSettings().zooms.length - 1];
        }
        off = (off = target - this.minimapZoom) > 0.01 || off < -0.01 ? (double)((float)OldAnimation.animate(off, 0.8)) : 0.0;
        this.minimapZoom = target - off;
    }

    public MinimapWriter getMinimapWriter() {
        return this.minimapWriter;
    }

    public MinimapFBORenderer getMinimapFBORenderer() {
        return this.minimapFBORenderer;
    }

    public MinimapSafeModeRenderer getMinimapSafeModeRenderer() {
        return this.minimapSafeModeRenderer;
    }

    public boolean canUseFrameBuffer() {
        return GLContext.getCapabilities().OpenGL14 && (GLContext.getCapabilities().GL_ARB_framebuffer_object || GLContext.getCapabilities().GL_EXT_framebuffer_object || GLContext.getCapabilities().OpenGL30);
    }

    public int getFBOBufferSize() {
        return 512;
    }

    public int[] getMinimapWidthAndBuffer() {
        int[] nArray;
        if (this.enlargedMap) {
            int[] nArray2 = new int[2];
            nArray2[0] = 448;
            nArray = nArray2;
            nArray2[1] = 512;
        } else {
            int[] nArray3 = new int[2];
            nArray3[0] = this.minimapSizes[this.modMain.getSettings().getMinimapSize()];
            nArray = nArray3;
            nArray3[1] = this.bufferSizes[this.modMain.getSettings().getMinimapSize()];
        }
        return nArray;
    }

    public void onClientTick() {
        World world = null;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player != null) {
            world = player.world;
        }
        if (world != null && player != null && this.modMain.getSettings().getMinimap()) {
            this.entityRadar.updateRadar(world, (EntityPlayer)player);
        }
    }

    public void onPlayerTick() {
        if (this.modMain.getWaypointsManager().getCurrentContainerID() != null && this.modMain.getWaypointsManager().getCurrentWorldID() != null) {
            this.minimapWriter.setSeedForLoading(this.modMain.getSettings().getSlimeChunksSeed());
        }
    }

    public void onRender(int x, int y, int width, int height, int scale, int size, float partial) {
        if (this.crashedWith != null) {
            throw new RuntimeException("Xaero's Minimap has crashed! Please contact the author at planetminecraft.com/member/xaero96 or minecraftforum.net/members/xaero96", this.crashedWith);
        }
        try {
            if (this.minimapFBORenderer.isLoadedFBO() && !this.canUseFrameBuffer()) {
                this.minimapFBORenderer.setLoadedFBO(false);
                this.minimapFBORenderer.deleteFramebuffers();
                this.toResetImage = true;
            }
            if (!this.getMinimapFBORenderer().isLoadedFBO() && !this.modMain.getSettings().mapSafeMode && this.canUseFrameBuffer()) {
                this.minimapFBORenderer.loadFrameBuffer();
            }
            if (this.usingFBO()) {
                this.minimapFBORenderer.renderMinimap(this, x, y, width, height, scale, size, partial);
            } else {
                this.minimapSafeModeRenderer.renderMinimap(this, x, y, width, height, scale, size, partial);
            }
        }
        catch (Throwable e) {
            this.setCrashedWith(e);
        }
    }

    public static boolean hasMinimapItem(EntityPlayer player) {
        for (int i = 0; i < 9; ++i) {
            if (player.inventory.mainInventory.get(i) == null || ((ItemStack)player.inventory.mainInventory.get(i)).getItem() != ModSettings.minimapItem) continue;
            return true;
        }
        return false;
    }

    public int getMinimapWidth() {
        return this.minimapWidth;
    }

    public int setMinimapWidth(int minimapWidth) {
        this.minimapWidth = minimapWidth;
        return minimapWidth;
    }

    public int getMinimapHeight() {
        return this.minimapHeight;
    }

    public int setMinimapHeight(int minimapHeight) {
        this.minimapHeight = minimapHeight;
        return minimapHeight;
    }

    public boolean isToResetImage() {
        return this.toResetImage;
    }

    public void setToResetImage(boolean toResetImage) {
        this.toResetImage = toResetImage;
    }

    public MinimapRadar getEntityRadar() {
        return this.entityRadar;
    }

    public boolean isEnlargedMap() {
        return this.enlargedMap;
    }

    public void setEnlargedMap(boolean enlargedMap) {
        this.enlargedMap = enlargedMap;
    }

    public ArrayList<Integer> getTexturesToDelete() {
        return this.texturesToDelete;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMainValues() {
        Object object = this.mainStuffSync;
        synchronized (object) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player != null) {
                this.mainWorld = player.world;
                this.mainPlayerX = player.posX;
                this.mainPlayerY = player.posY;
                this.mainPlayerZ = player.posZ;
            } else {
                this.mainWorld = null;
            }
        }
    }
}

