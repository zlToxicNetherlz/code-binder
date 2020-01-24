package co.edu.eafit.code.binder.api.json.control;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SetPointJson {

    private String id;
    private String label;
    private String type;
    private String valueSetpoint;
    private String time;

}
