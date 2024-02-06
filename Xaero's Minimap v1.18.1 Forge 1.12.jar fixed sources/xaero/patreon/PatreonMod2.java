/*
 * Decompiled with CFR 0.152.
 */
package xaero.patreon;

import java.io.File;

public class PatreonMod2 {
    public String fileLayoutID;
    public String latestVersionLayout;
    public String changelogLink;
    public String modName;
    public File modJar;
    public String currentVersion;
    public String latestVersion;

    public PatreonMod2(String fileLayoutID, String latestVersionLayout, String changelogLink, String modName) {
        this.fileLayoutID = fileLayoutID;
        this.latestVersionLayout = latestVersionLayout;
        this.changelogLink = changelogLink;
        this.modName = modName;
    }
}

