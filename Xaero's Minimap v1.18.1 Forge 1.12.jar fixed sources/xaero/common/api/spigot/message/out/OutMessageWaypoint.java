/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 */
package xaero.common.api.spigot.message.out;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xaero.common.api.spigot.ServerWaypoint;
import xaero.common.api.spigot.message.MessageWaypoint;

public abstract class OutMessageWaypoint
extends MessageWaypoint
implements IMessage {
    public OutMessageWaypoint(char packetID, ServerWaypoint waypoint) {
        this.packetID = packetID;
        this.waypoint = waypoint;
    }

    public void fromBytes(ByteBuf buf) {
        System.err.println("Incorrect packet usage! (OutMessageWaypoint)");
    }

    public void toBytes(ByteBuf buf) {
        buf.writeChar((int)this.packetID);
    }

    @Override
    public void run() {
        System.err.println("Incorrect packet usage! (OutMessageWaypoint)");
    }

    public static class Handler
    implements IMessageHandler<OutMessageWaypoint, IMessage> {
        public IMessage onMessage(OutMessageWaypoint message, MessageContext ctx) {
            return null;
        }
    }
}

