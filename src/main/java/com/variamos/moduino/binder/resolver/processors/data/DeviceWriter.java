package com.variamos.moduino.binder.resolver.processors.data;

import com.variamos.moduino.binder.api.processors.BindingProcessor;
import com.variamos.moduino.binder.api.processors.HardwareProcessor;
import lombok.Getter;

@Getter
public class DeviceWriter {

    private BindingProcessor bindingProcessor;
    private HardwareProcessor hardwareProcessor;

    public DeviceWriter(BindingProcessor bindingProcessor, HardwareProcessor hardwareProcessor) {
        this.hardwareProcessor = hardwareProcessor;
        this.bindingProcessor = bindingProcessor;
    }

}
