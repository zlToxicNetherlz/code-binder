package co.edu.eafit.code.binder.api.json.control;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MeasuredOutputJson {

    private String id;
    private String label;
    private String type;
    private String valueSummingpoint;

}
