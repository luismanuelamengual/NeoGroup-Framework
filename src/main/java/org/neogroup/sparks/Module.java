
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.sparks.views.View;
import org.neogroup.sparks.views.ViewException;
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
    public Processor getProcessorInstance(Class<? extends Processor> processorClass) {
        Processor processor = super.getProcessorInstance(processorClass);
        if (processor == null) {
            processor = application.getProcessorInstance(processorClass);
        }
        return processor;
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
    public View createView(String viewName) throws ViewException {
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
    public View createView(String viewFactoryName, String viewName) throws ViewException {
        View view = null;
        try {
            view = super.createView(viewFactoryName, viewName);
        }
        catch (ViewNotFoundException notFoundExeption) {
            view = application.createView(viewFactoryName, viewName);
        }
        return view;
    }

    @Override
    public DataSource getDataSource() {
        DataSource dataSource = super.getDataSource();
        if (dataSource == null) {
            dataSource = application.getDataSource();
        }
        return dataSource;
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
