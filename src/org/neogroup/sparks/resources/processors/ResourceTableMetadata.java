
package org.neogroup.sparks.resources.processors;

import org.neogroup.sparks.resources.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceTableMetadata {

    private final Class<? extends Resource> resourceClass;
    private final String tableName;
    private final List<ResourceColumnMetadata> columnMetadatas;
    private final Map<String, ResourceColumnMetadata> columnMetadatasByPropertyName;

    public ResourceTableMetadata(Class<? extends Resource> resourceClass, String tableName) {
        this.resourceClass = resourceClass;
        this.tableName = tableName;
        columnMetadatas = new ArrayList<>();
        columnMetadatasByPropertyName = new HashMap<>();
    }

    public Class<? extends Resource> getResourceClass() {
        return resourceClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void addColumnMetadata(ResourceColumnMetadata columnMetadata) {
        columnMetadatas.add(columnMetadata);
        columnMetadatasByPropertyName.put(columnMetadata.getField().getName(), columnMetadata);
    }

    public ResourceColumnMetadata getColumnMetadataByPropertyName (String propertyName) {
        return columnMetadatasByPropertyName.get(propertyName);
    }

    public List<ResourceColumnMetadata> getColumnMetadatas() {
        return columnMetadatas;
    }
}
