/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package xaero.common.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xaero.common.settings.ModOptions;

@SideOnly(value=Side.CLIENT)
public class MySuperTinyButton
extends GuiButton {
    private final ModOptions modOptions;

    public MySuperTinyButton(int par1, int par2, int par3, String par4Str) {
        this(par1, par2, par3, null, par4Str);
    }

    public MySuperTinyButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.modOptions = null;
    }

    public MySuperTinyButton(int par1, int par2, int par3, ModOptions par4EnumOptions, String par5Str) {
        super(par1, par2, par3, 50, 20, par5Str);
        this.modOptions = par4EnumOptions;
    }

    public ModOptions returnModOptions() {
        return this.modOptions;
    }
}

