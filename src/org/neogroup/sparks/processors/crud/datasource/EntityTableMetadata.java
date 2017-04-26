
package org.neogroup.sparks.processors.crud.datasource;

import org.neogroup.sparks.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityTableMetadata {

    private final Class<? extends Entity> modelClass;
    private final String tableName;
    private final List<EntityColumnMetadata> columnMetadatas;
    private final Map<String, EntityColumnMetadata> columnMetadatasByPropertyName;

    public EntityTableMetadata(Class<? extends Entity> modelClass, String tableName) {
        this.modelClass = modelClass;
        this.tableName = tableName;
        columnMetadatas = new ArrayList<>();
        columnMetadatasByPropertyName = new HashMap<>();
    }

    public Class<? extends Entity> getModelClass() {
        return modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void addColumnMetadata(EntityColumnMetadata columnMetadata) {
        columnMetadatas.add(columnMetadata);
        columnMetadatasByPropertyName.put(columnMetadata.getField().getName(), columnMetadata);
    }

    public EntityColumnMetadata getColumnMetadataByPropertyName (String propertyName) {
        return columnMetadatasByPropertyName.get(propertyName);
    }

    public List<EntityColumnMetadata> getColumnMetadatas() {
        return columnMetadatas;
    }
}
