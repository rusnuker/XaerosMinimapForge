//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.math.MathHelper
 */
package xaero.common.settings;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;

public enum ModOptions {
    DEFAULT("Default", false, true),
    DOTS("gui.xaero_entity_radar", false, true),
    MINIMAP("gui.xaero_minimap", false, true),
    CAVE_MAPS("gui.xaero_cave_maps", false, true),
    CAVE_ZOOM("gui.xaero_cave_zoom", false, true),
    DISPLAY_OTHER_TEAM("gui.xaero_display_teams", false, true),
    WAYPOINTS("gui.xaero_display_waypoints", false, true),
    INGAME_WAYPOINTS("gui.xaero_ingame_waypoints", false, true),
    PLAYERS("gui.xaero_display_players", false, true),
    HOSTILE("gui.xaero_display_hostile", false, true),
    MOBS("gui.xaero_display_mobs", false, true),
    ITEMS("gui.xaero_display_items", false, true),
    ENTITIES("gui.xaero_display_other", false, true),
    ZOOM("gui.xaero_zoom", false, true),
    SIZE("gui.xaero_minimap_size", false, true),
    EAMOUNT("gui.xaero_entity_amount", false, true),
    COORDS("gui.xaero_display_coords", false, true),
    NORTH("gui.xaero_lock_north", false, true),
    DEATHPOINTS("gui.xaero_deathpoints", false, true),
    OLD_DEATHPOINTS("gui.xaero_old_deathpoints", false, true),
    CHUNK_GRID("gui.xaero_chunkgrid", false, true),
    SLIME_CHUNKS("gui.xaero_slime_chunks", false, true),
    SAFE_MAP("gui.xaero_safe_mode", false, true),
    OPACITY("gui.xaero_opacity", true, false, 30.0f, 100.0f, 1.0f),
    WAYPOINTS_SCALE("gui.xaero_waypoints_scale", true, false, 1.0f, 5.0f, 0.5f),
    AA("gui.xaero_antialiasing", false, true),
    DISTANCE("gui.xaero_show_distance", false, true),
    COLOURS("gui.xaero_block_colours", false, true),
    LIGHT("gui.xaero_lighting", false, true),
    REDSTONE("gui.xaero_display_redstone", false, true),
    DOTS_SCALE("gui.xaero_dots_scale", true, false, 1.0f, 2.0f, 0.5f),
    COMPASS("gui.xaero_compass_over_wp", false, true),
    BIOME("gui.xaero_current_biome", false, true),
    ENTITY_HEIGHT("gui.xaero_entity_depth", false, true),
    FLOWERS("gui.xaero_show_flowers", false, true),
    KEEP_WP_NAMES("gui.xaero_waypoint_names", false, true),
    WAYPOINTS_DISTANCE("gui.xaero_waypoints_distance", true, false, 0.0f, 50000.0f, 500.0f),
    WAYPOINTS_DISTANCE_MIN("gui.xaero_waypoints_distance_min", true, false, 0.0f, 100.0f, 5.0f),
    WAYPOINTS_TP("gui.xaero_teleport_command", false, true),
    ARROW_SCALE("gui.xaero_arrow_scale", true, false, 1.0f, 2.0f, 0.1f),
    ARROW_COLOUR("gui.xaero_arrow_colour", false, true),
    SMOOTH_DOTS("gui.xaero_smooth_dots", false, true),
    PLAYER_HEADS("gui.xaero_lock_player_heads", false, true),
    PLAYER_NAMES("gui.xaero_player_names", false, true),
    HEIGHT_LIMIT("gui.xaero_height_limit", true, false, 10.0f, 40.0f, 5.0f),
    WORLD_MAP("gui.xaero_use_world_map", false, true),
    CAPES("gui.xaero_patron_capes", false, true),
    TERRAIN_DEPTH("gui.xaero_terrain_depth", false, true),
    TERRAIN_SLOPES("gui.xaero_terrain_slopes", false, true),
    ALWAYS_ARROW("gui.xaero_arrow_instead_of_dot", false, true),
    BLOCK_TRANSPARENCY("gui.xaero_block_transparency", false, true),
    WAYPOINT_OPACITY_INGAME("gui.xaero_waypoint_opacity_ingame", true, false, 10.0f, 100.0f, 1.0f),
    WAYPOINT_OPACITY_MAP("gui.xaero_waypoint_opacity_map", true, false, 10.0f, 100.0f, 1.0f),
    HIDE_WORLD_NAMES("gui.xaero_hide_world_names", false, true),
    OPEN_SLIME_SETTINGS("gui.xaero_open_slime", false, true),
    ALWAYS_SHOW_DISTANCE("gui.xaero_always_show_distance", false, true),
    SHOW_LIGHT_LEVEL("gui.xaero_show_light_level", false, true),
    RENDER_LAYER("gui.xaero_render_layer", false, true),
    SHOW_EFFECTS("gui.xaero_potion_status", false, true),
    SHOW_ARMOR("gui.xaero_armour_status", false, true),
    BETTER_SPRINT("gui.xaero_sprint", false, true),
    KEEP_SNEAK("gui.xaero_sneak", false, true),
    ENCHANT_COLOR("gui.xaero_enchants_color", false, true),
    DURABILITY("gui.xaero_durability", false, true),
    NOTIFICATIONS("gui.xaero_notifications", false, true),
    NOTIFICATIONS_HUNGER("gui.xaero_hunger_setting", false, true),
    NOTIFICATIONS_HUNGER_LOW("gui.xaero_hunger_low", false, true),
    NOTIFICATIONS_HP("gui.xaero_hp_setting", false, true),
    NOTIFICATIONS_HP_LOW("gui.xaero_hp_low", false, true),
    NOTIFICATIONS_TNT("gui.xaero_explosion_setting", false, true),
    NOTIFICATIONS_ARROW("gui.xaero_being_shot_setting", false, true),
    NOTIFICATIONS_AIR("gui.xaero_air_setting", false, true),
    NOTIFICATIONS_AIR_LOW("gui.xaero_air_low", false, true),
    XP("gui.xaero_xp_setting", false, true),
    CUSTOMIZATION("gui.xaero_custom_settings", false, true),
    EDIT("gui.xaero_edit_mode", false, true),
    RESET("gui.xaero_reset_defaults", false, true),
    NUMBERS("gui.xaero_quick_use", false, true),
    SHOW_ENCHANTS("gui.xaero_show_enchants", false, true),
    ARCHERY("gui.xaero_archery_status", false, true),
    POTION_NAMES("gui.xaero_potion_names", false, true),
    ENTITY_INFO("gui.xaero_entity_info", false, true),
    ENTITY_INFO_STAY("gui.xaero_entity_info_stay", false, true),
    ENTITY_INFO_DISTANCE("gui.xaero_entity_info_distance", true, false, 1.0f, 40.0f, 1.0f),
    ENTITY_INFO_MAX_HEARTS("gui.xaero_entity_info_max_hearts", true, false, 10.0f, 1000.0f, 10.0f),
    ENTITY_INFO_NUMBERS("gui.xaero_entity_info_numbers", false, true),
    ENTITY_INFO_EFFECTS("gui.xaero_entity_info_potion_effects", false, true),
    ENTITY_INFO_EFFECTS_SCALE("gui.xaero_entity_info_potion_effects_scale", true, false, 1.0f, 4.0f, 1.0f),
    SHOW_FULL_AMOUNT("gui.xaero_show_full_amount", false, true),
    ENTITY_INFO_ARMOUR_NUMBERS("gui.xaero_entity_info_armour_numbers", false, true),
    ENTITY_INFO_ARMOUR("gui.xaero_entity_info_armour", false, true),
    SHOW_ENTITY_MODEL("gui.xaero_show_entity_model", false, true),
    ITEM_TOOLTIP("gui.xaero_item_tooltip", false, true),
    ITEM_TOOLTIP_MIN_LINES("gui.xaero_item_tooltip_min_lines", true, false, 0.0f, 10.0f, 1.0f),
    ITEM_TOOLTIP_TIME("gui.xaero_item_tooltip_time", true, false, 1.0f, 40.0f, 1.0f);

