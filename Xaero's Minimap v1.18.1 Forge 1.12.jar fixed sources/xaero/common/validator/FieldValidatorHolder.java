/*
 * Decompiled with CFR 0.152.
 */
package xaero.common.validator;

import xaero.common.validator.NumericFieldValidator;

public class FieldValidatorHolder {
    private NumericFieldValidator numericFieldValidator;

    public FieldValidatorHolder(NumericFieldValidator numericFieldValidator) {
        this.numericFieldValidator = numericFieldValidator;
    }

    public NumericFieldValidator getNumericFieldValidator() {
        return this.numericFieldValidator;
    }
}

