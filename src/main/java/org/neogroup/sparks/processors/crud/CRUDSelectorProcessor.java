
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.SelectorProcessor;
import org.neogroup.sparks.model.Entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@ProcessorComponent(commands = {
        CreateEntitiesCommand.class,
        RetrieveEntitiesCommand.class,
        UpdateEntitiesCommand.class,
        DeleteEntitiesCommand.class
})
public class CRUDSelectorProcessor extends SelectorProcessor<CRUDCommand, CRUDProcessor> {

    private Map<Class<? extends Entity>, Class<? extends CRUDProcessor>> processorsbyModel;

    public CRUDSelectorProcessor() {
        this.processorsbyModel = new HashMap<>();
    }

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

    @Override
    protected Class<? extends CRUDProcessor> getProcessorClass(CRUDCommand command) {
        return processorsbyModel.get(command.getEntityClass());
    }
}
