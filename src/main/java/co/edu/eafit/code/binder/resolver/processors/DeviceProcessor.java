package co.edu.eafit.code.binder.resolver.processors;

import co.edu.eafit.code.binder.api.json.binding.actions.ActionArgumentJson;
import co.edu.eafit.code.binder.api.json.binding.relations.DeviceActionJson;
import co.edu.eafit.code.binder.api.json.binding.relations.hardwareBehavior.ActionResultJson;
import co.edu.eafit.code.binder.api.json.hardware.PinJson;
import co.edu.eafit.code.binder.api.processors.HardwareProcessor;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.binder.api.structure.dynamic.*;
import co.edu.eafit.code.binder.resolver.VariamosResolver;
import co.edu.eafit.code.binder.resolver.json.DeviceJson;
import co.edu.eafit.code.binder.resolver.json.actions.DeviceReadActionJson;
import co.edu.eafit.code.binder.resolver.json.actions.DeviceWriteActionJson;
import co.edu.eafit.code.binder.resolver.json.actions.instructions.DeviceInstructionJson;
import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.binder.resolver.json.macros.MacroType;
import co.edu.eafit.code.binder.resolver.json.variables.DeviceVariable;
import co.edu.eafit.code.binder.resolver.json.variables.DeviceVariableType;
import co.edu.eafit.code.binder.resolver.processors.data.DeviceWriter;
import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Board;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.Pin;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preloads.SketchNativeFunctionType;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchFloatVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchIntegerVariable;
import lombok.Getter;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class DeviceProcessor {

    private LinkedList<DeviceJson> devices;

    public DeviceProcessor() {

        this.devices = new LinkedList<>();

        try {

            DeviceJson[] devicesJson = new VariamosResolver().resolveJSON();

            for (DeviceJson deviceJson : devicesJson)
                devices.add(deviceJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void processDeviceMacro(DeviceData device, HardwareProcessor hardwareProcessor) {

        if (device.getResolverJson().getMacros() == null || device.getResolverJson().getMacros().length == 0)
            return;

        co.edu.eafit.code.binder.api.json.hardware.DeviceJson remote = device.getDeviceJson();

        LinkedList<DeviceBoardPinData> deviceBoardPinDatas = new LinkedList<>();

        for (DeviceBoardPinData pinData : hardwareProcessor.getDeviceBoardConnections())
            if (pinData.getDeviceJson().getId().equals(device.getDeviceJson().getId())) {
                deviceBoardPinDatas.add(pinData);
                continue;
            }

        for (MacroJson macro : device.getResolverJson().getMacros()) {

            int i = 0;
            String[] processed = new String[macro.getParameters().length];

            for (String parameter : macro.getParameters()) {

                parameter = parameter.replaceAll("\\%remote_id\\%", remote.getId().toUpperCase());
                parameter = parameter.replaceAll("\\%remote_label\\%", remote.getLabel());

                String pattern1 = "%var_";
                String pattern2 = "%";

                Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                Matcher m = p.matcher(parameter);

                while (m.find()) {

                    String variable = m.group(1);

                    DeviceVariable deviceVariable = getVariable(device.getResolverJson(), variable);
                    DeviceBoardPinData pinData = getSpecificPin(device.getDeviceJson().getPins(), deviceVariable.getTruePin(), deviceBoardPinDatas);

                    parameter = parameter.replaceAll("%var_" + variable + "%", pinData.getBoardPin().getLabel());

                }

                processed[i] = parameter;
                i++;

            }

            macro.setParameters(processed);

            MacroType type = macro.getMacroType();
            Macro omacro = type.process();

            omacro.process(macro, hardwareProcessor.getBoard().getSketch());

        }

    }

    public void processDeviceRead(DeviceData device, DeviceWriter writer, ReadActionData readActionData) {

        LinkedList<DeviceBoardPinData> deviceBoardPinDatas = new LinkedList<>();

        for (DeviceBoardPinData pinData : writer.getHardwareProcessor().getDeviceBoardConnections())
            if (pinData.getDeviceJson().getId().equals(device.getDeviceJson().getId())) {
                deviceBoardPinDatas.add(pinData);
                continue;
            }

        for (DeviceReadActionJson actionJson : device.getResolverJson().getReadActions()) {

            if (actionJson.getName().equals(readActionData.getReadActionJson().getSubType())) {

                // System.out.println("[Generating](READ) " + device.getResolverJson().getName() + " >> function (" + actionJson.getName() + ")");

                for (DeviceInstructionJson instructionJson : actionJson.getInstructions())
                    if (instructionJson.isSdk())
                        processSdkFunction(deviceBoardPinDatas, instructionJson, device, writer, readActionData);
                    else
                        processCustomFunction(deviceBoardPinDatas, instructionJson, device, writer, readActionData);

            }
        }

    }

    public void processDeviceWrite(DeviceData device, DeviceWriter writer, WriteActionData writeActionData) {

        LinkedList<DeviceBoardPinData> deviceBoardPinDatas = new LinkedList<>();

        for (DeviceBoardPinData pinData : writer.getHardwareProcessor().getDeviceBoardConnections())
            if (pinData.getDeviceJson().getId().equals(device.getDeviceJson().getId())) {
                deviceBoardPinDatas.add(pinData);
                continue;
            }

        for (DeviceWriteActionJson actionJson : device.getResolverJson().getWriteActions()) {

            if (actionJson.getName().equals(writeActionData.getWriteActionJson().getSubType())) {

                // System.out.println("[Generating](WRITE) " + device.getResolverJson().getName() + " >> function (" + actionJson.getName() + ")");

                for (DeviceInstructionJson instructionJson : actionJson.getInstructions())
                    if (instructionJson.isSdk())
                        processSdkFunction(deviceBoardPinDatas, instructionJson, device, writer, writeActionData);
                    else
                        processCustomFunction(deviceBoardPinDatas, instructionJson, device, writer, writeActionData);

            }
        }

    }

    public void processCustomFunction(LinkedList<DeviceBoardPinData> deviceBoardPinDatas, DeviceInstructionJson instructionJson, DeviceData device, DeviceWriter writer, ActionData data) {

        String resultLabel = "";

        String fname = instructionJson.getFname();
        LinkedList<Object> parametersObjects = new LinkedList<>();

        resultLabel = resultLabel.replaceAll("\\%remote_id\\%", device.getDeviceJson().getId().toUpperCase());
        resultLabel = resultLabel.replaceAll("\\%remote_label\\%", device.getDeviceJson().getLabel());

        if (fname != null && data instanceof ReadActionData) {

            z:
            for (DeviceActionJson deviceActionJson : writer.getBindingProcessor().getDeviceActionJsons())
                if (deviceActionJson.getAction().equals(((ReadActionData) data).getReadActionJson().getId()))
                    for (ActionResultJson actionResultJson : writer.getBindingProcessor().getActionResultJsons())
                        if (actionResultJson.getAction().equals(deviceActionJson.getAction())) {
                            StructureModifier structure = writer.getBindingProcessor().getStructure();
                            resultLabel = structure.getVariable(actionResultJson.getVariable()).getLabel();
                            break z;
                        }

        }

        // Parametros
        for (String parameter : instructionJson.getParameters()) {

            DeviceVariable deviceVariable = getVariable(device.getResolverJson(), parameter);

            switch (deviceVariable.getScope()) {

                case PWMPIN:
                case ANALOGPIN:
                case DIGITALPIN:

                    DeviceVariableType scope = deviceVariable.getScope();
                    DeviceBoardPinData deviceBoardPinData = getSpecificPin(device.getDeviceJson().getPins(), deviceVariable.getTruePin(), deviceBoardPinDatas);

                    String boardPinLabel = deviceBoardPinData.getBoardPin().getLabel();
                    String directiveId = device.getDeviceJson().getId().toUpperCase() + "_" + boardPinLabel;
                    Board board = deviceBoardPinData.getBoard();

                    if (!deviceBoardPinData.isUsed()) {

                        Pin abstractPin;

                        if (scope == DeviceVariableType.DIGITALPIN)
                            abstractPin = board.useDigitalPin(boardPinLabel, directiveId, deviceVariable.getPinMode());
                        else if (scope == DeviceVariableType.ANALOGPIN)
                            abstractPin = board.useAnalogPin(boardPinLabel, directiveId, deviceVariable.getPinMode());
                        else
                            abstractPin = board.usePWMPin(boardPinLabel, directiveId, deviceVariable.getPinMode());

                        deviceBoardPinData.setUsed(true);
                        deviceBoardPinData.setPin(abstractPin);

                    }

                    parametersObjects.add(directiveId);
                    break;

                case DYNAMIC:
                    if (data instanceof WriteActionData) {
                        for (ActionArgumentJson actionArgumentJson : ((WriteActionData) data).getWriteActionJson().getArguments())
                            if (actionArgumentJson.getLabel().equals(deviceVariable.getName())) {
                                SketchVariable sketchVariable = writer.getBindingProcessor().getStructure().getVariable(actionArgumentJson.getVariableId());
                                parametersObjects.add(sketchVariable);
                            }
                        break;
                    }
                    for (ActionArgumentJson actionArgumentJson : ((ReadActionData) data).getReadActionJson().getArguments())
                        if (actionArgumentJson.getLabel().equals(deviceVariable.getName())) {
                            SketchVariable sketchVariable = writer.getBindingProcessor().getStructure().getVariable(actionArgumentJson.getVariableId());
                            parametersObjects.add(sketchVariable);
                        }
                    break;

                case STATIC:
                    parametersObjects.add(getStaticVariable(deviceVariable.getType(), deviceVariable.getLiteral()));
                    break;

            }

        }

        LinkedList<String> directives = new LinkedList<>();
        LinkedList<SketchVariable> variables = new LinkedList<>();

        for (Object object : parametersObjects)
            if (object instanceof String)
                directives.add((String) object);
            else if (object instanceof SketchVariable)
                variables.add((SketchVariable) object);

        String finalResultLabel = resultLabel;
        data.getSketchFunction().addInstruction(codeBuffer -> {

            if (data instanceof WriteActionData) {

                codeBuffer.append(fname + "(", true);
                for (int i = 0; i < parametersObjects.size(); i++) {
                    String param = String.valueOf(parametersObjects.get(i));
                    codeBuffer.append((i + 1 == parametersObjects.size()) ? param : param + ", ", false);
                }
                codeBuffer.append(");", false);
                codeBuffer.appendBreakline();

            } else if (finalResultLabel != null && !finalResultLabel.isEmpty()) {

                codeBuffer.append(finalResultLabel + " = " + fname + "(", true);
                for (int i = 0; i < parametersObjects.size(); i++) {
                    String param = String.valueOf(parametersObjects.get(i));
                    codeBuffer.append((i + 1 == parametersObjects.size()) ? param : param + ", ", false);
                }
                codeBuffer.append(");", false);
                codeBuffer.appendBreakline();

            } else {

                codeBuffer.append(fname + "(", true);
                for (int i = 0; i < parametersObjects.size(); i++) {
                    String param = String.valueOf(parametersObjects.get(i));
                    codeBuffer.append((i + 1 == parametersObjects.size()) ? param : param + ", ", false);
                }
                codeBuffer.append(");", false);
                codeBuffer.appendBreakline();

            }

            return true;

        });

    }

    public void processSdkFunction(LinkedList<DeviceBoardPinData> deviceBoardPinDatas, DeviceInstructionJson instructionJson, DeviceData device, DeviceWriter writer, ActionData data) {

        String resultLabel = "";

        SketchNativeFunctionType sdkFunction = getNativeFunction(instructionJson.getFname());
        LinkedList<Object> parametersObjects = new LinkedList<>();

        if (sdkFunction.getResult() != null && data instanceof ReadActionData) {

            z:
            for (DeviceActionJson deviceActionJson : writer.getBindingProcessor().getDeviceActionJsons())
                if (deviceActionJson.getAction().equals(((ReadActionData) data).getReadActionJson().getId()))
                    for (ActionResultJson actionResultJson : writer.getBindingProcessor().getActionResultJsons())
                        if (actionResultJson.getAction().equals(deviceActionJson.getAction())) {
                            StructureModifier structure = writer.getBindingProcessor().getStructure();
                            resultLabel = structure.getVariable(actionResultJson.getVariable()).getLabel();
                            break z;
                        }

        }

        // Parametros
        for (String parameter : instructionJson.getParameters()) {

            DeviceVariable deviceVariable = getVariable(device.getResolverJson(), parameter);

            switch (deviceVariable.getScope()) {

                case PWMPIN:
                case ANALOGPIN:
                case DIGITALPIN:

                    DeviceVariableType scope = deviceVariable.getScope();
                    DeviceBoardPinData deviceBoardPinData = getSpecificPin(device.getDeviceJson().getPins(), deviceVariable.getTruePin(), deviceBoardPinDatas);

                    String boardPinLabel = deviceBoardPinData.getBoardPin().getLabel();
                    String directiveId = device.getDeviceJson().getId().toUpperCase() + "_" + boardPinLabel;
                    Board board = deviceBoardPinData.getBoard();

                    if (!deviceBoardPinData.isUsed()) {

                        Pin abstractPin;

                        if (scope == DeviceVariableType.DIGITALPIN)
                            abstractPin = board.useDigitalPin(boardPinLabel, directiveId, deviceVariable.getPinMode());
                        else if (scope == DeviceVariableType.ANALOGPIN)
                            abstractPin = board.useAnalogPin(boardPinLabel, directiveId, deviceVariable.getPinMode());
                        else
                            abstractPin = board.usePWMPin(boardPinLabel, directiveId, deviceVariable.getPinMode());

                        deviceBoardPinData.setUsed(true);
                        deviceBoardPinData.setPin(abstractPin);

                    }

                    parametersObjects.add(directiveId);
                    break;

                case DYNAMIC:
                    if (data instanceof WriteActionData) {
                        for (ActionArgumentJson actionArgumentJson : ((WriteActionData) data).getWriteActionJson().getArguments())
                            if (actionArgumentJson.getLabel().equals(deviceVariable.getName())) {
                                SketchVariable sketchVariable = writer.getBindingProcessor().getStructure().getVariable(actionArgumentJson.getVariableId());
                                parametersObjects.add(sketchVariable);
                            }
                        break;
                    }
                    for (ActionArgumentJson actionArgumentJson : ((ReadActionData) data).getReadActionJson().getArguments())
                        if (actionArgumentJson.getLabel().equals(deviceVariable.getName())) {
                            SketchVariable sketchVariable = writer.getBindingProcessor().getStructure().getVariable(actionArgumentJson.getVariableId());
                            parametersObjects.add(sketchVariable);
                        }
                    break;

                case STATIC:
                    parametersObjects.add(getStaticVariable(deviceVariable.getType(), deviceVariable.getLiteral()));
                    break;

            }

        }

        LinkedList<String> directives = new LinkedList<>();
        LinkedList<SketchVariable> variables = new LinkedList<>();

        for (Object object : parametersObjects)
            if (object instanceof String)
                directives.add((String) object);
            else if (object instanceof SketchVariable)
                variables.add((SketchVariable) object);

        String finalResultLabel = resultLabel;
        data.getSketchFunction().addInstruction(codeBuffer -> {
            if (data instanceof WriteActionData)
                codeBuffer.appendLine(sdkFunction.createCall(directives, variables, true));
            else if (finalResultLabel != null && !finalResultLabel.isEmpty())
                codeBuffer.appendLine(sdkFunction.createCallAndSave(finalResultLabel, directives, variables, true));
            else
                codeBuffer.appendLine(sdkFunction.createCall(directives, variables, true));
            return true;
        });

    }

    public SketchVariable getStaticVariable(String type, String literal) {
        switch (type) {
            case "int":
                return new SketchIntegerVariable(Integer.valueOf(literal));
            case "float":
                return new SketchFloatVariable(Float.valueOf(literal));
        }
        return null;
    }

    public DeviceBoardPinData getSpecificPin(PinJson[] devicePins, String resolverPin, LinkedList<DeviceBoardPinData> deviceBoardPinDatas) {

        PinJson pinJson = null;

        for (PinJson pin : devicePins)
            if (pin.getLabel().equals(resolverPin)) {
                pinJson = pin;
                break;
            }

        if (pinJson == null)
            throw new RuntimeException("El pin a resolver no está definido por el JSON.");

        for (DeviceBoardPinData deviceBoardPinData : deviceBoardPinDatas)
            if (deviceBoardPinData.getDevicePin().getLabel().equals(pinJson.getLabel()))
                return deviceBoardPinData;

        throw new RuntimeException("No se encontró un Pin Especifico para '" + pinJson + "'.");

    }

    public DeviceVariable getVariable(DeviceJson deviceJson, String name) {
        for (DeviceVariable deviceVariable : deviceJson.getVariables())
            if (deviceVariable.getName().equals(name))
                return deviceVariable;
        return null;
    }

    public SketchNativeFunctionType getNativeFunction(String name) {
        for (SketchNativeFunctionType sketchNativeFunctionType : SketchNativeFunctionType.values())
            if (sketchNativeFunctionType.name().equalsIgnoreCase(name))
                return sketchNativeFunctionType;
        return null;
    }

    public DeviceJson getDevice(String name) {

        for (DeviceJson deviceJson : devices)
            if (deviceJson.getName().equals(name))
                return deviceJson;

        System.out.println("[Warning] Cannot find <" + name + "> device!");
        return null;

    }

}
