
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.sparks.views.View;
import org.neogroup.sparks.views.ViewNotFoundException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Module extends ApplicationContext {

    protected final Application application;

    public Module(Application application) {
        this.application = application;
        this.properties = application.getProperties();
        this.logger = application.getLogger();
    }

    public Application getApplication() {
        return application;
    }

    @Override
    public Collection<Processor> getRegisteredProcessors() {
        List<Processor> processors = new ArrayList<>();
        processors.addAll(application.getRegisteredProcessors());
        processors.addAll(super.getRegisteredProcessors());
        return processors;
    }

    @Override
    public <R> R processCommand(Command command) {
        R response = null;
        try {
            response = super.processCommand(command);
        }
        catch (ProcessorNotFoundException notFoundExeption) {
            response = application.processCommand(command);
        }
        return response;
    }

    @Override
    public View createView(String viewName) {
        View view = null;
        try {
            view = super.createView(viewName);
        }
        catch (ViewNotFoundException notFoundExeption) {
            view = application.createView(viewName);
        }
        return view;
    }

    @Override
    public DataSource getDataSource(String name) {
        DataSource dataSource = super.getDataSource(name);
        if (dataSource == null) {
            dataSource = application.getDataSource(name);
        }
        return dataSource;
    }
}
