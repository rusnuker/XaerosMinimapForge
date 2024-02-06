/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import xaero.common.IXaeroMinimap;
import xaero.common.settings.ModSettings;
import xaero.patreon.Patreon4;

public class Internet {
    public static void checkModVersion(IXaeroMinimap modMain) {
        String s = modMain.getVersionsURL();
        s = s.replaceAll(" ", "%20");
        try {
            URL url = new URL(s);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(900);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                modMain.setMessage("\u00a7e\u00a7l" + line);
            }
            if ((line = reader.readLine()) != null) {
                modMain.setNewestUpdateID(Integer.parseInt(line));
                if (!ModSettings.updateNotification || modMain.getNewestUpdateID() == ModSettings.ignoreUpdate) {
                    modMain.setOutdated(false);
                    reader.close();
                    return;
                }
            }
            String[] current = modMain.getVersionID().split("_");
            while ((line = reader.readLine()) != null) {
                String[] args;
                if (line.equals(modMain.getVersionID())) {
                    modMain.setOutdated(false);
                    break;
                }
                if (Patreon4.patronPledge < 5 || !line.startsWith(current[0]) || current.length != 2 && !line.endsWith(current[2]) || (args = line.split("_")).length != current.length) continue;
                modMain.setLatestVersion(args[1]);
            }
            reader.close();
        }
        catch (Exception e) {
            modMain.setOutdated(false);
        }
    }
}

