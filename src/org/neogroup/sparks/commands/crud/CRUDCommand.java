
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.model.Entity;

import java.util.Map;

public abstract class CRUDCommand<E extends Entity> extends Command {

    public static final String START_PARAMETER = "start";
    public static final String LIMIT_PARAMETER = "limit";

    private final Class<? extends E> entityClass;
    private Map<String,Object> parameters;

    public CRUDCommand(Class<? extends E> entityClass) {
        this.entityClass = entityClass;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Class<? extends E> getEntityClass() {
        return entityClass;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter (String parameter, Object value) {
        parameters.put(parameter, value);
    }
}
