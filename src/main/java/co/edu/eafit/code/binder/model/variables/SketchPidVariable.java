package co.edu.eafit.code.binder.model.variables;

import co.edu.eafit.code.binder.model.library.SketchHeaderPidLibrary;
import co.edu.eafit.code.generator.generator.buffer.CodeBuffer;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchInstruction;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchDoubleVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchLibraryVariable;
import lombok.ToString;

@ToString
public class SketchPidVariable extends SketchLibraryVariable<SketchHeaderPidLibrary> {

    private SketchDoubleVariable input;
    private SketchDoubleVariable output;

    private SketchDoubleVariable setPoint;

    private SketchDoubleVariable kp;
    private SketchDoubleVariable ki;
    private SketchDoubleVariable kd;

    private SketchDoubleVariable direction;

    public SketchPidVariable(String label, boolean parameter, boolean constant) {
        super("PID", label, null, parameter, constant);
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer codeBuffer) {

        if (input == null || output == null || setPoint == null ||
                kp == null || ki == null || kd == null)
            return false;

        final String methodSeparator = ", ";

        codeBuffer.append(getKeyword() + " " + getLabel() + "(", true);

        codeBuffer.append(input.getAsReference() + methodSeparator, false);
        codeBuffer.append(output.getAsReference() + methodSeparator, false);
        codeBuffer.append(setPoint.getAsReference() + methodSeparator, false);

        codeBuffer.append(kp.getLabel() + methodSeparator, false);
        codeBuffer.append(ki.getLabel() + methodSeparator, false);
        codeBuffer.append(kd.getLabel() + methodSeparator, false);

        if (direction == null)
            codeBuffer.append("DIRECT", false);
        else
            codeBuffer.append(direction.getLabel(), false);

        codeBuffer.append(");", false);
        codeBuffer.appendBreakline();
        return true;
    }

    public SketchInstruction operateSetMode(ModeType modeType) {
        return codeBuffer -> {
            codeBuffer.appendLine(getLabel() + ".SetMode(" + modeType.name() + ");");
            return true;
        };
    }

    public void setInput(SketchDoubleVariable inputVariable) {
        this.input = inputVariable;
    }

    public void setOutput(SketchDoubleVariable outputVariable) {
        this.output = outputVariable;
    }

    public void addSetPoint(SketchDoubleVariable setPointVariable) {
        this.setPoint = setPointVariable;
    }

    public void setKp(SketchDoubleVariable kpVariable) {
        this.kp = kpVariable;
    }

    public void setKi(SketchDoubleVariable kiVariable) {
        this.ki = kiVariable;
    }

    public void setKd(SketchDoubleVariable kdVariable) {
        this.kd = kdVariable;
    }

    public void setDirection(SketchDoubleVariable directionVariable) {
        this.direction = directionVariable;
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
