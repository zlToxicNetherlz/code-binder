package co.edu.eafit.code.binder.api.structure.dynamic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ControllerData {

    private String id;
    private String label;

    private double proportional;
    private double integral;
    private double derivate;

    private SummingPointData summingPointData;

    public void setSummingPoint(SummingPointData summingPoint) {
        this.summingPointData = summingPoint;
    }

    public boolean isValid() {
        return summingPointData != null && summingPointData.isValid();
    }

}
