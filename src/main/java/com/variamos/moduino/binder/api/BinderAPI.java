package com.variamos.moduino.binder.api;

import com.variamos.moduino.binder.api.json.RemoteJson;
import com.variamos.moduino.binder.api.processors.BindingProcessor;
import com.variamos.moduino.binder.api.processors.ControlProcessor;
import com.variamos.moduino.binder.api.processors.HardwareProcessor;
import com.variamos.moduino.binder.api.processors.MachineProcessor;
import com.variamos.moduino.binder.api.structure.StructureModifier;
import com.variamos.moduino.binder.api.structure.dynamic.BasicStateData;
import com.variamos.moduino.binder.resolver.processors.DeviceProcessor;
import com.variamos.moduino.binder.resolver.processors.macros.IncludeMacro;
import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.ArduinoMetamodel;
import me.itoxic.moduino.metamodel.arduino.entries.Project;
import me.itoxic.moduino.metamodel.arduino.entries.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.operations.SketchCaseOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.operations.SketchSwitchOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.UnknownHostException;

public class BinderAPI {

    @Deprecated
    public static void setupServer(int port) throws Exception {

        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(server, "/json");

        handler.addServlet(TestServer.class, "/test-api");
        server.start();

    }

    public static Project getLocalProject(String projectName) throws FileNotFoundException {

        Gson gson = new Gson();
        RemoteJson json = gson.fromJson(new FileReader("C:\\Users\\abrah\\Desktop\\" + projectName + ".json"), RemoteJson.class);

        return getProject(json);

    }

    public static Project getDynamicProject(String projectJson) {

        Gson gson = new Gson();
        RemoteJson json = gson.fromJson(projectJson, RemoteJson.class);

        return getProject(json);

    }

    private static Project getProject(RemoteJson remoteJson) {

        ArduinoMetamodel arduinoMetamodel = new ArduinoMetamodel();
        Project project = new Project(remoteJson.getName(), arduinoMetamodel.getManager());

        startProcessment(project, remoteJson);

        return project;

    }

    private static void startProcessment(Project project, RemoteJson remoteJson) {

        ArduinoUnoBoard board = new ArduinoUnoBoard();
        StructureModifier structureModifier = new StructureModifier();

        DeviceProcessor deviceProcessor = new DeviceProcessor();
        HardwareProcessor hardwareProcessor = new HardwareProcessor(structureModifier);
        MachineProcessor machineProcessor = new MachineProcessor(structureModifier);
        BindingProcessor bindingProcessor = new BindingProcessor(structureModifier, deviceProcessor, hardwareProcessor, machineProcessor);
        ControlProcessor controlProcessor = new ControlProcessor(structureModifier, machineProcessor, bindingProcessor);

        hardwareProcessor.compose(remoteJson.getHardwareComponents(), board);
        machineProcessor.compose(remoteJson.getMachineComponents(), board);
        bindingProcessor.compose(remoteJson.getBindingComponents(), board);
        controlProcessor.compose(remoteJson.getControlComponents(), board);

        composeLoopFunction(machineProcessor, board);
        clearCache();

        project.addBoard(board);

    }

    private static void clearCache() {
        IncludeMacro.clearIncluded();
    }

    private static void composeLoopFunction(MachineProcessor machineProcessor, ArduinoUnoBoard board) {

        SketchFunction sketchFunction = board.getSketch().getLoopFunction();

        SketchIntegerVariable currentState = machineProcessor.getCurrentState();
        SketchSwitchOperation switchOperation = new SketchSwitchOperation(currentState);

        for (BasicStateData basicStateData : machineProcessor.getStates()) {

            SketchDefineDirective<Integer> stateDirective = basicStateData.getSketchDefineDirective();
            SketchCaseOperation sketchCaseOperation = new SketchCaseOperation(stateDirective);

            SketchFunctionCall sketchFunctionCall = new SketchFunctionCall(basicStateData.getSketchFunction());
            sketchCaseOperation.addInstruction(sketchFunctionCall);

            switchOperation.addCaseOperation(sketchCaseOperation);

        }

        sketchFunction.addInstruction(switchOperation);

        // TODO Control

    }

    public static CodeBuffer generateCode(Project project) throws UnknownHostException {

        ArduinoUnoBoard board = (ArduinoUnoBoard) project.getBoards().get(0);
        return board.generateCode();

    }

}
