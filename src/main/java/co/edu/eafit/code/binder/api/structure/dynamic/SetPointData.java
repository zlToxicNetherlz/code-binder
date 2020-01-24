package co.edu.eafit.code.binder.api.structure.dynamic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SetPointData {

    private String id;
    private String label;

    private double valueSetPoint;
    private float time;

}
