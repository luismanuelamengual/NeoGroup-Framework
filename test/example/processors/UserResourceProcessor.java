
package example.processors;

import example.resources.User;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;
import org.neogroup.sparks.resources.processors.ResourceProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected List<User> retrieve(Class<? extends User> resourceClass, ResourceFilter filters, List<ResourceOrder> orders, Map<String, Object> params) {
        List<User> usersList = new ArrayList<User>(users.values());
        return usersList;
    }
}
