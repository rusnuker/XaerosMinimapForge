//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.opengl.ARBFramebufferObject
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL30
 *  org.lwjgl.opengl.GLContext
 */
package xaero.common.graphics;

import java.nio.IntBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class ImprovedFramebuffer
extends Framebuffer {
    private int type;
    private static final int GL_FB_INCOMPLETE_ATTACHMENT = 36054;
    private static final int GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
    private static final int GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
    private static final int GL_FB_INCOMPLETE_READ_BUFFER = 36060;

    public ImprovedFramebuffer(int width, int height, boolean useDepthIn) {
        super(width, height, useDepthIn);
    }

    public void createBindFramebuffer(int width, int height) {
        GlStateManager.enableDepth();
        if (this.framebufferObject >= 0) {
            this.deleteFramebuffer();
        }
        this.createFramebuffer(width, height);
        this.checkFramebufferComplete();
        ImprovedFramebuffer.bindFramebuffer(this.type, 36160, 0);
    }

    public void createFramebuffer(int width, int height) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.framebufferTextureWidth = width;
        this.framebufferTextureHeight = height;
        this.framebufferObject = this.genFrameBuffers();
        if (this.framebufferObject == -1) {
            this.framebufferClear();
            return;
        }
        this.framebufferTexture = TextureUtil.glGenTextures();
        if (this.framebufferTexture == -1) {
            this.framebufferClear();
            return;
        }
        if (this.useDepth) {
            this.depthBuffer = this.genRenderbuffers();
            if (this.framebufferTexture == -1) {
                this.framebufferClear();
                return;
            }
        }
        this.setFramebufferFilter(9728);
        GlStateManager.bindTexture((int)this.framebufferTexture);
        GlStateManager.glTexImage2D((int)3553, (int)0, (int)32856, (int)this.framebufferTextureWidth, (int)this.framebufferTextureHeight, (int)0, (int)6408, (int)5121, (IntBuffer)null);
        ImprovedFramebuffer.bindFramebuffer(this.type, 36160, this.framebufferObject);
        ImprovedFramebuffer.framebufferTexture2D(this.type, 36160, 36064, 3553, this.framebufferTexture, 0);
        if (this.useDepth) {
            ImprovedFramebuffer.bindRenderbuffer(this.type, 36161, this.depthBuffer);
            if (!this.isStencilEnabled()) {
                ImprovedFramebuffer.renderbufferStorage(this.type, 36161, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
                ImprovedFramebuffer.framebufferRenderbuffer(this.type, 36160, 36096, 36161, this.depthBuffer);
            } else {
                ImprovedFramebuffer.renderbufferStorage(this.type, 36161, 35056, this.framebufferTextureWidth, this.framebufferTextureHeight);
                ImprovedFramebuffer.framebufferRenderbuffer(this.type, 36160, 36096, 36161, this.depthBuffer);
                ImprovedFramebuffer.framebufferRenderbuffer(this.type, 36160, 36128, 36161, this.depthBuffer);
            }
        }
        this.framebufferClear();
        this.unbindFramebufferTexture();
    }

    private int genFrameBuffers() {
        int fbo = -1;
        this.type = -1;
        if (GLContext.getCapabilities().OpenGL30) {
            fbo = GL30.glGenFramebuffers();
            this.type = 0;
        } else if (GLContext.getCapabilities().GL_ARB_framebuffer_object) {
            fbo = ARBFramebufferObject.glGenFramebuffers();
            this.type = 1;
        } else if (GLContext.getCapabilities().GL_EXT_framebuffer_object) {
            fbo = EXTFramebufferObject.glGenFramebuffersEXT();
            this.type = 2;
        }
        return fbo;
    }

    public int genRenderbuffers() {
        int rbo = -1;
        switch (this.type) {
            case 0: {
                rbo = GL30.glGenRenderbuffers();
                break;
            }
            case 1: {
                rbo = ARBFramebufferObject.glGenRenderbuffers();
                break;
            }
            case 2: {
                rbo = EXTFramebufferObject.glGenRenderbuffersEXT();
            }
        }
        return rbo;
    }

    public void deleteFramebuffer() {
        this.unbindFramebufferTexture();
        this.unbindFramebuffer();
        if (this.depthBuffer > -1) {
            this.deleteRenderbuffers(this.depthBuffer);
            this.depthBuffer = -1;
        }
        if (this.framebufferTexture > -1) {
            TextureUtil.deleteTexture((int)this.framebufferTexture);
            this.framebufferTexture = -1;
        }
        if (this.framebufferObject > -1) {
            ImprovedFramebuffer.bindFramebuffer(this.type, 36160, 0);
            this.deleteFramebuffers(this.framebufferObject);
            this.framebufferObject = -1;
        }
    }

    private void deleteFramebuffers(int framebufferIn) {
        switch (this.type) {
            case 0: {
                GL30.glDeleteFramebuffers((int)framebufferIn);
                break;
            }
            case 1: {
                ARBFramebufferObject.glDeleteFramebuffers((int)framebufferIn);
                break;
            }
            case 2: {
                EXTFramebufferObject.glDeleteFramebuffersEXT((int)framebufferIn);
            }
        }
    }

    private void deleteRenderbuffers(int renderbuffer) {
        switch (this.type) {
            case 0: {
                GL30.glDeleteRenderbuffers((int)renderbuffer);
                break;
            }
            case 1: {
                ARBFramebufferObject.glDeleteRenderbuffers((int)renderbuffer);
                break;
            }
            case 2: {
                EXTFramebufferObject.glDeleteRenderbuffersEXT((int)renderbuffer);
            }
        }
    }

    public void checkFramebufferComplete() {
        int i = this.checkFramebufferStatus(36160);
        if (i != 36053) {
            if (i == 36054) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
            }
            if (i == 36055) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
            }
            if (i == 36059) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
            }
            if (i == 36060) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
            }
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
        }
    }

    private int checkFramebufferStatus(int target) {
        switch (this.type) {
            case 0: {
                return GL30.glCheckFramebufferStatus((int)target);
            }
            case 1: {
                return ARBFramebufferObject.glCheckFramebufferStatus((int)target);
            }
            case 2: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT((int)target);
            }
        }
        return -1;
    }

    public static void bindFramebuffer(int type, int target, int framebufferIn) {
        switch (type) {
            case 0: {
                GL30.glBindFramebuffer((int)target, (int)framebufferIn);
                break;
            }
            case 1: {
                ARBFramebufferObject.glBindFramebuffer((int)target, (int)framebufferIn);
                break;
            }
            case 2: {
                EXTFramebufferObject.glBindFramebufferEXT((int)target, (int)framebufferIn);
            }
        }
    }

    public static void framebufferTexture2D(int type, int target, int attachment, int textarget, int texture, int level) {
        switch (type) {
            case 0: {
                GL30.glFramebufferTexture2D((int)target, (int)attachment, (int)textarget, (int)texture, (int)level);
                break;
            }
            case 1: {
                ARBFramebufferObject.glFramebufferTexture2D((int)target, (int)attachment, (int)textarget, (int)texture, (int)level);
                break;
            }
            case 2: {
                EXTFramebufferObject.glFramebufferTexture2DEXT((int)target, (int)attachment, (int)textarget, (int)texture, (int)level);
            }
        }
    }

    public static void bindRenderbuffer(int type, int target, int renderbuffer) {
        switch (type) {
            case 0: {
                GL30.glBindRenderbuffer((int)target, (int)renderbuffer);
                break;
            }
            case 1: {
                ARBFramebufferObject.glBindRenderbuffer((int)target, (int)renderbuffer);
                break;
            }
            case 2: {
                EXTFramebufferObject.glBindRenderbufferEXT((int)target, (int)renderbuffer);
            }
        }
    }

    public static void renderbufferStorage(int type, int target, int internalFormat, int width, int height) {
        switch (type) {
            case 0: {
                GL30.glRenderbufferStorage((int)target, (int)internalFormat, (int)width, (int)height);
                break;
            }
            case 1: {
                ARBFramebufferObject.glRenderbufferStorage((int)target, (int)internalFormat, (int)width, (int)height);
                break;
            }
            case 2: {
                EXTFramebufferObject.glRenderbufferStorageEXT((int)target, (int)internalFormat, (int)width, (int)height);
            }
        }
    }

    public static void framebufferRenderbuffer(int type, int target, int attachment, int renderBufferTarget, int renderBuffer) {
        switch (type) {
            case 0: {
                GL30.glFramebufferRenderbuffer((int)target, (int)attachment, (int)renderBufferTarget, (int)renderBuffer);
                break;
            }
            case 1: {
                ARBFramebufferObject.glFramebufferRenderbuffer((int)target, (int)attachment, (int)renderBufferTarget, (int)renderBuffer);
                break;
            }
            case 2: {
                EXTFramebufferObject.glFramebufferRenderbufferEXT((int)target, (int)attachment, (int)renderBufferTarget, (int)renderBuffer);
            }
        }
    }

    public void bindFramebuffer(boolean p_147610_1_) {
        ImprovedFramebuffer.bindFramebuffer(this.type, 36160, this.framebufferObject);
        if (p_147610_1_) {
            GlStateManager.viewport((int)0, (int)0, (int)this.framebufferWidth, (int)this.framebufferHeight);
        }
    }

    public void unbindFramebuffer() {
        ImprovedFramebuffer.bindFramebuffer(this.type, 36160, 0);
    }

    public void bindFramebufferTexture() {
        GlStateManager.bindTexture((int)this.framebufferTexture);
    }

    public void unbindFramebufferTexture() {
        GlStateManager.bindTexture((int)0);
    }

    public void setFramebufferFilter(int framebufferFilterIn) {
        this.framebufferFilter = framebufferFilterIn;
        GlStateManager.bindTexture((int)this.framebufferTexture);
        GlStateManager.glTexParameteri((int)3553, (int)10241, (int)framebufferFilterIn);
        GlStateManager.glTexParameteri((int)3553, (int)10240, (int)framebufferFilterIn);
        GlStateManager.glTexParameteri((int)3553, (int)10242, (int)10496);
        GlStateManager.glTexParameteri((int)3553, (int)10243, (int)10496);
        GlStateManager.bindTexture((int)0);
    }
}

