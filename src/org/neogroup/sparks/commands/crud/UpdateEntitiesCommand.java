
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.models.Model;

import java.util.List;

public class UpdateEntitiesCommand<M extends Model> extends ModifyEntitiesCommand<M> {

    public UpdateEntitiesCommand(Class<? extends M> resourceClass, M resource) {
        super(resourceClass, resource);
    }

    public UpdateEntitiesCommand(Class<? extends M> resourceClass, List<M> resources) {
        super(resourceClass, resources);
    }
}
