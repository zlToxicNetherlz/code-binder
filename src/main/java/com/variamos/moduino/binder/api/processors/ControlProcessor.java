package com.variamos.moduino.binder.api.processors;

import com.variamos.moduino.binder.api.json.binding.relations.ControlActionPortJson;
import com.variamos.moduino.binder.api.json.component.ControlComponentJson;
import com.variamos.moduino.binder.api.json.control.ControllerJson;
import com.variamos.moduino.binder.api.json.control.SetPointJson;
import com.variamos.moduino.binder.api.json.control.SummingPointJson;
import com.variamos.moduino.binder.api.json.control.relations.ControllerControlActionJson;
import com.variamos.moduino.binder.api.json.control.relations.SetPointSummingPointJson;
import com.variamos.moduino.binder.api.json.control.relations.SummingPointControllerJson;
import com.variamos.moduino.binder.api.structure.ControlComponent;
import com.variamos.moduino.binder.api.structure.StructureModifier;
import com.variamos.moduino.binder.api.structure.dynamic.*;
import com.variamos.moduino.binder.model.library.SketchHeaderPidLibrary;
import com.variamos.moduino.binder.model.variables.SketchPidVariable;
import com.variamos.moduino.binder.api.structure.dynamic.*;
import com.variamos.moduino.binder.api.type.ControlComponentType;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.AnalogPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.SketchIntegerCondition;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator.SketchIntegerComparatorType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.operations.SketchIfOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchIncludeDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchDoubleVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchFloatVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchLongVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.operator.SketchNumberOperator;

import java.util.LinkedList;
import java.util.List;

public class ControlProcessor extends Processor<ControlComponentJson> {

    private BindingProcessor bindingProcessor;
    private MachineProcessor machineProcessor;

    private LinkedList<SetPointData> setPointDatas;
    private LinkedList<SummingPointData> summingPointDatas;
    private LinkedList<ControllerData> controllerDatas;
    private LinkedList<ControlActionData> controlActionDatas;

    private LinkedList<ControllerControlActionJson> controllerControlActionJsons;
    private LinkedList<SetPointSummingPointJson> setPointSummingPointJsons;
    private LinkedList<SummingPointControllerJson> summingPointControllerJsons;

    public ControlProcessor(StructureModifier structureModifier, MachineProcessor machineProcessor, BindingProcessor bindingProcessor) {
        super(structureModifier);

        this.machineProcessor = machineProcessor;
        this.bindingProcessor = bindingProcessor;

        this.setPointDatas = new LinkedList<>();
        this.summingPointDatas = new LinkedList<>();
        this.controllerDatas = new LinkedList<>();
        this.controlActionDatas = bindingProcessor.getControlActionDatas();

        this.controllerControlActionJsons = new LinkedList<>();
        this.setPointSummingPointJsons = new LinkedList<>();
        this.summingPointControllerJsons = new LinkedList<>();

    }