    private final boolean enumFloat;
    private final boolean enumBoolean;
    private final String enumString;
    private float valueMin;
    private float valueMax;
    private float valueStep;

    public static ModOptions getModOptions(int par0) {
        for (ModOptions enumoptions : ModOptions.values()) {
            if (enumoptions.returnEnumOrdinal() != par0) continue;
            return enumoptions;
        }
        return null;
    }

    private ModOptions(String par3Str, boolean par4, boolean par5) {
        this.enumString = par3Str;
        this.enumFloat = par4;
        this.enumBoolean = par5;
    }

    private ModOptions(String translation, boolean isFloat, boolean isBoolean, float valMin, float valMax, float valStep) {
        this.enumString = translation;
        this.enumFloat = isFloat;
        this.enumBoolean = isBoolean;
        this.valueMin = valMin;
        this.valueMax = valMax;
        this.valueStep = valStep;
    }

    public boolean getEnumFloat() {
        return this.enumFloat;
    }

    public boolean getEnumBoolean() {
        return this.enumBoolean;
    }

    public int returnEnumOrdinal() {
        return this.ordinal();
    }

    public float getValueMax() {
        return this.valueMax;
    }

    public void setValueMax(float value) {
        this.valueMax = value;
    }

    public float normalizeValue(float value) {
        return MathHelper.clamp((float)((this.snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin)), (float)0.0f, (float)1.0f);
    }

    public float denormalizeValue(float value) {
        return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp((float)value, (float)0.0f, (float)1.0f));
    }

    public float snapToStepClamp(float value) {
        value = this.snapToStep(value);
        return MathHelper.clamp((float)value, (float)this.valueMin, (float)this.valueMax);
    }

    protected float snapToStep(float value) {
        if (this.valueStep > 0.0f) {
            value = this.valueStep * (float)Math.round(value / this.valueStep);
        }
        return value;
    }

    public String getEnumString() {
        return I18n.format((String)this.enumString, (Object[])new Object[0]);
    }
}

