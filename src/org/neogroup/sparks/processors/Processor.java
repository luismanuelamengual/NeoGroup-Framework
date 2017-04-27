
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.*;
import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.model.*;
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

    protected <I extends Object, E extends Entity<I>> E retrieveEntity(Class<? extends E> entityClass, I id) {
        return retrieveEntity(entityClass, id, null);
    }

    protected <I extends Object, E extends Entity<I>> E retrieveEntity(Class<? extends E> entityClass, I id, Map<String, Object> params) {
        EntityQuery query = new EntityQuery();
        query.addFilter("id", id);
        RetrieveEntitiesCommand command = new RetrieveEntitiesCommand(entityClass, query);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass) {
        return retrieveEntities(entityClass, new EntityQuery());
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass, EntityQuery query) {
        return retrieveEntities(entityClass, query, null);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass, Map<String, Object> params) {
        return retrieveEntities(entityClass, new EntityQuery(), params);
    }

    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass, EntityQuery query, Map<String, Object> params) {
        RetrieveEntitiesCommand command = new RetrieveEntitiesCommand(entityClass, query);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    public abstract R process (C command) throws ProcessorException;
}