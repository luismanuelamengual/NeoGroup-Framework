
package org.neogroup.sparks.processors.web;

import org.neogroup.sparks.commands.web.WebCommand;
import org.neogroup.sparks.processors.ProcessorManager;

import java.util.HashMap;
import java.util.Map;

public class WebProcessorManager extends ProcessorManager<WebCommand,WebProcessor> {

    private final Map<String, Class<? extends WebProcessor>> processorsByPath;

    public WebProcessorManager() {
        this.processorsByPath = new HashMap<>();
    }

    @Override
    public void addProcessorClass(Class<? extends WebProcessor> processorClass) {
        super.addProcessorClass(processorClass);
        WebRoute route = processorClass.getAnnotation(WebRoute.class);
        if (route != null) {
            processorsByPath.put(route.path(), processorClass);
        }
    }

    @Override
    protected Class<? extends WebProcessor> selectProcessor(WebCommand command) {
        String path = WebProcessor.getProcessorPath(command.getRequest());
        return processorsByPath.get(path);
    }
}
