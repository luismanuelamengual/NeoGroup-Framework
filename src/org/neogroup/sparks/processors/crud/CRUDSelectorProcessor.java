
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.commands.crud.*;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.SelectorProcessor;
import org.neogroup.sparks.model.Entity;

import java.util.HashMap;
import java.util.Map;

@ProcessorComponent(commands = {
        CreateEntitiesCommand.class,
        RetrieveEntitiesCommand.class,
        UpdateEntitiesCommand.class,
        DeleteEntitiesCommand.class
})
public class CRUDSelectorProcessor extends SelectorProcessor<CRUDCommand, CRUDProcessor> {

    private Map<Class<? extends Entity>, CRUDProcessor> processorsbyModel;

    public CRUDSelectorProcessor() {
        this.processorsbyModel = new HashMap<>();
    }

    @Override
    public boolean registerProcessorCandidate(CRUDProcessor processor) {
        processorsbyModel.put(processor.getModelClass(), processor);
        return true;
    }

    @Override
    public CRUDProcessor getProcessor(CRUDCommand command) {
        return processorsbyModel.get(command.getModelClass());
    }
}
