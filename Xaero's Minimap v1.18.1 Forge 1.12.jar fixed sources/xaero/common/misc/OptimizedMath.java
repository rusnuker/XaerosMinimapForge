/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.misc;

public class OptimizedMath {
    public static int myFloor(double d) {
        if (d < 0.0) {
            d -= 1.0;
        }
        return (int)d;
    }
}

