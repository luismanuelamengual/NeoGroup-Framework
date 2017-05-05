
package org.neogroup.sparks.data;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.Manager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourcesManager extends Manager {

    private static final String DEFAULT_DATA_SOURCE_PROPERTY = "defaultDataSource";

    private final Map<String, DataSource> dataSources;

    public DataSourcesManager(ApplicationContext applicationContext) {
        super(applicationContext);
        this.dataSources = new HashMap<>();
    }

    public void addDataSource (String name, DataSource dataSource) {
        this.dataSources.put(name, dataSource);
    }

    public void removeDataSource (String name) {
        this.dataSources.remove(name);
    }

    public DataSource getDataSource () {
        DataSource source = null;
        if (dataSources.size() == 1) {
            source = dataSources.values().iterator().next();
        }
        else if (applicationContext.getProperties().contains(DEFAULT_DATA_SOURCE_PROPERTY)) {
            source = dataSources.get(applicationContext.getProperties().get(DEFAULT_DATA_SOURCE_PROPERTY));
        }
        return source;
    }

    public DataSource getDataSource (String name) {
        return dataSources.get(name);
    }
}
