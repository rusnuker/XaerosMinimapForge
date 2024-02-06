//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.I18n
 */
package xaero.common.minimap.waypoints;

import net.minecraft.client.resources.I18n;

public class Waypoint {
    private int x;
    private int y;
    private int z;
    private String name;
    private String symbol;
    private int color;
    private boolean disabled = false;
    private int type = 0;
    private boolean rotation = false;
    private int yaw = 0;
    private double lastDistance = 0.0;
    private boolean temporary;

    public Waypoint(int x, int y, int z, String name, String symbol, int color) {
        this(x, y, z, name, symbol, color, 0, false);
    }

    public Waypoint(int x, int y, int z, String name, String symbol, int color, int type) {
        this(x, y, z, name, symbol, color, type, false);
    }

    public Waypoint(int x, int y, int z, String name, String symbol, int color, int type, boolean temp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.symbol = symbol;
        this.color = color;
        this.type = type;
        this.name = name;
        this.temporary = temp;
    }

    public double getDistanceSq(double x, double y, double z) {
        double d3 = (double)this.x - x;
        double d4 = (double)this.y - y;
        double d5 = (double)this.z - z;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public String getLocalizedName() {
        return I18n.format((String)this.name, (Object[])new Object[0]);
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean b) {
        this.disabled = b;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLastDistance() {
        return this.lastDistance;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getName() {
        return this.name;
    }

    public String getNameSafe(String replacement) {
        return this.getName().replace(":", replacement);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTemporary() {
        return this.temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getSymbolSafe(String replacement) {
        return this.getSymbol().replace(":", replacement);
    }

    public boolean isRotation() {
        return this.rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    public int getYaw() {
        return this.yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public int getColor() {
        return this.color;
    }

    public void setLastDistance(double lastDistance) {
        this.lastDistance = lastDistance;
    }

    public static String getStringFromStringSafe(String stringSafe, String replacement) {
        return stringSafe.replace(replacement, ":");
    }
}

