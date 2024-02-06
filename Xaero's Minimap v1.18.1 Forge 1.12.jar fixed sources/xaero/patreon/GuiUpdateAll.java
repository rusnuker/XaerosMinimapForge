//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.resources.I18n
 */
package xaero.patreon;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import xaero.patreon.Patreon4;
import xaero.patreon.PatreonMod2;

public class GuiUpdateAll
extends GuiYesNo {
    public GuiUpdateAll() {
        super(null, "These mods are out-of-date: " + GuiUpdateAll.modListToNames(Patreon4.getOutdatedMods()), "Would you like to automatically update them?", 0);
    }

    private static String modListToNames(List<PatreonMod2> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(list.get((int)i).modName);
        }
        return builder.toString();
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 120, I18n.format((String)"Changelogs", (Object[])new Object[0])));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (Patreon4.patronPledge >= 5) {
                    for (GuiButton b : this.buttonList) {
                        b.enabled = false;
                    }
                    this.autoUpdate();
                }
                Minecraft.getMinecraft().shutdown();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(null);
                break;
            }
            case 201: {
                for (int i = 0; i < Patreon4.getOutdatedMods().size(); ++i) {
                    PatreonMod2 mod = Patreon4.getOutdatedMods().get(i);
                    try {
                        Desktop d = Desktop.getDesktop();
                        d.browse(new URI(mod.changelogLink));
                        continue;
                    }
                    catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public void autoUpdate() {
        try {
            int read;
            URL url = new URL("http://data.chocolateminecraft.com/jars/xaero_autoupdater_2.0.jar");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
            InputStream input = conn.getInputStream();
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File("./xaero_autoupdater.jar")));
            byte[] buffer = new byte[256];
            while ((read = input.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, read);
            }
            output.flush();
            input.close();
            output.close();
            StringBuilder s = new StringBuilder();
            s.append("java -jar ./xaero_autoupdater.jar 5 ").append(Patreon4.updateLocation);
            for (int i = 0; i < Patreon4.getOutdatedMods().size(); ++i) {
                PatreonMod2 m = Patreon4.getOutdatedMods().get(i);
                if (m.modJar == null) continue;
                s.append(" \"").append(m.modJar.getPath()).append("\" \"").append(m.latestVersionLayout).append("\" ").append(m.currentVersion.split("_")[1]).append(" ").append(m.latestVersion).append(" ").append(m.currentVersion.split("_")[0]);
            }
            System.out.println(s.toString());
            Runtime.getRuntime().exec(s.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

