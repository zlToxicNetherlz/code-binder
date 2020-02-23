package co.edu.eafit.code.binder.resolver.json.variables;

import co.edu.eafit.code.generator.metamodel.arduino.classes.model.Pin;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.type.PinMode;
import lombok.Getter;

@Getter
public class DeviceVariable {

    private String name;
    private String scope;
    private String literal;
    private String pinMode;
    private String type;

    private String digitalPin;
    private String analogPin;
    private String pwmPin;

    public DeviceVariableType getScope() {

        for (DeviceVariableType deviceVariableType : DeviceVariableType.values())
            if (deviceVariableType.getValue().equalsIgnoreCase(scope))
                return deviceVariableType;

        throw new RuntimeException("El ambito de la variable '" + name + "' no fue encontrado!");
    }

    public String getTruePin() {
        if (digitalPin != null)
            return digitalPin;
        else if (analogPin != null)
            return analogPin;
        else
            return pwmPin;
    }

    public String getDynamicVariable(SketchVariable variable) {
        return variable.getLabel();
    }

    public String getParametrizedPin(Pin boardPin) {
        return "todo";
    }

    public PinMode getPinMode() {

        if (pinMode.equals("INPUT"))
            return PinMode.INPUT;

        if (pinMode.equals("OUTPUT"))
            return PinMode.OUTPUT;

        if (pinMode.equals("INPUT_PULLUP"))
            return PinMode.INPUT_PULLUP;

        return null;

    }

}
