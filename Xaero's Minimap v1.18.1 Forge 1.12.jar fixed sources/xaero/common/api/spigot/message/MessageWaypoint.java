/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.api.spigot.message;

import xaero.common.api.spigot.ServerWaypoint;

public abstract class MessageWaypoint
implements Runnable {
    protected char packetID;
    protected String worldUID;
    protected String worldName;
    protected ServerWaypoint waypoint;
    protected int waypointID;

    @Override
    public abstract void run();
}

