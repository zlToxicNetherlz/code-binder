package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.hardware.DeviceJson;
import co.edu.eafit.code.binder.api.json.hardware.PinJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Board;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.Pin;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DeviceBoardPinData {

    private DeviceJson deviceJson;
    private PinJson devicePin, boardPin;

    private Board board;

    @Setter
    private boolean used;

    @Setter
    private Pin pin;

    public DeviceBoardPinData(Board board, DeviceJson deviceJson, PinJson devicePin, PinJson boardPin) {

        this.deviceJson = deviceJson;
        this.board = board;

        this.devicePin = devicePin;
        this.boardPin = boardPin;

    }

}
