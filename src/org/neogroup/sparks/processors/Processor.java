
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.*;
import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Processor <C extends Command, R extends Object> {

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    protected <R extends Resource> R createResource (R resource) {
        return createResource(resource, null);
    }

    protected <R extends Resource> R createResource (R resource, Map<String,Object> params) {
        CreateResourcesCommand command = new CreateResourcesCommand();
        command.addResource(resource);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    protected <R extends Resource> R updateResource (R resource) {
        return updateResource(resource, null);
    }

    protected <R extends Resource> R updateResource (R resource, Map<String,Object> params) {
        UpdateResourcesCommand command = new UpdateResourcesCommand();
        command.addResource(resource);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    protected <R extends Resource> R deleteResource (R resource) {
        return deleteResource(resource, null);
    }

    protected <R extends Resource> R deleteResource (R resource, Map<String,Object> params) {
        DeleteResourcesCommand command = new DeleteResourcesCommand();
        command.addResource(resource);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    protected <R extends Resource> List<R> retrieveResources (Class<? extends R> resourceClass) {
        return retrieveResources(resourceClass, null);
    }

    protected <R extends Resource> List<R> retrieveResources (Class<? extends R> resourceClass, ResourceFilter filters) {
        return retrieveResources(resourceClass, filters, null);
    }

    protected <R extends Resource> List<R> retrieveResources (Class<? extends R> resourceClass, ResourceFilter filters, List<ResourceOrder> orders) {
        return retrieveResources(resourceClass, filters, orders, null);
    }

    protected <R extends Resource> List<R> retrieveResources (Class<? extends R> resourceClass, ResourceFilter filters, List<ResourceOrder> orders, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put(ResourcesCommand.START_PARAMETER, start);
        params.put(ResourcesCommand.LIMIT_PARAMETER, limit);
        return retrieveResources(resourceClass, filters, orders, params);
    }

    protected <R extends Resource> List<R> retrieveResources (Class<? extends R> resourceClass, ResourceFilter filters, List<ResourceOrder> orders, Map<String, Object> params) {
        RetrieveResourcesCommand command = new RetrieveResourcesCommand();
        command.setResourceClass(resourceClass);
        command.setFilters(filters);
        command.setOrders(orders);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    public abstract R process (C command) throws ProcessorException;
}