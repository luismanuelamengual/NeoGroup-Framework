
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.models.Model;

import java.util.List;

public class DeleteEntitiesCommand<M extends Model> extends ModifyEntitiesCommand<M> {

    public DeleteEntitiesCommand(Class<? extends M> resourceClass, M resource) {
        super(resourceClass, resource);
    }

    public DeleteEntitiesCommand(Class<? extends M> resourceClass, List<M> resources) {
        super(resourceClass, resources);
    }
}
