
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.ResourcesCommand;

public class ResourceSelectorProcessor extends SelectorProcessor<ResourcesCommand, ResourceProcessor> {

    @Override
    public boolean registerProcessorCandidate(ResourceProcessor processor) {
        return false;
    }

    @Override
    public ResourceProcessor getProcessor(ResourcesCommand command) {
        return null;
    }
}
