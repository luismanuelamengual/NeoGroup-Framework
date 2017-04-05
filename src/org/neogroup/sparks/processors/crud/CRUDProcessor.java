
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorException;
import org.neogroup.sparks.models.Model;
import org.neogroup.sparks.models.ModelFilter;
import org.neogroup.sparks.models.ModelSorter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CRUDProcessor<R extends Model> extends Processor<CRUDCommand,List<R>> {

    protected Class<? extends R> modelClass;

    public CRUDProcessor() {

        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            modelClass = (Class<? extends R>) fieldArgTypes[0];
        }
    }

    public Class<? extends R> getModelClass() {
        return modelClass;
    }

    @Override
    public final List<R> process(CRUDCommand command) throws ProcessorException {

        List<R> result = null;
        if (command instanceof RetrieveEntitiesCommand) {
            RetrieveEntitiesCommand retrieveResourcesCommand = (RetrieveEntitiesCommand)command;
            result = retrieve(retrieveResourcesCommand.getFilters(), retrieveResourcesCommand.getOrders(), retrieveResourcesCommand.getParameters());
        }
        else if (command instanceof ModifyEntitiesCommand) {
            ModifyEntitiesCommand modifyResourcesCommand = (ModifyEntitiesCommand)command;
            List<R> resources = modifyResourcesCommand.getResources();
            result = new ArrayList<R>();
            if (command instanceof CreateEntitiesCommand) {
                for (R resource : resources) {
                    result.add((R)create(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof UpdateEntitiesCommand) {
                for (R resource : resources) {
                    result.add((R)update(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof DeleteEntitiesCommand) {
                for (R resource : resources) {
                    result.add((R)delete(resource, modifyResourcesCommand.getParameters()));
                }
            }
        }
        return result;
    }

    protected abstract R create (R resource, Map<String,Object> params);
    protected abstract R update (R resource, Map<String,Object> params);
    protected abstract R delete (R resource, Map<String,Object> params);
    protected abstract List<R> retrieve (ModelFilter filters, List<ModelSorter> orders, Map<String,Object> params);
}
