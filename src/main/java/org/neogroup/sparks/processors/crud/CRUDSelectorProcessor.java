
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.processors.ProcessorCommands;
import org.neogroup.sparks.processors.SelectorProcessor;
import org.neogroup.sparks.model.Entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Selector for crud processors
 */
@ProcessorCommands({
        CreateEntitiesCommand.class,
        RetrieveEntitiesCommand.class,
        UpdateEntitiesCommand.class,
        DeleteEntitiesCommand.class
})
public class CRUDSelectorProcessor extends SelectorProcessor<CRUDCommand, CRUDProcessor> {

    private Map<Class<? extends Entity>, Class<? extends CRUDProcessor>> processorsbyModel;

    /**
     * Constructor for the crud selector
     */
    public CRUDSelectorProcessor() {
        this.processorsbyModel = new HashMap<>();
    }

    /**
     * Registers a crud processor class
     * @param processorClass processor class
     * @return boolean
     */
    @Override
    protected boolean registerProcessorClass(Class<? extends CRUDProcessor> processorClass) {
        boolean registered = false;
        Type type = processorClass.getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            Class<? extends Entity> entityClass = (Class<? extends Entity>) fieldArgTypes[0];
            processorsbyModel.put(entityClass, processorClass);
            registered = true;
        }
        return registered;
    }

    /**
     * Get a crud processor class for a given crud command
     * @param command crud command
     * @return class of a crud processor
     */
    @Override
    protected Class<? extends CRUDProcessor> getProcessorClass(CRUDCommand command) {
        return processorsbyModel.get(command.getEntityClass());
    }
}
