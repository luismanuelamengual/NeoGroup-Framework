
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.models.Model;

import java.util.Map;

public abstract class CRUDCommand<M extends Model> extends Command {

    public static final String START_PARAMETER = "start";
    public static final String LIMIT_PARAMETER = "limit";

    private final Class<? extends M> modelClass;
    private Map<String,Object> parameters;

    public CRUDCommand(Class<? extends M> modelClass) {
        this.modelClass = modelClass;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Class<? extends M> getModelClass() {
        return modelClass;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter (String parameter, Object value) {
        parameters.put(parameter, value);
    }
}
