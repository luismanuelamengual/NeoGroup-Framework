
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

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Processor of commands for the sparks framework
 * @param <C> Command type
 * @param <R> Response type
 */
public abstract class Processor <C extends Command, R extends Object> {

    private ApplicationContext applicationContext;

    /**
     * Set the application context
     * @param applicationContext context
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Get the application context
     * @return ApplicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Get a property from the context
     * @param property name of property
     * @return value of the property
     */
    protected Object getProperty(String property) {
        return applicationContext.getProperty(property);
    }

    /**
     * Indicates if a property exists
     * @param property name of property
     * @return boolean
     */
    protected boolean hasProperty(String property) {
        return applicationContext.hasProperty(property);
    }

    /**
     * Get the logger of the context
     * @return Logger
     */
    protected Logger getLogger() {
        return applicationContext.getLogger();
    }

    /**
     * Get a bundle string with the default locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    protected String getString(String key, Object... args) {
        return applicationContext.getString(key, args);
    }

    /**
     * Get a bundle string
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    protected String getString(Locale locale, String key, Object... args) {
        return applicationContext.getString(locale, key, args);
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    protected String getString(String bundleName, String key, Object... args) {
        return applicationContext.getString(bundleName, key, args);
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    protected String getString(String bundleName, Locale locale, String key, Object... args) {
        return applicationContext.getString(bundleName, locale, key, args);
    }

    /**
     * Get the default data source.
     * @return DataSource Data source
     */
    protected DataSource getDataSource() {
        return applicationContext.getDataSource();
    }

    /**
     * Get a data source
     * @param dataSourceName name of the data source
     * @return DataSource Data source
     */
    protected DataSource getDataSource(String dataSourceName) {
        return applicationContext.getDataSource(dataSourceName);
    }

    /**
     * Creates a view
     * @param viewFactoryName name of the view factory
     * @param viewName name of the view
     * @return View created view
     */
    protected View createView (String viewFactoryName, String viewName) {
        return applicationContext.createView(viewName);
    }

    /**
     * Creates a view
     * @param viewName name of the view
     * @return View created view
     */
    protected View createView (String viewName) {
        return applicationContext.createView(viewName);
    }

    /**
     * Process a command
     * @param command command to be processed
     * @param <R> Response type
     * @return casted response
     */
    protected <R> R processCommand(Command command) {
        return applicationContext.processCommand(command);
    }

    /**
     * Creates an entity
     * @param entity entity to be created
     * @param <E> Entity type
     * @return entity created
     */
    protected <E extends Entity> E createEntity(E entity) {
        return createEntity(entity, null);
    }

    /**
     * Creates an entity
     * @param entity entity to be created
     * @param params parameters to create the entity
     * @param <E> Entity type
     * @return entity created
     */
    protected <E extends Entity> E createEntity(E entity, Map<String,Object> params) {
        CreateEntitiesCommand command = new CreateEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<E>)applicationContext.processCommand(command)).get(0);
    }

    /**
     * Updates an entity
     * @param entity entity to be updated
     * @param <E> Entity type
     * @return entity updated
     */
    protected <E extends Entity> E updateEntity(E entity) {
        return updateEntity(entity, null);
    }

    /**
     * Updates an entity
     * @param entity entity to be updated
     * @param params parameters to update the entity
     * @param <E> Entity type
     * @return entity updated
     */
    protected <E extends Entity> E updateEntity(E entity, Map<String,Object> params) {
        UpdateEntitiesCommand command = new UpdateEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<E>)applicationContext.processCommand(command)).get(0);
    }

    /**
     * Deletes an entity
     * @param entity entity to be deleted
     * @param <E> Entity type
     * @return deleted entity
     */
    protected <E extends Entity> E deleteEntity(E entity) {
        return deleteEntity(entity, null);
    }

    /**
     * Deletes an entity
     * @param entity entity to be deleted
     * @param params parameters to delete the entity
     * @param <E> Entity type
     * @return deleted entity
     */
    protected <E extends Entity> E deleteEntity(E entity, Map<String,Object> params) {
        DeleteEntitiesCommand command = new DeleteEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<E>)applicationContext.processCommand(command)).get(0);
    }

    /**
     * Retrieves an entity
     * @param entityClass entity class
     * @param id id of the entity
     * @param <I> Id type
     * @param <E> Entity type
     * @return entity
     */
    protected <I extends Object, E extends Entity<I>> E retrieveEntity(Class<? extends E> entityClass, I id) {
        return retrieveEntity(entityClass, id, null);
    }

    /**
     * Retrieves an entity
     * @param entityClass entity class
     * @param id id of the entity
     * @param params parameters to retrieve the entity
     * @param <I> Id type
     * @param <E> Entity type
     * @return entity
     */
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

    /**
     * Retrieve entities
     * @param entityClass entity class
     * @param <E> Entity type
     * @return list of entities
     */
    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass) {
        return retrieveEntities(entityClass, new EntityQuery());
    }

    /**
     * Retrieve entities
     * @param entityClass entity class
     * @param query query to get entities
     * @param <E> Entity type
     * @return list of entities
     */
    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass, EntityQuery query) {
        return retrieveEntities(entityClass, query, null);
    }

    /**
     * Retrieve entities
     * @param entityClass entity class
     * @param params parameters to retrieve entities
     * @param <E> Entity type
     * @return list of entities
     */
    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass, Map<String, Object> params) {
        return retrieveEntities(entityClass, new EntityQuery(), params);
    }

    /**
     * Retrieve entities
     * @param entityClass entity class
     * @param query Query to get entities
     * @param params parameters to retrieve entities
     * @param <E> Entity type
     * @return list of entities
     */
    protected <E extends Entity> List<E> retrieveEntities(Class<? extends E> entityClass, EntityQuery query, Map<String, Object> params) {
        RetrieveEntitiesCommand command = new RetrieveEntitiesCommand(entityClass, query);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    /**
     * Method that is executed when the processor is created
     * and an application context is assigned to it
     */
    public void initialize () {}

    /**
     * Processes a command
     * @param command command to process
     * @return R casted response
     * @throws ProcessorException
     */
    public abstract R process (C command) throws ProcessorException;
}