//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package xaero.common.interfaces;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import xaero.common.graphics.CursorBox;
import xaero.common.interfaces.InterfaceManager;
import xaero.common.interfaces.Preset;
import xaero.common.settings.ModOptions;

public abstract class Interface {
    private CursorBox cBox;
    private String iname;
    private int id;
    private int bx;
    private int by;
    private int x;
    private int y;
    private int actualx;
    private int actualy;
    private int w0;
    private int h0;
    private int w;
    private int h;
    private int wc;
    private int hc;
    private boolean multisized;
    private boolean centered;
    private boolean bcentered;
    private boolean flipped;
    private boolean bflipped;
    private boolean flippedInitial;
    private boolean fromRight;
    private boolean bfromRight;
    private boolean fromBottom;
    private boolean bfromBottom;
    private ModOptions option;

    public Interface(InterfaceManager interfaceHandler, String name, int id, int w, int h, ModOptions option) {
        this(interfaceHandler, name, id, w, h, w, h, option);
    }

    public Interface(InterfaceManager interfaceHandler, String name, int id, int w, int h, int wc, int hc, ModOptions option) {
        this.id = id;
        this.iname = name;
        this.w0 = this.w = w;
        this.h0 = this.h = h;
        this.wc = wc;
        this.hc = hc;
        this.multisized = wc != w || hc != h;
        this.bflipped = false;
        this.flipped = false;
        this.flippedInitial = false;
        Preset preset = interfaceHandler.getDefaultPreset();
        this.actualx = this.x = preset.getCoords(id)[0];
        this.bx = this.x;
        this.actualy = this.y = preset.getCoords(id)[1];
        this.by = this.y;
        this.bcentered = this.centered = preset.getTypes(id)[0];
        this.bfromRight = this.fromRight = preset.getTypes(id)[1];
        this.bfromBottom = this.fromBottom = preset.getTypes(id)[2];
        this.option = option;
        this.cBox = new CursorBox(3){

            @Override
            public String getString(int line) {
                switch (line) {
                    case 0: {
                        return I18n.format((String)Interface.this.iname, (Object[])new Object[0]);
                    }
                    case 1: {
                        return I18n.format((String)"gui.xaero_centered", (Object[])new Object[0]) + " " + I18n.format((String)(Interface.this.centered ? "gui.xaero_true" : "gui.xaero_false"), (Object[])new Object[0]) + " " + I18n.format((String)"gui.xaero_press_c", (Object[])new Object[0]);
                    }
                    case 2: {
                        return I18n.format((String)"gui.xaero_flipped", (Object[])new Object[0]) + " " + I18n.format((String)(Interface.this.flipped ? "gui.xaero_true" : "gui.xaero_false"), (Object[])new Object[0]) + " " + I18n.format((String)"gui.xaero_press_f", (Object[])new Object[0]);
                    }
                }
                return "";
            }
        };
    }

    public void drawInterface(int width, int height, int scale, float partial) {
        if (this.fromRight) {
            this.x = width - this.x;
        }
        if (this.fromBottom) {
            this.y = height - this.y;
        }
        GL11.glEnable((int)3008);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
    }

    public boolean shouldFlip(int width) {
        return this.flipped && this.x + this.w / 2 < width / 2 || !this.flipped && this.x + this.w / 2 > width / 2;
    }

    public void backup() {
        this.bx = this.actualx;
        this.by = this.actualy;
        this.bcentered = this.centered;
        this.bflipped = this.flipped;
        this.bfromRight = this.fromRight;
        this.bfromBottom = this.fromBottom;
    }

    public void restore() {
        this.actualx = this.bx;
        this.actualy = this.by;
        this.centered = this.bcentered;
        this.flipped = this.bflipped;
        this.fromRight = this.bfromRight;
        this.fromBottom = this.bfromBottom;
    }

    public void applyPreset(Preset preset) {
        this.actualx = preset.getCoords(this.id)[0];
        this.actualy = preset.getCoords(this.id)[1];
        this.centered = preset.getTypes(this.id)[0];
        this.flipped = this.flippedInitial;
        this.fromRight = preset.getTypes(this.id)[1];
        this.fromBottom = preset.getTypes(this.id)[2];
    }

    public ModOptions getOption() {
        return this.option;
    }

    public boolean isFromRight() {
        return this.fromRight;
    }

    public void setFromRight(boolean fromRight) {
        this.fromRight = fromRight;
    }

    public int getW(int scale) {
        return this.w;
    }

    public int getH(int scale) {
        return this.h;
    }

    public int getWC(int scale) {
        return this.wc;
    }

    public int getHC(int scale) {
        return this.hc;
    }

    public int getW0(int scale) {
        return this.w0;
    }

    public int getH0(int scale) {
        return this.h0;
    }

    public int getSize() {
        return this.w * this.h;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFlipped() {
        return this.flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isCentered() {
        return this.centered;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public int getActualx() {
        return this.actualx;
    }

    public void setActualx(int actualx) {
        this.actualx = actualx;
    }

    public int getActualy() {
        return this.actualy;
    }

    public void setActualy(int actualy) {
        this.actualy = actualy;
    }

    public boolean isMulti() {
        return this.multisized;
    }

    public int getW() {
        return this.w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public CursorBox getcBox() {
        return this.cBox;
    }

    public String getIname() {
        return this.iname;
    }

    public boolean isFromBottom() {
        return this.fromBottom;
    }

    public void setFromBottom(boolean fromBottom) {
        this.fromBottom = fromBottom;
    }
}

