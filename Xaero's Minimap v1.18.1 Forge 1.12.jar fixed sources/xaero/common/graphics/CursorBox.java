//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.graphics;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;

public class CursorBox {
    private ArrayList<String> strings;
    private String language;
    private String fullCode = "";
    private String formatting;
    private int boxWidth = 150;
    private static final int color = -2147483640;

    public CursorBox(String code) {
        this(code, "");
    }

    public CursorBox(String code, String formatting) {
        this.fullCode = code;
        this.formatting = formatting;
        String text = I18n.format((String)code, (Object[])new Object[0]);
        this.createLines(text);
    }

    public CursorBox(int size) {
        this.strings = new ArrayList();
        for (int i = 0; i < size; ++i) {
            this.strings.add("");
        }
    }

    private String currentLanguage() {
        return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
    }

    public void createLines(String text) {
        try {
            this.language = this.currentLanguage();
        }
        catch (NullPointerException e) {
            this.language = "en_us";
        }
        this.strings = new ArrayList();
        String[] words = text.split(" ");
        int spaceWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(" ");
        StringBuilder line = new StringBuilder();
        int width = 0;
        for (int i = 0; i < words.length; ++i) {
            int wordWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(words[i]);
            if (width == 0 && wordWidth > this.boxWidth - 15) {
                this.boxWidth = wordWidth + 15;
            }
            if (width + wordWidth <= this.boxWidth - 15) {
                width += spaceWidth + wordWidth;
                line.append(words[i]).append(" ");
            } else {
                this.strings.add(this.formatting + line.toString());
                line.delete(0, line.length());
                width = 0;
                --i;
            }
            if (i != words.length - 1) continue;
            this.strings.add(this.formatting + line.toString());
        }
    }

    public String getString(int line) {
        return this.strings.get(line);
    }

    public void drawBox(int x, int y, int width, int height) {
        try {
            if (!this.language.equals(this.currentLanguage())) {
                this.createLines(I18n.format((String)this.fullCode, (Object[])new Object[0]));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        int fix = x + this.boxWidth > width ? x + this.boxWidth - width : 0;
        int h = 5 + this.strings.size() * 10 + 5;
        int fiy = y + h > height ? y + h - height : 0;
        Gui.drawRect((int)(x - fix), (int)(y - fiy), (int)(x + this.boxWidth - fix), (int)(y + h - fiy), (int)-2147483640);
        for (int i = 0; i < this.strings.size(); ++i) {
            String s = this.getString(i);
            Minecraft.getMinecraft().fontRenderer.drawString("\u00a7f" + s, x + 10 - fix, y + 5 + 10 * i - fiy, 0xFFFFFF);
        }
    }
}

