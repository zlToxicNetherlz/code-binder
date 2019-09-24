package co.edu.eafit.code.binder.api.processors.loggers;

import lombok.Getter;

import java.util.LinkedList;

@Getter
public class ProcessorLogger {

    private LinkedList<String> informations;
    private LinkedList<String> warnings;
    private LinkedList<String> errors;

    public ProcessorLogger() {

        this.informations = new LinkedList<>();
        this.warnings = new LinkedList<>();
        this.errors = new LinkedList<>();

    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public LinkedList<String> getAll() {

        final LinkedList<String> logging = new LinkedList<>();

        informations.forEach(i -> logging.add(i));
        warnings.forEach(w -> logging.add(w));
        errors.forEach(e -> logging.add(e));

        return logging;

    }

}
