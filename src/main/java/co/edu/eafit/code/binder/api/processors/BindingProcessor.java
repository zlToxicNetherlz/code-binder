package co.edu.eafit.code.binder.api.processors;

import co.edu.eafit.code.binder.api.json.binding.*;
import co.edu.eafit.code.binder.api.json.binding.actions.ControlActionJson;
import co.edu.eafit.code.binder.api.json.binding.actions.ReadActionJson;
import co.edu.eafit.code.binder.api.json.binding.actions.WriteActionJson;
import co.edu.eafit.code.binder.api.json.binding.relations.*;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonPhaseJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonPredicateJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonWriteActionJson;
import co.edu.eafit.code.binder.api.json.component.BindingComponentJson;
import co.edu.eafit.code.binder.api.structure.BindingComponent;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.binder.api.structure.dynamic.*;
import co.edu.eafit.code.binder.api.type.PredicateComponentType;
import co.edu.eafit.code.binder.api.type.VariableComponentType;
import co.edu.eafit.code.binder.model.variables.SketchKeypadVariable;
import co.edu.eafit.code.binder.model.variables.SketchLiquidCrystalVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.pins.AnalogPin;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.pins.DigitalPin;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.conditions.SketchBooleanCondition;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.conditions.SketchIntegerCondition;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.conditions.comparator.SketchIntegerComparatorType;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.function.SketchFunctionCall;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.function.type.SketchFunctionType;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.operations.SketchElseOperation;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.operations.SketchIfOperation;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preloads.SketchNativeFunctionType;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preprocessor.SketchDefineDirective;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.*;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.operator.SketchNumberOperator;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class BindingProcessor extends Processor<BindingComponentJson> {

    private MachineProcessor machineProcessor;

    private LinkedList<ActivityJson> activityJsons;
    private LinkedList<LogicalOperatorJson> logicalOperatorJsons;
    private LinkedList<PredicateJson> predicateJsons;
    private LinkedList<ReadPortJson> readPortJsons;
    private LinkedList<OperatorPredicateJson> operatorPredicateJsons;
    private LinkedList<OperatorTransitionJson> operatorTransitionJsons;
    private LinkedList<PredicateReadJson> predicateReadJsons;
    private LinkedList<ControlActionPortJson> controlActionPortJsons;

    private LinkedList<WriteActionData> writeActionDatas;
    private LinkedList<ReadActionData> readActionDatas;
    private LinkedList<ControlActionData> controlActionDatas;
    private LinkedList<TimerData> timerDatas;

    private SketchFunction resetTimer;

    public BindingProcessor(StructureModifier structureModifier, MachineProcessor machineProcessor) {
        super(structureModifier);

        this.machineProcessor = machineProcessor;

        this.activityJsons = new LinkedList<>();
        this.predicateJsons = new LinkedList<>();
        this.readPortJsons = new LinkedList<>();
        this.logicalOperatorJsons = new LinkedList<>();
        this.operatorPredicateJsons = new LinkedList<>();
        this.operatorTransitionJsons = new LinkedList<>();
        this.predicateReadJsons = new LinkedList<>();
        this.controlActionPortJsons = new LinkedList<>();

        this.timerDatas = new LinkedList<>();
        this.writeActionDatas = new LinkedList<>();
        this.readActionDatas = new LinkedList<>();
        this.controlActionDatas = new LinkedList<>();

    }

    @Override
    public void compose(List<BindingComponentJson> componentJson, ArduinoUnoBoard board) {

        LinkedList<BindingComponent<?>> bindingComponents = getBindingComponents(componentJson);

        iterator:
        for (BindingComponent<?> bindingComponent : bindingComponents) {

            switch (bindingComponent.getType()) {

                case VARIABLE:

                    VariableJson variableJson = bindingComponent.getGenericComponent();

                    if (variableJson.getType() == VariableComponentType.ANALOG_VARIABLE) {

                        Float value = Float.parseFloat(variableJson.getValue());
                        SketchFloatVariable variable = new SketchFloatVariable(variableJson.getLabel(), value);

                        board.getSketch().addVariable(variable);
                        getStructure().getOrPut(variableJson.getId(), variable);
                        continue iterator;

                    } else if (variableJson.getType() == VariableComponentType.DIGITAL_VARIABLE) {

                        Integer value = variableJson.getValue().equals("LOW") ? 0 : 1;
                        SketchIntegerVariable variable = new SketchIntegerVariable(variableJson.getLabel(), value);

                        board.getSketch().addVariable(variable);
                        getStructure().getOrPut(variableJson.getId(), variable);
                        continue iterator;

                    } else if (variableJson.getType() == VariableComponentType.STRING_VARIABLE) {

                        String value = variableJson.getValue();
                        SketchStringVariable variable = new SketchStringVariable(variableJson.getLabel(), value);

                        board.getSketch().addVariable(variable);
                        getStructure().getOrPut(variableJson.getId(), variable);
                        continue iterator;

                    }

                    break;

                case CONTROL_ACTION:

                    ControlActionJson controlActionJson = bindingComponent.getGenericComponent();
                    ControlActionData controlActionData = new ControlActionData(controlActionJson);

                    controlActionData.setId(controlActionJson.getId());
                    controlActionData.setLabel(controlActionJson.getLabel());

                    controlActionDatas.add(controlActionData);
                    break;

                case TIMER:

                    TimerJson timerJson = bindingComponent.getGenericComponent();
                    TimerData timerData = new TimerData(timerJson);

                    Long value = Long.parseLong(timerJson.getInitialValue());

                    SketchLongVariable variable = new SketchLongVariable(timerJson.getLabel(), value);
                    SketchFunctionCall call = new SketchFunctionCall(SketchNativeFunctionType.MILLIS);

                    variable.setUnsigned(true);
                    variable.addComputedFunction(call);

                    timerData.setVariable(variable);
                    board.getSketch().addVariable(variable);
                    getStructure().getOrPut(timerJson.getId(), variable);

                    timerDatas.add(timerData);
                    break;

                case READ_ACTION:

                    ReadActionJson readActionJson = bindingComponent.getGenericComponent();
                    ReadActionData readActionData = new ReadActionData(readActionJson);

                    SketchFunction rfunction = new SketchFunction("action_" + readActionJson.getLabel(), readActionJson.getLabel().equals("leer_teclado") || readActionJson.getLabel().equals("leer_tecla") ? SketchFunctionType.STRING : SketchFunctionType.FLOAT, false);

                    board.getSketch().addFunction(rfunction);
                    readActionData.setFunction(rfunction);
                    getStructure().getOrPut(readActionJson.getId(), rfunction);
                    readActionDatas.add(readActionData);
                    break;

                case WRITE_ACTION:

                    WriteActionJson writeActionJson = bindingComponent.getGenericComponent();
                    WriteActionData writeActionData = new WriteActionData(writeActionJson);

                    SketchFunction wfunction = new SketchFunction("action_" + writeActionJson.getLabel(), SketchFunctionType.VOID, false);

                    if (wfunction.getLabel().equals("action_resetear_timer")) {

                        resetTimer = wfunction;

                        for (TimerData basicTimer : timerDatas)
                            wfunction.addInstruction(basicTimer.getSketchLongVariable().redefineVariablesAsMillis());

                    }

                    writeActionData.setFunction(wfunction);
                    board.getSketch().addFunction(wfunction);
                    getStructure().getOrPut(writeActionJson.getId(), wfunction);

                    writeActionDatas.add(writeActionData);
                    break;

                case PREDICATE:

                    PredicateJson predicateJson = bindingComponent.getGenericComponent();
                    predicateJsons.add(predicateJson);
                    break;

                case ACTIVITY:

                    ActivityJson activityJson = bindingComponent.getGenericComponent();
                    SketchFunction sketchFunction = new SketchFunction(activityJson.getLabel(), SketchFunctionType.VOID, false);

                    board.getSketch().addFunction(sketchFunction);
                    getStructure().getOrPut(activityJson.getId(), sketchFunction);

                    activityJsons.add(activityJson);
                    break;

                case ACTIVITY_WRITE:

                    ActivityWriteJson activityWriteJson = bindingComponent.getGenericComponent();
                    ActivityJson activity = getActivity(activityWriteJson.getActivity());

                    SketchFunction function = getStructure().getFunction(activity.getId());

                    for (CommonWriteActionJson commonWriteActionJson : activityWriteJson.getWriteActions()) {

                        WriteActionData data = getWriteAction(commonWriteActionJson.getWriteAction());
                        SketchFunctionCall functionCall = new SketchFunctionCall(data.getSketchFunction());

                        function.addInstruction(functionCall);
                        continue;

                    }

                    break;

                case CONTROL_ACTION_PORT:

                    ControlActionPortJson controlActionPortJson = bindingComponent.getGenericComponent();
                    controlActionPortJsons.add(controlActionPortJson);
                    break;

                case PREDICATE_READ:

                    PredicateReadJson predicateReadJson = bindingComponent.getGenericComponent();
                    predicateReadJsons.add(predicateReadJson);
                    break;

                case LOGICAL_OPERATOR:

                    LogicalOperatorJson logicalOperatorJson = bindingComponent.getGenericComponent();
                    logicalOperatorJsons.add(logicalOperatorJson);
                    break;

                case OPERATOR_TRANSITION:

                    OperatorTransitionJson operatorTransitionJson = bindingComponent.getGenericComponent();
                    operatorTransitionJsons.add(operatorTransitionJson);
                    break;

                case OPERATOR_PREDICATE:

                    OperatorPredicateJson operatorPredicateJson = bindingComponent.getGenericComponent();
                    operatorPredicateJsons.add(operatorPredicateJson);
                    break;

                case READ_PORT:

                    ReadPortJson readPortJson = bindingComponent.getGenericComponent();
                    readPortJsons.add(readPortJson);
                    break;

                case WRITE_PORT:

                    WritePortJson writePortJson = bindingComponent.getGenericComponent();
                    WriteActionData actionData = getWriteAction(writePortJson.getWriteAction());

                    SketchVariable writeVariable = getStructure().getVariable(writePortJson.getReadPort());

                    if (writeVariable instanceof SketchFloatVariable) {

                        SketchFloatVariable floatVariable = (SketchFloatVariable) writeVariable;
                        DigitalPin digitalPin = getStructure().getDigitalPin(writePortJson.getWritePort());

                        if (digitalPin != null) {
                            actionData.getSketchFunction().addInstruction(digitalPin.getWriteInstruction(floatVariable));
                            break;
                        }

                        AnalogPin analogPin = getStructure().getAnalogPin(writePortJson.getWritePort());

                        if (analogPin != null) {
                            break;
                        }

                        TimerData tData = getTimer(writePortJson.getWritePort());

                        if (tData != null) {

                            SketchLongVariable sketchLongVariable = tData.getSketchLongVariable();
                            actionData.getSketchFunction().addInstruction(sketchLongVariable.redefineVariablesAsMillis());

                        }

                        break;

                    }

                    if (writeVariable instanceof SketchStringVariable) {

                        SketchStringVariable stringVariable = (SketchStringVariable) writeVariable;
                        SketchLibraryVariable sketchLibraryVariable = getStructure().getLibraryVariable(writePortJson.getWritePort());

                        if (sketchLibraryVariable != null) {

                            if (sketchLibraryVariable instanceof SketchLiquidCrystalVariable) {

                                SketchLiquidCrystalVariable sketchLiquidCrystalVariable = (SketchLiquidCrystalVariable) sketchLibraryVariable;
                                actionData.getSketchFunction().addInstruction(sketchLiquidCrystalVariable.operatePrintText(stringVariable));

                            }

                            break;

                        }
                        break;

                    }

                    DigitalPin digitalPin = getStructure().getDigitalPin(writePortJson.getWritePort());

                    if (digitalPin != null) {

                        SketchIntegerVariable digitalNumberVariable = getStructure().getVariable(writePortJson.getReadPort());
                        actionData.getSketchFunction().addInstruction(digitalPin.getWriteInstruction(digitalNumberVariable));
                        break;

                    }

                    break;

                case STATE_ACTIVITY:

                    SketchIntegerVariable currentState = getStructure().getVariable("flag_state");
                    SketchIntegerVariable lastState = getStructure().getVariable("flag_last_state");

                    if (currentState == null || lastState == null)
                        break;

                    StateActivityJson stateActivityJson = bindingComponent.getGenericComponent();
                    BasicStateData basicStateData = machineProcessor.getState(stateActivityJson.getState());

                    SketchIntegerCondition sketchIntegerCondition = new SketchIntegerCondition(lastState, currentState, SketchIntegerComparatorType.NOT_EQUALS);
                    SketchIfOperation sketchIfOperation = new SketchIfOperation(sketchIntegerCondition);

                    if (stateActivityJson.getBeginPhase().size() > 0)
                        for (CommonPhaseJson beginPhase : stateActivityJson.getBeginPhase())
                            sketchIfOperation.addInstruction(new SketchFunctionCall(getStructure().getFunction(beginPhase.getActivity())));

                    if (stateActivityJson.getWhilePhase().size() > 0)
                        for (CommonPhaseJson whilePhase : stateActivityJson.getWhilePhase()) {

                            SketchElseOperation sketchElseOperation = new SketchElseOperation(sketchIfOperation);
                            sketchElseOperation.addInstruction(new SketchFunctionCall(getStructure().getFunction(whilePhase.getActivity())));

                        }

                    sketchIfOperation.addInstruction(lastState.redefineVariable(currentState));
                    basicStateData.getSketchFunction().addInstruction(sketchIfOperation);

                    LinkedList<BasicTransitionData> stateTransitions = machineProcessor.getProperlyTransitions(basicStateData.getMachineStateJson().getId());

                    for (BasicTransitionData transition : stateTransitions) {

                        SketchFunction transitionFunction = transition.getSketchFunction();
                        SketchFunctionCall transitionCall = new SketchFunctionCall(transitionFunction);

                        SketchBooleanCondition booleanCondition = new SketchBooleanCondition(transitionCall);
                        SketchIfOperation checker = new SketchIfOperation(booleanCondition);

                        BasicStateData targetState = machineProcessor.getState(transition.getMachineTransitionJson().getTarget());
                        checker.addInstruction(currentState.redefineVariable(targetState.getSketchDefineDirective()));
                        basicStateData.getSketchFunction().addInstruction(checker);

                        if (resetTimer != null) {

                            SketchFunctionCall callReset = new SketchFunctionCall(resetTimer);
                            checker.addInstruction(callReset);

                        }

                    }

                    if (stateActivityJson.getEndPhase().size() > 0) {

                        SketchDefineDirective<Integer> defineDirective = getStructure().getPreprocessor(stateActivityJson.getState());

                        SketchIntegerCondition endIntegerCondition = new SketchIntegerCondition(currentState, defineDirective, SketchIntegerComparatorType.NOT_EQUALS);
                        SketchIfOperation endPhaseIfOperation = new SketchIfOperation(endIntegerCondition);

                        for (CommonPhaseJson endPhase : stateActivityJson.getEndPhase())
                            endPhaseIfOperation.addInstruction(new SketchFunctionCall(getStructure().getFunction(endPhase.getActivity())));

                        basicStateData.getSketchFunction().addInstruction(endPhaseIfOperation);

                    }

                    break;

                default:

                    break;

            }

        }

        for (ReadPortJson readPortJson : readPortJsons) {

            ReadActionData readActionData = getReadAction(readPortJson.getReadAction());
            TimerData timerData = getTimer(readPortJson.getReadPort());

            if (timerData != null) {

                readActionData.getSketchFunction().setResultInstruction(timerData.getSketchLongVariable().operateVariable(Long.valueOf("0"), SketchNumberOperator.SELF_MILLIS, new Object[0]));
                continue;

            }

            SketchVariable variable = getStructure().getVariable(readPortJson.getReadPort());

            if (variable != null) {

                if (variable instanceof SketchStringVariable)
                    readActionData.getSketchFunction().setReturnType(SketchFunctionType.STRING);

                readActionData.getSketchFunction().setResultVariable(variable);
                continue;

            }

            SketchLibraryVariable sketchLibraryVariable = getStructure().getLibraryVariable(readPortJson.getReadPort());

            if (sketchLibraryVariable != null) {

                if (sketchLibraryVariable instanceof SketchKeypadVariable) {

                    SketchKeypadVariable sketchKeypadVariable = (SketchKeypadVariable) sketchLibraryVariable;
                    readActionData.getSketchFunction().addInstruction(sketchKeypadVariable.operateReadAsResult());
                    continue;

                }

                readActionData.getSketchFunction().setResultLibraryVariable(sketchLibraryVariable);
                continue;

            }

        }

        for (PredicateReadJson predicateReadJson : predicateReadJsons) {

            PredicateJson predicateJson = getPredicate(predicateReadJson.getPredicate());
            ReadActionData primaryAction = getReadAction(predicateReadJson.getReadActionPrimary());
            ReadActionData secondaryAction = getReadAction(predicateReadJson.getReadActionSecondary());

            PredicateComponentType operatorType = predicateJson.getOperator();

            SketchFunction predicateFunction = new SketchFunction("predicate_" + predicateJson.getLabel(), SketchFunctionType.BOOLEAN, false);

            SketchFunctionCall primaryCall = new SketchFunctionCall(primaryAction.getSketchFunction());
            SketchFunctionCall secondaryCall = new SketchFunctionCall(secondaryAction.getSketchFunction());

            SketchIntegerCondition condition = new SketchIntegerCondition(primaryCall, secondaryCall, operatorType.getComparatorType());
            predicateFunction.setResultOperation(condition);

            board.getSketch().addFunction(predicateFunction);
            getStructure().getOrPut(predicateJson.getId(), predicateFunction);

        }

        for (OperatorTransitionJson operatorTransitionJson : operatorTransitionJsons) {

            LinkedList<CommonPredicateJson> transitionPredicates = new LinkedList<>();

            BasicTransitionData transitionData = machineProcessor.getTransitionState(operatorTransitionJson.getTransition());
            LogicalOperatorJson loJson = getLogicalOperator(operatorTransitionJson.getLogicalOperator());

            for (OperatorPredicateJson operatorPredicateJson : operatorPredicateJsons)
                if (operatorPredicateJson.getLogicalOperator().equals(loJson.getId()))
                    transitionPredicates.addAll(operatorPredicateJson.getPredicates());

            SketchFunctionCall sketchFunctionCall = new SketchFunctionCall(getStructure().getFunction(getPredicate(transitionPredicates.get(0).getPredicate()).getId()));
            SketchBooleanCondition sketchBooleanCondition = new SketchBooleanCondition(sketchFunctionCall);

            transitionData.getSketchFunction().setResultOperation(sketchBooleanCondition);

        }

    }

    public LinkedList<BindingComponent<?>> getBindingComponents(List<BindingComponentJson> bindingComponentJsons) {

        LinkedList<BindingComponent<?>> bindingComponents = new LinkedList<>();

        for (BindingComponentJson bindingComponentJson : bindingComponentJsons)
            bindingComponents.add(bindingComponentJson.getComponent());

        return bindingComponents;

    }

    public LogicalOperatorJson getLogicalOperator(String id) {

        for (LogicalOperatorJson json : logicalOperatorJsons)
            if (json.getId().equals(id))
                return json;

        return null;

    }

    public PredicateJson getPredicate(String id) {

        for (PredicateJson json : predicateJsons)
            if (json.getId().equals(id))
                return json;

        return null;

    }

    public ControlActionData getControlAction(String id) {

        for (ControlActionData data : controlActionDatas)
            if (data.getControlActionJson().getId().equals(id))
                return data;

        return null;

    }

    public WriteActionData getWriteAction(String id) {

        for (WriteActionData data : writeActionDatas)
            if (data.getWriteActionJson().getId().equals(id))
                return data;

        return null;

    }

    public ReadActionData getReadAction(String id) {

        for (ReadActionData data : readActionDatas)
            if (data.getReadActionJson().getId().equals(id))
                return data;

        return null;

    }

    public TimerData getTimer(String id) {

        for (TimerData data : timerDatas)
            if (data.getTimerJson().getId().equals(id))
                return data;

        return null;

    }

    public ActivityJson getActivity(String id) {

        for (ActivityJson activityJson : activityJsons)
            if (activityJson.getId().equals(id))
                return activityJson;

        return null;

    }

}
