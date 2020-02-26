package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.actions.ReadActionJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReadActionData extends ActionData {

    private ReadActionJson readActionJson;

    public ReadActionData(ReadActionJson readActionJson) {
        this.readActionJson = readActionJson;
    }

    public ReadActionJson getJson() {
        return readActionJson;
    }

}
