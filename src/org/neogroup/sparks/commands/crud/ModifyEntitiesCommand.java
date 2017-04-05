
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.models.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModifyEntitiesCommand<M extends Model> extends CRUDCommand<M> {

    private final List<M> resources;

    public ModifyEntitiesCommand(Class<? extends M> resourceClass, M resource) {
        super(resourceClass);
        this.resources = new ArrayList<>();
        this.resources.add(resource);
    }

    public ModifyEntitiesCommand(Class<? extends M> resourceClass, List<M> resources) {
        super(resourceClass);
        this.resources = resources;
    }

    public List<M> getResources() {
        return resources;
    }
}
