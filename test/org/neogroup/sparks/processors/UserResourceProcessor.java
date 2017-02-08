
package org.neogroup.sparks.processors;

import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;
import org.neogroup.sparks.resources.User;

import java.util.*;

public class UserResourceProcessor extends ResourceProcessor<User> {

    private Map<Integer, User> users;
    private int nextId;

    public UserResourceProcessor() {
        this.users = new HashMap<>();
        nextId = 1;
    }

    @Override
    protected User create(User resource, Map<String, Object> params) {
        resource.setId(nextId++);
        users.put(resource.getId(), resource);
        return resource;
    }

    @Override
    protected User update(User resource, Map<String, Object> params) {
        users.put(resource.getId(), resource);
        return resource;
    }

    @Override
    protected User delete(User resource, Map<String, Object> params) {
        users.remove(resource);
        return resource;
    }

    @Override
    protected List<User> retrieve(ResourceFilter filters, List<ResourceOrder> orders, Map<String, Object> params) {
        return new ArrayList<User>(users.values());
    }
}
