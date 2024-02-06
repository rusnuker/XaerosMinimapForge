//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 */
package xaero.common.controls.event;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import xaero.common.IXaeroMinimap;
import xaero.common.controls.event.KeyEvent;

public class KeyEventHandler {
    public ArrayList<KeyEvent> keyEvents = new ArrayList();
    public ArrayList<KeyEvent> oldKeyEvents = new ArrayList();

    private boolean eventExists(KeyBinding kb) {
        for (KeyEvent o : this.keyEvents) {
            if (o.getKb() != kb) continue;
            return true;
        }
        return this.oldEventExists(kb);
    }

    private boolean oldEventExists(KeyBinding kb) {
        for (KeyEvent o : this.oldKeyEvents) {
            if (o.getKb() != kb) continue;
            return true;
        }
        return false;
    }

    public void handleEvents(Minecraft mc, IXaeroMinimap modMain) {
        KeyEvent ke;
        int i;
        for (i = 0; i < this.keyEvents.size(); ++i) {
            ke = this.keyEvents.get(i);
            if (mc.currentScreen == null) {
                modMain.getControls().keyDown(ke.getKb(), ke.isTickEnd(), ke.isRepeat());
            }
            if (!ke.isRepeat()) {
                if (!this.oldEventExists(ke.getKb())) {
                    this.oldKeyEvents.add(ke);
                }
                this.keyEvents.remove(i);
                --i;
                continue;
            }
            if (modMain.getControls().isDown(ke.getKb())) continue;
            modMain.getControls().keyUp(ke.getKb(), ke.isTickEnd());
            this.keyEvents.remove(i);
            --i;
        }
        for (i = 0; i < this.oldKeyEvents.size(); ++i) {
            ke = this.oldKeyEvents.get(i);
            if (modMain.getControls().isDown(ke.getKb())) continue;
            modMain.getControls().keyUp(ke.getKb(), ke.isTickEnd());
            this.oldKeyEvents.remove(i);
            --i;
        }
    }

    public void onKeyInput(Minecraft mc, IXaeroMinimap modMain) {
        for (int i = 0; i < mc.gameSettings.keyBindings.length; ++i) {
            try {
                if (mc.currentScreen != null || this.eventExists(mc.gameSettings.keyBindings[i]) || !modMain.getControls().isDown(mc.gameSettings.keyBindings[i])) continue;
                this.keyEvents.add(new KeyEvent(mc.gameSettings.keyBindings[i], false, modMain.getSettings().isKeyRepeat(mc.gameSettings.keyBindings[i]), true));
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