    @Override
    public void compose(List<ControlComponentJson> componentJson, ArduinoUnoBoard board) {

        LinkedList<ControlComponent<?>> controlComponents = getControlComponents(componentJson);

        iterator:
        for (ControlComponent<?> controlComponent : controlComponents) {

            switch (controlComponent.getType()) {

                case SETPOINT:

                    SetPointJson setPointJson = controlComponent.getGenericComponent();
                    SetPointData setPointData = new SetPointData();

                    setPointData.setId(setPointJson.getId());
                    setPointData.setLabel(setPointJson.getLabel());

                    setPointData.setValueSetPoint(Double.parseDouble(setPointJson.getValueSetpoint()));
                    setPointData.setTime(Float.parseFloat(setPointJson.getTime()));

                    setPointDatas.add(setPointData);
                    break;

                case SUMMING_POINT:

                    SummingPointJson summingPointJson = controlComponent.getGenericComponent();
                    SummingPointData summingPointData = new SummingPointData();

                    summingPointData.setId(summingPointJson.getId());
                    summingPointData.setLabel(summingPointJson.getLabel());

                    summingPointData.setDirection(summingPointJson.getDirection());
                    summingPointDatas.add(summingPointData);
                    break;

                case CONTROLLER:

                    ControllerJson controllerJson = controlComponent.getGenericComponent();
                    ControllerData controllerData = new ControllerData();

                    controllerData.setId(controllerJson.getId());
                    controllerData.setLabel(controllerJson.getLabel());

                    controllerData.setProportional(Double.parseDouble(controllerJson.getProportional()));
                    controllerData.setIntegral(Double.parseDouble(controllerJson.getIntegral()));
                    controllerData.setDerivate(Double.parseDouble(controllerJson.getDerivate()));

                    controllerDatas.add(controllerData);
                    break;

                case SET_POINT_SUMMING_POINT:
                    setPointSummingPointJsons.add(controlComponent.getGenericComponent());
                    break;

                case CONTROLLER_CONTROLACTION:
                    controllerControlActionJsons.add(controlComponent.getGenericComponent());
                    break;

                case SUMMING_POINT_CONTROLLER:
                    summingPointControllerJsons.add(controlComponent.getGenericComponent());
                    break;

                default:

                    break;

            }

        }

        composeRelations();

        if (controlActionDatas.size() > 0) {

            SketchIncludeDirective includeDirective = new SketchHeaderPidLibrary();
            board.getSketch().addPreprocessorDirective(includeDirective);

        }

        SketchDoubleVariable inputVariable = null;

        for (ControlActionData controlActionData : controlActionDatas) {

            String suffix = "_" + controlActionData.getLabel();

            SketchPidVariable pidVariable = new SketchPidVariable("pid_" + controlActionData.getLabel(), false, true);

            inputVariable = new SketchDoubleVariable("ctl_input" + suffix, 0.0);
            SketchDoubleVariable outputVariable = new SketchDoubleVariable("ctl_output" + suffix, 0.0);
            SketchDoubleVariable setpointVariable = new SketchDoubleVariable("ctl_sp" + suffix, controlActionData.getValueSetPoint());

            SketchDoubleVariable kpVariable = new SketchDoubleVariable("ctl_conskp" + suffix, controlActionData.getControllerData().getProportional());
            SketchDoubleVariable kiVariable = new SketchDoubleVariable("ctl_conski" + suffix, controlActionData.getControllerData().getIntegral());
            SketchDoubleVariable kdVariable = new SketchDoubleVariable("ctl_conskd" + suffix, controlActionData.getControllerData().getDerivate());

            pidVariable.setInput(inputVariable);
            pidVariable.setOutput(outputVariable);
            pidVariable.addSetPoint(setpointVariable);

            pidVariable.setKp(kpVariable);
            pidVariable.setKi(kiVariable);
            pidVariable.setKd(kdVariable);

            board.getSketch().addVariables(outputVariable, inputVariable, setpointVariable);
            board.getSketch().addVariables(kpVariable, kiVariable, kdVariable);
            //board.getSketch().addLibraryVariable(pidVariable);

            board.getSketch().getSetupFunction().addInstruction(pidVariable.operateSetMode(SketchPidVariable.ModeType.AUTOMATIC));

            getStructure().getOrPut(outputVariable.getLabel(), outputVariable);
            getStructure().getOrPut(inputVariable.getLabel(), inputVariable);
            getStructure().getOrPut(setpointVariable.getLabel(), setpointVariable);

            getStructure().getOrPut(kpVariable.getLabel(), kpVariable);
            getStructure().getOrPut(kiVariable.getLabel(), kiVariable);
            getStructure().getOrPut(kdVariable.getLabel(), kdVariable);

            getStructure().getOrPut(controlActionData.getId(), pidVariable);

        }

        for (ControlActionPortJson controlActionPortJson : bindingProcessor.getControlActionPortJsons()) {

            SketchIntegerVariable currentState = getStructure().getVariable("flag_state");

            ControlActionData controlAction = getControlAction(controlActionPortJson.getControlAction());
            TimerData timer = bindingProcessor.getTimer(controlActionPortJson.getTimer());
            BasicStateData stateData = machineProcessor.getState(controlActionPortJson.getState());

            SketchFunction controlFunction = new SketchFunction("control_action_" + controlAction.getLabel());
            SketchIntegerCondition condition = new SketchIntegerCondition(currentState, stateData.getSketchDefineDirective(), SketchIntegerComparatorType.EQUALS);

            SketchLongVariable sketchLongVariable = timer.getSketchLongVariable();
            SketchIfOperation sketchIfOperation = new SketchIfOperation(condition);

            SketchFloatVariable floatVariable = getStructure().getVariable(controlActionPortJson.getVarTimerLimit());
            SketchIntegerCondition millisCondition = new SketchIntegerCondition(sketchLongVariable.operateVariable(Long.valueOf("0"), SketchNumberOperator.SELF_MILLIS_WITHOUT_SEMICOLON, new Object[0]), floatVariable, SketchIntegerComparatorType.GREATER_OR_EQUAL);
            SketchIfOperation millisIfOperation = new SketchIfOperation(millisCondition);

            millisIfOperation.addInstruction(sketchLongVariable.redefineVariablesAsMillis());

            AnalogPin pin = getStructure().getAnalogPin(controlActionPortJson.getReadPort());
            millisIfOperation.addInstruction(pin.getReadInstruction(inputVariable));

            sketchIfOperation.addInstruction(millisIfOperation);
            controlFunction.addInstruction(sketchIfOperation);
            board.getSketch().addFunction(controlFunction);
            getStructure().getOrPut(controlAction.getId(), controlFunction);

        }

    }

