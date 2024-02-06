//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package xaero.common.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xaero.common.IXaeroMinimap;
import xaero.common.gui.GuiDropDown;
import xaero.common.gui.GuiWaypointContainers;
import xaero.common.gui.GuiWaypointSets;
import xaero.common.gui.GuiWaypointWorlds;
import xaero.common.gui.IDropDownCallback;
import xaero.common.gui.MySmallButton;
import xaero.common.gui.MySuperTinyButton;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.misc.OptimizedMath;
import xaero.common.settings.ModOptions;
import xaero.common.settings.ModSettings;
import xaero.common.validator.NumericFieldValidator;

public class GuiAddWaypoint
extends GuiScreen
implements IDropDownCallback {
    private IXaeroMinimap modMain;
    private WaypointsManager waypointsManager;
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private GuiTextField nameTextField;
    private String nameText;
    private GuiTextField xTextField;
    private GuiTextField yTextField;
    private GuiTextField zTextField;
    private GuiTextField yawTextField;
    private GuiTextField charTextField;
    private String initial;
    private String yaw;
    private ArrayList<GuiDropDown> dropDowns;
    private int defaultContainer;
    private WaypointWorld defaultWorld;
    private GuiWaypointContainers containers;
    private GuiWaypointWorlds worlds;
    private GuiWaypointSets sets;
    private GuiDropDown containersDD;
    private GuiDropDown worldsDD;
    private GuiDropDown setsDD;
    private GuiDropDown colorDD;
    private String fromSet;
    private int color;
    private Waypoint point;
    private boolean dropped;
    private boolean waypointDisabled;
    private MySuperTinyButton disableButton;
    private NumericFieldValidator fieldValidator;

    public GuiAddWaypoint(IXaeroMinimap modMain, GuiScreen par1GuiScreen, Waypoint point, String defaultParentContainer, WaypointWorld defaultWorld) {
        this.parentGuiScreen = par1GuiScreen;
        this.point = point;
        this.modMain = modMain;
        this.waypointsManager = modMain.getWaypointsManager();
        this.fromSet = defaultWorld.getCurrent();
        this.defaultWorld = defaultWorld;
        this.containers = new GuiWaypointContainers(modMain, this.waypointsManager, defaultParentContainer);
        this.defaultContainer = this.containers.current;
        this.worlds = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(defaultParentContainer), this.waypointsManager, defaultWorld.getFullId());
        this.sets = new GuiWaypointSets(false, defaultWorld);
        this.nameText = "";
        this.dropDowns = new ArrayList();
        this.initial = "";
        this.yaw = "";
        this.fieldValidator = modMain.getFieldValidators().getNumericFieldValidator();
        this.color = point == null ? (int)(Math.random() * (double)(ModSettings.ENCHANT_COLORS.length - 1)) : point.getColor();
    }

    public String[] createColorOptions() {
        String[] options = new String[ModSettings.ENCHANT_COLOR_NAMES.length];
        for (int i = 0; i < options.length; ++i) {
            options[i] = i == 0 ? I18n.format((String)ModSettings.ENCHANT_COLOR_NAMES[i], (Object[])new Object[0]) : "\u00a7" + ModSettings.ENCHANT_COLORS[i] + I18n.format((String)ModSettings.ENCHANT_COLOR_NAMES[i], (Object[])new Object[0]);
        }
        return options;
    }

    public int[] createValues() {
        int[] values = new int[ModSettings.ENCHANT_COLOR_NAMES.length];
        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
        }
        return values;
    }

    public void initGui() {
        this.modMain.getInterfaces().setSelectedId(-1);
        this.modMain.getInterfaces().setDraggingId(-1);
        this.buttonList.clear();
        this.buttonList.add(new MySmallButton(200, this.width / 2 - 155, this.height / 6 + 168, I18n.format((String)"gui.xaero_confirm", (Object[])new Object[0])));
        this.buttonList.add(new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, I18n.format((String)"gui.xaero_cancel", (Object[])new Object[0])));
        this.buttonList.add(new MySuperTinyButton(203, this.width / 2 - 165, 121, I18n.format((String)"* 8", (Object[])new Object[0])));
        this.buttonList.add(new MySuperTinyButton(204, this.width / 2 - 165, 147, I18n.format((String)"/ 8", (Object[])new Object[0])));
        this.nameTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 104, 200, 20);
        this.xTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 109, 134, 50, 20);
        this.yTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 53, 134, 50, 20);
        this.zTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 + 3, 134, 50, 20);
        this.yawTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 + 59, 134, 50, 20);
        this.charTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 25, 164, 50, 20);
        if (this.point == null) {
            this.screenTitle = I18n.format((String)"gui.xaero_new_waypoint", (Object[])new Object[0]);
            this.nameTextField.setText(this.nameText);
            boolean divideBy8 = this.waypointsManager.divideBy8(this.worlds.getCurrentKeys()[0]);
            this.xTextField.setText("" + OptimizedMath.myFloor(this.mc.player.posX) * (divideBy8 ? 8 : 1));
            this.yTextField.setText("" + OptimizedMath.myFloor(this.mc.player.posY));
            this.zTextField.setText("" + OptimizedMath.myFloor(this.mc.player.posZ) * (divideBy8 ? 8 : 1));
            this.yawTextField.setText("\u00a78" + I18n.format((String)"gui.xaero_yaw", (Object[])new Object[0]));
            this.charTextField.setText("\u00a78" + I18n.format((String)"gui.xaero_initial", (Object[])new Object[0]));
            this.color = (int)(Math.random() * (double)(ModSettings.ENCHANT_COLORS.length - 1));
        } else {
            this.screenTitle = I18n.format((String)"gui.xaero_edit_waypoint", (Object[])new Object[0]);
            this.nameTextField.setText(this.point.getLocalizedName());
            this.xTextField.setText("" + this.point.getX());
            this.yTextField.setText("" + this.point.getY());
            this.zTextField.setText("" + this.point.getZ());
            this.initial = this.point.getSymbol();
            if (this.point.isRotation()) {
                this.yaw = "" + this.point.getYaw();
            }
            this.yawTextField.setText(this.yaw);
            this.charTextField.setText(this.initial);
            this.color = this.point.getColor();
            this.waypointDisabled = this.point.isDisabled();
        }
        String[] enabledisable = I18n.format((String)"gui.xaero_disable_enable", (Object[])new Object[0]).split("/");
        this.disableButton = new MySuperTinyButton(205, this.width / 2 + 31, 164, enabledisable[this.waypointDisabled ? 1 : 0]);
        this.buttonList.add(this.disableButton);
        this.dropDowns.clear();
        this.colorDD = new GuiDropDown(this.createColorOptions(), this.width / 2 - 60, 82, 120, this.color, this);
        this.dropDowns.add(this.colorDD);
        this.setsDD = new GuiDropDown(this.sets.getOptions(), this.width / 2 - 101, 60, 201, this.sets.getCurrentSet(), this);
        this.dropDowns.add(this.setsDD);
        this.containersDD = new GuiDropDown(this.containers.options, this.width / 2 - 203, 38, 200, this.containers.current, this);
        this.dropDowns.add(this.containersDD);
        this.worldsDD = new GuiDropDown(this.worlds.options, this.width / 2 + 2, 38, 200, this.worlds.current, this);
        this.dropDowns.add(this.worldsDD);
        this.nameTextField.setFocused(true);
        Keyboard.enableRepeatEvents((boolean)true);
        this.updateConfirmButton();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if (this.nameTextField.isFocused()) {
            if (par2 == 15) {
                this.nameTextField.setFocused(false);
                this.xTextField.setFocused(true);
            }
            this.nameTextField.textboxKeyTyped(par1, par2);
            if (this.initial.length() == 0 && this.nameTextField.getText().length() > 0) {
                this.initial = this.nameTextField.getText().substring(0, 1);
            }
        } else if (this.xTextField.isFocused()) {
            if (par2 == 15) {
                this.xTextField.setFocused(false);
                this.yTextField.setFocused(true);
            }
            this.xTextField.textboxKeyTyped(par1, par2);
        } else if (this.yTextField.isFocused()) {
            if (par2 == 15) {
                this.yTextField.setFocused(false);
                this.zTextField.setFocused(true);
            }
            this.yTextField.textboxKeyTyped(par1, par2);
        } else if (this.zTextField.isFocused()) {
            if (par2 == 15) {
                this.zTextField.setFocused(false);
                this.yawTextField.setFocused(true);
            }
            this.zTextField.textboxKeyTyped(par1, par2);
        } else if (this.yawTextField.isFocused()) {
            if (par2 == 15) {
                this.yawTextField.setFocused(false);
                this.charTextField.setFocused(true);
            }
            this.yawTextField.setText(this.yaw);
            this.yawTextField.textboxKeyTyped(par1, par2);
            this.fieldValidator.validate(this.yawTextField);
            this.yaw = this.yawTextField.getText();
        } else if (this.charTextField.isFocused()) {
            if (par2 == 15) {
                this.charTextField.setFocused(false);
                this.nameTextField.setFocused(true);
            }
            this.charTextField.setText(this.initial);
            if (par2 != 57) {
                this.charTextField.textboxKeyTyped(par1, par2);
            }
            this.initial = this.charTextField.getText();
        }
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        this.checkFields();
        this.updateConfirmButton();
        super.keyTyped(par1, par2);
    }

    private void updateConfirmButton() {
        ((GuiButton)this.buttonList.get((int)0)).enabled = this.nameTextField.getText().length() > 0 && this.initial.length() > 0 && this.xTextField.getText().length() > 0 && this.yTextField.getText().length() > 0 && this.zTextField.getText().length() > 0;
    }

    protected void checkFields() {
        this.fieldValidator.validate(this.xTextField);
        this.fieldValidator.validate(this.yTextField);
        this.fieldValidator.validate(this.zTextField);
        this.initial = this.initial.toUpperCase();
        if (this.initial.length() > 1) {
            this.initial = this.initial.substring(0, 1);
            this.charTextField.setText(this.initial);
        }
        if (this.yaw.length() > 4) {
            this.yaw = this.yaw.substring(0, 4);
            this.yawTextField.setText(this.yaw);
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
        this.nameTextField.mouseClicked(par1, par2, par3);
        this.xTextField.mouseClicked(par1, par2, par3);
        this.yTextField.mouseClicked(par1, par2, par3);
        this.zTextField.mouseClicked(par1, par2, par3);
        this.yawTextField.mouseClicked(par1, par2, par3);
        this.charTextField.mouseClicked(par1, par2, par3);
    }

    protected void mouseReleased(int par1, int par2, int par3) {
        super.mouseReleased(par1, par2, par3);
        for (GuiDropDown d : this.dropDowns) {
            d.mouseReleased(par1, par2, par3, this.height);
        }
    }

    public void updateScreen() {
        if (this.mc.player == null) {
            this.mc.displayGuiScreen(null);
            return;
        }
        this.nameTextField.updateCursorCounter();
        this.xTextField.updateCursorCounter();
        this.yTextField.updateCursorCounter();
        this.zTextField.updateCursorCounter();
        this.yawTextField.updateCursorCounter();
        this.charTextField.updateCursorCounter();
        if (this.yawTextField.isFocused() || this.yaw.length() > 0) {
            this.yawTextField.setText(this.yaw);
        } else {
            this.yawTextField.setText("\u00a78" + I18n.format((String)"gui.xaero_yaw", (Object[])new Object[0]));
        }
        if (this.charTextField.isFocused() || this.initial.length() > 0) {
            this.charTextField.setText(this.initial);
        } else {
            this.charTextField.setText("\u00a78" + I18n.format((String)"gui.xaero_initial", (Object[])new Object[0]));
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.mc.gameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                try {
                    this.modMain.getSettings().setOptionValue(((MySmallButton)par1GuiButton).returnModOptions(), 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                par1GuiButton.displayString = this.modMain.getSettings().getKeyBinding(ModOptions.getModOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                int x = this.xTextField.getText().equals("-") ? 0 : Integer.parseInt(this.xTextField.getText());
                int y = this.yTextField.getText().equals("-") ? 0 : Integer.parseInt(this.yTextField.getText());
                int z = this.zTextField.getText().equals("-") ? 0 : Integer.parseInt(this.zTextField.getText());
                String name = this.nameTextField.getText();
                Waypoint created = new Waypoint(x, y, z, name, this.initial, this.color);
                if (this.waypointDisabled) {
                    created.setDisabled(true);
                }
                if (this.yaw.length() > 0 && !this.yaw.equals("-")) {
                    created.setRotation(true);
                    created.setYaw(Integer.parseInt(this.yawTextField.getText()));
                }
                WaypointWorld sourceWorld = this.defaultWorld;
                WaypointSet sourceSet = sourceWorld.getSets().get(this.fromSet);
                String[] destinationWorldKeys = this.worlds.getCurrentKeys();
                String destinationSetKey = this.sets.getCurrentSetKey();
                WaypointWorld destinationWorld = this.waypointsManager.getWorld(destinationWorldKeys[0], destinationWorldKeys[1]);
                WaypointSet destinationSet = destinationWorld.getSets().get(destinationSetKey);
                if (this.point != null && sourceSet == destinationSet && destinationSet.getList().contains(this.point)) {
                    destinationSet.getList().add(destinationSet.getList().indexOf(this.point), created);
                } else {
                    destinationSet.getList().add(created);
                }
                if (this.point != null) {
                    sourceSet.getList().remove(this.point);
                }
                try {
                    this.modMain.getSettings().saveWaypoints(sourceWorld);
                    if (destinationWorld != sourceWorld) {
                        this.modMain.getSettings().saveWaypoints(destinationWorld);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 201) {
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 202) {
                this.color = (this.color + 1) % (ModSettings.ENCHANT_COLORS.length - 1);
            }
            if (par1GuiButton.id == 203) {
                this.multiplyCoordinates(8.0);
            } else if (par1GuiButton.id == 204) {
                this.multiplyCoordinates(0.125);
            }
            if (par1GuiButton.id == 205) {
                this.waypointDisabled = !this.waypointDisabled;
                String[] enabledisable = I18n.format((String)"gui.xaero_disable_enable", (Object[])new Object[0]).split("/");
                String string = this.disableButton.displayString = this.waypointDisabled ? enabledisable[1] : enabledisable[0];
            }
            if (this.mc.gameSettings.guiScale != var2) {
                ScaledResolution var3 = new ScaledResolution(this.mc);
                int var4 = var3.getScaledWidth();
                int var5 = var3.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }
        }
    }

    public List getButtons() {
        return this.buttonList;
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        this.nameTextField.drawTextBox();
        this.xTextField.drawTextBox();
        this.yTextField.drawTextBox();
        this.zTextField.drawTextBox();
        this.yawTextField.drawTextBox();
        this.charTextField.drawTextBox();
        if (this.dropped) {
            super.drawScreen(0, 0, par3);
        } else {
            super.drawScreen(par1, par2, par3);
        }
        this.dropped = false;
        GuiDropDown openDropdown = null;
        for (int k = 0; k < this.dropDowns.size(); ++k) {
            GuiDropDown dropdown = this.dropDowns.get(k);
            if (!dropdown.isClosed()) {
                this.dropped = true;
                openDropdown = dropdown;
                continue;
            }
            dropdown.drawButton(par1, par2, this.height);
        }
        if (openDropdown != null) {
            openDropdown.drawButton(par1, par2, this.height);
        }
    }

    private void multiplyCoordinates(double factor) {
        this.multiplyCoordinateField(this.xTextField, factor);
        this.multiplyCoordinateField(this.zTextField, factor);
    }

    private void multiplyCoordinateField(GuiTextField field, double factor) {
        if (field.getText().isEmpty() || field.getText().equals("-")) {
            return;
        }
        int value = Integer.parseInt(field.getText());
        value = (int)Math.floor((double)value * factor);
        field.setText("" + value);
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
        if (menu == this.setsDD) {
            this.sets.setCurrentSet(selected);
            if (this.waypointsManager.getCurrentContainerAndWorldID().equals(this.worlds.getCurrentKey())) {
                this.waypointsManager.getCurrentWorld().setCurrent(this.sets.getCurrentSetKey());
                this.waypointsManager.updateWaypoints();
                try {
                    this.modMain.getSettings().saveWaypoints(this.waypointsManager.getCurrentWorld());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (menu == this.colorDD) {
            this.color = selected;
        } else if (menu == this.containersDD) {
            this.containers.current = selected;
            WaypointWorld currentWorld = this.containers.current != this.defaultContainer ? this.waypointsManager.getWorldContainer(this.containers.getCurrentKey()).getFirstWorld() : this.defaultWorld;
            this.sets = new GuiWaypointSets(false, currentWorld);
            this.worlds = new GuiWaypointWorlds(this.waypointsManager.getWorldContainer(this.containers.getCurrentKey()), this.waypointsManager, currentWorld.getFullId());
            this.setsDD = new GuiDropDown(this.sets.getOptions(), this.width / 2 - 101, 60, 201, this.sets.getCurrentSet(), this);
            this.dropDowns.set(1, this.setsDD);
            this.worldsDD = new GuiDropDown(this.worlds.options, this.width / 2 + 2, 38, 200, this.worlds.current, this);
            this.dropDowns.set(3, this.worldsDD);
        } else if (menu == this.worldsDD) {
            this.worlds.current = selected;
            String[] worldKeys = this.worlds.getCurrentKeys();
            this.sets = new GuiWaypointSets(false, this.waypointsManager.getWorld(worldKeys[0], worldKeys[1]));
            this.setsDD = new GuiDropDown(this.sets.getOptions(), this.width / 2 - 101, 60, 201, this.sets.getCurrentSet(), this);
            this.dropDowns.set(1, this.setsDD);
        }
    }
}

