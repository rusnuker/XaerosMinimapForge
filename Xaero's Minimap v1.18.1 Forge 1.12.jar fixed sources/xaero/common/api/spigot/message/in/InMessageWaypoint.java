//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 */
package xaero.common.api.spigot.message.in;

import io.netty.buffer.ByteBuf;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xaero.common.api.spigot.ServerWaypoint;
import xaero.common.api.spigot.ServerWaypointStorage;
import xaero.common.api.spigot.message.MessageWaypoint;
import xaero.common.minimap.waypoints.WaypointWorld;

public class InMessageWaypoint
extends MessageWaypoint
implements IMessage {
    public void fromBytes(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.waypoint = null;
            this.packetID = in.readChar();
            switch (this.packetID) {
                case 'A': {
                    this.worldUID = in.readUTF();
                    int x = in.readInt();
                    int y = in.readInt();
                    int z = in.readInt();
                    String name = in.readUTF();
                    char symbol = in.readChar();
                    int color = in.read();
                    int ID = in.readInt();
                    boolean hasRotation = in.readBoolean();
                    short yaw = 0;
                    if (hasRotation) {
                        yaw = in.readShort();
                    }
                    this.waypoint = new ServerWaypoint(this.worldUID, ID, x, y, z, name, Character.toString(symbol), color, hasRotation, yaw);
                    break;
                }
                case 'R': {
                    this.waypointID = in.readInt();
                    break;
                }
                case 'W': {
                    this.worldUID = in.readUTF();
                    this.worldName = in.readUTF();
                }
            }
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toBytes(ByteBuf buf) {
        System.err.println("Incorrect packet usage! (InMessageWaypoint)");
    }

    @Override
    public void run() {
        switch (this.packetID) {
            case 'A': {
                WaypointWorld world;
                ServerWaypoint wp = this.waypoint;
                if (wp == null || (world = ServerWaypointStorage.getWorld(wp.getWorldUID())) == null) break;
                world.getServerWaypoints().put(wp.getID(), wp);
                break;
            }
            case 'R': {
                ServerWaypointStorage.removeWaypoint(this.waypointID);
                break;
            }
            case 'W': {
                ServerWaypointStorage.addWorld(this.worldUID, this.worldName);
                ServerWaypointStorage.autoWorldUID = this.worldUID;
            }
        }
    }

    public static class Handler
    implements IMessageHandler<InMessageWaypoint, IMessage> {
        public IMessage onMessage(InMessageWaypoint message, MessageContext ctx) {
            Minecraft mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask((Runnable)message);
            return null;
        }
    }
}

