/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.minimap.write;

public class MinimapWriterHelper {
    void getBrightestColour(int r, int g, int b, int[] result) {
        int max = Math.max(r, Math.max(g, b));
        if (max == 0) {
            result[0] = r;
            result[1] = g;
            result[2] = b;
            return;
        }
        result[0] = 255 * r / max;
        result[1] = 255 * g / max;
        result[2] = 255 * b / max;
    }
}

