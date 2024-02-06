//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.interfaces;

import net.minecraft.client.resources.I18n;

public class Preset {
    private int[][] coords;
    private boolean[][] types;
    private String name;

    public Preset(String name, int[][] coords, boolean[][] types) {
        this.coords = coords;
        this.types = types;
        this.name = name;
    }

    public String getName() {
        return I18n.format((String)this.name, (Object[])new Object[0]);
    }

    public int[] getCoords(int i) {
        return this.coords[i];
    }

    public boolean[] getTypes(int i) {
        return this.types[i];
    }
}

