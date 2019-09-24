package co.edu.eafit.code.binder.api.processors;

import co.edu.eafit.code.binder.api.json.ComponentJson;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;

import java.util.List;

public abstract class Processor<E extends ComponentJson> {

    private StructureModifier structureModifier;

    public Processor(StructureModifier structureModifier) {
        this.structureModifier = structureModifier;
    }

    public abstract void compose(List<E> componentJson, ArduinoUnoBoard board);

    public StructureModifier getStructure() {
        return structureModifier;
    }

}
