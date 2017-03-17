
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.*;
import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;
import org.neogroup.sparks.resources.ResourcePropertyFilter;
import org.neogroup.sparks.resources.commands.*;
import org.neogroup.sparks.templating.TemplatesManager;
import org.neogroup.util.BundlesManager;
import org.neogroup.util.Properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Processor <C extends Command, R extends Object> {

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void onStart () {}

    public void onStop () {}

    public Properties getProperties() {
        return applicationContext.getProperties();
    }

    public Logger getLogger() {
        return applicationContext.getLogger();
    }

    public BundlesManager getBundlesManager() {
        return applicationContext.getBundlesManager();
    }

    public TemplatesManager getTemplatesManager() {
        return applicationContext.getTemplatesManager();
    }

    protected <R extends Resource> R createResource (R resource) {
        return createResource(resource, null);
    }

    protected <R extends Resource> R createResource (R resource, Map<String,Object> params) {
        CreateResourcesCommand command = new CreateResourcesCommand(resource.getClass(), resource);
        command.setParameters(params);
        return ((List<R>)applicationContext.processCommand(command)).get(0);
    }

    protected <R extends Resource> R updateResource (R resource) {
        return updateResource(resource, null);
    }

    protected <R extends Resource> R updateResource (R resource, Map<String,Object> params) {
        UpdateResourcesCommand command = new UpdateResourcesCommand(resource.getClass(), resource);
        command.setParameters(params);
        return ((List<R>)applicationContext.processCommand(command)).get(0);
    }

    protected <R extends Resource> R deleteResource (R resource) {
        return deleteResource(resource, null);
    }

    protected <R extends Resource> R deleteResource (R resource, Map<String,Object> params) {
        DeleteResourcesCommand command = new DeleteResourcesCommand(resource.getClass(), resource);
        command.setParameters(params);
        return ((List<R>)applicationContext.processCommand(command)).get(0);
    }

    protected <I extends Object, R extends Resource<I>> R retrieveResource(Class<? extends R> resourceClass, I id) {
        return retrieveResource(resourceClass, id, null);
    }

    protected <I extends Object, R extends Resource<I>> R retrieveResource(Class<? extends R> resourceClass, I id, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(ResourcesCommand.START_PARAMETER, 0);
        params.put(ResourcesCommand.LIMIT_PARAMETER, 1);
        List<R> resources = retrieveResources(resourceClass, new ResourcePropertyFilter("id", id), null, params);
        R resource = null;
        if (resources != null && !resources.isEmpty()) {
            resource = resources.get(0);
        }
        return resource;
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
        RetrieveResourcesCommand command = new RetrieveResourcesCommand(resourceClass);
        command.setFilters(filters);
        command.setOrders(orders);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    public abstract R process (C command) throws ProcessorException;
}