package com.variamos.moduino.binder.api.json.hardware;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BoardJson {

    private String id;
    private String label;
    private String type;
    private PinJson[] pins;

}
