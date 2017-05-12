
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.commands.crud.CreateEntitiesCommand;
import org.neogroup.sparks.commands.crud.DeleteEntitiesCommand;
import org.neogroup.sparks.commands.crud.RetrieveEntitiesCommand;
import org.neogroup.sparks.commands.crud.UpdateEntitiesCommand;
import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityQuery;
import org.neogroup.sparks.model.annotations.Id;
import org.neogroup.sparks.views.View;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Processor <C extends Command, R extends Object> {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Object getProperty(String property) {
        return applicationContext.getProperty(property);
    }

    public boolean hasProperty(String property) {
        return applicationContext.hasProperty(property);
    }

    protected Logger getLogger() {
        return applicationContext.getLogger();
    }

    protected View createView (String viewFactoryName, String viewName) {
        return applicationContext.createView(viewName);
    }

    protected View createView (String viewName) {
        return applicationContext.createView(viewName);
    }

    public <R> R processCommand(Command command) {
        return applicationContext.processCommand(command);
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

        String idProperty = null;
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                idProperty = field.getName();
                break;
            }
        }

        EntityQuery query = new EntityQuery();
        query.addFilter(idProperty, id);
        query.setLimit(1);
        RetrieveEntitiesCommand command = new RetrieveEntitiesCommand(entityClass, query);
        command.setParameters(params);
        List<E> entities = applicationContext.processCommand(command);
        return entities.get(0);
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

    public void initialize () {}

    public abstract R process (C command) throws ProcessorException;
}