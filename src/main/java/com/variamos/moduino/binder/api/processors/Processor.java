package com.variamos.moduino.binder.api.processors;

import com.variamos.moduino.binder.api.json.ComponentJson;
import com.variamos.moduino.binder.api.structure.StructureModifier;
import me.itoxic.moduino.metamodel.arduino.entries.model.uno.ArduinoUnoBoard;

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
