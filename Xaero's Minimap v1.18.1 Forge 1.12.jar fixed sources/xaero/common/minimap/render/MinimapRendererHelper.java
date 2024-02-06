//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.minimap.render;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class MinimapRendererHelper {
    void drawMyTexturedModalRect(float x, float y, int textureX, int textureY, float width, float height, float factor) {
        float f;
        float f1 = f = 1.0f / factor;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos((double)(x + 0.0f), (double)(y + height), 0.0).tex((double)((float)(textureX + 0) * f), (double)(((float)textureY + height) * f1)).endVertex();
        vertexBuffer.pos((double)(x + width), (double)(y + height), 0.0).tex((double)(((float)textureX + width) * f), (double)(((float)textureY + height) * f1)).endVertex();
        vertexBuffer.pos((double)(x + width), (double)(y + 0.0f), 0.0).tex((double)(((float)textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        vertexBuffer.pos((double)(x + 0.0f), (double)(y + 0.0f), 0.0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }

    void bindTextureBuffer(ByteBuffer image, int width, int height, int par0) {
        GlStateManager.bindTexture((int)par0);
        GL11.glTexImage2D((int)3553, (int)0, (int)6407, (int)width, (int)height, (int)0, (int)6407, (int)5121, (ByteBuffer)image);
    }

    void putColor(byte[] bytes, int x, int y, int red, int green, int blue, int size) {
        int pixel = (y * size + x) * 3;
        bytes[pixel] = (byte)red;
        bytes[++pixel] = (byte)green;
        bytes[++pixel] = (byte)blue;
    }

    void gridOverlay(int[] result, int grid, int red, int green, int blue) {
        result[0] = (red * 3 + (grid >> 16 & 0xFF)) / 4;
        result[1] = (green * 3 + (grid >> 8 & 0xFF)) / 4;
        result[2] = (blue * 3 + (grid & 0xFF)) / 4;
    }

    void slimeOverlay(int[] result, int red, int green, int blue) {
        result[0] = (red + 82) / 2;
        result[1] = (green + 241) / 2;
        result[2] = (blue + 64) / 2;
    }
}

