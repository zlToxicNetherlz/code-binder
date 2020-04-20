package com.variamos.moduino.binder.model.variables;

import com.variamos.moduino.binder.api.structure.StructureModifier;
import com.variamos.moduino.binder.model.library.SketchHeaderLiquidCrystalLibrary;
import com.variamos.moduino.binder.model.variables.data.StaticData;
import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchByteVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchLibraryVariable;
import lombok.ToString;

@ToString
public class SketchKeypadVariable extends SketchLibraryVariable<SketchHeaderLiquidCrystalLibrary> {

    private String[] columns = new String[4];
    private String[] rows = new String[4];

    private StructureModifier structureModifier;

    public SketchKeypadVariable(String label, boolean parameter, boolean constant, StructureModifier structureModifier) {
        super("Keypad", label, null, parameter, constant);

        this.structureModifier = structureModifier;

    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer codeBuffer) {

        final String methodSeparator = ", ";
        SketchByteVariable constColumns, constRows;

        if (structureModifier.getVariable(getLabel().toUpperCase() + "_KEYPAD_COLS") == null) {

            constColumns = new SketchByteVariable(getLabel().toUpperCase() + "_KEYPAD_COLS", 4, false, true);
            constRows = new SketchByteVariable(getLabel().toUpperCase() + "_KEYPAD_ROWS", 4, false, true);

            constColumns.appendCodeLiteral(codeBuffer);
            constRows.appendCodeLiteral(codeBuffer);

        } else {

            constColumns = structureModifier.getVariable(getLabel().toUpperCase() + "_KEYPAD_COLS");
            constRows = structureModifier.getVariable(getLabel().toUpperCase() + "_KEYPAD_ROWS");

        }

        String KEYPAD_6X6 = String.format(StaticData.KEYPAD_6X6, getLabel() + "_hexaKeys", constRows.getLabel(), constColumns.getLabel());

        String COLPINS = String.format(StaticData.COLPINS, getLabel() + "_colPins", constColumns.getLabel(), columns[0], columns[1], columns[2], columns[3]);
        String ROWPINS = String.format(StaticData.ROWPINS, getLabel() + "_rowPins", constRows.getLabel(), rows[0], rows[1], rows[2], rows[3]);

        codeBuffer.appendLine(KEYPAD_6X6);
        codeBuffer.appendLine(COLPINS);
        codeBuffer.appendLine(ROWPINS);

        codeBuffer.append(getKeyword() + " " + getLabel() + " = Keypad(", true);

        codeBuffer.append("makeKeymap(" + getLabel() + "_hexaKeys" + ")" + methodSeparator, false);
        codeBuffer.append(getLabel() + "_rowPins" + methodSeparator, false);
        codeBuffer.append(getLabel() + "_colPins" + methodSeparator, false);

        codeBuffer.append(constColumns.getLabel() + methodSeparator, false);
        codeBuffer.append(constRows.getLabel(), false);

        codeBuffer.append(");", false);
        codeBuffer.appendBreakline();
        return true;
    }

    public SketchInstruction operateRead(int columns, int rows) {
        return codeBuffer -> {
            codeBuffer.appendLine("String(" + getLabel() + ".getKey()" + ");");
            return true;
        };
    }

    public SketchInstruction operateReadAsResult() {
        return codeBuffer -> {
            codeBuffer.appendLine("return String(" + getLabel() + ".getKey());");
            return true;
        };
    }

    public void setColumns(String a, String b, String c, String d) {
        this.columns = new String[]{a, b, c, d};
    }

    public void setRows(String a, String b, String c, String d) {
        this.rows = new String[]{a, b, c, d};
    }

    @Override
    public String getParametizedForm() {
        return null;
    }

    @Override
    public String getLiteralValue() {
        return null;
    }

    public enum ModeType {

        AUTOMATIC,

        ;

    }

}
