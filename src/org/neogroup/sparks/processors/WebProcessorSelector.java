
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.WebCommand;

import java.util.HashMap;
import java.util.Map;

public class WebProcessorSelector extends ProcessorSelector<WebCommand, WebProcessor>{

    private final Map<String, Class<? extends WebProcessor>> routes;

    public WebProcessorSelector() {
        this.routes = new HashMap<>();
    }

    @Override
    public void addProcessorCandidate(Class<? extends WebProcessor> processorClass) {
        WebRoute webRoute = processorClass.getAnnotation(WebRoute.class);
        if (webRoute != null) {
            routes.put(webRoute.path(), processorClass);
        }
    }

    @Override
    public Class<? extends WebProcessor> getProcessorClass(WebCommand command) {
        return routes.get(command.getWebRoute());
    }
}
