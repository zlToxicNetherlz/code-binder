package co.edu.eafit.code.binder.api.json.binding.relations.base;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommonExecutionActionJson {

    private String action;
    private String execution;
    private int time;

}
