//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderPlayerEvent$Post
 */
package xaero.patreon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderPlayerEvent;
import xaero.patreon.PatreonMod2;

public class Patreon4 {
    public static HashMap<Integer, ArrayList<String>> patrons = new HashMap();
    public static boolean loaded = false;
    public static boolean showCapes = true;
    public static int patronPledge = -1;
    public static String updateLocation;
    public static HashMap<String, PatreonMod2> mods;
    private static ArrayList<PatreonMod2> outdatedMods;
    public static File optionsFile;
    public static String rendersCapes;
    private static ResourceLocation cape1;
    private static ResourceLocation cape2;
    private static ResourceLocation cape3;
    private static ResourceLocation cape4;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void checkPatreon() {
        HashMap<Integer, ArrayList<String>> hashMap = patrons;
        synchronized (hashMap) {
            if (loaded) {
                return;
            }
            Patreon4.loadSettings();
            String s = "http://data.chocolateminecraft.com/Versions/Patreon.txt";
            s = s.replaceAll(" ", "%20");
            try {
                String[] args;
                String line;
                URL url = new URL(s);
                URLConnection conn = url.openConnection();
                conn.setConnectTimeout(900);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                int pledge = -1;
                while ((line = reader.readLine()) != null && !line.equals("LAYOUTS")) {
                    if (line.startsWith("PATREON")) {
                        pledge = Integer.parseInt(line.substring(7));
                        patrons.put(pledge, new ArrayList());
                        continue;
                    }
                    if (pledge == -1) continue;
                    args = line.split("\\t");
                    patrons.get(pledge).add(args[0]);
                    if (!args[0].equalsIgnoreCase(Minecraft.getMinecraft().getSession().getProfile().getName())) continue;
                    patronPledge = pledge;
                }
                if (pledge >= 5) {
                    updateLocation = reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        args = line.split("\\t");
                        mods.put(args[0], new PatreonMod2(args[0], args[1], args[2], args[3]));
                    }
                }
                reader.close();
                loaded = true;
            }
            catch (Exception e) {
                patrons.clear();
                mods.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addOutdatedMod(PatreonMod2 mod) {
        ArrayList<PatreonMod2> arrayList = Patreon4.getOutdatedMods();
        synchronized (arrayList) {
            Patreon4.getOutdatedMods().add(mod);
        }
    }

    public static int getPatronPledge(String name) {
        Integer[] keys = patrons.keySet().toArray(new Integer[0]);
        for (int i = 0; i < keys.length; ++i) {
            if (!patrons.get(keys[i]).contains(name)) continue;
            return keys[i];
        }
        return -1;
    }

    public static void saveSettings() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(optionsFile));
            writer.println("showCapes:" + showCapes);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            String line;
            if (!optionsFile.exists()) {
                Patreon4.saveSettings();
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(optionsFile));
            while ((line = reader.readLine()) != null) {
                String[] args = line.split(":");
                if (!args[0].equalsIgnoreCase("showCapes")) continue;
                showCapes = args[1].equals("true");
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderCape(String modID, RenderPlayerEvent.Post event) {
        if (!(event.getEntityPlayer() instanceof AbstractClientPlayer) || !showCapes || !modID.equals(rendersCapes) || patrons.size() < 3 || event.getEntityPlayer().isDead || ((AbstractClientPlayer)event.getEntityPlayer()).getLocationCape() != null && event.getEntityPlayer().isWearing(EnumPlayerModelParts.CAPE) || ((AbstractClientPlayer)event.getEntityPlayer()).getLocationCape() == null && !event.getEntityPlayer().isWearing(EnumPlayerModelParts.CAPE)) {
            return;
        }
        ResourceLocation cape = null;
        int pledge = Patreon4.getPatronPledge(event.getEntityPlayer().getName());
        if (pledge == -1 || event.getEntityPlayer().isPlayerSleeping()) {
            return;
        }
        cape = pledge == 2 ? cape1 : (pledge == 5 ? cape2 : (pledge == 50 ? cape4 : cape3));
        if (cape == null) {
            return;
        }
        ItemStack itemstack = event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (itemstack == null || itemstack.getItem() != Items.ELYTRA) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)event.getX(), (double)event.getY(), (double)event.getZ());
            GlStateManager.translate((double)0.0, (double)((double)event.getEntityPlayer().eyeHeight - 0.25), (double)0.0);
            GlStateManager.rotate((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)(event.getEntityPlayer().prevRenderYawOffset + (event.getEntityPlayer().renderYawOffset - event.getEntityPlayer().prevRenderYawOffset) * event.getPartialRenderTick()), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            event.getRenderer().bindTexture(cape);
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.125f);
            double d0 = event.getEntityPlayer().prevChasingPosX + (event.getEntityPlayer().chasingPosX - event.getEntityPlayer().prevChasingPosX) * (double)event.getPartialRenderTick() - (event.getEntityPlayer().prevPosX + (event.getEntityPlayer().posX - event.getEntityPlayer().prevPosX) * (double)event.getPartialRenderTick());
            double d1 = event.getEntityPlayer().prevChasingPosY + (event.getEntityPlayer().chasingPosY - event.getEntityPlayer().prevChasingPosY) * (double)event.getPartialRenderTick() - (event.getEntityPlayer().prevPosY + (event.getEntityPlayer().posY - event.getEntityPlayer().prevPosY) * (double)event.getPartialRenderTick());
            double d2 = event.getEntityPlayer().prevChasingPosZ + (event.getEntityPlayer().chasingPosZ - event.getEntityPlayer().prevChasingPosZ) * (double)event.getPartialRenderTick() - (event.getEntityPlayer().prevPosZ + (event.getEntityPlayer().posZ - event.getEntityPlayer().prevPosZ) * (double)event.getPartialRenderTick());
            float f = event.getEntityPlayer().prevRenderYawOffset + (event.getEntityPlayer().renderYawOffset - event.getEntityPlayer().prevRenderYawOffset) * event.getPartialRenderTick();
            double d3 = MathHelper.sin((float)(f * ((float)Math.PI / 180)));
            double d4 = -MathHelper.cos((float)(f * ((float)Math.PI / 180)));
            float f1 = (float)d1 * 10.0f;
            f1 = MathHelper.clamp((float)f1, (float)-6.0f, (float)32.0f);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0f;
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0f;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            float f4 = event.getEntityPlayer().prevCameraYaw + (event.getEntityPlayer().cameraYaw - event.getEntityPlayer().prevCameraYaw) * event.getPartialRenderTick();
            f1 += MathHelper.sin((float)((event.getEntityPlayer().prevDistanceWalkedModified + (event.getEntityPlayer().distanceWalkedModified - event.getEntityPlayer().prevDistanceWalkedModified) * event.getPartialRenderTick()) * 6.0f)) * 32.0f * f4;
            if (event.getEntityPlayer().isSneaking()) {
                f1 += 25.0f;
            }
            GlStateManager.rotate((float)(6.0f + Math.min(90.0f, f2 / 2.0f) + f1), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)(f3 / 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)(-f3 / 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            event.getRenderer().getMainModel().renderCape(0.0625f);
            GlStateManager.popMatrix();
        }
    }

    public static ArrayList<PatreonMod2> getOutdatedMods() {
        return outdatedMods;
    }

    static {
        mods = new HashMap();
        outdatedMods = new ArrayList();
        optionsFile = new File("./config/xaeropatreon.txt");
        rendersCapes = null;
        cape1 = new ResourceLocation("xaeropatreon", "capes/cape1.png");
        cape2 = new ResourceLocation("xaeropatreon", "capes/cape2.png");
        cape3 = new ResourceLocation("xaeropatreon", "capes/cape3.png");
        cape4 = new ResourceLocation("xaeropatreon", "capes/cape4.png");
    }
}

