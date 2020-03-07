package io.ddd.aspect.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * This class provides methods to access the attributes of a class as it is required by {@link DeepEqualsAndHashCode }
 * {@link DeepToString }
 */
public class ClassAccessor
{

    public static Method getMethodAnnotatedWith(final Object object, final Class<? extends Annotation> annotation) {

        List<Method> methodList = Arrays.asList(object.getClass().getDeclaredMethods());

        Optional<Method> result = methodList.stream().
                                    filter(method -> method.isAnnotationPresent(annotation)).
                                    findFirst();

        if ( !result.isPresent() ) {
            throw new IllegalArgumentException(
                    "Class " + object.getClass().getSimpleName() +
                    " is not annotated with " + annotation.getSimpleName());
        }

        return result.get();
    }
    /**
     * Returns a key/value list with all class attributes.
     *
     * The name of the attribute is returned as key. The attribute itself is returned as value.     
     *
     * @return a key/value list with all class attributes of given object.
     */
    public static List<Map.Entry<String, Object>> getAttributeList(Object object)
    {
        List<Map.Entry<String, Object>> result = new ArrayList<>();

        getClassHierarchy(object).
                forEach( entry -> result.addAll(getDeclaredClassAttributes(entry, object).entrySet()));

        return result;
    }

    /**
     * Returns a map including the name of all attributes as {@code <full class name>.<attribute name> ->
     * and the attribute itself.
     *
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


    private static Map<String, Object> getDeclaredClassAttributes(Class<?> clazz, Object object)
    {
        Map<String, Object> returnedAttributes = new HashMap<>();
        Field[] declaredAttributes = clazz.getDeclaredFields();

        for (Field attribute : declaredAttributes)
        {
            // Falls statische Attribute nicht herausgenommen werden, kann es bei Klassen, die rekursiv definiert sind und die Equals Methode aus diesem Package verwenden, ein StackOverflow vorkommen.
            if (!Modifier.isStatic(attribute.getModifiers()))
            {
                attribute.setAccessible(true);
                try
                {
                    returnedAttributes.put(attribute.getName(), attribute.get(object));
                }
                catch (final IllegalAccessException e)
                {
                    throw new AttributeAccessException("Context: Aspect/Generic Hash, Equals, toString: error accessing " + attribute.toString(), e);
                }
            }
        }

        return returnedAttributes;
    }


    private static Map<String, Object> addClassNameToAttributeNames(final Map<String, Object> attributes, final String className)
    {
        final Map<String, Object> renamedAttributes = new HashMap<>();
        for (Map.Entry<String, Object> entry : attributes.entrySet())
        {
            Object value = entry.getValue();
            renamedAttributes.put(String.format("%s.%s", className, entry.getKey()), value
            );
        }

        return renamedAttributes;
    }

    private ClassAccessor()
    {

    }
}
