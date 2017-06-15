
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.Module;
import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.processors.*;

import java.util.*;

/**
 * Processor that handles the crud commands
 */
@ProcessorCommands({
        CreateEntitiesCommand.class,
        RetrieveEntitiesCommand.class,
        UpdateEntitiesCommand.class,
        DeleteEntitiesCommand.class
})
public class CRUDCommandProcessor extends CommandProcessor<CRUDCommand> {

    private final Map<Class<? extends Entity>, CRUDProcessor> processorsbyEntity;

    /**
     * Constructor for the crud command processor
     */
    public CRUDCommandProcessor() {
        processorsbyEntity = new HashMap<>();
    }

    /**
     * Registers all crud processors
     */
    @Override
    public void start() {

        //Retrieve all processors visible from this module/application
        Set<Processor> registeredProcessors = new HashSet<>();
        registeredProcessors.addAll(getApplicationContext().getRegisteredProcessors());
        if (getApplicationContext() instanceof Module) {
            Module module = (Module)getApplicationContext();
            registeredProcessors.addAll(module.getApplication().getRegisteredProcessors());
        }
        else if (getApplicationContext() instanceof Application) {
            Application application = (Application)getApplicationContext();
            for (Module module : application.getModules()) {
                registeredProcessors.addAll(module.getRegisteredProcessors());
            }
        }

        //Register crud processors
        for (Processor processor : registeredProcessors) {
            if (processor instanceof CRUDProcessor) {
                CRUDProcessor crudProcessor = (CRUDProcessor)processor;
                processorsbyEntity.put(crudProcessor.getEntityClass(), crudProcessor);
            }
        }
    }

    /**
     * Un-registers all crud processors
     */
    @Override
    public void stop() {
        processorsbyEntity.clear();
    }

    /**
     * Processes a crud command
     * @param command command to process
     * @return result of the command
     * @throws ProcessorException
     */
    @Override
    public Object process(CRUDCommand command) throws ProcessorException {

        CRUDProcessor crudProcessor = processorsbyEntity.get(command.getEntityClass());
        if (crudProcessor == null) {
            throw new ProcessorNotFoundException("Processor not found for entity \"" + command.getEntityClass() + "\"");
        }

        Collection<Entity> result = null;
        if (command instanceof RetrieveEntitiesCommand) {
            RetrieveEntitiesCommand retrieveResourcesCommand = (RetrieveEntitiesCommand)command;
            result = crudProcessor.retrieve(retrieveResourcesCommand.getQuery(), retrieveResourcesCommand.getParameters());
        }
        else if (command instanceof ModifyEntitiesCommand) {
            ModifyEntitiesCommand modifyResourcesCommand = (ModifyEntitiesCommand)command;
            Collection<Entity> resources = modifyResourcesCommand.getEntities();
            result = new ArrayList<Entity>();
            if (command instanceof CreateEntitiesCommand) {
                for (Entity resource : resources) {
                    result.add(crudProcessor.create(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof UpdateEntitiesCommand) {
                for (Entity resource : resources) {
                    result.add(crudProcessor.update(resource, modifyResourcesCommand.getParameters()));
                }
            }
            else if (command instanceof DeleteEntitiesCommand) {
                for (Entity resource : resources) {
                    result.add(crudProcessor.delete(resource, modifyResourcesCommand.getParameters()));
                }
            }
        }
        return result;
    }
}
