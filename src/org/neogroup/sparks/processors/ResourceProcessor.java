
package org.neogroup.sparks.processors;

import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import java.util.List;
import java.util.Map;

public abstract class ResourceProcessor<R extends Resource> extends Processor {
    protected abstract R createResource (R resource);
    protected abstract R updateResource (R resource);
    protected abstract R deleteResource (R resource);
    protected abstract List<R> retrieveResources (ResourceFilter filters, List<ResourceOrder> orders, Map<String,Object> params);
}
