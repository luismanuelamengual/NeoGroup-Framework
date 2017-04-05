
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.*;
import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.models.Model;
import org.neogroup.sparks.models.ModelFilter;
import org.neogroup.sparks.models.ModelSorter;
import org.neogroup.sparks.models.ModelPropertyFilter;
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

    protected <M extends Model> M createEntity(M entity) {
        return createEntity(entity, null);
    }

    protected <M extends Model> M createEntity(M entity, Map<String,Object> params) {
        CreateEntitiesCommand command = new CreateEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<M>)applicationContext.processCommand(command)).get(0);
    }

    protected <M extends Model> M updateEntity(M entity) {
        return updateEntity(entity, null);
    }

    protected <M extends Model> M updateEntity(M entity, Map<String,Object> params) {
        UpdateEntitiesCommand command = new UpdateEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<M>)applicationContext.processCommand(command)).get(0);
    }

    protected <M extends Model> M deleteEntity(M entity) {
        return deleteEntity(entity, null);
    }

    protected <M extends Model> M deleteEntity(M entity, Map<String,Object> params) {
        DeleteEntitiesCommand command = new DeleteEntitiesCommand(entity.getClass(), entity);
        command.setParameters(params);
        return ((List<M>)applicationContext.processCommand(command)).get(0);
    }

    protected <I extends Object, M extends Model<I>> M retrieveEntity(Class<? extends M> modelClass, I id) {
        return retrieveEntity(modelClass, id, null);
    }

    protected <I extends Object, M extends Model<I>> M retrieveEntity(Class<? extends M> modelClass, I id, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(CRUDCommand.START_PARAMETER, 0);
        params.put(CRUDCommand.LIMIT_PARAMETER, 1);
        List<M> resources = retrieveEntities(modelClass, new ModelPropertyFilter("id", id), null, params);
        M resource = null;
        if (resources != null && !resources.isEmpty()) {
            resource = resources.get(0);
        }
        return resource;
    }

    protected <M extends Model> List<M> retrieveEntities(Class<? extends M> modelClass) {
        return retrieveEntities(modelClass, null);
    }

    protected <M extends Model> List<M> retrieveEntities(Class<? extends M> modelClass, ModelFilter filters) {
        return retrieveEntities(modelClass, filters, null);
    }

    protected <M extends Model> List<M> retrieveEntities(Class<? extends M> modelClass, ModelFilter filters, List<ModelSorter> sorters) {
        return retrieveEntities(modelClass, filters, sorters, null);
    }

    protected <M extends Model> List<M> retrieveEntities(Class<? extends M> modelClass, ModelFilter filters, List<ModelSorter> sorters, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put(CRUDCommand.START_PARAMETER, start);
        params.put(CRUDCommand.LIMIT_PARAMETER, limit);
        return retrieveEntities(modelClass, filters, sorters, params);
    }

    protected <M extends Model> List<M> retrieveEntities(Class<? extends M> modelClass, ModelFilter filters, List<ModelSorter> sorters, Map<String, Object> params) {
        RetrieveEntitiesCommand command = new RetrieveEntitiesCommand(modelClass);
        command.setFilters(filters);
        command.setOrders(sorters);
        command.setParameters(params);
        return applicationContext.processCommand(command);
    }

    public abstract R process (C command) throws ProcessorException;
}