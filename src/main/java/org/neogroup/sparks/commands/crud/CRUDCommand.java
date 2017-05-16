
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.model.Entity;

import java.util.Map;

/**
 * Command for crud operation on entities
 * @param <E> Entity type
 */
public abstract class CRUDCommand<E extends Entity> extends Command {

    private final Class<? extends E> entityClass;
    private Map<String,Object> parameters;

    /**
     * Constructor for a CRUD command
     * @param entityClass entity class
     */
    public CRUDCommand(Class<? extends E> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Get extra parameters for the crud operation
     * @return Map of parameters
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Get the entity class
     * @return class of the entity
     */
    public Class<? extends E> getEntityClass() {
        return entityClass;
    }

    /**
     * Sets parameters for the crud operation
     * @param parameters Map of parameters
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Adds a new parameter
     * @param parameter parameter name
     * @param value parameter value
     */
    public void addParameter (String parameter, Object value) {
        parameters.put(parameter, value);
    }
}
