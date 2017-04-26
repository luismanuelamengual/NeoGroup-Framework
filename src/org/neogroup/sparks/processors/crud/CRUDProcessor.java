
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorException;
import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityFilter;
import org.neogroup.sparks.model.EntitySorter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CRUDProcessor<E extends Entity> extends Processor<CRUDCommand,List<E>> {

    protected Class<? extends E> modelClass;

    public CRUDProcessor() {

        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            modelClass = (Class<? extends E>) fieldArgTypes[0];
        }
    }

    public Class<? extends E> getModelClass() {
        return modelClass;
    }

    @Override
    public final List<E> process(CRUDCommand command) throws ProcessorException {

        List<E> result = null;
        if (command instanceof RetrieveEntitiesCommand) {
            RetrieveEntitiesCommand retrieveResourcesCommand = (RetrieveEntitiesCommand)command;
            result = retrieve(retrieveResourcesCommand.getFilters(), retrieveResourcesCommand.getOrders(), retrieveResourcesCommand.getParameters());
        }
        else if (command instanceof ModifyEntitiesCommand) {
            ModifyEntitiesCommand modifyResourcesCommand = (ModifyEntitiesCommand)command;
            List<E> resources = modifyResourcesCommand.getResources();
            result = new ArrayList<E>();
            if (command instanceof CreateEntitiesCommand) {
                for (E resource : resources) {
                    result.add((E)create(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof UpdateEntitiesCommand) {
                for (E resource : resources) {
                    result.add((E)update(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof DeleteEntitiesCommand) {
                for (E resource : resources) {
                    result.add((E)delete(resource, modifyResourcesCommand.getParameters()));
                }
            }
        }
        return result;
    }

    protected abstract E create (E resource, Map<String,Object> params);
    protected abstract E update (E resource, Map<String,Object> params);
    protected abstract E delete (E resource, Map<String,Object> params);
    protected abstract List<E> retrieve (EntityFilter filters, List<EntitySorter> orders, Map<String,Object> params);
}
