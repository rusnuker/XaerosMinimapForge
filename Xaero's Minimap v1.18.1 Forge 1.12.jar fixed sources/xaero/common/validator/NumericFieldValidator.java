//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiTextField
 */
package xaero.common.validator;

import net.minecraft.client.gui.GuiTextField;

public class NumericFieldValidator {
    private StringBuilder stringBuilder = new StringBuilder();

    private boolean charIsValid(char c, int index) {
        return c >= '0' && c <= '9' || c == '-' && index == 0;
    }

    public void validate(GuiTextField field) {
        String text = field.getText();
        char[] charArray = text.toCharArray();
        this.stringBuilder.delete(0, this.stringBuilder.length());
        boolean validated = true;
        for (int i = 0; i < charArray.length; ++i) {
            if (!this.charIsValid(charArray[i], i)) {
                validated = false;
                continue;
            }
            this.stringBuilder.append(charArray[i]);
        }
        boolean validFormat = false;
        while (!validFormat) {
            try {
                if (this.stringBuilder.length() != 0 && (this.stringBuilder.length() != 1 || this.stringBuilder.charAt(0) != '-')) {
                    Integer.parseInt(this.stringBuilder.toString());
                }
                validFormat = true;
            }
            catch (NumberFormatException e) {
                this.stringBuilder.deleteCharAt(this.stringBuilder.length() - 1);
                validated = false;
            }
        }
        if (!validated) {
            field.setText(this.stringBuilder.toString());
        }
    }
}

