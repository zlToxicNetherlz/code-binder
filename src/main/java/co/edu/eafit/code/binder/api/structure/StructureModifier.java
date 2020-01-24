package co.edu.eafit.code.binder.api.structure;

import co.edu.eafit.code.generator.metamodel.arduino.classes.model.pins.AnalogPin;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.pins.DigitalPin;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchOperation;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchPreprocessor;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchLibraryVariable;

import java.util.HashMap;
import java.util.Map;

public class StructureModifier {

    private Map<String, DigitalPin> sketchDigitalPins;
    private Map<String, AnalogPin> sketchAnalogPins;

    private Map<String, SketchPreprocessor> sketchPreprocessors;
    private Map<String, SketchVariable> sketchVariables;
    private Map<String, SketchLibraryVariable> sketchLibraryVariables;
    private Map<String, SketchOperation> sketchOperations;
    private Map<String, SketchFunction> sketchFunctions;

    public StructureModifier() {

        this.sketchDigitalPins = new HashMap<>();
        this.sketchAnalogPins = new HashMap<>();

        this.sketchPreprocessors = new HashMap<>();
        this.sketchFunctions = new HashMap<>();
        this.sketchOperations = new HashMap<>();
        this.sketchVariables = new HashMap<>();
        this.sketchLibraryVariables = new HashMap<>();

    }

    public void put(String id, DigitalPin digitalPin) {
        sketchDigitalPins.put(id, digitalPin);
    }

    public void put(String id, AnalogPin analogPin) {
        sketchAnalogPins.put(id, analogPin);
    }

    public SketchPreprocessor getOrPut(String id, SketchPreprocessor directive) {

        if (sketchPreprocessors.containsKey(id))
            return sketchPreprocessors.get(id);

        sketchPreprocessors.put(id, directive);
        return directive;

    }

    public SketchVariable getOrPut(String id, SketchVariable variable) {

        if (sketchVariables.containsKey(id))
            return sketchVariables.get(id);

        sketchVariables.put(id, variable);
        return variable;

    }

    public SketchLibraryVariable getOrPut(String id, SketchLibraryVariable variable) {

        if (sketchLibraryVariables.containsKey(id))
            return sketchLibraryVariables.get(id);

        sketchLibraryVariables.put(id, variable);
        return variable;

    }

    public SketchFunction getOrPut(String id, SketchFunction function) {

        if (sketchFunctions.containsKey(id))
            return sketchFunctions.get(id);

        sketchFunctions.put(id, function);
        return function;

    }

    public SketchOperation getOrPut(String id, SketchOperation operation) {

        if (sketchOperations.containsKey(id))
            return sketchOperations.get(id);

        sketchOperations.put(id, operation);
        return operation;

    }

    public <E extends SketchPreprocessor> E getPreprocessor(String id) {
        return (E) sketchPreprocessors.get(id);
    }

    public <E extends SketchVariable> E getVariable(String id) {
        return (E) sketchVariables.get(id);
    }

    public <E extends SketchLibraryVariable> E getLibraryVariable(String id) {
        return (E) sketchLibraryVariables.get(id);
    }

    public <E extends SketchFunction> E getFunction(String id) {
        return (E) sketchFunctions.get(id);
    }

    public <E extends SketchOperation> E getOperation(String id) {
        return (E) sketchOperations.get(id);
    }

    public DigitalPin getDigitalPin(String id) {
        return sketchDigitalPins.get(id);
    }

    public AnalogPin getAnalogPin(String id) {
        return sketchAnalogPins.get(id);
    }

}
