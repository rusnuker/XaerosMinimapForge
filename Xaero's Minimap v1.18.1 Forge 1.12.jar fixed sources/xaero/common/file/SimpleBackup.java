/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;

public class SimpleBackup {
    public static Path moveToBackup(Path directory) {
        Path backupFolder = directory.getParent().resolve("backup");
        while (Files.exists(backupFolder, new LinkOption[0])) {
            backupFolder = backupFolder.getParent().resolve(backupFolder.getFileName().toString() + "-");
        }
        Path backupPath = backupFolder.resolve(directory.getFileName());
        try {
            Files.createDirectories(backupFolder, new FileAttribute[0]);
            Files.move(directory, backupPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to backup a directory! Can't continue.", e);
        }
        return backupPath;
    }
}

