
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.model.Entity;

import java.util.Map;

public abstract class CRUDCommand<E extends Entity> extends Command {

    public static final String START_PARAMETER = "start";
    public static final String LIMIT_PARAMETER = "limit";

    private final Class<? extends E> modelClass;
    private Map<String,Object> parameters;

    public CRUDCommand(Class<? extends E> modelClass) {
        this.modelClass = modelClass;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Class<? extends E> getModelClass() {
        return modelClass;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter (String parameter, Object value) {
        parameters.put(parameter, value);
    }
}
