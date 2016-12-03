
package org.neogroup.websparks;

import org.neogroup.websparks.util.Scanner;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Application {

    private final Map<Class<? extends Action>, Executor> executors;

    public Application () {
        executors = new HashMap<>();
    }

    public void registerExecutor(Class<? extends Executor> executorClass) {
        try {
            Executor executor = executorClass.newInstance();
            executor.setApplication(this);
            registerExecutor(executor);
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering executor \"" + executorClass + "\"", ex);
        }
    }

    public void registerComponents () {

        Scanner controllersScanner = new Scanner();
        Set<Class> executorClasses = controllersScanner.findClasses(new Scanner.ClassFilter() {
            @Override
            public boolean accept(Class clazz) {
                return Executor.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers());
            }
        });
        for (Class executorClass : executorClasses) {
            registerExecutor(executorClass);
        }
    }

    public Object executeAction(Action action) {
        Executor executor = getExecutor(action);
        return executor.execute(action);
    }

    protected void registerExecutor(Executor processor) {

        ExecutorComponent controllerAnnotation = processor.getClass().getAnnotation(ExecutorComponent.class);
        if(controllerAnnotation != null){
            Class<? extends Action>[] commandClasses = controllerAnnotation.commands();
            for (Class<? extends Action> commandClass : commandClasses) {
                executors.put(commandClass, processor);
            }
        }
    }

    protected Executor getExecutor(Action action) {
        return executors.get(action.getClass());
    }
}