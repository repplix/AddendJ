package io.jexxa.addendj.applicationcore.object;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class provides methods to access the attributes of a class as it is required by {@link DeepEqualsAndHashCode }
 * {@link DeepToString }
 */
public final class ClassAccessor
{
    public static Method getMethodAnnotatedWith(final Object object, final Class<? extends Annotation> annotation) {
        return Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(object.getClass().getName() +
                                " is not annotated with " + annotation.getSimpleName())
                );
    }

    /**
     * Returns a key/value list with all class attributes.
     *
     * The name of the attribute is returned as key. The attribute itself is returned as value.     
     *
     * @param object whose attributes should be extracted 
     * @return a key/value list with all class attributes of given object.
     */
    public static List<Map.Entry<String, Object>> getAttributeList(Object object)
    {
        List<Map.Entry<String, Object>> result = new ArrayList<>();

        getClassHierarchy(object).
                forEach( entry -> result.addAll(getDeclaredClassAttributes(entry, object)));

        return result;
    }

    /**
     * Returns a map including the name of all attributes as {@code <full class name>.<attribute name>} 
     * and the attribute itself.
     *
     * @param object whose attributes should be extracted 
     * @return  a map including name of all attributes and the attribute itself 
     */
    public static Map<String, Object> getAllClassAttributes(Object object)
    {
        final Map<String, Object> result = new HashMap<>();

        getClassHierarchy(object).forEach(entry -> result.putAll(
                addClassNameToAttributeNames(
                        getDeclaredClassAttributes(entry, object), entry.getName())));

        return result;
    }

    /**
     * returns class hierarchy (excluding Object.class) 
     */
    static List<Class<?>> getClassHierarchy(Object object)
    {
        Class<?> currentSuperClass = object.getClass();
        List<Class<?>> superClasses = new ArrayList<>();


        while (currentSuperClass != Object.class)
        {
            superClasses.add(currentSuperClass);
            currentSuperClass = currentSuperClass.getSuperclass();
        }

        return superClasses;
    }


    @SuppressWarnings("java:S3011")
    private static List< Map.Entry<String, Object>> getDeclaredClassAttributes(Class<?> clazz, Object object)
    {
        var returnedAttributes = new ArrayList<Map.Entry<String, Object>>();
        Field[] declaredAttributes = clazz.getDeclaredFields();

        for (Field attribute : declaredAttributes)
        {
            // Ignore static members
            if (!Modifier.isStatic(attribute.getModifiers()))
            {
                attribute.setAccessible(true); // We need access to all fields (also private ones)
                try
                {
                    returnedAttributes.add(new AbstractMap.SimpleEntry<>(attribute.getName(), attribute.get(object)));
                }
                catch (final IllegalAccessException e)
                {
                    throw new AttributeAccessException("Context: Aspect/Generic Hash, Equals, toString: error accessing " + attribute.toString(), e);
                }
            }
        }

        return returnedAttributes;
    }


    private static Map<String, Object> addClassNameToAttributeNames(final List<Map.Entry<String, Object>> attributes, final String className)
    {
        final Map<String, Object> renamedAttributes = new HashMap<>();

        attributes.forEach(entry -> renamedAttributes.put(String.format("%s.%s", className, entry.getKey()), entry.getValue()));

        return renamedAttributes;
    }

    private ClassAccessor()
    {
       //Private constructor
    }
}
