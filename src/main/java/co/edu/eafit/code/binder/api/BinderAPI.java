package co.edu.eafit.code.binder.api;

import co.edu.eafit.code.binder.api.json.RemoteJson;
import co.edu.eafit.code.binder.api.processors.BindingProcessor;
import co.edu.eafit.code.binder.api.processors.HardwareProcessor;
import co.edu.eafit.code.binder.api.processors.MachineProcessor;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.generator.generator.buffer.CodeBuffer;
import co.edu.eafit.code.generator.metamodel.arduino.ArduinoMetamodel;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Project;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.UnknownHostException;

public class BinderAPI {

    public static Project getProject() throws FileNotFoundException {

        Gson gson = new Gson();
        RemoteJson json = gson.fromJson(new FileReader("C:/Temp/binding.json"), RemoteJson.class);

        return getProject(json);

    }

    private static Project getProject(RemoteJson remoteJson) {

        ArduinoMetamodel arduinoMetamodel = new ArduinoMetamodel();
        Project project = new Project(remoteJson.getName(), arduinoMetamodel.getManager());

        startProcessment(project, remoteJson);

        try {

            generateCode(project).printAll();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return project;

    }

    private static void startProcessment(Project project, RemoteJson remoteJson) {

        ArduinoUnoBoard board = new ArduinoUnoBoard();
        StructureModifier structureModifier = new StructureModifier();

        HardwareProcessor hardwareProcessor = new HardwareProcessor(structureModifier);
        MachineProcessor machineProcessor = new MachineProcessor(structureModifier);
        BindingProcessor bindingProcessor = new BindingProcessor(structureModifier);

        hardwareProcessor.compose(remoteJson.getHardwareComponents(), board);
        machineProcessor.compose(remoteJson.getMachineComponents(), board);
        bindingProcessor.compose(remoteJson.getBindingComponents(), board);

        project.addBoard(board);

    }

    private static CodeBuffer generateCode(Project project) throws UnknownHostException {

        ArduinoUnoBoard board = (ArduinoUnoBoard) project.getBoards().get(0);
        return board.generateCode();

    }

}
