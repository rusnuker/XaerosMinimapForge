//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.world.World
 */
package xaero.common.minimap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.World;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.settings.ModSettings;

public class MinimapRadar {
    public static final Color radarPlayers = new Color(255, 255, 255);
    public static final Color radarShadow = new Color(0, 0, 0);
    private IXaeroMinimap modMain;
    private ArrayList<Entity> players;
    private ArrayList<Entity> living;
    private ArrayList<Entity> hostile;
    private ArrayList<Entity> items;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> updatingPlayers;
    private ArrayList<Entity> updatingHostile;
    private ArrayList<Entity> updatingLiving;
    private ArrayList<Entity> updatingItems;
    private ArrayList<Entity> updatingEntities;

    public MinimapRadar(IXaeroMinimap modMain) {
        this.modMain = modMain;
        this.players = new ArrayList();
        this.living = new ArrayList();
        this.hostile = new ArrayList();
        this.items = new ArrayList();
        this.entities = new ArrayList();
        this.updatingPlayers = new ArrayList();
        this.updatingHostile = new ArrayList();
        this.updatingLiving = new ArrayList();
        this.updatingItems = new ArrayList();
        this.updatingEntities = new ArrayList();
    }

    public void updateRadar(World world, EntityPlayer p) {
        this.updatingPlayers.clear();
        this.updatingHostile.clear();
        this.updatingLiving.clear();
        this.updatingItems.clear();
        this.updatingEntities.clear();
        for (int i = 0; i < world.loadedEntityList.size(); ++i) {
            try {
                Entity e = (Entity)world.loadedEntityList.get(i);
                int type = 0;
                if (e instanceof EntityPlayer) {
                    if (e != p && (!this.modMain.getSettings().getShowPlayers() || !this.modMain.getSettings().getShowOtherTeam() && p.getTeam() != ((EntityLivingBase)e).getTeam())) continue;
                    type = 1;
                } else if (e.getEntityData().hasKey("hostileMinimap") ? e.getEntityData().getBoolean("hostileMinimap") : e instanceof EntityMob || e instanceof IMob) {
                    if (!this.modMain.getSettings().getShowHostile()) continue;
                    type = 2;
                } else if (e instanceof EntityLiving) {
                    if (!this.modMain.getSettings().getShowMobs()) continue;
                    type = 3;
                } else if (e instanceof EntityItem) {
                    if (!this.modMain.getSettings().getShowItems()) continue;
                    type = 4;
                } else if (!this.modMain.getSettings().getShowOther()) continue;
                double offx = e.posX - p.posX;
                double offy = e.posZ - p.posZ;
                double offh = p.posY - e.posY;
                double offheight2 = offh * offh;
                double offx2 = offx * offx;
                double offy2 = offy * offy;
                double maxDistance = 31250.0 / (MinimapProcessor.instance.getMinimapZoom() * MinimapProcessor.instance.getMinimapZoom());
                if (offx2 > maxDistance || offy2 > maxDistance || offheight2 > (double)(this.modMain.getSettings().heightLimit * this.modMain.getSettings().heightLimit)) continue;
                ArrayList<Entity> typeList = this.updatingEntities;
                switch (type) {
                    case 1: {
                        typeList = this.updatingPlayers;
                        break;
                    }
                    case 2: {
                        typeList = this.updatingHostile;
                        break;
                    }
                    case 3: {
                        typeList = this.updatingLiving;
                        break;
                    }
                    case 4: {
                        typeList = this.updatingItems;
                    }
                }
                typeList.add(e);
                if (this.modMain.getSettings().entityAmount == 0 || typeList.size() < 100 * this.modMain.getSettings().entityAmount) continue;
                break;
            }
            catch (Exception e) {
                // empty catch block
            }
        }
        ArrayList<Entity> backupPlayers = this.players;
        ArrayList<Entity> backupHostile = this.hostile;
        ArrayList<Entity> backupLiving = this.living;
        ArrayList<Entity> backupItems = this.items;
        ArrayList<Entity> backupEntities = this.entities;
        this.players = this.updatingPlayers;
        this.hostile = this.updatingHostile;
        this.living = this.updatingLiving;
        this.items = this.updatingItems;
        this.entities = this.updatingEntities;
        this.updatingPlayers = backupPlayers;
        this.updatingHostile = backupHostile;
        this.updatingLiving = backupLiving;
        this.updatingItems = backupItems;
        this.updatingEntities = backupEntities;
    }

