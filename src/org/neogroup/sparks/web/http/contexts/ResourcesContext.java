package org.neogroup.sparks.web.http.contexts;

public class ResourcesContext extends FilesContext {

    public ResourcesContext(String path, String resourceName) {
        super(path, ResourcesContext.class.getClassLoader().getResource(resourceName).getFile());
    }
}