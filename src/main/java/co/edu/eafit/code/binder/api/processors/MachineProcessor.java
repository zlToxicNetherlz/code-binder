package co.edu.eafit.code.binder.api.processors;

import co.edu.eafit.code.binder.api.json.component.MachineComponentJson;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;

import java.util.List;

public class MachineProcessor extends Processor<MachineComponentJson> {

    public MachineProcessor(StructureModifier structureModifier) {
        super(structureModifier);
    }

    @Override
    public void compose(List<MachineComponentJson> componentJson, ArduinoUnoBoard board) {

    }

}