    public double getEntityX(Entity e, float partial) {
        return e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)partial;
    }

    public double getEntityZ(Entity e, float partial) {
        return e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)partial;
    }

    public boolean shouldRenderEntity(Entity e) {
        return !e.isSneaking() && !e.isInvisible();
    }

    public int getPlayerTeamColour(EntityPlayer p) {
        int teamColour = -1;
        if (p.getTeam() != null && ((ScorePlayerTeam)p.getTeam()).getPrefix() != null && ((ScorePlayerTeam)p.getTeam()).getPrefix().length() >= 2) {
            String prefix = ((ScorePlayerTeam)p.getTeam()).getPrefix();
            try {
                teamColour = Minecraft.getMinecraft().fontRenderer.getColorCode(prefix.charAt(prefix.length() - 1));
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                // empty catch block
            }
        }
        return teamColour;
    }

    public int getEntityColour(EntityPlayer p, Entity e, double offh) {
        int color = ModSettings.COLORS[this.modMain.getSettings().otherColor];
        if (e instanceof EntityPlayer) {
            int teamColour = this.getPlayerTeamColour(p);
            int entityTeamColour = this.getPlayerTeamColour((EntityPlayer)e);
            color = this.modMain.getSettings().otherTeamColor != -1 && entityTeamColour != teamColour ? ModSettings.COLORS[this.modMain.getSettings().otherTeamColor] : (this.modMain.getSettings().playersColor != -1 ? ModSettings.COLORS[this.modMain.getSettings().playersColor] : (entityTeamColour != -1 ? entityTeamColour : radarPlayers.hashCode()));
        } else if (e instanceof EntityMob || e instanceof IMob) {
            color = ModSettings.COLORS[this.modMain.getSettings().hostileColor];
        } else if (e instanceof EntityLiving) {
            color = ModSettings.COLORS[this.modMain.getSettings().mobsColor];
        } else if (e instanceof EntityItem) {
            color = ModSettings.COLORS[this.modMain.getSettings().itemsColor];
        }
        int l = color >> 16 & 0xFF;
        int i1 = color >> 8 & 0xFF;
        int j1 = color & 0xFF;
        double brightness = this.getEntityBrightness(offh);
        if (brightness < 1.0) {
            if ((l = (int)((double)l * brightness)) > 255) {
                l = 255;
            }
            if ((i1 = (int)((double)i1 * brightness)) > 255) {
                i1 = 255;
            }
            if ((j1 = (int)((double)j1 * brightness)) > 255) {
                j1 = 255;
            }
            color = 0xFF000000 | l << 16 | i1 << 8 | j1;
        }
        return color;
    }

    public double getEntityBrightness(double offh) {
        double level = (double)this.modMain.getSettings().heightLimit - offh;
        if (level < 0.0) {
            level = 0.0;
        }
        double brightness = 1.0;
        if (level <= (double)(this.modMain.getSettings().heightLimit / 2) && this.modMain.getSettings().showEntityHeight) {
            brightness = level / (double)this.modMain.getSettings().heightLimit;
        }
        return brightness;
    }

    public Iterator<Entity> getEntitiesIterator() {
        return this.entities.iterator();
    }

    public Iterator<Entity> getItemsIterator() {
        return this.items.iterator();
    }

    public Iterator<Entity> getLivingIterator() {
        return this.living.iterator();
    }

    public Iterator<Entity> getHostileIterator() {
        return this.hostile.iterator();
    }

    public Iterator<Entity> getPlayersIterator() {
        return this.players.iterator();
    }
}

