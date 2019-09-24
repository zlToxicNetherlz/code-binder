package co.edu.eafit.code.binder.api.processors;

import co.edu.eafit.code.binder.api.json.binding.instruction.VariableJson;
import co.edu.eafit.code.binder.api.json.component.BindingComponentJson;
import co.edu.eafit.code.binder.api.structure.BindingComponent;
import co.edu.eafit.code.binder.api.structure.StructureModifier;
import co.edu.eafit.code.generator.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;

import java.util.LinkedList;
import java.util.List;

public class BindingProcessor extends Processor<BindingComponentJson> {

    public BindingProcessor(StructureModifier structureModifier) {
        super(structureModifier);
    }

    @Override
    public void compose(List<BindingComponentJson> componentJson, ArduinoUnoBoard board) {

        LinkedList<BindingComponent<?>> bindingComponents = getBindingComponents(componentJson);

        for (BindingComponent<?> bindingComponent : bindingComponents) {

            switch (bindingComponent.getType()) {

                case VARIABLE:

                    VariableJson json = bindingComponent.getGenericComponent();
                    System.out.println(json.getLabel());

                    break;

                default:

                    break;

            }

        }

    }

    public LinkedList<BindingComponent<?>> getBindingComponents(List<BindingComponentJson> bindingComponentJsons) {

        LinkedList<BindingComponent<?>> bindingComponents = new LinkedList<>();

        for (BindingComponentJson bindingComponentJson : bindingComponentJsons)
            bindingComponents.add(bindingComponentJson.getComponent());

        return bindingComponents;

    }

}
