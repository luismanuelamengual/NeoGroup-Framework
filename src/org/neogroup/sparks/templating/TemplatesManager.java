
package org.neogroup.sparks.templating;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TemplatesManager {

    private static final String TEMPLATE_NAME_WILDCARD = "{0}";
    private static final String TEMPLATE_NAME_SEPARATOR = ".";
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String TEMPLATE_NOT_FOUND_ERROR = "Template \"{0}\" not found !!";
    private static final String TEMPLATE_READ_ERROR = "Error retrieving template from file \"{0}\"";

    private final Set<TemplateFactory> templateFactories;
    private final Map<String, TemplateFactory> templateFactoriesByFileTemplate;

    public TemplatesManager() {
        this.templateFactories = new HashSet<>();
        this.templateFactoriesByFileTemplate = new HashMap<>();
    }

    public void addTemplateFactory(TemplateFactory templateFactory) {

        if (templateFactories.add(templateFactory)) {

            //Obtain the file extensions associated with the template this factory works with
            String[] fileExtensions = null;
            Type type = templateFactory.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                Class<? extends Template> templateClass = (Class) fieldArgTypes[0];
                TemplateExtensions extensionsAnnotation = templateClass.getAnnotation(TemplateExtensions.class);
                if (extensionsAnnotation != null) {
                    fileExtensions = extensionsAnnotation.value();
                }
            }

            //Register the file templates associated with this factory
            if (fileExtensions != null) {
                for (String fileExtension : fileExtensions) {
                    StringBuilder fileTemplate = new StringBuilder();
                    fileTemplate.append(templateFactory.getBasePath());
                    fileTemplate.append(File.separator);
                    fileTemplate.append(TEMPLATE_NAME_WILDCARD);
                    fileTemplate.append(FILE_EXTENSION_SEPARATOR);
                    fileTemplate.append(fileExtension);
                    templateFactoriesByFileTemplate.put(fileTemplate.toString(), templateFactory);
                }
            }
        }
    }

    public void removeTemplateFactory(TemplateFactory templateFactory) {

        templateFactories.remove(templateFactory);
        templateFactoriesByFileTemplate.values().remove(templateFactory);
    }

    public Template createTemplate (String templateName) {

        Template template = null;
        try {
            String relativeFilename = templateName.replace(TEMPLATE_NAME_SEPARATOR, File.separator);
            for (String fileTemplate : templateFactoriesByFileTemplate.keySet()) {
                File templateFile = new File(MessageFormat.format(fileTemplate, relativeFilename));
                if (templateFile.exists()) {
                    TemplateFactory factory = templateFactoriesByFileTemplate.get(fileTemplate);
                    template = factory.createTemplate(templateFile.getAbsolutePath().substring(factory.getBasePath().length()));
                    break;
                }
            }
            if (template == null) {
                throw new Exception(MessageFormat.format(TEMPLATE_NOT_FOUND_ERROR, templateName));
            }
        }
        catch (Throwable throwable) {
            throw new RuntimeException(MessageFormat.format(TEMPLATE_READ_ERROR, templateName), throwable);
        }
        return template;
    }
}
