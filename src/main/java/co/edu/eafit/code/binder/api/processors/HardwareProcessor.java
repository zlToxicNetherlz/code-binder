package co.edu.eafit.code.binder.api.processors;

import co.edu.eafit.code.binder.api.json.binding.relations.hardware.DeviceBoardJson;
import co.edu.eafit.code.binder.api.json.component.HardwareComponentJson;
import co.edu.eafit.code.binder.api.json.hardware.BoardJson;
import co.edu.eafit.code.binder.api.json.hardware.DeviceJson;
import co.edu.eafit.code.binder.api.json.hardware.PinJson;
import co.edu.eafit.code.binder.api.json.hardware.PortJson;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.binder.api.structure.dynamic.DeviceBoardPinData;
import co.edu.eafit.code.binder.api.type.PortComponentType;
import co.edu.eafit.code.binder.model.library.SketchHeaderKeypadLibrary;
import co.edu.eafit.code.binder.model.library.SketchHeaderLiquidCrystalLibrary;
import co.edu.eafit.code.binder.model.variables.SketchKeypadVariable;
import co.edu.eafit.code.binder.model.variables.SketchLiquidCrystalVariable;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Board;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preprocessor.SketchDefineDirective;
import co.edu.eafit.code.generator.metamodel.arduino.classes.type.PinMode;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class HardwareProcessor extends Processor<HardwareComponentJson> {

    private Board board;

    private LinkedList<BoardJson> boardJsons;
    private LinkedList<DeviceJson> deviceJsons;

    private LinkedList<DeviceBoardPinData> deviceBoardConnections;

    public HardwareProcessor(StructureModifier structureModifier) {
        super(structureModifier);

        this.boardJsons = new LinkedList<>();
        this.deviceJsons = new LinkedList<>();
        this.deviceBoardConnections = new LinkedList<>();

    }

    @Override
    public void compose(List<HardwareComponentJson> componentJson, ArduinoUnoBoard board) {

        this.board = board;

        for (HardwareComponentJson hardwareComponentJson : componentJson) {
            if (hardwareComponentJson.isBoard())
                boardJsons.add(hardwareComponentJson.getBoard());
            if (hardwareComponentJson.isDevice())
                deviceJsons.add(hardwareComponentJson.getDevice());
        }

        for (HardwareComponentJson hardwareComponentJson : componentJson) {
            if (hardwareComponentJson.isRelationshipDeviceBoard()) {

                DeviceBoardJson relation = hardwareComponentJson.getRelationshipDeviceBoard();

                DeviceJson relationDevice = null;
                PinJson pinDevice = null;

                for (DeviceJson deviceJson : deviceJsons)
                    for (PinJson pinJson : deviceJson.getPins())
                        if (pinJson.getId().equals(relation.getPinDevice())) {
                            pinDevice = pinJson;
                            relationDevice = deviceJson;
                            break;
                        }

                PinJson pinBoard = null;
                for (BoardJson boardJson : boardJsons)
                    for (PinJson pinJson : boardJson.getPins())
                        if (pinJson.getId().equals(relation.getPinBoard())) {
                            pinBoard = pinJson;
                            break;
                        }

                DeviceBoardPinData deviceBoardPinData = new DeviceBoardPinData(board, relationDevice, pinDevice, pinBoard);
                deviceBoardConnections.add(deviceBoardPinData);

            }
        }
    }

    public void mapSketchPortOld(PortJson port, ArduinoUnoBoard board) {

        PortComponentType type = port.getType();
        PinMode mode = type.isSensor() ? PinMode.INPUT : PinMode.OUTPUT;

        if (port.getSubType() == null || port.getSubType().equals("Simple")) {

            if (port.getPins().length < 1)
                return;

            if (type.isDigital())
                getStructure().put(port.getId(), board.useDigitalPin(port.getPins()[0], port.getLabel(), mode));
            else
                getStructure().put(port.getId(), board.useAnalogPin(port.getPins()[0], port.getLabel(), mode));

        } else if (port.getSubType().equals("LiquidCrystal")) {

            if (port.getPins().length >= 7) {

                String[] pins = port.getPins();

                SketchHeaderLiquidCrystalLibrary sketchHeaderLiquidCrystalLibrary = new SketchHeaderLiquidCrystalLibrary();
                SketchLiquidCrystalVariable sketchLiquidCrystalVariable = new SketchLiquidCrystalVariable(port.getLabel(), false, false);

                String port_prefix = port.getLabel().toUpperCase();

                SketchDefineDirective<String> lcd_register_select = new SketchDefineDirective(port_prefix + "_READ_WRITE", pins[0]);
                SketchDefineDirective<String> lcd_read_write = new SketchDefineDirective(port_prefix + "_REGISTER_SELECT", pins[1]);
                SketchDefineDirective<String> lcd_enable = new SketchDefineDirective(port_prefix + "_ENABLE", pins[2]);

                SketchDefineDirective<String> lcd_db4 = new SketchDefineDirective(port_prefix + "_DB4", pins[3]);
                SketchDefineDirective<String> lcd_db5 = new SketchDefineDirective(port_prefix + "_DB5", pins[4]);
                SketchDefineDirective<String> lcd_db6 = new SketchDefineDirective(port_prefix + "_DB6", pins[5]);
                SketchDefineDirective<String> lcd_db7 = new SketchDefineDirective(port_prefix + "_DB7", pins[6]);

                sketchLiquidCrystalVariable.setVariables(lcd_register_select, lcd_read_write, lcd_enable, lcd_db4, lcd_db5, lcd_db6, lcd_db7);

                board.getSketch().addLibraryVariable(sketchLiquidCrystalVariable);

                board.getSketch().addPreprocessorDirective(lcd_register_select);
                board.getSketch().addPreprocessorDirective(lcd_read_write);
                board.getSketch().addPreprocessorDirective(lcd_enable);

                board.getSketch().addPreprocessorDirective(lcd_db4);
                board.getSketch().addPreprocessorDirective(lcd_db5);
                board.getSketch().addPreprocessorDirective(lcd_db6);
                board.getSketch().addPreprocessorDirective(lcd_db7);

                board.getSketch().addPreprocessorDirective(sketchHeaderLiquidCrystalLibrary);
                getStructure().getOrPut(port.getId(), sketchLiquidCrystalVariable);

            }

            return;

        } else if (port.getSubType().equals("Pwm")) {

            return;

        } else if (port.getSubType().equals("Keypad")) {

            String[] pins = port.getPins();

            if (pins.length != 8)
                return;

            SketchHeaderKeypadLibrary sketchHeaderKeypadLibrary = new SketchHeaderKeypadLibrary();
            SketchKeypadVariable sketchKeypadVariable = new SketchKeypadVariable(port.getLabel(), false, false, getStructure());

            sketchKeypadVariable.setRows(pins[4], pins[5], pins[6], pins[7]);
            sketchKeypadVariable.setColumns(pins[0], pins[1], pins[2], pins[3]);

            board.getSketch().addPreprocessorDirective(sketchHeaderKeypadLibrary);
            board.getSketch().addLibraryVariable(sketchKeypadVariable);

            getStructure().getOrPut(port.getId(), sketchKeypadVariable);
            return;

        }

    }

}
