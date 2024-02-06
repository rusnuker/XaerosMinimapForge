/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.minimap.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import xaero.common.settings.ModSettings;

public class MinimapTile {
    public static List<MinimapTile> recycled = new ArrayList<MinimapTile>();
    private boolean wasTransfered;
    private int[][][] red = new int[5][16][16];
    private int[][][] green = new int[5][16][16];
    private int[][][] blue = new int[5][16][16];
    private boolean chunkGrid;
    private boolean slimeChunk;
    private int X;
    private int Z;
    private boolean success = true;
    private int[] lastHeights;
    private double[] lastSlopeShades;

    public static MinimapTile getANewTile(ModSettings settings, int X, int Z, Long seed) {
        if (recycled.isEmpty()) {
            return new MinimapTile(settings, X, Z, seed);
        }
        MinimapTile t = recycled.remove(0);
        t.create(settings, X, Z, seed);
        return t;
    }

    public MinimapTile(ModSettings settings, int X, int Z, Long seed) {
        this.create(settings, X, Z, seed);
    }

    private void create(ModSettings settings, int X, int Z, Long seed) {
        this.X = X;
        this.Z = Z;
        this.chunkGrid = (X & 1) == (Z & 1);
        this.slimeChunk = MinimapTile.isSlimeChunk(settings, X, Z, seed);
        this.lastHeights = new int[16];
        this.lastSlopeShades = new double[16];
    }

    public void recycle() {
        this.success = true;
        recycled.add(this);
    }

    public static boolean isSlimeChunk(ModSettings settings, int xPosition, int zPosition, Long seed) {
        try {
            if (seed == null) {
                return false;
            }
            Random rnd = new Random(seed + (long)(xPosition * xPosition * 4987142) + (long)(xPosition * 5947611) + (long)(zPosition * zPosition) * 4392871L + (long)(zPosition * 389711) ^ 0x3AD8025FL);
            return rnd.nextInt(10) == 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean isWasTransfered() {
        return this.wasTransfered;
    }

    public void setWasTransfered(boolean wasTransfered) {
        this.wasTransfered = wasTransfered;
    }

    public boolean isChunkGrid() {
        return this.chunkGrid;
    }

    public boolean isSlimeChunk() {
        return this.slimeChunk;
    }

    public int getRed(int l, int x, int z) {
        return this.red[l][x][z];
    }

    public int getGreen(int l, int x, int z) {
        return this.green[l][x][z];
    }

    public int getBlue(int l, int x, int z) {
        return this.blue[l][x][z];
    }

    public void setRed(int l, int x, int z, int r) {
        this.red[l][x][z] = r;
    }

    public void setGreen(int l, int x, int z, int g) {
        this.green[l][x][z] = g;
    }

    public void setBlue(int l, int x, int z, int b) {
        this.blue[l][x][z] = b;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getX() {
        return this.X;
    }

    public int getZ() {
        return this.Z;
    }

    public int getLastHeight(int x) {
        return this.lastHeights[x];
    }

    public void setLastHeight(int x, int h) {
        this.lastHeights[x] = h;
    }

    public double getLastSlopeShade(int x) {
        return this.lastSlopeShades[x];
    }

    public void setLastSlopeShade(int x, double s) {
        this.lastSlopeShades[x] = s;
    }
}

