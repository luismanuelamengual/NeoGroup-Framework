
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityQuery;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CRUDProcessor<E extends Entity> extends Processor<CRUDCommand,List<E>> {

    protected Class<? extends E> entityClass;

    public CRUDProcessor() {

        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            entityClass = (Class<? extends E>) fieldArgTypes[0];
        }
    }

    public Class<? extends E> getEntityClass() {
        return entityClass;
    }

    @Override
    public final List<E> process(CRUDCommand command) throws ProcessorException {

        List<E> result = null;
        if (command instanceof RetrieveEntitiesCommand) {
            RetrieveEntitiesCommand retrieveResourcesCommand = (RetrieveEntitiesCommand)command;
            result = retrieve(retrieveResourcesCommand.getQuery(), retrieveResourcesCommand.getParameters());
        }
        else if (command instanceof ModifyEntitiesCommand) {
            ModifyEntitiesCommand modifyResourcesCommand = (ModifyEntitiesCommand)command;
            List<E> resources = modifyResourcesCommand.getEntities();
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

    protected abstract E create (E entity, Map<String,Object> params);
    protected abstract E update (E entity, Map<String,Object> params);
    protected abstract E delete (E entity, Map<String,Object> params);
    protected abstract List<E> retrieve (EntityQuery query, Map<String,Object> params);
}
