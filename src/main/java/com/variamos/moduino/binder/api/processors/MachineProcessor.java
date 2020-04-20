package com.variamos.moduino.binder.api.processors;

import com.variamos.moduino.binder.api.json.component.MachineComponentJson;
import com.variamos.moduino.binder.api.json.machine.MachineStateJson;
import com.variamos.moduino.binder.api.json.machine.MachineTransitionJson;
import com.variamos.moduino.binder.api.structure.StructureModifier;
import com.variamos.moduino.binder.api.structure.dynamic.BasicStateData;
import com.variamos.moduino.binder.api.structure.dynamic.BasicTransitionData;
import com.variamos.moduino.binder.api.type.StateComponentType;
import me.itoxic.moduino.metamodel.arduino.entries.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.type.SketchFunctionType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class MachineProcessor extends Processor<MachineComponentJson> {

    @Getter
    private LinkedList<BasicStateData> states;

    @Getter
    private LinkedList<BasicTransitionData> transitions;

    @Getter
    private SketchIntegerVariable currentState, lastState;

    public MachineProcessor(StructureModifier structureModifier) {
        super(structureModifier);

        this.states = new LinkedList<>();
        this.transitions = new LinkedList<>();

    }

    @Override
    public void compose(List<MachineComponentJson> componentJson, ArduinoUnoBoard board) {

        int statesCounter = 0;

        for (MachineComponentJson component : componentJson) {

            if (component.isState()) {

                MachineStateJson stateJson = component.getState();
                BasicStateData basicStateData = new BasicStateData(stateJson, statesCounter++);

                if (stateJson.getType() == StateComponentType.INITIALSTATE)
                    basicStateData.makeInitial();

                SketchDefineDirective<Integer> stateFlag = new SketchDefineDirective<>(stateJson.getAsFlag(), basicStateData.getStateId());
                SketchFunction stateFunction = new SketchFunction("state_" + stateJson.getLabel(), SketchFunctionType.VOID, false);

                board.getSketch().addFunction(stateFunction);
                board.getSketch().addPreprocessorDirective(stateFlag);

                getStructure().getOrPut(stateJson.getId(), stateFunction);
                getStructure().getOrPut(stateJson.getId(), stateFlag);

                basicStateData.setDefineDirective(stateFlag);
                basicStateData.setFunction(stateFunction);
                states.add(basicStateData);
                continue;

            }

            MachineTransitionJson machineTransitionJson = component.getTransition();
            BasicTransitionData basicTransitionData = new BasicTransitionData(machineTransitionJson);

            SketchFunction transitionFunction = new SketchFunction("transition_" + machineTransitionJson.getLabel(), SketchFunctionType.BOOLEAN, false);

            board.getSketch().addFunction(transitionFunction);
            basicTransitionData.setFunction(transitionFunction);

            transitions.add(basicTransitionData);

        }

        if (states.size() > 0) {

            currentState = null;
            lastState = null;

            for (BasicStateData stateData : states)
                if (stateData.isInitial()) {

                    SketchDefineDirective initialStateDirective = getStructure().getPreprocessor(stateData.getMachineStateJson().getId());
                    currentState = new SketchIntegerVariable("flag_state");
                    lastState = new SketchIntegerVariable("flag_last_state", -1);

                    lastState.setUnsigned(true);
                    currentState.setUnsigned(true);

                    currentState.setDirectiveAsValue(initialStateDirective);
                    board.getSketch().addVariables(currentState, lastState);

                    getStructure().getOrPut("flag_state", currentState);
                    getStructure().getOrPut("flag_last_state", lastState);
                    break;

                }

        }

    }

    public LinkedList<BasicTransitionData> getProperlyTransitions(String sourceId) {

        LinkedList<BasicTransitionData> transitionsData = new LinkedList<>();

        for (BasicTransitionData transition : transitions)
            if (transition.getMachineTransitionJson().getSource().equals(sourceId))
                transitionsData.add(transition);

        return transitionsData;

    }

    public BasicTransitionData getTransitionState(String id) {

        for (BasicTransitionData transitionData : transitions)
            if (transitionData.getMachineTransitionJson().getId().equals(id))
                return transitionData;

        return null;

    }

    public BasicStateData getState(String id) {

        for (BasicStateData stateData : states)
            if (stateData.getMachineStateJson().getId().equals(id))
                return stateData;

        return null;

    }

}
