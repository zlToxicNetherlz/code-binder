package co.edu.eafit.code.binder.api.processors;

import co.edu.eafit.code.binder.api.json.component.HardwareComponentJson;
import co.edu.eafit.code.binder.api.json.hardware.PortJson;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.binder.api.type.PortComponentType;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import co.edu.eafit.code.generator.metamodel.arduino.classes.type.PinMode;

import java.util.List;

public class HardwareProcessor extends Processor<HardwareComponentJson> {

    public HardwareProcessor(StructureModifier structureModifier) {
        super(structureModifier);
    }

    @Override
    public void compose(List<HardwareComponentJson> componentJson, ArduinoUnoBoard board) {

        for (HardwareComponentJson hardwareComponentJson : componentJson) {

            if (hardwareComponentJson.isBoard())
                continue;

            mapSketchPort(hardwareComponentJson.getPort(), board);

        }

    }

    public void mapSketchPort(PortJson port, ArduinoUnoBoard board) {

        PortComponentType type = port.getType();
        PinMode mode = type.isSensor() ? PinMode.INPUT : PinMode.OUTPUT;

        if (type.isDigital())
            getStructure().put(port.getId(), board.useDigitalPin(port.getPin(), port.getLabel(), mode));
        else
            getStructure().put(port.getId(), board.useAnalogPin(port.getPin(), port.getLabel(), mode));

    }

}
