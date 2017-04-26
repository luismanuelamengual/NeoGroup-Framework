
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.*;
import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityFilter;
import org.neogroup.sparks.model.EntitySorter;
import org.neogroup.sparks.model.EntityPropertyFilter;
import org.neogroup.sparks.views.ViewsManager;
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

    public ViewsManager getViewsManager() {
        return applicationContext.getViewsManager();
    }

    protected <E extends Entity> E createEntity(E entity) {
        return createEntity(entity, null);
    }

    protected <E extends Entity> E createEntity(E entity, Map<String,Object> params) {
        CreateEntitiesCommand command = new CreateEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<E>)applicationContext.processCommand(command)).get(0);
    }

    protected <E extends Entity> E updateEntity(E entity) {
        return updateEntity(entity, null);
    }

    protected <E extends Entity> E updateEntity(E entity, Map<String,Object> params) {
        UpdateEntitiesCommand command = new UpdateEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<E>)applicationContext.processCommand(command)).get(0);
    }

    protected <E extends Entity> E deleteEntity(E entity) {
        return deleteEntity(entity, null);
    }

    protected <E extends Entity> E deleteEntity(E entity, Map<String,Object> params) {
        DeleteEntitiesCommand command = new DeleteEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<E>)applicationContext.processCommand(command)).get(0);
    }

    protected <I extends Object, E extends Entity<I>> E retrieveEntity(Class<? extends E> modelClass, I id) {
        return retrieveEntity(modelClass, id, null);
    }

    protected <I extends Object, E extends Entity<I>> E retrieveEntity(Class<? extends E> modelClass, I id, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(CRUDCommand.START_PARAMETER, 0);
        params.put(CRUDCommand.LIMIT_PARAMETER, 1);
        List<E> resources = retrieveEntities(modelClass, new EntityPropertyFilter("id", id), null, params);
        E resource = null;
        if (resources != null && !resources.isEmpty()) {
            resource = resources.get(0);
        }
        return resource;
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> modelClass) {
        return retrieveEntities(modelClass, null);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> modelClass, EntityFilter filters) {
        return retrieveEntities(modelClass, filters, null);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> modelClass, EntityFilter filters, List<EntitySorter> sorters) {
        return retrieveEntities(modelClass, filters, sorters, null);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> modelClass, EntityFilter filters, List<EntitySorter> sorters, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put(CRUDCommand.START_PARAMETER, start);
        params.put(CRUDCommand.LIMIT_PARAMETER, limit);
        return retrieveEntities(modelClass, filters, sorters, params);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> modelClass, EntityFilter filters, List<EntitySorter> sorters, Map<String, Object> params) {
        RetrieveEntitiesCommand command = new RetrieveEntitiesCommand(modelClass);
        command.setFilters(filters);
        command.setOrders(sorters);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    public abstract R process (C command) throws ProcessorException;
}