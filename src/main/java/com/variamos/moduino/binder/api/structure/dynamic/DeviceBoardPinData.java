package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.hardware.DeviceJson;
import com.variamos.moduino.binder.api.json.hardware.PinJson;
import me.itoxic.moduino.metamodel.arduino.entries.Board;
import me.itoxic.moduino.metamodel.arduino.entries.model.Pin;
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
