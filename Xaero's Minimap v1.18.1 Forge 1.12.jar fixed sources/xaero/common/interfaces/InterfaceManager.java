//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package xaero.common.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiEditMode;
import xaero.common.interfaces.IInterfaceLoader;
import xaero.common.interfaces.Interface;
import xaero.common.interfaces.Preset;
import xaero.common.minimap.MinimapInterface;
import xaero.common.minimap.MinimapProcessor;

public class InterfaceManager {
    private IXaeroMinimap modMain;
    private Minecraft mc;
    private ArrayList<Preset> presets;
    private ArrayList<Interface> list;
    private int actionTimer;
    private int selectedId;
    private int draggingId;
    private int draggingOffX;
    private int draggingOffY;
    private long lastFlip;

    public InterfaceManager(IXaeroMinimap modMain, IInterfaceLoader loader) {
        this.modMain = modMain;
        this.presets = new ArrayList();
        this.list = new ArrayList();
        this.mc = Minecraft.getMinecraft();
        this.selectedId = -1;
        this.draggingId = -1;
        loader.loadPresets(this);
        loader.load(modMain, this);
    }

    public MinimapInterface getMinimapInterface() {
        return (MinimapInterface)this.list.get(4);
    }

    public MinimapProcessor getMinimap() {
        return this.getMinimapInterface().getMinimap();
    }

    public boolean overAButton(int mouseX, int mouseY) {
        if (this.mc.currentScreen instanceof GuiEditMode) {
            for (int k = 0; k < ((GuiEditMode)this.mc.currentScreen).getButtons().size(); ++k) {
                GuiButton b = (GuiButton)((GuiEditMode)this.mc.currentScreen).getButtons().get(k);
                if (mouseX < b.x || mouseY < b.y || mouseX >= b.x + 150 || mouseY >= b.y + 20) continue;
                return true;
            }
        }
        return false;
    }

    protected void updateBlinkingOverridable() {
    }

