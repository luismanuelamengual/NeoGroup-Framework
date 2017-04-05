
package org.neogroup.sparks.resources.processors;

import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.SelectorProcessor;
import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.commands.*;

import java.util.HashMap;
import java.util.Map;

@ProcessorComponent(commands = {
        CreateResourcesCommand.class,
        RetrieveResourcesCommand.class,
        UpdateResourcesCommand.class,
        DeleteResourcesCommand.class
})
public class ResourceSelectorProcessor extends SelectorProcessor<ResourcesCommand, ResourceProcessor> {

    private Map<Class<? extends Resource>, ResourceProcessor> processorsbyResource;

    public ResourceSelectorProcessor() {
        this.processorsbyResource = new HashMap<>();
    }

    @Override
    public boolean registerProcessorCandidate(ResourceProcessor processor) {
        processorsbyResource.put(processor.getResourceClass(), processor);
        return true;
    }

    @Override
    public ResourceProcessor getProcessor(ResourcesCommand command) {
        return processorsbyResource.get(command.getResourceClass());
    }
}
