
package org.neogroup.sparks.processors.crud;

import org.neogroup.sparks.model.*;
import org.neogroup.sparks.model.annotations.Column;
import org.neogroup.sparks.model.annotations.GeneratedValue;
import org.neogroup.sparks.model.annotations.Id;
import org.neogroup.sparks.model.annotations.Table;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public abstract class DataSourceCRUDProcessor<E extends Entity> extends CRUDProcessor<E> {

    protected DataSource dataSource;

    @Override
    public void initialize() {
        String dataSourceName = getDataSourceName();
        if (dataSourceName != null) {
            dataSource = getApplicationContext().getDataSource(dataSourceName);
        }
        else {
            dataSource = getApplicationContext().getDataSource();
        }
    }

    protected String getDataSourceName () {
        return null;
    }

    protected DataSource getDataSource () {
        return dataSource;
    }

    @Override
    protected E create(E entity, Map<String, Object> params) {

        try {
            DataSource source = getDataSource();
            Connection connection = source.getConnection();
            List<Object> sqlParameters = new ArrayList<>();

            StringBuilder fieldsStatement = new StringBuilder();
            for (Field field : entityClass.getDeclaredFields()) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && field.getAnnotation(GeneratedValue.class) == null) {
                    if (fieldsStatement.length() > 0) {
                        fieldsStatement.append(",");
                    }
                    fieldsStatement.append(columnAnnotation.name());
                }
            }

            StringBuilder valuesStatement = new StringBuilder();
            for (Field field : entityClass.getDeclaredFields()) {

                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && field.getAnnotation(GeneratedValue.class) == null) {
                    if (valuesStatement.length() > 0) {
                        valuesStatement.append(",");
                    }
                    valuesStatement.append("?");
                    field.setAccessible(true);
                    sqlParameters.add(field.get(entity));
                }
            }

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ");
            sql.append(entityClass.getAnnotation(Table.class).name());
            sql.append(" (");
            sql.append(fieldsStatement);
            sql.append(") VALUES (");
            sql.append(valuesStatement);
            sql.append(")");

            PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int parameterIndex = 1;
            for (Object sqlParameter : sqlParameters) {
                statement.setObject(parameterIndex++, sqlParameter);
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    while (generatedKeys.next()) {
                        for (Field field : entityClass.getDeclaredFields()) {
                            if (field.getAnnotation(GeneratedValue.class) != null) {
                                Column columnAnnotation = field.getAnnotation(Column.class);
                                field.setAccessible(true);
                                field.set(entity, generatedKeys.getObject(columnAnnotation.name()));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error creating entity", ex);
        }
        return entity;
    }

    @Override
    protected E update(E entity, Map<String, Object> params) {

        try {
            DataSource source = getDataSource();
            Connection connection = source.getConnection();
            List<Object> sqlParameters = new ArrayList<>();

            StringBuilder setStatement = new StringBuilder();
            for (Field field : entityClass.getDeclaredFields()) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && field.getAnnotation(Id.class) == null) {
                    if (setStatement.length() > 0) {
                        setStatement.append(",");
                    }
                    setStatement.append(columnAnnotation.name());
                    setStatement.append("=");
                    setStatement.append("?");
                    field.setAccessible(true);
                    sqlParameters.add(field.get(entity));
                }
            }

            StringBuilder whereStatement = new StringBuilder();
            for (Field field : entityClass.getDeclaredFields()) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && field.getAnnotation(Id.class) != null) {
                    if (whereStatement.length() > 0) {
                        whereStatement.append(" AND ");
                    }
                    whereStatement.append(columnAnnotation.name());
                    whereStatement.append("=");
                    whereStatement.append("?");
                    field.setAccessible(true);
                    sqlParameters.add(field.get(entity));
                }
            }

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ");
            sql.append(entityClass.getAnnotation(Table.class).name());
            sql.append(" SET ");
            sql.append(setStatement);
            sql.append(" WHERE ");
            sql.append(whereStatement);

            PreparedStatement statement = connection.prepareStatement(sql.toString());

            int parameterIndex = 1;
            for (Object sqlParameter : sqlParameters) {
                statement.setObject(parameterIndex++, sqlParameter);
            }

            statement.executeUpdate();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error updating entity", ex);
        }

        return entity;
    }

    @Override
    protected E delete(E entity, Map<String, Object> params) {

        try {
            DataSource source = getDataSource();
            Connection connection = source.getConnection();
            List<Object> sqlParameters = new ArrayList<>();

            StringBuilder whereStatement = new StringBuilder();
            for (Field field : entityClass.getDeclaredFields()) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && field.getAnnotation(Id.class) != null) {
                    if (whereStatement.length() > 0) {
                        whereStatement.append(" AND ");
                    }
                    whereStatement.append(columnAnnotation.name());
                    whereStatement.append("=");
                    whereStatement.append("?");
                    field.setAccessible(true);
                    sqlParameters.add(field.get(entity));
                }
            }

            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM ");
            sql.append(entityClass.getAnnotation(Table.class).name());
            sql.append(" WHERE ");
            sql.append(whereStatement);

            PreparedStatement statement = connection.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for (Object sqlParameter : sqlParameters) {
                statement.setObject(parameterIndex++, sqlParameter);
            }

            statement.executeUpdate();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error deleting entity", ex);
        }

        return entity;
    }

    @Override
    protected Collection<E> retrieve(EntityQuery query, Map<String, Object> params) {

        List<E> resources = new ArrayList<>();
        try {
            DataSource source = getDataSource();
            Connection connection = source.getConnection();

            List<Object> sqlParameters = new ArrayList<>();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(entityClass.getAnnotation(Table.class).name());

            if (!query.getFilters().isEmpty()) {
                sql.append(" WHERE ");
                buildFilterSQL(query.getFilters(), sql, sqlParameters);
            }

            if (!query.getSorters().isEmpty()) {
                sql.append(" ORDER BY ");
                Iterator<EntitySorter> orderIterator = query.getSorters().iterator();
                while (orderIterator.hasNext()) {
                    EntitySorter order = orderIterator.next();
                    for (Field field : entityClass.getDeclaredFields()) {
                        if (field.getName().equals(order.getProperty())) {
                            Column columnAnnotation = field.getAnnotation(Column.class);
                            if (columnAnnotation != null) {
                                sql.append(columnAnnotation.name());
                                sql.append(" ");
                                switch (order.getDirection()) {
                                    case ASC:
                                        sql.append("ASC");
                                        break;
                                    case DESC:
                                        sql.append("DESC");
                                        break;
                                }
                                if (orderIterator.hasNext()) {
                                    sql.append(",");
                                }
                            }
                            break;
                        }
                    }
                }
            }

            if (query.getStart() != null) {
                sql.append(" START ");
                sql.append(query.getStart());
            }
            if (query.getLimit() != null) {
                sql.append(" LIMIT ");
                sql.append(query.getLimit());
            }

            PreparedStatement statement = connection.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for (Object sqlParameter : sqlParameters) {
                statement.setObject(parameterIndex++, sqlParameter);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                E resource = createEntityFromResultSet(resultSet);
                resources.add(resource);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Error retrieving entities", ex);
        }

        return resources;
    }

    private void buildFilterSQL(EntityFilter filter, StringBuilder sql, List<Object> sqlParameters) {

        if (filter instanceof EntityFilterGroup) {
            EntityFilterGroup resourceFilterGroup = (EntityFilterGroup)filter;
            sql.append("(");
            Iterator<EntityFilter> resourceFilterIterator = resourceFilterGroup.getFilters().iterator();
            while (resourceFilterIterator.hasNext()) {
                EntityFilter childEntityFilter = resourceFilterIterator.next();
                buildFilterSQL(childEntityFilter, sql, sqlParameters);

                if (resourceFilterIterator.hasNext()) {
                    switch (resourceFilterGroup.getConnector()) {
                        case AND:
                            sql.append(" AND ");
                            break;
                        case OR:
                            sql.append(" OR ");
                            break;
                    }
                }
            }
            sql.append(")");
        }
        else if (filter instanceof EntityPropertyFilter) {
            EntityPropertyFilter resourcePropertyFilter = (EntityPropertyFilter)filter;
            for (Field field : entityClass.getDeclaredFields()) {
                if (field.getName().equals(resourcePropertyFilter.getProperty())) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation != null) {
                        switch (resourcePropertyFilter.getOperator()) {
                            case EntityPropertyOperator.EQUALS:
                                sql.append(columnAnnotation.name());
                                sql.append("=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.DISTINCT:
                                sql.append(columnAnnotation.name());
                                sql.append("!=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.GREATER_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append(">");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.GREATER_OR_EQUALS_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append(">=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.LESS_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append("<");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.LESS_OR_EQUALS_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append("<=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.CONTAINS:
                                sql.append(columnAnnotation.name());
                                sql.append(" LIKE ");
                                sql.append("?");
                                sqlParameters.add("%" + resourcePropertyFilter.getValue() + "%");
                                break;
                            case EntityPropertyOperator.NOT_CONTAINS:
                                sql.append(columnAnnotation.name());
                                sql.append(" NOT LIKE ");
                                sql.append("?");
                                sqlParameters.add("%" + resourcePropertyFilter.getValue() + "%");
                                break;
                            case EntityPropertyOperator.IN: {
                                sql.append(columnAnnotation.name());
                                sql.append(" IN ");
                                sql.append("(");
                                Collection valueCollection = (Collection) resourcePropertyFilter.getValue();
                                Iterator valueCollectionIterator = valueCollection.iterator();
                                while (valueCollectionIterator.hasNext()) {
                                    Object singleValue = valueCollectionIterator.next();
                                    sql.append("?");
                                    sqlParameters.add(singleValue);
                                    if (valueCollectionIterator.hasNext()) {
                                        sql.append(",");
                                    }
                                }
                                sql.append(")");
                                break;
                            }
                            case EntityPropertyOperator.NOT_IN: {
                                sql.append(columnAnnotation.name());
                                sql.append(" NOT IN ");
                                sql.append("(");
                                Collection valueCollection = (Collection) resourcePropertyFilter.getValue();
                                Iterator valueCollectionIterator = valueCollection.iterator();
                                while (valueCollectionIterator.hasNext()) {
                                    Object singleValue = valueCollectionIterator.next();
                                    sql.append("?");
                                    sqlParameters.add(singleValue);
                                    if (valueCollectionIterator.hasNext()) {
                                        sql.append(",");
                                    }
                                }
                                sql.append(")");
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    protected E createEntityFromResultSet(ResultSet resultSet) {

        try {
            E entity = entityClass.newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(columnAnnotation.name()));
                }
            }
            return entity;
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error creating entity from resultSet", ex);
        }
    }
}
