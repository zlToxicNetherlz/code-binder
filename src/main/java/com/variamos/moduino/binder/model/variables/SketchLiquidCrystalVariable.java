package com.variamos.moduino.binder.model.variables;

import com.variamos.moduino.binder.model.library.SketchHeaderLiquidCrystalLibrary;
import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchLibraryVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchStringVariable;
import lombok.ToString;

@ToString
public class SketchLiquidCrystalVariable extends SketchLibraryVariable<SketchHeaderLiquidCrystalLibrary> {

    private SketchDefineDirective lcd_register_select;
    private SketchDefineDirective lcd_read_write;
    private SketchDefineDirective lcd_enable;

    private SketchDefineDirective lcd_db4;
    private SketchDefineDirective lcd_db5;
    private SketchDefineDirective lcd_db6;
    private SketchDefineDirective lcd_db7;

    public SketchLiquidCrystalVariable(String label, boolean parameter, boolean constant) {
        super("LiquidCrystal", label, null, parameter, constant);
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer codeBuffer) {

        final String methodSeparator = ", ";

        codeBuffer.append(getKeyword() + " " + getLabel() + "(", true);

        codeBuffer.append(lcd_read_write.getLabel() + methodSeparator, false);
        codeBuffer.append(lcd_register_select.getLabel() + methodSeparator, false);
        codeBuffer.append(lcd_enable.getLabel() + methodSeparator, false);

        codeBuffer.append(lcd_db4.getLabel() + methodSeparator, false);
        codeBuffer.append(lcd_db5.getLabel() + methodSeparator, false);
        codeBuffer.append(lcd_db6.getLabel() + methodSeparator, false);
        codeBuffer.append(lcd_db7.getLabel(), false);

        codeBuffer.append(");", false);
        codeBuffer.appendBreakline();
        return true;
    }

    public SketchInstruction operatePrintText(SketchStringVariable stringVariable) {
        return codeBuffer -> {
            codeBuffer.appendLine(getLabel() + ".print(" + stringVariable.getLabel() + ");");
            return true;
        };
    }

    public SketchInstruction operateBegin(int columns, int rows) {
        return codeBuffer -> {
            codeBuffer.appendLine(getLabel() + ".begin(" + columns + ", " + rows + ");");
            return true;
        };
    }

    public SketchInstruction operateSetCursor(int column, int row) {
        return codeBuffer -> {
            codeBuffer.appendLine(getLabel() + ".setCursor(" + column + ", " + row + ");");
            return true;
        };
    }

    public void setVariables(SketchDefineDirective LCD_REGISTER_SELECT, SketchDefineDirective LCD_READ_WRITE, SketchDefineDirective LCD_ENABLE, SketchDefineDirective LCD_DB4, SketchDefineDirective LCD_DB5, SketchDefineDirective LCD_DB6, SketchDefineDirective LCD_DB7) {
        lcd_register_select = LCD_REGISTER_SELECT;
        lcd_read_write = LCD_READ_WRITE;
        lcd_enable = LCD_ENABLE;
        lcd_db4 = LCD_DB4;
        lcd_db5 = LCD_DB5;
        lcd_db6 = LCD_DB6;
        lcd_db7 = LCD_DB7;
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
