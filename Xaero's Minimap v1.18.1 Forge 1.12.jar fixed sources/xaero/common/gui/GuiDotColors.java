//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.input.Mouse
 */
package xaero.common.gui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiDropDown;
import xaero.common.gui.IDropDownCallback;
import xaero.common.gui.MySmallButton;
import xaero.common.gui.MyTinyButton;
import xaero.common.settings.ModOptions;
import xaero.common.settings.ModSettings;

public class GuiDotColors
extends GuiScreen
implements IDropDownCallback {
    private IXaeroMinimap modMain;
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private ArrayList<GuiDropDown> dropDowns;
    private String[] colorOptions;
    private int mobsColor;
    private int hostileColor;
    private int itemsColor;
    private int otherColor;
    private int playerOption;
    private int teamOption;
    private GuiDropDown mobsColorDD;
    private GuiDropDown hostileColorDD;
    private GuiDropDown itemsColorDD;
    private GuiDropDown otherColorDD;
    private GuiDropDown playerOptionDD;
    private GuiDropDown teamOptionDD;
    private boolean[] displaySettingsChanged;
    private boolean dropped;

    public GuiDotColors(IXaeroMinimap modMain, GuiScreen par1GuiScreen) {
        this.modMain = modMain;
        this.parentGuiScreen = par1GuiScreen;
        this.dropDowns = new ArrayList();
        this.displaySettingsChanged = new boolean[6];
        this.colorOptions = this.createColorOptions();
        this.mobsColor = modMain.getSettings().mobsColor;
        this.hostileColor = modMain.getSettings().hostileColor;
        this.itemsColor = modMain.getSettings().itemsColor;
        this.otherColor = modMain.getSettings().otherColor;
        this.playerOption = modMain.getSettings().playersColor != -1 ? modMain.getSettings().playersColor : this.colorOptions.length;
        this.teamOption = modMain.getSettings().otherTeamColor != -1 ? modMain.getSettings().otherTeamColor + 1 : 0;
    }

    private String getButtonText(ModOptions par1EnumOptions) {
        String s = "";
        int id = this.settingIdForEnum(par1EnumOptions);
        boolean changed = this.displaySettingsChanged[id];
        boolean currentClientSetting = this.modMain.getSettings().getClientBooleanValue(par1EnumOptions);
        boolean clientSetting = changed ? !currentClientSetting : currentClientSetting;
        boolean serverSetting = this.modMain.getSettings().getBooleanValue(par1EnumOptions);
        s = s + ModSettings.getTranslation(clientSetting) + (serverSetting != currentClientSetting ? "\u00a7e (" + ModSettings.getTranslation(serverSetting) + ")" : "");
        return s;
    }

    public void initGui() {
        this.screenTitle = I18n.format((String)"gui.xaero_entity_radar", (Object[])new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(new MySmallButton(200, this.width / 2 - 155, this.height / 6 + 168, I18n.format((String)"gui.xaero_confirm", (Object[])new Object[0])));
        this.buttonList.add(new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        this.dropDowns.clear();
        String[] playerOptions = new String[this.colorOptions.length + 1];
        String[] teamOptions = new String[this.colorOptions.length + 1];
        for (int i = 0; i < this.colorOptions.length; ++i) {
            playerOptions[i] = this.colorOptions[i];
            teamOptions[i + 1] = this.colorOptions[i];
        }
        playerOptions[this.colorOptions.length] = "gui.xaero_team_colours";
        teamOptions[0] = "gui.xaero_players";
        this.mobsColorDD = new GuiDropDown(this.colorOptions, this.width / 2 - 60, this.height / 7 + 70, 120, this.mobsColor, this);
        this.dropDowns.add(this.mobsColorDD);
        this.buttonList.add(new MyTinyButton(ModOptions.MOBS.returnEnumOrdinal(), this.width / 2 + 63, this.height / 7 + 66, ModOptions.MOBS, this.getButtonText(ModOptions.MOBS)));
        this.hostileColorDD = new GuiDropDown(this.colorOptions, this.width / 2 - 60, this.height / 7 + 95, 120, this.hostileColor, this);
        this.dropDowns.add(this.hostileColorDD);
        this.buttonList.add(new MyTinyButton(ModOptions.HOSTILE.returnEnumOrdinal(), this.width / 2 + 63, this.height / 7 + 91, ModOptions.HOSTILE, this.getButtonText(ModOptions.HOSTILE)));
        this.itemsColorDD = new GuiDropDown(this.colorOptions, this.width / 2 - 60, this.height / 7 + 120, 120, this.itemsColor, this);
        this.dropDowns.add(this.itemsColorDD);
        this.buttonList.add(new MyTinyButton(ModOptions.ITEMS.returnEnumOrdinal(), this.width / 2 + 63, this.height / 7 + 116, ModOptions.ITEMS, this.getButtonText(ModOptions.ITEMS)));
        this.otherColorDD = new GuiDropDown(this.colorOptions, this.width / 2 - 60, this.height / 7 + 145, 120, this.otherColor, this);
        this.dropDowns.add(this.otherColorDD);
        this.buttonList.add(new MyTinyButton(ModOptions.ENTITIES.returnEnumOrdinal(), this.width / 2 + 63, this.height / 7 + 141, ModOptions.ENTITIES, this.getButtonText(ModOptions.ENTITIES)));
        this.playerOptionDD = new GuiDropDown(playerOptions, this.width / 2 - 60, this.height / 7 + 20, 120, this.playerOption, this);
        this.dropDowns.add(this.playerOptionDD);
        this.buttonList.add(new MyTinyButton(ModOptions.PLAYERS.returnEnumOrdinal(), this.width / 2 + 63, this.height / 7 + 16, ModOptions.PLAYERS, this.getButtonText(ModOptions.PLAYERS)));
        this.teamOptionDD = new GuiDropDown(teamOptions, this.width / 2 - 60, this.height / 7 + 45, 120, this.teamOption, this);
        this.dropDowns.add(this.teamOptionDD);
        this.buttonList.add(new MyTinyButton(ModOptions.DISPLAY_OTHER_TEAM.returnEnumOrdinal(), this.width / 2 + 63, this.height / 7 + 41, ModOptions.DISPLAY_OTHER_TEAM, this.getButtonText(ModOptions.DISPLAY_OTHER_TEAM)));
    }

    private String[] createColorOptions() {
        String[] options = new String[ModSettings.ENCHANT_COLOR_NAMES.length];
        for (int i = 0; i < options.length; ++i) {
            options[i] = i == 0 ? I18n.format((String)ModSettings.ENCHANT_COLOR_NAMES[i], (Object[])new Object[0]) : "\u00a7" + ModSettings.ENCHANT_COLORS[i] + I18n.format((String)ModSettings.ENCHANT_COLOR_NAMES[i], (Object[])new Object[0]);
        }
        return options;
    }

    private ModOptions settingEnumForId(int opt) {
        switch (opt) {
            case 0: {
                return ModOptions.PLAYERS;
            }
            case 1: {
                return ModOptions.DISPLAY_OTHER_TEAM;
            }
            case 2: {
                return ModOptions.MOBS;
            }
            case 3: {
                return ModOptions.HOSTILE;
            }
            case 4: {
                return ModOptions.ITEMS;
            }
        }
        return ModOptions.ENTITIES;
    }

    private int settingIdForEnum(ModOptions opt) {
        if (opt == ModOptions.PLAYERS) {
            return 0;
        }
        if (opt == ModOptions.DISPLAY_OTHER_TEAM) {
            return 1;
        }
        if (opt == ModOptions.MOBS) {
            return 2;
        }
        if (opt == ModOptions.HOSTILE) {
            return 3;
        }
        if (opt == ModOptions.ITEMS) {
            return 4;
        }
        return 5;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.mc.gameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MyTinyButton) {
                boolean changed = this.displaySettingsChanged[this.settingIdForEnum(((MyTinyButton)par1GuiButton).returnModOptions())];
                this.displaySettingsChanged[this.settingIdForEnum((ModOptions)((MyTinyButton)par1GuiButton).returnModOptions())] = !changed;
                par1GuiButton.displayString = this.getButtonText(((MyTinyButton)par1GuiButton).returnModOptions());
            }
            if (par1GuiButton.id == 200) {
                this.modMain.getSettings().mobsColor = this.mobsColor;
                this.modMain.getSettings().hostileColor = this.hostileColor;
                this.modMain.getSettings().itemsColor = this.itemsColor;
                this.modMain.getSettings().otherColor = this.otherColor;
                this.modMain.getSettings().playersColor = this.playerOption < this.colorOptions.length ? this.playerOption : -1;
                this.modMain.getSettings().otherTeamColor = this.teamOption > 0 ? this.teamOption - 1 : -1;
                for (int opt = 0; opt < this.displaySettingsChanged.length; ++opt) {
                    if (!this.displaySettingsChanged[opt]) continue;
                    try {
                        this.modMain.getSettings().setOptionValue(this.settingEnumForId(opt), 1);
                        continue;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    this.modMain.getSettings().saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 201) {
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.mc.gameSettings.guiScale != var2) {
                ScaledResolution var3 = new ScaledResolution(this.mc);
                int var4 = var3.getScaledWidth();
                int var5 = var3.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        for (GuiDropDown d : this.dropDowns) {
            if (!d.isClosed() && d.onDropDown(par1, par2, this.height)) {
                d.mouseClicked(par1, par2, par3, this.height);
                return;
            }
            d.setClosed(true);
        }
        for (GuiDropDown d : this.dropDowns) {
            if (d.onDropDown(par1, par2, this.height)) {
                d.mouseClicked(par1, par2, par3, this.height);
                return;
            }
            d.setClosed(true);
        }
        super.mouseClicked(par1, par2, par3);
    }

    protected void mouseReleased(int par1, int par2, int par3) {
        super.mouseReleased(par1, par2, par3);
        for (GuiDropDown d : this.dropDowns) {
            d.mouseReleased(par1, par2, par3, this.height);
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        int k;
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_players", (Object[])new Object[0]) + ":", this.width / 2, this.height / 7 + 10, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_other_teams", (Object[])new Object[0]) + ":", this.width / 2, this.height / 7 + 35, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_mobs", (Object[])new Object[0]) + ":", this.width / 2, this.height / 7 + 60, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_hostile", (Object[])new Object[0]) + ":", this.width / 2, this.height / 7 + 85, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_items", (Object[])new Object[0]) + ":", this.width / 2, this.height / 7 + 110, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"gui.xaero_other", (Object[])new Object[0]) + ":", this.width / 2, this.height / 7 + 135, 0xFFFFFF);
        if (this.dropped) {
            super.drawScreen(0, 0, par3);
        } else {
            super.drawScreen(par1, par2, par3);
        }
        this.dropped = false;
        for (k = 0; k < this.dropDowns.size(); ++k) {
            if (this.dropDowns.get(k).isClosed()) {
                this.dropDowns.get(k).drawButton(par1, par2, this.height);
                continue;
            }
            this.dropped = true;
        }
        for (k = 0; k < this.dropDowns.size(); ++k) {
            if (this.dropDowns.get(k).isClosed()) continue;
            this.dropDowns.get(k).drawButton(par1, par2, this.height);
        }
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getEventDWheel() / 120;
        if (wheel != 0) {
            ScaledResolution var3 = new ScaledResolution(this.mc);
            int mouseXScaled = Mouse.getX() / var3.getScaleFactor();
            int mouseYScaled = var3.getScaledHeight() - 1 - Mouse.getY() / var3.getScaleFactor();
            for (GuiDropDown d : this.dropDowns) {
                if (d.isClosed() || !d.onDropDown(mouseXScaled, mouseYScaled, this.height)) continue;
                d.mouseScrolled(wheel, mouseXScaled, mouseYScaled, this.height);
                return;
            }
        }
    }

    @Override
    public void onSelected(GuiDropDown menu, int selected) {
        if (menu == this.mobsColorDD) {
            this.mobsColor = selected;
        } else if (menu == this.hostileColorDD) {
            this.hostileColor = selected;
        } else if (menu == this.itemsColorDD) {
            this.itemsColor = selected;
        } else if (menu == this.otherColorDD) {
            this.otherColor = selected;
        } else if (menu == this.playerOptionDD) {
            this.playerOption = selected;
        } else if (menu == this.teamOptionDD) {
            this.teamOption = selected;
        }
    }
}

