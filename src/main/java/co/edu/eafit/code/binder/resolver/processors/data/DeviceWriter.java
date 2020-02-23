package co.edu.eafit.code.binder.resolver.processors.data;

import co.edu.eafit.code.binder.api.processors.BindingProcessor;
import co.edu.eafit.code.binder.api.processors.HardwareProcessor;
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
