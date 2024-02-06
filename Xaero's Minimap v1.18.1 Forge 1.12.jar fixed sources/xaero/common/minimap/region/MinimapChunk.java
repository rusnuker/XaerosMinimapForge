//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL15
 */
package xaero.common.minimap.region;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import xaero.common.minimap.region.MinimapTile;

public class MinimapChunk {
    private static final int SIZE_TILES = 4;
    private static final int BUFFER_SIZE = 16384;
    public static final int LIGHT_LEVELS = 5;
    private boolean blockTextureUpload;
    private int X;
    private int Z;
    private boolean hasSomething;
    private MinimapTile[][] tiles;
    private int[] glTexture;
    private boolean[] refreshRequired;
    private boolean refreshed;
    private ByteBuffer[] buffer;
    private boolean changed;
    private int levelsBuffered = 0;
    private int workaroundPBO;

    public MinimapChunk(int X, int Z) {
        this.X = X;
        this.Z = Z;
        this.tiles = new MinimapTile[4][4];
        this.glTexture = new int[5];
        this.refreshRequired = new boolean[5];
        this.buffer = new ByteBuffer[5];
    }

    public void reset(int X, int Z) {
        int i;
        this.X = X;
        this.Z = Z;
        this.hasSomething = false;
        for (i = 0; i < this.glTexture.length; ++i) {
            this.glTexture[i] = 0;
            this.refreshRequired[i] = false;
            if (this.buffer[i] == null) continue;
            this.buffer[i].clear();
        }
        this.refreshed = false;
        this.changed = false;
        this.levelsBuffered = 0;
        for (i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles.length; ++j) {
                this.tiles[i][j] = null;
            }
        }
        this.blockTextureUpload = false;
    }

    public void recycleTiles() {
        for (int i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles.length; ++j) {
                MinimapTile tile = this.tiles[i][j];
                if (tile == null) continue;
                if (!tile.isWasTransfered()) {
                    tile.recycle();
                    continue;
                }
                tile.setWasTransfered(false);
            }
        }
    }

    public int getLevelToRefresh(int currentLevel) {
        if (this.refreshed || this.levelsBuffered == 0 || currentLevel == -1) {
            return -1;
        }
        int prev = currentLevel - 1;
        if (prev < 0) {
            prev = this.levelsBuffered - 1;
        }
        int i = currentLevel;
        while (true) {
            if (this.refreshRequired[i]) {
                return i;
            }
            if (i == prev) break;
            i = (i + 1) % this.levelsBuffered;
        }
        this.refreshed = true;
        return -1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void bindTexture(int level) {
        MinimapChunk minimapChunk = this;
        synchronized (minimapChunk) {
            int levelToRefresh;
            if (!this.hasSomething) {
                GlStateManager.bindTexture((int)0);
                return;
            }
            if (!this.blockTextureUpload && (levelToRefresh = this.getLevelToRefresh(Math.min(level, this.levelsBuffered - 1))) != -1) {
                boolean result = false;
                if (this.glTexture[levelToRefresh] == 0) {
                    this.glTexture[levelToRefresh] = GL11.glGenTextures();
                    result = true;
                }
                GlStateManager.bindTexture((int)this.glTexture[levelToRefresh]);
                if (result) {
                    GL11.glTexParameteri((int)3553, (int)33085, (int)0);
                    GL11.glTexParameterf((int)3553, (int)33082, (float)0.0f);
                    GL11.glTexParameterf((int)3553, (int)33083, (float)0.0f);
                    GL11.glTexParameterf((int)3553, (int)34049, (float)0.0f);
                    GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
                    GL11.glTexParameteri((int)3553, (int)10242, (int)33071);
                    GL11.glTexParameteri((int)3553, (int)10243, (int)33071);
                    GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)64, (int)64, (int)0, (int)32993, (int)32821, (ByteBuffer)null);
                }
                if (this.workaroundPBO == 0) {
                    this.workaroundPBO = GL15.glGenBuffers();
                }
                GL15.glBindBuffer((int)35052, (int)this.workaroundPBO);
                GL15.glBufferData((int)35052, (long)16384L, (int)35040);
                ByteBuffer mappedPBO = GL15.glMapBuffer((int)35052, (int)35001, (long)16384L, null);
                mappedPBO.put(this.buffer[levelToRefresh]);
                GL15.glUnmapBuffer((int)35052);
                GL11.glTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)64, (int)64, (int)32993, (int)32821, (long)0L);
                GL15.glBindBuffer((int)35052, (int)0);
                this.refreshRequired[levelToRefresh] = false;
            }
            if (this.glTexture[level] != 0) {
                GlStateManager.bindTexture((int)this.glTexture[level]);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateBuffers(int levelsToLoad) {
        this.refreshed = true;
        for (int l = 0; l < levelsToLoad; ++l) {
            this.refreshRequired[l] = false;
            if (this.buffer[l] != null) continue;
            this.buffer[l] = ByteBuffer.allocate(16384);
        }
        byte[][] bytes = new byte[levelsToLoad][16384];
        for (int o = 0; o < this.tiles.length; ++o) {
            int offX = o * 16;
            for (int p = 0; p < this.tiles.length; ++p) {
                MinimapTile tile = this.tiles[o][p];
                if (tile == null) continue;
                int offZ = p * 16;
                for (int z = 0; z < 16; ++z) {
                    for (int x = 0; x < 16; ++x) {
                        for (int i = 0; i < levelsToLoad; ++i) {
                            this.putColour(offX + x, offZ + z, tile.getRed(i, x, z), tile.getGreen(i, x, z), tile.getBlue(i, x, z), bytes[i], 64);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < levelsToLoad; ++i) {
            MinimapChunk minimapChunk = this;
            synchronized (minimapChunk) {
                this.blockTextureUpload = true;
            }
            this.buffer[i].clear();
            this.buffer[i].put(bytes[i]);
            this.buffer[i].flip();
            this.refreshRequired[i] = true;
            minimapChunk = this;
            synchronized (minimapChunk) {
                this.blockTextureUpload = false;
                continue;
            }
        }
        this.refreshed = false;
    }

    public void putColour(int x, int y, int red, int green, int blue, byte[] texture, int size) {
        int pos = (y * size + x) * 4;
        texture[pos] = -1;
        texture[++pos] = (byte)red;
        texture[++pos] = (byte)green;
        texture[++pos] = (byte)blue;
    }

    public void copyBuffer(int level, ByteBuffer toCopy) {
        if (this.buffer[level] == null) {
            this.buffer[level] = ByteBuffer.allocate(16384);
        } else {
            this.buffer[level].clear();
        }
        this.buffer[level].put(toCopy);
        this.buffer[level].flip();
    }

    public int getLevelsBuffered() {
        return this.levelsBuffered;
    }

    public boolean isHasSomething() {
        return this.hasSomething;
    }

    public void setHasSomething(boolean hasSomething) {
        this.hasSomething = hasSomething;
    }

    public int getX() {
        return this.X;
    }

    public int getZ() {
        return this.Z;
    }

    public int getGlTexture(int l) {
        return this.glTexture[l];
    }

    public void setGlTexture(int l, int t) {
        this.glTexture[l] = t;
    }

    public MinimapTile getTile(int x, int z) {
        return this.tiles[x][z];
    }

    public void setTile(int x, int z, MinimapTile t) {
        this.tiles[x][z] = t;
    }

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void setLevelsBuffered(int levelsBuffered) {
        this.levelsBuffered = levelsBuffered;
    }

    public boolean isBlockTextureUpload() {
        return this.blockTextureUpload;
    }

    public void setBlockTextureUpload(boolean blockTextureUpload) {
        this.blockTextureUpload = blockTextureUpload;
    }

    public boolean isRefreshRequired(int l) {
        return this.refreshRequired[l];
    }

    public void setRefreshRequired(int l, boolean r) {
        this.refreshRequired[l] = r;
    }

    public ByteBuffer getBuffer(int l) {
        return this.buffer[l];
    }
}

