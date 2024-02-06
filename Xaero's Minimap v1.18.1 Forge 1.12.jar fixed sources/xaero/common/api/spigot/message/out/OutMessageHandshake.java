/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 */
package xaero.common.api.spigot.message.out;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xaero.common.api.spigot.message.out.OutMessageWaypoint;

public class OutMessageHandshake
extends OutMessageWaypoint {
    public OutMessageHandshake() {
        super('H', null);
    }

    public static class Handler
    implements IMessageHandler<OutMessageHandshake, IMessage> {
        public IMessage onMessage(OutMessageHandshake message, MessageContext ctx) {
            return null;
        }
    }
}