    public void updateInterfaces(int mouseX, int mouseY, int width, int height, int scale) {
        if (this.actionTimer <= 0) {
            this.updateBlinkingOverridable();
            if (this.modMain.getEvents().getLastGuiOpen() instanceof GuiEditMode) {
                int i;
                if (Mouse.isButtonDown((int)1)) {
                    this.selectedId = -1;
                }
                if ((i = this.getInterfaceId(mouseX, mouseY, width, height, scale)) == -1) {
                    i = this.selectedId;
                }
                if (i != -1) {
                    if (Mouse.isButtonDown((int)0) && this.draggingId == -1) {
                        this.draggingId = i;
                        this.selectedId = i;
                        if (this.list.get(i).isFromRight()) {
                            this.list.get(i).setX(width - this.list.get(i).getX());
                        }
                        if (this.list.get(i).isFromBottom()) {
                            this.list.get(i).setY(height - this.list.get(i).getY());
                        }
                        this.draggingOffX = this.list.get(i).getX() - mouseX;
                        this.draggingOffY = this.list.get(i).getY() - mouseY;
                        if (this.list.get(i).isFromRight()) {
                            this.list.get(i).setX(width - this.list.get(i).getX());
                        }
                        if (this.list.get(i).isFromBottom()) {
                            this.list.get(i).setY(height - this.list.get(i).getY());
                        }
                    } else if (!Mouse.isButtonDown((int)0) && this.draggingId != -1) {
                        this.draggingId = -1;
                        this.draggingOffX = 0;
                        this.draggingOffY = 0;
                    }
                    if (this.selectedId != -1) {
                        i = this.selectedId;
                    }
                    if (Keyboard.isKeyDown((int)33) && System.currentTimeMillis() - this.lastFlip > 300L) {
                        this.lastFlip = System.currentTimeMillis();
                        this.list.get(i).setFlipped(!this.list.get(i).isFlipped());
                    }
                    if (Keyboard.isKeyDown((int)46) && System.currentTimeMillis() - this.lastFlip > 300L) {
                        this.lastFlip = System.currentTimeMillis();
                        this.list.get(i).setCentered(!this.list.get(i).isCentered());
                    }
                    if (Keyboard.isKeyDown((int)31)) {
                        this.selectedId = -1;
                        this.draggingId = -1;
                        this.modMain.getGuiHelper().openInterfaceSettings(i);
                    }
                }
                if (this.draggingId != -1) {
                    Interface dragged = this.list.get(this.draggingId);
                    if (!dragged.isCentered()) {
                        dragged.setActualx(mouseX + this.draggingOffX);
                        if (dragged.isFromRight()) {
                            dragged.setActualx(width - dragged.getActualx());
                        }
                    }
                    int centerX = dragged.getActualx() + dragged.getW() / 2 * (dragged.isFromRight() ? -1 : 1);
                    if (dragged.isFromRight() && (width & 1) == 0) {
                        ++centerX;
                    }
                    if (centerX > width / 2) {
                        dragged.setFromRight(!dragged.isFromRight());
                        dragged.setActualx(width - dragged.getActualx());
                    }
                    dragged.setActualy(mouseY + this.draggingOffY);
                    if (dragged.isFromBottom()) {
                        dragged.setActualy(height - dragged.getActualy());
                    }
                    int centerY = dragged.getActualy() + dragged.getH() / 2 * (dragged.isFromBottom() ? -1 : 1);
                    if (dragged.isFromBottom() && (height & 1) == 0) {
                        ++centerY;
                    }
                    if (centerY > height / 2) {
                        dragged.setFromBottom(!dragged.isFromBottom());
                        dragged.setActualy(height - dragged.getActualy());
                    }
                }
            }
        } else {
            --this.actionTimer;
        }
        for (Interface j : this.list) {
            j.setX(j.getActualx());
            j.setY(j.getActualy());
            if (j.isFromRight()) {
                j.setX(width - j.getX());
            }
            if (j.isFromBottom()) {
                j.setY(height - j.getY());
            }
            if (j.isCentered()) {
                if (j.isMulti()) {
                    j.setW(j.getWC(scale));
                    j.setH(j.getHC(scale));
                }
                j.setX(width / 2 - j.getW(scale) / 2);
            } else if (j.isMulti()) {
                j.setW(j.getW0(scale));
                j.setH(j.getH0(scale));
            }
            if (j.getX() < 5) {
                j.setX(0);
            }
            if (j.getY() < 5) {
                j.setY(0);
            }
            if (j.getX() + j.getW(scale) > width - 5) {
                j.setX(width - j.getW(scale));
            }
            if (j.getY() + j.getH(scale) <= height - 5) continue;
            j.setY(height - j.getH(scale));
        }
    }

    public int getInterfaceId(int mouseX, int mouseY, int width, int height, int scale) {
        int toReturn = -1;
        int size = 0;
        for (int i = 0; i < this.list.size(); ++i) {
            Interface l = this.list.get(i);
            int x = l.getX();
            if (l.isFromRight()) {
                x = width - x;
            }
            int y = l.getY();
            if (l.isFromBottom()) {
                y = height - y;
            }
            int x2 = x + l.getW(scale);
            int y2 = y + l.getH(scale);
            int isize = l.getSize();
            if (l.getIname().equals("dummy") || size != 0 && isize >= size || this.overAButton(mouseX, mouseY) || mouseX < x || mouseX >= x2 || mouseY < y || mouseY >= y2) continue;
            size = isize;
            toReturn = i;
        }
        return toReturn;
    }

    public void add(Interface i) {
        this.list.add(i);
    }

    public Preset getDefaultPreset() {
        return this.presets.get(0);
    }

    public Preset getPreset(int id) {
        return this.presets.get(id);
    }

    public int getNextId() {
        return this.list.size();
    }

    public void addPreset(Preset preset) {
        this.presets.add(preset);
    }

    public int getSelectedId() {
        return this.selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public int getDraggingId() {
        return this.draggingId;
    }

    public void setDraggingId(int draggingId) {
        this.draggingId = draggingId;
    }

    public Iterator<Interface> getInterfaceIterator() {
        return this.list.iterator();
    }

    public Iterator<Preset> getPresetsIterator() {
        return this.presets.iterator();
    }

    public int getActionTimer() {
        return this.actionTimer;
    }

    public void setActionTimer(int actionTimer) {
        this.actionTimer = actionTimer;
    }
}

