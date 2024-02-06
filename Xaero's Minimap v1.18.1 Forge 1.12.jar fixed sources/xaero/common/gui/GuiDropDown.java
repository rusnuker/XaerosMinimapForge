//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import xaero.common.gui.IDropDownCallback;

public class GuiDropDown
extends Gui {
    public static final Color background = new Color(0, 0, 0, 200);
    public static final Color trim = new Color(160, 160, 160, 255);
    public static final Color trimInside = new Color(50, 50, 50, 255);
    private static final int h = 11;
    private int x;
    private int y;
    private int xOffset = 0;
    private int yOffset = 0;
    private int w;
    private String[] realOptions = new String[0];
    private String[] options = new String[0];
    private int[] realValues = new int[0];
    private int[] values = new int[0];
    private int selected = 0;
    private boolean closed = true;
    private int scroll;
    private long scrollTime;
    private int autoScrolling;
    private IDropDownCallback callback;

    public GuiDropDown(String[] options, int x, int y, int w, Integer selected, IDropDownCallback callback) {
        this(options, GuiDropDown.createDefaultValues(options.length), x, y, w, selected, callback);
    }

    public GuiDropDown(String[] options, int[] values, int x, int y, int w, Integer selected, IDropDownCallback callback) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.realOptions = options;
        this.realValues = values;
        this.callback = callback;
        this.selectValue(selected);
    }

    private static int[] createDefaultValues(int length) {
        int[] values = new int[length];
        for (int i = 0; i < length; ++i) {
            values[i] = i;
        }
        return values;
    }

    public int size() {
        return this.options.length;
    }

    public int getXWithOffset() {
        return this.x + this.xOffset;
    }

    public int getYWithOffset() {
        return this.y + this.yOffset;
    }

    public int getValue(int slot) {
        return this.values[slot];
    }

    private void drawSlot(String text, int pos, int mouseX, int mouseY, int scaledHeight) {
        if (this.closed && this.onDropDown(mouseX, mouseY, scaledHeight) || !this.closed && this.onDropDownSlot(mouseX, mouseY, pos)) {
            GuiDropDown.drawRect((int)this.getXWithOffset(), (int)(this.getYWithOffset() + 11 * pos), (int)(this.getXWithOffset() + this.w), (int)(this.getYWithOffset() + 11 + 11 * pos), (int)trimInside.hashCode());
        } else {
            GuiDropDown.drawRect((int)this.getXWithOffset(), (int)(this.getYWithOffset() + 11 * pos), (int)(this.getXWithOffset() + this.w), (int)(this.getYWithOffset() + 11 + 11 * pos), (int)background.hashCode());
        }
        this.drawHorizontalLine(this.getXWithOffset() + 1, this.getXWithOffset() + this.w - 1, this.getYWithOffset() + 11 * pos, trimInside.hashCode());
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, text, this.getXWithOffset() + this.w / 2, this.getYWithOffset() + 2 + 11 * pos, 0xFFFFFF);
    }

    private void drawMenu(int amount, int mouseX, int mouseY, int scaledHeight) {
        int first;
        boolean scrolling = this.scrolling(scaledHeight);
        int totalH = 11 * (amount + (scrolling ? 2 : 0));
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        int height = scaledresolution.getScaledHeight();
        this.yOffset = this.y + totalH + 1 > height ? height - this.y - totalH - 1 : 0;
        int n = first = this.closed ? 0 : this.scroll;
        if (scrolling) {
            this.drawSlot((this.scroll == 0 ? "\u00a78" : "\u00a77") + I18n.format((String)"gui.xaero_up", (Object[])new Object[0]), 0, mouseX, mouseY, scaledHeight);
            this.drawSlot((this.scroll + this.optionLimit(scaledHeight) >= this.options.length ? "\u00a78" : "\u00a77") + I18n.format((String)"gui.xaero_down", (Object[])new Object[0]), amount + 1, mouseX, mouseY, scaledHeight);
        }
        for (int i = first; i < first + amount; ++i) {
            this.drawSlot(I18n.format((String)this.options[i], (Object[])new Object[0]).replace("\u00a7\u00a7", ":"), i - first + (scrolling ? 1 : 0), mouseX, mouseY, scaledHeight);
        }
        this.drawVerticalLine(this.getXWithOffset(), this.getYWithOffset(), this.getYWithOffset() + totalH, trim.hashCode());
        this.drawVerticalLine(this.getXWithOffset() + this.w, this.getYWithOffset(), this.getYWithOffset() + totalH, trim.hashCode());
        this.drawHorizontalLine(this.getXWithOffset(), this.getXWithOffset() + this.w, this.getYWithOffset(), trim.hashCode());
        this.drawHorizontalLine(this.getXWithOffset(), this.getXWithOffset() + this.w, this.getYWithOffset() + totalH, trim.hashCode());
    }

    private boolean scrolling(int scaledHeight) {
        return this.options.length > this.optionLimit(scaledHeight) && !this.closed;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton, int scaledHeight) {
        if (!this.closed) {
            int clickedValue = this.getClickedValue(mouseX, mouseY, scaledHeight);
            if (clickedValue >= 0) {
                this.selectValue(clickedValue);
            } else {
                this.autoScrolling = clickedValue == -1 ? 1 : -1;
                this.scrollTime = System.currentTimeMillis();
                this.mouseScrolled(this.autoScrolling, mouseX, mouseY, scaledHeight);
            }
        } else if (this.options.length > 1) {
            this.closed = false;
            this.scroll = 0;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton, int scaledHeight) {
        this.autoScrolling = 0;
    }

    private int getClickedValue(int mouseX, int mouseY, int scaledHeight) {
        int yOnMenu = mouseY - this.getYWithOffset();
        int visibleSlotIndex = yOnMenu / 11;
        boolean upArrow = this.scrolling(scaledHeight);
        if (upArrow && visibleSlotIndex == 0) {
            return -1;
        }
        if (visibleSlotIndex >= this.optionLimit(scaledHeight) + (upArrow ? 1 : 0)) {
            return -2;
        }
        int slot = this.scroll + visibleSlotIndex - (upArrow ? 1 : 0);
        if (slot >= this.options.length) {
            slot = this.options.length - 1;
        }
        return this.values[slot];
    }

    public boolean onDropDown(int mouseX, int mouseY, int scaledHeight) {
        int xOnMenu = mouseX - this.getXWithOffset();
        int yOnMenu = mouseY - this.getYWithOffset();
        return xOnMenu >= 0 && yOnMenu >= 0 && xOnMenu <= this.w && yOnMenu < (this.closed ? 11 : (Math.min(this.options.length, this.optionLimit(scaledHeight)) + (this.scrolling(scaledHeight) ? 2 : 0)) * 11);
    }

    private boolean onDropDownSlot(int mouseX, int mouseY, int id) {
        int xOnMenu = mouseX - this.getXWithOffset();
        int yOnMenu = mouseY - this.getYWithOffset();
        return xOnMenu >= 0 && yOnMenu >= id * 11 && xOnMenu <= this.w && yOnMenu < id * 11 + 11;
    }

    public void selectValue(int id) {
        boolean newValue = id != this.selected;
        this.selected = id;
        this.closed = true;
        this.options = (String[])this.realOptions.clone();
        this.values = (int[])this.realValues.clone();
        this.options[0] = this.realOptions[this.selected];
        this.values[0] = this.realValues[this.selected];
        for (int i = this.selected; i > 0; --i) {
            this.options[i] = this.realOptions[i - 1];
            this.values[i] = this.realValues[i - 1];
        }
        if (newValue) {
            this.callback.onSelected(this, this.selected);
        }
    }

    public void drawButton(int mouseX, int mouseY, int scaledHeight) {
        if (this.autoScrolling != 0 && System.currentTimeMillis() - this.scrollTime > 100L) {
            this.scrollTime = System.currentTimeMillis();
            this.mouseScrolled(this.autoScrolling, mouseX, mouseY, scaledHeight);
        }
        this.drawMenu(this.closed ? 1 : Math.min(this.optionLimit(scaledHeight), this.options.length), mouseX, mouseY, scaledHeight);
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void mouseScrolled(int wheel, int mouseXScaled, int mouseYScaled, int scaledHeight) {
        int newScroll = this.scroll - wheel;
        int optionLimit = this.optionLimit(scaledHeight);
        if (newScroll + optionLimit > this.options.length) {
            newScroll = this.options.length - optionLimit;
        }
        if (newScroll < 0) {
            newScroll = 0;
        }
        this.scroll = newScroll;
    }

    private int optionLimit(int scaledHeight) {
        return Math.max(1, scaledHeight / 11 - 2);
    }
}

