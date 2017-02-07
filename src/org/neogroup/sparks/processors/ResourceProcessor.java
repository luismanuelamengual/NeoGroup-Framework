
package org.neogroup.sparks.processors;

import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import java.util.List;
import java.util.Map;

public abstract class ResourceProcessor<R extends Resource> extends Processor {
    protected abstract R create (R resource);
    protected abstract R update (R resource);
    protected abstract R delete (R resource);
    protected abstract List<R> retrieve (ResourceFilter filters, List<ResourceOrder> orders, Map<String,Object> params);
}
