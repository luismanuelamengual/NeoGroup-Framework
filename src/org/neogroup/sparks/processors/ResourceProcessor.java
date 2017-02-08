
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.*;
import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ResourceProcessor<R extends Resource> extends Processor<ResourcesCommand,Object> {

    @Override
    public Object process(ResourcesCommand command) throws ProcessorException {

        Object result = null;
        if (command instanceof RetrieveResourcesCommand) {
            RetrieveResourcesCommand retrieveResourcesCommand = (RetrieveResourcesCommand)command;
            result = retrieve(retrieveResourcesCommand.getFilters(), retrieveResourcesCommand.getOrders(), retrieveResourcesCommand.getParameters());
        }
        else if (command instanceof ModifyResourcesCommand) {
            ModifyResourcesCommand modifyResourcesCommand = (ModifyResourcesCommand)command;
            List<R> resources = modifyResourcesCommand.getResources();
            List<R> resultArray = new ArrayList<R>();
            if (command instanceof CreateResourcesCommand) {
                for (R resource : resources) {
                    resultArray.add((R)create(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof UpdateResourcesCommand) {
                for (R resource : resources) {
                    resultArray.add((R)update(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof DeleteResourcesCommand) {
                for (R resource : resources) {
                    resultArray.add((R)delete(resource, modifyResourcesCommand.getParameters()));
                }
            }
            result = resultArray;
        }
        return result;
    }

    protected abstract R create (R resource, Map<String,Object> params);
    protected abstract R update (R resource, Map<String,Object> params);
    protected abstract R delete (R resource, Map<String,Object> params);
    protected abstract List<R> retrieve (ResourceFilter filters, List<ResourceOrder> orders, Map<String,Object> params);
}
