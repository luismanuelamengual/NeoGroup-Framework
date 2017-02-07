
package org.neogroup.sparks.commands;

import org.neogroup.sparks.resources.Resource;

public abstract class ResourcesCommand<R extends Resource> extends Command {

    public static final String START_PARAMETER = "start";
    public static final String LIMIT_PARAMETER = "limit";

    private final ResourcesCommandType type;

    public ResourcesCommand(ResourcesCommandType type) {
        this.type = type;
    }

    public ResourcesCommandType getType() {
        return type;
    }
}
