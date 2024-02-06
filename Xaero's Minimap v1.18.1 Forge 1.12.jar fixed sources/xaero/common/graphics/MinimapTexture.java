//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.BufferUtils
 */
package xaero.common.graphics;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;

public class MinimapTexture
extends SimpleTexture {
    public BufferedImage bufferedimage;
    public ByteBuffer buffer512 = BufferUtils.createByteBuffer((int)0x100000);
    public ByteBuffer buffer256 = BufferUtils.createByteBuffer((int)262144);
    public ByteBuffer buffer128 = BufferUtils.createByteBuffer((int)65536);

    public ByteBuffer getBuffer(int size) {
        switch (size) {
            case 128: {
                return this.buffer128;
            }
            case 256: {
                return this.buffer256;
            }
        }
        return this.buffer512;
    }

    public MinimapTexture(ResourceLocation location) {
        super(location);
        this.loadTexture(Minecraft.getMinecraft().getResourceManager());
    }

    public void loadTexture(IResourceManager par1ResourceManager) {
        this.bufferedimage = new BufferedImage(512, 512, 5);
        TextureUtil.uploadTextureImageAllocate((int)this.getGlTextureId(), (BufferedImage)this.bufferedimage, (boolean)false, (boolean)false);
    }
}

