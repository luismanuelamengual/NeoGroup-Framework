
package example.processors;

import example.models.User;
import org.neogroup.sparks.model.EntityFilter;
import org.neogroup.sparks.model.EntitySorter;
import org.neogroup.sparks.processors.crud.CRUDProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCRUDProcessor extends CRUDProcessor<User> {

    private Map<Integer, User> users;
    private int nextId;

    public UserCRUDProcessor() {
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
    protected List<User> retrieve(EntityFilter filters, List<EntitySorter> orders, Map<String, Object> params) {
        List<User> usersList = new ArrayList<User>(users.values());
        return usersList;
    }
}
