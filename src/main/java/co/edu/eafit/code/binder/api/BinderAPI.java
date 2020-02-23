package co.edu.eafit.code.binder.api;

import co.edu.eafit.code.binder.api.json.RemoteJson;
import co.edu.eafit.code.binder.api.processors.BindingProcessor;
import co.edu.eafit.code.binder.api.processors.ControlProcessor;
import co.edu.eafit.code.binder.api.processors.HardwareProcessor;
import co.edu.eafit.code.binder.api.processors.MachineProcessor;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.binder.api.structure.dynamic.BasicStateData;
import co.edu.eafit.code.binder.resolver.processors.DeviceProcessor;
import co.edu.eafit.code.generator.generator.buffer.CodeBuffer;
import co.edu.eafit.code.generator.metamodel.arduino.ArduinoMetamodel;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Project;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.function.SketchFunctionCall;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.operations.SketchCaseOperation;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.operations.SketchSwitchOperation;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preprocessor.SketchDefineDirective;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchIntegerVariable;
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
        RemoteJson json = gson.fromJson(new FileReader("C:/Users/abrah/Desktop/Integrador/" + projectName + ".json"), RemoteJson.class);

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

        project.addBoard(board);

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
