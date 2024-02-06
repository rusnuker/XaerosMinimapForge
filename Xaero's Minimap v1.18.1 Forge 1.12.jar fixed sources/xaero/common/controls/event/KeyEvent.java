/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package xaero.common.controls.event;

import net.minecraft.client.settings.KeyBinding;

public class KeyEvent {
    private KeyBinding kb;
    private boolean tickEnd;
    private boolean isRepeat;
    private boolean keyDown;

    public KeyEvent(KeyBinding kb, boolean tickEnd, boolean isRepeat, boolean keyDown) {
        this.kb = kb;
        this.tickEnd = tickEnd;
        this.isRepeat = isRepeat;
        this.keyDown = keyDown;
    }

    public KeyBinding getKb() {
        return this.kb;
    }

    public boolean isTickEnd() {
        return this.tickEnd;
    }

    public boolean isRepeat() {
        return this.isRepeat;
    }

    public boolean isKeyDown() {
        return this.keyDown;
    }
}

