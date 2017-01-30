
package org.neogroup.util;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Scanner {

    private static final String CLASS_EXTENSION = ".class";

    private Set<URI> classPaths;

    public Scanner() {
        classPaths = new HashSet<>();
        addClassPaths(getClass().getClassLoader());
    }

    public void addClassPaths (ClassLoader classLoader) {
        try {
            Enumeration<URL> roots = classLoader.getResources("");
            while (roots.hasMoreElements()) {
                URL root = roots.nextElement();
                addClassPath(root.toURI());
            }
        }
        catch (Exception ex) {}
    }

    public void addClassPath (URI classPath) {
        classPaths.add(classPath);
    }

    public Set<Class> findClasses () {
        return findClasses(new ClassFilter() {
            @Override
            public boolean accept(Class clazz) {
                return true;
            }
        });
    }

    public Set<Class> findClasses (ClassFilter classFilter) {

        Set<Class> classes = new HashSet<>();
        for (URI classPath : classPaths) {
            File resource = new File(classPath);
            if(resource.isDirectory()) {
                findClasses(classes, resource, "", classFilter);
            }
            else {
                try {
                    JarFile jar = new JarFile(resource);
                    Enumeration en = jar.entries();
                    while (en.hasMoreElements()) {
                        JarEntry entry = (JarEntry) en.nextElement();
                        String entryName = entry.getName();

                        if (entryName.endsWith(CLASS_EXTENSION)) {
                            String className = entryName.substring(0, entryName.indexOf("."));
                            className = className.replace('/', '.');
                            Class clazz = getClass(className);
                            if (clazz != null) {
                                if (classFilter.accept(clazz)) {
                                    classes.add(clazz);
                                }
                            }
                        }
                    }
                }
                catch (Exception ex) {}
            }
        }
        return classes;
    }

    private void findClasses (Set<Class> classes, File file, String baseName, ClassFilter classFilter) {

        if(file.isDirectory()){
            for(File subFile : file.listFiles()) {
                if(baseName.isEmpty()){
                    findClasses(classes, subFile, subFile.getName(), classFilter);
                }
                else {
                    findClasses(classes, subFile, baseName + "." + subFile.getName(), classFilter);
                }
            }
        }
        else {
            if(file.getName().endsWith(CLASS_EXTENSION)) {
                String className = baseName.substring(0, baseName.length() - CLASS_EXTENSION.length());
                Class clazz = getClass(className);
                if (clazz != null) {
                    if (classFilter.accept(clazz)) {
                        classes.add(clazz);
                    }
                }
            }
        }
    }

    private Class getClass (String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (Exception ex) {}
        return clazz;
    }

    public interface ClassFilter {

        public boolean accept (Class clazz);
    }
}