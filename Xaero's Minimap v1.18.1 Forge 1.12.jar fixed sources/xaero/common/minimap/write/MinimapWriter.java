//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockBush
 *  net.minecraft.block.BlockGlass
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockOre
 *  net.minecraft.block.BlockRedstoneComparator
 *  net.minecraft.block.BlockRedstoneRepeater
 *  net.minecraft.block.BlockStainedGlass
 *  net.minecraft.block.material.MapColor
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockModelShapes
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.client.resources.IResource
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.EnumBlockRenderType
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ReportedException
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.common.property.IExtendedBlockState
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 */
package xaero.common.minimap.write;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.region.MinimapChunk;
import xaero.common.minimap.region.MinimapTile;
import xaero.common.minimap.write.MinimapWriterHelper;
import xaero.common.misc.OptimizedMath;

public class MinimapWriter
implements Runnable {
    private static final String[] dimensionsToIgnore = new String[]{"FZHammer"};
    private static final int UPDATE_EVERY_RUNS = 5;
    private IXaeroMinimap modMain;
    private MinimapWriterHelper helper;
    private int loadingSideInChunks;
    private int updateRadius;
    private MinimapChunk[][] loadingBlocks;
    private int loadingMapChunkX;
    private int loadingMapChunkZ;
    private int loadingCaving;
    private int loadingLevels;
    private MinimapChunk[][] loadedBlocks;
    private int loadedMapChunkX;
    private int loadedMapChunkZ;
    private int loadedCaving;
    private int loadedLevels;
    private int updateChunkX;
    private int updateChunkZ;
    private int tileInsideX;
    private int tileInsideZ;
    private int runNumber;
    private boolean previousShouldLoad;
    private int lastCaving;
    private boolean clearBlockColours;
    private HashMap<String, Integer> textureColours;
    private HashMap<Integer, Integer> blockColours;
    private boolean forcedRefresh;
    private MinimapChunk oldChunk;
    private int[][] lastBlockY = new int[4][16];
    private int updates;
    private int loads;
    private long before;
    private int processingTime;
    boolean isglowing;
    private Integer previousTransparentBlock;
    private int underRed;
    private int underGreen;
    private int underBlue;
    private float divider;
    private int sun;
    private int blockY;
    private int blockColor;
    private final int[] red;
    private final int[] green;
    private final int[] blue;
    private final float[] brightness;
    private final float[] postBrightness;
    private final int[] tempColor;
    private double secondaryB;
    private double[][] lastSlopeShades;
    private BlockPos.MutableBlockPos cavingDetectorBlockPos;
    private Long seedForLoading;

    public MinimapWriter(IXaeroMinimap modMain) {
        this.modMain = modMain;
        this.loadingSideInChunks = 16;
        this.updateRadius = 16;
        this.loadingCaving = -1;
        this.lastCaving = -1;
        this.textureColours = new HashMap();
        this.blockColours = new HashMap();
        this.loadedCaving = -1;
        this.divider = 1.0f;
        this.red = new int[5];
        this.green = new int[5];
        this.blue = new int[5];
        this.brightness = new float[5];
        this.postBrightness = new float[5];
        this.tempColor = new int[3];
        this.lastSlopeShades = new double[4][16];
        this.helper = new MinimapWriterHelper();
        this.cavingDetectorBlockPos = new BlockPos.MutableBlockPos();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block12: while (true) {
            try {
                while (true) {
                    long startTime = System.currentTimeMillis();
                    int totalTime = 3000000;
                    int sleepTime = 10;
                    while (totalTime > 0) {
                        try {
                            World world = null;
                            double playerX = 0.0;
                            double playerY = 0.0;
                            double playerZ = 0.0;
                            if (this.modMain.getInterfaces() != null) {
                                MinimapProcessor minimapProcessor = this.modMain.getInterfaces().getMinimap();
                                Object object = minimapProcessor.mainStuffSync;
                                synchronized (object) {
                                    world = minimapProcessor.mainWorld;
                                    playerX = minimapProcessor.mainPlayerX;
                                    playerY = minimapProcessor.mainPlayerY;
                                    playerZ = minimapProcessor.mainPlayerZ;
                                }
                            }
                            if (this.modMain.getSettings() == null || !this.modMain.getSettings().getMinimap() || world == null) {
                                sleepTime = 100;
                                break;
                            }
                            World world2 = world;
                            synchronized (world2) {
                                int time = this.writeChunk(playerX, playerY, playerZ, world);
                                totalTime -= time;
                            }
                            if (this.tileInsideX != 0 || this.tileInsideZ != 0 || this.updateChunkX != 0 || this.updateChunkZ != 0) continue;
                            sleepTime = 300;
                            break;
                        }
                        catch (ConcurrentModificationException world) {
                        }
                    }
                    int passed = (int)(System.currentTimeMillis() - startTime);
                    try {
                        if (passed >= sleepTime) continue block12;
                        Thread.sleep(sleepTime - passed);
                        continue block12;
                    }
                    catch (InterruptedException interruptedException) {
                        continue;
                    }
                    break;
                }
            }
            catch (Throwable e) {
                MinimapProcessor.instance.setCrashedWith(e);
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int writeChunk(double playerX, double playerY, double playerZ, World world) {
        MinimapChunk mchunk;
        boolean shouldLoad;
        long processStart = System.nanoTime();
        boolean bl = shouldLoad = !this.ignoreWorld(world) && (!this.modMain.getSupportMods().shouldUseWorldMapChunks() || this.loadingCaving != -1 || this.getCaving(playerX, playerY, playerZ, world) != -1);
        if (shouldLoad != this.previousShouldLoad) {
            this.updateChunkZ = 0;
            this.updateChunkX = 0;
            this.tileInsideZ = 0;
            this.tileInsideX = 0;
            this.previousShouldLoad = shouldLoad;
        }
        if (shouldLoad) {
            if (this.tileInsideX == 0 && this.tileInsideZ == 0) {
                if (this.updateChunkX == 0 && this.updateChunkZ == 0) {
                    if (this.clearBlockColours) {
                        this.clearBlockColours = false;
                        if (!this.blockColours.isEmpty()) {
                            this.blockColours.clear();
                            this.textureColours.clear();
                            System.out.println("Minimap block colour cache cleaned.");
                        }
                    }
                    this.loadingSideInChunks = this.getLoadSide();
                    this.updateRadius = this.getUpdateRadiusInChunks();
                    this.loadingMapChunkX = this.getMapCoord(this.loadingSideInChunks, playerX);
                    this.loadingMapChunkZ = this.getMapCoord(this.loadingSideInChunks, playerZ);
                    this.loadingCaving = this.getCaving(playerX, playerY, playerZ, world);
                    if (this.loadingCaving != this.loadedCaving) {
                        this.runNumber = 0;
                    }
                    int n = this.loadingLevels = this.modMain.getSettings().getLighting() ? 5 : 1;
                    if (this.loadingBlocks == null || this.loadingBlocks.length != this.loadingSideInChunks) {
                        this.loadingBlocks = new MinimapChunk[this.loadingSideInChunks][this.loadingSideInChunks];
                    }
                    if (MinimapProcessor.instance.usingFBO() && MinimapProcessor.instance.isToResetImage()) {
                        this.forcedRefresh = true;
                        MinimapProcessor.instance.setToResetImage(false);
                    }
                }
                this.oldChunk = null;
                if (this.loadedBlocks != null) {
                    int updateChunkXOld = this.loadingMapChunkX + this.updateChunkX - this.loadedMapChunkX;
                    int updateChunkZOld = this.loadingMapChunkZ + this.updateChunkZ - this.loadedMapChunkZ;
                    if (updateChunkXOld > -1 && updateChunkXOld < this.loadedBlocks.length && updateChunkZOld > -1 && updateChunkZOld < this.loadedBlocks.length) {
                        this.oldChunk = this.loadedBlocks[updateChunkXOld][updateChunkZOld];
                    }
                }
            }
            if ((mchunk = this.loadingBlocks[this.updateChunkX][this.updateChunkZ]) == null) {
                MinimapChunk minimapChunk = new MinimapChunk(this.loadingMapChunkX + this.updateChunkX, this.loadingMapChunkZ + this.updateChunkZ);
                this.loadingBlocks[this.updateChunkX][this.updateChunkZ] = minimapChunk;
                mchunk = minimapChunk;
            } else if (this.tileInsideX == 0 && this.tileInsideZ == 0) {
                mchunk.reset(this.loadingMapChunkX + this.updateChunkX, this.loadingMapChunkZ + this.updateChunkZ);
            }
            this.writeTile(playerX, playerY, playerZ, world, mchunk, this.oldChunk, this.updateChunkX, this.updateChunkZ, this.tileInsideX, this.tileInsideZ, this.runNumber % 5 != 0);
        }
        ++this.tileInsideZ;
        if (this.tileInsideZ >= 4) {
            this.tileInsideZ = 0;
            ++this.tileInsideX;
            if (this.tileInsideX >= 4) {
                this.tileInsideX = 0;
                if (shouldLoad) {
                    mchunk = this.loadingBlocks[this.updateChunkX][this.updateChunkZ];
                    if (MinimapProcessor.instance.usingFBO() && mchunk.isHasSomething() && mchunk.isChanged()) {
                        mchunk.updateBuffers(this.loadingLevels);
                        mchunk.setChanged(false);
                    }
                    mchunk.setLevelsBuffered(this.loadingLevels);
                }
                if (this.updateChunkX == this.loadingSideInChunks - 1 && this.updateChunkZ == this.loadingSideInChunks - 1) {
                    if (shouldLoad) {
                        if (this.runNumber % 5 == 0 && !MinimapTile.recycled.isEmpty()) {
                            MinimapTile.recycled.subList(0, MinimapTile.recycled.size() / 2).clear();
                        }
                        if (this.loadedBlocks != null) {
                            for (int i = 0; i < this.loadedBlocks.length; ++i) {
                                for (int j = 0; j < this.loadedBlocks.length; ++j) {
                                    boolean shouldTransfer;
                                    MinimapChunk m = this.loadedBlocks[i][j];
                                    MinimapChunk lm = null;
                                    if (m == null) continue;
                                    m.recycleTiles();
                                    int loadingX = this.loadedMapChunkX + i - this.loadingMapChunkX;
                                    int loadingZ = this.loadedMapChunkZ + j - this.loadingMapChunkZ;
                                    if (loadingX > -1 && loadingZ > -1 && loadingX < this.loadingSideInChunks && loadingZ < this.loadingSideInChunks) {
                                        lm = this.loadingBlocks[loadingX][loadingZ];
                                    }
                                    boolean bl2 = shouldTransfer = m.getLevelsBuffered() == this.loadingLevels && lm != null;
                                    if (shouldTransfer) {
                                        MinimapChunk minimapChunk = m;
                                        synchronized (minimapChunk) {
                                            m.setBlockTextureUpload(true);
                                        }
                                    }
                                    for (int l = 0; l < m.getLevelsBuffered(); ++l) {
                                        if (m.getGlTexture(l) != 0) {
                                            if (shouldTransfer) {
                                                lm.setGlTexture(l, m.getGlTexture(l));
                                                continue;
                                            }
                                            MinimapProcessor.instance.requestTextureDelete(m.getGlTexture(l));
                                            continue;
                                        }
                                        if (!shouldTransfer || lm.isRefreshRequired(l) || !m.isRefreshRequired(l)) continue;
                                        lm.copyBuffer(l, m.getBuffer(l));
                                        lm.setRefreshRequired(l, true);
                                        m.setRefreshRequired(l, false);
                                    }
                                }
                            }
                        }
                        MinimapWriter i = this;
                        synchronized (i) {
                            MinimapChunk[][] bu = this.loadedBlocks;
                            this.loadedBlocks = this.loadingBlocks;
                            this.loadingBlocks = bu;
                            this.loadedMapChunkX = this.loadingMapChunkX;
                            this.loadedMapChunkZ = this.loadingMapChunkZ;
                            this.loadedLevels = this.loadingLevels;
                        }
                    }
                    this.loadedCaving = this.loadingCaving;
                    this.forcedRefresh = false;
                    ++this.runNumber;
                }
                ++this.updateChunkZ;
                if (this.updateChunkZ >= this.loadingSideInChunks) {
                    this.updateChunkZ = 0;
                    this.updateChunkX = (this.updateChunkX + 1) % this.loadingSideInChunks;
                    this.lastBlockY = new int[4][16];
                    this.lastSlopeShades = new double[4][16];
                }
            }
        }
        int passed = (int)(System.nanoTime() - processStart);
        return passed;
    }

    private void writeTile(double playerX, double playerY, double playerZ, World world, MinimapChunk mchunk, MinimapChunk oldChunk, int canvasX, int canvasZ, int insideX, int insideZ, boolean onlyLoad) {
        int tileX = mchunk.getX() * 4 + insideX;
        int tileZ = mchunk.getZ() * 4 + insideZ;
        int halfSide = this.loadingSideInChunks / 2;
        int tileFromCenterX = canvasX - halfSide;
        int tileFromCenterZ = canvasZ - halfSide;
        MinimapTile oldTile = null;
        if (oldChunk != null) {
            oldTile = oldChunk.getTile(insideX, insideZ);
        }
        Chunk bchunk = world.getChunk(tileX, tileZ);
        boolean neighborsLoaded = true;
        block0: for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                Chunk nchunk;
                if (i == 0 && j == 0 || (nchunk = world.getChunk(tileX + i, tileZ + j)) != null && nchunk.isLoaded()) continue;
                neighborsLoaded = false;
                continue block0;
            }
        }
        if (bchunk == null || !bchunk.isLoaded() || (onlyLoad || tileFromCenterX > this.updateRadius || tileFromCenterZ > this.updateRadius || tileFromCenterX < -this.updateRadius || tileFromCenterZ < -this.updateRadius || !neighborsLoaded) && oldTile != null && oldTile.isSuccess() && oldChunk.getLevelsBuffered() == this.loadingLevels) {
            int j;
            if (oldTile != null && oldChunk.getLevelsBuffered() == this.loadingLevels) {
                mchunk.setTile(insideX, insideZ, oldTile);
                oldTile.setWasTransfered(true);
                for (j = 0; j < 16; ++j) {
                    this.lastBlockY[insideX][j] = oldTile.getLastHeight(j);
                    this.lastSlopeShades[insideX][j] = oldTile.getLastSlopeShade(j);
                }
                mchunk.setHasSomething(oldChunk.isHasSomething());
                if (this.forcedRefresh) {
                    mchunk.setChanged(true);
                }
            } else {
                for (j = 0; j < 16; ++j) {
                    this.lastBlockY[insideX][j] = 0;
                    this.lastSlopeShades[insideX][j] = 0.0;
                }
            }
            return;
        }
        if (oldTile != null && oldChunk.getLevelsBuffered() != this.loadingLevels) {
            oldTile = null;
        }
        int x1 = tileX * 16;
        int z1 = tileZ * 16;
        for (int blockX = x1; blockX < x1 + 16; ++blockX) {
            for (int blockZ = z1; blockZ < z1 + 16; ++blockZ) {
                MinimapTile tile;
                this.loadBlockColor(playerX, playerY, playerZ, world, blockX, blockZ, bchunk, canvasX, canvasZ, tileX, tileZ, insideX, insideZ, oldTile, neighborsLoaded);
                if ((blockZ & 0xF) != 15 || (tile = mchunk.getTile(insideX, insideZ)) == null) continue;
                tile.setLastHeight(blockX & 0xF, this.lastBlockY[insideX][blockX & 0xF]);
                tile.setLastSlopeShade(blockX & 0xF, this.lastSlopeShades[insideX][blockX & 0xF]);
            }
        }
    }

    public void loadBlockColor(double playerX, double playerY, double playerZ, World world, int par1, int par2, Chunk bchunk, int canvasX, int canvasZ, int tileX, int tileZ, int tileInsideX, int tileInsideZ, MinimapTile oldTile, boolean neighborsLoaded) {
        MinimapTile tile;
        int lowY;
        int insideX = par1 & 0xF;
        int insideZ = par2 & 0xF;
        int playerYi = (int)playerY;
        int height = bchunk.getHeightValue(insideX, insideZ);
        int highY = this.loadingCaving != -1 ? this.loadingCaving : height + 3;
        int n = lowY = this.loadingCaving != -1 ? playerYi - 30 : 0;
        if (lowY < 0) {
            lowY = 0;
        }
        this.blockY = 0;
        this.underRed = 0;
        this.underGreen = 0;
        this.underBlue = 0;
        this.divider = 1.0f;
        this.sun = 15;
        this.previousTransparentBlock = null;
        this.blockColor = 0;
        this.isglowing = false;
        this.secondaryB = 1.0;
        Block block = this.findBlock(world, bchunk, insideX, insideZ, highY, lowY);
        this.isglowing = block != null && !(block instanceof BlockOre) && this.isglowing;
        boolean success = neighborsLoaded;
        if (this.lastBlockY[tileInsideX][insideX] <= 0) {
            this.lastBlockY[tileInsideX][insideX] = this.blockY;
            try {
                Chunk prevChunk = world.getChunk(tileX, tileZ - 1);
                if (prevChunk != null && prevChunk.isLoaded()) {
                    this.lastBlockY[tileInsideX][insideX] = prevChunk.getHeightValue(insideX, 15) - 1;
                } else {
                    success = false;
                }
            }
            catch (IllegalStateException e) {
                success = false;
            }
        }
        if (!this.isglowing) {
            BlockPos pos = new BlockPos(insideX, Math.min(this.blockY + 1, 255), insideZ);
            boolean lighting = this.modMain.getSettings().getLighting();
            for (int i = 0; i < this.loadingLevels; ++i) {
                if (!lighting && this.loadingCaving != -1) {
                    if (this.previousTransparentBlock == null) {
                        this.brightness[i] = (float)Math.min((double)this.blockY / (double)height, 1.0);
                    } else {
                        this.postBrightness[i] = (float)Math.min((double)this.blockY / (double)height, 1.0);
                    }
                } else {
                    this.brightness[i] = this.getBlockBrightness(bchunk, pos, 5.0f, this.sun, this.previousTransparentBlock == null ? i : -1);
                }
                if (this.previousTransparentBlock != null) continue;
                this.postBrightness[i] = 1.0f;
            }
            if (this.loadingCaving == -1 && this.modMain.getSettings().getTerrainDepth()) {
                this.secondaryB = (double)this.blockY / 63.0;
                if (this.secondaryB > 1.15) {
                    this.secondaryB = 1.15;
                } else if (this.secondaryB < 0.7) {
                    this.secondaryB = 0.7;
                }
            }
            if (this.modMain.getSettings().getTerrainSlopes()) {
                double slopeShade = 1.0;
                if (this.lastSlopeShades[tileInsideX][insideX] != 0.0) {
                    slopeShade = this.blockY < this.lastBlockY[tileInsideX][insideX] ? (this.modMain.getSettings().getTerrainSlopesExperiment() ? 1.0 - 0.1 * (double)Math.min(4, this.lastBlockY[tileInsideX][insideX] - this.blockY) : 0.85) : (this.blockY > this.lastBlockY[tileInsideX][insideX] ? 1.15 : 1.0);
                }
                this.secondaryB *= slopeShade;
                this.lastSlopeShades[tileInsideX][insideX] = slopeShade;
            }
        }
        this.lastBlockY[tileInsideX][insideX] = this.blockY;
        if (this.isglowing) {
            this.helper.getBrightestColour(this.blockColor >> 16 & 0xFF, this.blockColor >> 8 & 0xFF, this.blockColor & 0xFF, this.tempColor);
        }
        for (int i = 0; i < this.loadingLevels; ++i) {
            float b;
            if (this.isglowing) {
                this.red[i] = this.tempColor[0];
                this.green[i] = this.tempColor[1];
                this.blue[i] = this.tempColor[2];
                b = 1.0f;
            } else {
                this.red[i] = this.blockColor >> 16 & 0xFF;
                this.green[i] = this.blockColor >> 8 & 0xFF;
                this.blue[i] = this.blockColor & 0xFF;
                b = this.brightness[i];
            }
            this.red[i] = (int)(((double)((float)this.red[i] * b) * this.secondaryB + (double)this.underRed) / (double)this.divider * (double)this.postBrightness[i]);
            if (this.red[i] > 255) {
                this.red[i] = 255;
            }
            this.green[i] = (int)(((double)((float)this.green[i] * b) * this.secondaryB + (double)this.underGreen) / (double)this.divider * (double)this.postBrightness[i]);
            if (this.green[i] > 255) {
                this.green[i] = 255;
            }
            this.blue[i] = (int)(((double)((float)this.blue[i] * b) * this.secondaryB + (double)this.underBlue) / (double)this.divider * (double)this.postBrightness[i]);
            if (this.blue[i] <= 255) continue;
            this.blue[i] = 255;
        }
        if (canvasX < 0 || canvasX >= this.loadingSideInChunks || canvasZ < 0 || canvasZ >= this.loadingSideInChunks) {
            return;
        }
        MinimapChunk chunk = this.loadingBlocks[canvasX][canvasZ];
        if (this.notEmptyColor()) {
            chunk.setHasSomething(true);
        }
        if ((tile = chunk.getTile(tileInsideX, tileInsideZ)) == null) {
            tile = MinimapTile.getANewTile(this.modMain.getSettings(), tileX, tileZ, this.seedForLoading);
            chunk.setTile(tileInsideX, tileInsideZ, tile);
        }
        tile.setSuccess(success);
        if (oldTile != null) {
            int oldTileDarkestLevel = this.loadedLevels - 1;
            int tileDarkestLevel = this.loadingLevels - 1;
            if (oldTile.getRed(oldTileDarkestLevel, insideX, insideZ) != this.red[tileDarkestLevel] || oldTile.getGreen(oldTileDarkestLevel, insideX, insideZ) != this.green[tileDarkestLevel] || oldTile.getBlue(oldTileDarkestLevel, insideX, insideZ) != this.blue[tileDarkestLevel]) {
                chunk.setChanged(true);
            }
        } else {
            chunk.setChanged(true);
        }
        for (int i = 0; i < this.loadingLevels; ++i) {
            tile.setRed(i, insideX, insideZ, this.red[i]);
            tile.setGreen(i, insideX, insideZ, this.green[i]);
            tile.setBlue(i, insideX, insideZ, this.blue[i]);
        }
    }

    public Block findBlock(World world, Chunk bchunk, int insideX, int insideZ, int highY, int lowY) {
        boolean underair = false;
        for (int i = highY; i >= lowY; --i) {
            IBlockState state = bchunk.getBlockState(insideX, i, insideZ);
            if (state == null) continue;
            Block got = state.getBlock();
            if (!(got instanceof BlockAir) && (underair || this.loadingCaving == -1)) {
                if (state.getRenderType() == EnumBlockRenderType.INVISIBLE || got == Blocks.TORCH || got == Blocks.TALLGRASS || got == Blocks.DOUBLE_PLANT || !this.modMain.getSettings().showFlowers && got instanceof BlockBush || !this.modMain.getSettings().displayRedstone && (got == Blocks.REDSTONE_TORCH || got == Blocks.REDSTONE_WIRE || got instanceof BlockRedstoneRepeater || got instanceof BlockRedstoneComparator)) continue;
                this.blockY = i;
                BlockPos pos = new BlockPos(insideX, this.blockY, insideZ);
                BlockPos globalPos = this.getGlobalBlockPos(bchunk.x, bchunk.z, insideX, this.blockY, insideZ);
                IBlockState extended = null;
                if (this.modMain.getSettings().getBlockColours() == 0) {
                    try {
                        extended = got.getExtendedState(state, (IBlockAccess)world, globalPos);
                    }
                    catch (RuntimeException e) {
                        extended = state;
                    }
                }
                if (this.modMain.getSettings().blockTransparency && this.applyTransparentBlock(world, bchunk, got, state, extended, globalPos, pos)) continue;
                if (this.modMain.getSettings().getBlockColours() == 1) {
                    MapColor minimapColor = state.getMapColor((IBlockAccess)world, globalPos);
                    this.blockColor = minimapColor.colorValue;
                } else {
                    this.blockColor = this.loadBlockColourFromTexture(world, state, extended, got, globalPos, true);
                }
                if (this.blockColor == 0) continue;
                this.isglowing = this.isGlowing(state, bchunk.getWorld(), globalPos);
                return got;
            }
            if (!(got instanceof BlockAir)) continue;
            underair = true;
        }
        return null;
    }

    public boolean isGlowing(IBlockState state, World world, BlockPos pos) {
        try {
            return (double)state.getLightValue((IBlockAccess)world, pos) >= 0.5;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean applyTransparentBlock(World world, Chunk bchunk, Block b, IBlockState state, IBlockState extended, BlockPos globalPos, BlockPos pos) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int intensity = 1;
        boolean skip = false;
        if (b instanceof BlockLiquid && b.getLightOpacity(state, (IBlockAccess)bchunk.getWorld(), globalPos) != 255 && b.getLightOpacity(state, (IBlockAccess)bchunk.getWorld(), globalPos) != 0) {
            switch (this.modMain.getSettings().getBlockColours()) {
                case 1: {
                    red = 0;
                    green = 100;
                    blue = 225;
                    break;
                }
                case 0: {
                    int waterColor = this.loadBlockColourFromTexture(world, state, extended, b, globalPos, true);
                    red = waterColor >> 16 & 0xFF;
                    green = waterColor >> 8 & 0xFF;
                    blue = waterColor & 0xFF;
                }
            }
            intensity = 2;
            skip = true;
        } else if (this.modMain.getSettings().getBlockColours() == 0 && (b.getRenderLayer() == BlockRenderLayer.TRANSLUCENT || b instanceof BlockGlass)) {
            int glassColor = this.loadBlockColourFromTexture(world, state, extended, b, globalPos, true);
            red = glassColor >> 16 & 0xFF;
            green = glassColor >> 8 & 0xFF;
            blue = glassColor & 0xFF;
            skip = true;
        } else if (this.modMain.getSettings().getBlockColours() == 1 && b instanceof BlockStainedGlass) {
            int color = state.getMapColor((IBlockAccess)world, (BlockPos)globalPos).colorValue;
            red = color >> 16 & 0xFF;
            green = color >> 8 & 0xFF;
            blue = color & 0xFF;
            skip = true;
        }
        if (skip) {
            if (this.previousTransparentBlock == null) {
                for (int i = 0; i < this.loadingLevels; ++i) {
                    this.postBrightness[i] = this.getBlockBrightness(bchunk, new BlockPos(pos.getX(), Math.min(pos.getY() + 1, 255), pos.getZ()), 5.0f, this.sun, i);
                }
            }
            int rgb = red << 16 | green << 8 | blue;
            if (this.previousTransparentBlock == null || this.previousTransparentBlock != rgb) {
                this.previousTransparentBlock = rgb;
                float overlayIntensity = (float)intensity * this.getBlockBrightness(bchunk, new BlockPos(pos.getX(), Math.min(pos.getY() + 1, 255), pos.getZ()), 5.0f, this.sun, -1);
                if (this.isGlowing(state, bchunk.getWorld(), globalPos)) {
                    this.helper.getBrightestColour(red, green, blue, this.tempColor);
                    red = this.tempColor[0];
                    green = this.tempColor[1];
                    blue = this.tempColor[2];
                }
                this.divider += overlayIntensity;
                this.underRed = (int)((float)this.underRed + (float)red * overlayIntensity);
                this.underGreen = (int)((float)this.underGreen + (float)green * overlayIntensity);
                this.underBlue = (int)((float)this.underBlue + (float)blue * overlayIntensity);
            }
            this.sun -= b.getLightOpacity(state, (IBlockAccess)bchunk.getWorld(), globalPos);
            if (this.sun < 0) {
                this.sun = 0;
            }
        }
        return skip;
    }

    private int loadBlockColourFromTexture(World world, IBlockState state, IBlockState extended, Block b, BlockPos pos, boolean convert) {
        int stateHash = this.getHashCode(extended);
        Integer c = this.blockColours.get(stateHash);
        int red = 0;
        int green = 0;
        int blue = 0;
        if (c == null) {
            String name = null;
            try {
                Integer cachedColour;
                TextureAtlasSprite texture;
                List upQuads = null;
                BlockModelShapes bms = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
                IBakedModel model = bms.getModelForState(state);
                if (convert) {
                    upQuads = model.getQuads(extended, EnumFacing.UP, 0L);
                }
                if (upQuads == null || upQuads.isEmpty() || ((BakedQuad)upQuads.get(0)).getSprite() == bms.getModelManager().getTextureMap().getMissingSprite()) {
                    texture = bms.getTexture(state);
                    if (texture == bms.getModelManager().getTextureMap().getMissingSprite()) {
                        List quads;
                        for (int i = EnumFacing.VALUES.length - 1; i >= 0 && (i == 1 || (quads = model.getQuads(extended, EnumFacing.VALUES[i], 0L)).isEmpty() || (texture = ((BakedQuad)quads.get(0)).getSprite()) == bms.getModelManager().getTextureMap().getMissingSprite()); --i) {
                        }
                    }
                } else {
                    texture = ((BakedQuad)upQuads.get(0)).getSprite();
                }
                name = texture.getIconName() + ".png";
                if (b instanceof BlockOre && b != Blocks.QUARTZ_ORE) {
                    name = "minecraft:blocks/stone.png";
                }
                c = -1;
                String[] args = name.split(":");
                if (args.length < 2) {
                    args = new String[]{"minecraft", args[0]};
                }
                if ((cachedColour = this.textureColours.get(name)) == null) {
                    ResourceLocation location = new ResourceLocation(args[0], "textures/" + args[1]);
                    IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
                    InputStream input = resource.getInputStream();
                    BufferedImage img = TextureUtil.readBufferedImage((InputStream)input);
                    red = 0;
                    green = 0;
                    blue = 0;
                    int total = 64;
                    int tw = img.getWidth();
                    int diff = tw / 8;
                    for (int i = 0; i < 8; ++i) {
                        for (int j = 0; j < 8; ++j) {
                            int rgb = img.getRGB(i * diff, j * diff);
                            int alpha = rgb >> 24 & 0xFF;
                            if (rgb == 0 || alpha == 0) {
                                --total;
                                continue;
                            }
                            red += rgb >> 16 & 0xFF;
                            green += rgb >> 8 & 0xFF;
                            blue += rgb & 0xFF;
                        }
                    }
                    input.close();
                    if (total == 0) {
                        total = 1;
                    }
                    if (convert && (red /= total) == 0 && (green /= total) == 0 && (blue /= total) == 0) {
                        throw new Exception("Black texture");
                    }
                    c = 0xFF000000 | red << 16 | green << 8 | blue;
                    this.textureColours.put(name, c);
                } else {
                    c = cachedColour;
                }
            }
            catch (FileNotFoundException e) {
                if (convert) {
                    return this.loadBlockColourFromTexture(world, state, extended, b, pos, false);
                }
                c = 0;
                if (state != null && state.getMapColor((IBlockAccess)world, pos) != null) {
                    c = state.getMapColor((IBlockAccess)world, (BlockPos)pos).colorValue;
                }
                if (name != null) {
                    this.textureColours.put(name, c);
                }
                System.out.println("Block file not found: " + Block.REGISTRY.getNameForObject((Object)b));
            }
            catch (Exception e) {
                if (state.getMapColor((IBlockAccess)world, pos) != null) {
                    c = state.getMapColor((IBlockAccess)world, (BlockPos)pos).colorValue;
                }
                if (name != null) {
                    this.textureColours.put(name, c);
                }
                System.out.println("Exception when loading " + Block.REGISTRY.getNameForObject((Object)b) + " texture, using material colour.");
            }
            if (c != null) {
                this.blockColours.put(stateHash, c);
            }
        }
        int grassColor = 0xFFFFFF;
        try {
            grassColor = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, (IBlockAccess)world, pos, 0);
        }
        catch (IllegalArgumentException e) {
        }
        catch (NullPointerException e) {
        }
        catch (IllegalStateException e) {
        }
        catch (IndexOutOfBoundsException e) {
        }
        catch (ReportedException e) {
            // empty catch block
        }
        if (grassColor != 0xFFFFFF) {
            float rMultiplier = (float)(c >> 16 & 0xFF) / 255.0f;
            float gMultiplier = (float)(c >> 8 & 0xFF) / 255.0f;
            float bMultiplier = (float)(c & 0xFF) / 255.0f;
            red = (int)((float)(grassColor >> 16 & 0xFF) * rMultiplier);
            green = (int)((float)(grassColor >> 8 & 0xFF) * gMultiplier);
            blue = (int)((float)(grassColor & 0xFF) * bMultiplier);
            c = 0xFF000000 | red << 16 | green << 8 | blue;
        }
        return c;
    }

    public BlockPos getGlobalBlockPos(int chunkX, int chunkZ, int x, int y, int z) {
        return new BlockPos(chunkX * 16 + x, y, chunkZ * 16 + z);
    }

    private boolean ignoreWorld(World world) {
        for (int i = 0; i < dimensionsToIgnore.length; ++i) {
            if (!dimensionsToIgnore[i].equals(world.provider.getDimensionType().getName())) continue;
            return true;
        }
        return false;
    }

    private int getCaving(double playerX, double playerY, double playerZ, World world) {
        if (!this.modMain.getSettings().getCaveMaps()) {
            return -1;
        }
        if (this.ignoreWorld(world)) {
            return this.lastCaving;
        }
        int y = Math.max((int)playerY + 1, 0);
        if (y > 255 || y < 0) {
            return -1;
        }
        int x = OptimizedMath.myFloor(playerX);
        int z = OptimizedMath.myFloor(playerZ);
        this.cavingDetectorBlockPos.setPos(x, y, z);
        Chunk bchunk = world.getChunk(x >> 4, z >> 4);
        if (bchunk == null || !bchunk.isLoaded()) {
            return -1;
        }
        int skyLight = bchunk.getLightFor(EnumSkyBlock.SKY, (BlockPos)this.cavingDetectorBlockPos);
        if (skyLight < 15) {
            int roofRadius = this.modMain.getSettings().caveMaps - 1;
            int insideX = x & 0xF;
            int insideZ = z & 0xF;
            int top = bchunk.getHeightValue(insideX, insideZ);
            for (int i = y; i <= top; ++i) {
                boolean roofExists = true;
                block1: for (int o = x - roofRadius; o <= x + roofRadius; ++o) {
                    for (int p = z - roofRadius; p <= z + roofRadius; ++p) {
                        this.cavingDetectorBlockPos.setPos(o, i, p);
                        IBlockState state = world.getBlockState((BlockPos)this.cavingDetectorBlockPos);
                        if (state.getMaterial().isOpaque()) continue;
                        roofExists = false;
                        break block1;
                    }
                }
                if (!roofExists) continue;
                this.lastCaving = Math.min(i, y + 3);
                return this.lastCaving;
            }
        }
        return -1;
    }

    public int getLoadSide() {
        return 9;
    }

    public int getUpdateRadiusInChunks() {
        return (int)Math.ceil((double)this.loadingSideInChunks / Math.sqrt(2.0) / 2.0 / (double)this.modMain.getSettings().zooms[this.modMain.getSettings().zoom]);
    }

    public int getMapCoord(int side, double coord) {
        return (OptimizedMath.myFloor(coord) >> 6) - side / 2;
    }

    public int getLoadedCaving() {
        return this.loadedCaving;
    }

    private boolean notEmptyColor() {
        return this.red[0] != 0 || this.green[0] != 0 || this.blue[0] != 0;
    }

    public float getBlockBrightness(Chunk c, BlockPos pos, float min, int sun, int lightLevel) {
        if (!this.modMain.getSettings().getLighting()) {
            return (min + (float)sun) / (15.0f + min);
        }
        return (min + Math.max((lightLevel == -1 || lightLevel == 0 ? 1.0f : ((float)this.loadingLevels - 1.0f - (float)lightLevel) / ((float)this.loadingLevels - 1.0f)) * (float)sun, (float)c.getLightFor(EnumSkyBlock.BLOCK, pos))) / (15.0f + min);
    }

    public int getHashCode(IBlockState state) {
        HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(Block.getStateId((IBlockState)state));
        if (state instanceof IExtendedBlockState) {
            for (Optional value2 : ((IExtendedBlockState)state).getUnlistedProperties().values()) {
                hash.append((Object)value2);
            }
        }
        return hash.build();
    }

    public MinimapChunk[][] getLoadedBlocks() {
        return this.loadedBlocks;
    }

    public int getLoadedMapChunkZ() {
        return this.loadedMapChunkZ;
    }

    public int getLoadedMapChunkX() {
        return this.loadedMapChunkX;
    }

    public int getLoadedLevels() {
        return this.loadedLevels;
    }

    public void setClearBlockColours(boolean clearBlockColours) {
        this.clearBlockColours = clearBlockColours;
    }

    public Long getSeedForLoading() {
        return this.seedForLoading;
    }

    public void setSeedForLoading(Long seedForLoading) {
        this.seedForLoading = seedForLoading;
    }
}

