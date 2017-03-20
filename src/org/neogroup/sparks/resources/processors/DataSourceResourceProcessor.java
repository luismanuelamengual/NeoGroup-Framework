
package org.neogroup.sparks.resources.processors;

import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataSourceResourceProcessor<R extends Resource> extends ResourceProcessor<R> {

    private final DataSource source;
    private final Map<Class<? extends R>, Object> resourceMetadata;

    public DataSourceResourceProcessor() {
        this.source = createDataSource();
        this.resourceMetadata = new HashMap<>();
    }

    protected DataSource getDataSource () {
        return source;
    }

    @Override
    protected R create(R resource, Map<String, Object> params) {
        return null;
    }

    @Override
    protected R update(R resource, Map<String, Object> params) {
        return null;
    }

    @Override
    protected R delete(R resource, Map<String, Object> params) {
        return null;
    }

    @Override
    protected List<R> retrieve(Class<? extends R> resourceClass, ResourceFilter filters, List<ResourceOrder> orders, Map<String, Object> params) {
        return null;
    }

    protected abstract DataSource createDataSource();

    public class ResourceMetadata {

        private String tableName;

    }
}