    public void composeRelations() {

        for (SetPointSummingPointJson setPointSummingPointJson : setPointSummingPointJsons) {

            String setPointId = setPointSummingPointJson.getSet_point();
            String summingPointId = setPointSummingPointJson.getSumming_point();

            SetPointData setPointData = getSetPoint(setPointId);
            SummingPointData summingPointData = getSummingPoint(summingPointId);

            summingPointData.createSetPoint(setPointData);

        }

        for (SummingPointControllerJson summingPointControllerJson : summingPointControllerJsons) {

            String summingPointId = summingPointControllerJson.getSumming_point();
            String controllerId = summingPointControllerJson.getController();

            SummingPointData summingPointData = getSummingPoint(summingPointId);
            ControllerData controllerData = getController(controllerId);

            controllerData.setSummingPoint(summingPointData);

        }

        for (ControllerControlActionJson controllerControlActionJson : controllerControlActionJsons) {

            String controllerId = controllerControlActionJson.getController();
            String controlActionId = controllerControlActionJson.getControlAction();

            ControllerData controllerData = getController(controllerId);
            ControlActionData controlActionData = getControlAction(controlActionId);

            controlActionData.setControllerData(controllerData);

        }

    }

    public ControlActionData getControlAction(String id) {

        for (ControlActionData controlActionData : controlActionDatas)
            if (controlActionData.getId().equals(id))
                return controlActionData;

        return null;

    }

    public ControllerData getController(String id) {

        for (ControllerData controllerData : controllerDatas)
            if (controllerData.getId().equals(id))
                return controllerData;

        return null;

    }

    public SummingPointData getSummingPoint(String id) {

        for (SummingPointData summingPointData : summingPointDatas)
            if (summingPointData.getId().equals(id))
                return summingPointData;

        return null;

    }

    public SetPointData getSetPoint(String id) {

        for (SetPointData setPointData : setPointDatas)
            if (setPointData.getId().equals(id))
                return setPointData;

        return null;

    }

    public LinkedList<ControlComponent<?>> getControlComponents(List<ControlComponentJson> controlComponentJsons) {

        LinkedList<ControlComponent<?>> controlComponents = new LinkedList<>();

        if (controlComponentJsons == null)
            return controlComponents;

        for (ControlComponentJson controlComponentJson : controlComponentJsons)
            controlComponents.add(controlComponentJson.getComponent());

        return controlComponents;

    }

}
