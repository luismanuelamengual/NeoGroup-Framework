
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class ModifyEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private final List<E> resources;

    public ModifyEntitiesCommand(Class<? extends E> resourceClass, E resource) {
        super(resourceClass);
        this.resources = new ArrayList<>();
        this.resources.add(resource);
    }

    public ModifyEntitiesCommand(Class<? extends E> resourceClass, List<E> resources) {
        super(resourceClass);
        this.resources = resources;
    }

    public List<E> getResources() {
        return resources;
    }
}
