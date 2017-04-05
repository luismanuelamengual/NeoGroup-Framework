
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.models.Model;

import java.util.List;

public class CreateEntitiesCommand<M extends Model> extends ModifyEntitiesCommand<M> {

    public CreateEntitiesCommand(Class<? extends M> resourceClass, M resource) {
        super(resourceClass, resource);
    }

    public CreateEntitiesCommand(Class<? extends M> resourceClass, List<M> resources) {
        super(resourceClass, resources);
    }
}
