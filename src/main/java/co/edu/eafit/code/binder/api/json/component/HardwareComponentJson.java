package co.edu.eafit.code.binder.api.json.component;

import co.edu.eafit.code.binder.api.json.ComponentJson;
import co.edu.eafit.code.binder.api.json.hardware.BoardJson;
import co.edu.eafit.code.binder.api.json.hardware.PortJson;
import lombok.ToString;

@ToString
public class HardwareComponentJson extends ComponentJson {

    private BoardJson board;
    private PortJson port;

    public boolean isBoard() {
        return board != null;
    }

    public BoardJson getBoard() {
        return board;
    }

    public PortJson getPort() {
        return port;
    }

}
