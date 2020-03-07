package io.ddd.aspect.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Eine Klasse, die ein Objekt Kapselt und reflexiven zugriff auf Attribute bietet.
 */
public class ClassAccessor
{

    /**
     * Generiert eine Liste aus Schlüssel, Wert Paaren, die nach ihrem Auftreten in der Klassenhierarchie und danach Alphabetisch sortiert sind.
     * Der Schlüssel besteht nur Rein aus dem Attribut-Namen ohne Klassen-Name und der Wert ist der eigentliche Attribut-Wert.
     *
     * @return Eine sortierte Liste mit allen Attributen und deren Werten.
     */
    public static List<Map.Entry<String, Object>> getAllClassAttributesSorted(Object object)
    {
        List<Map.Entry<String, Object>> result = new ArrayList<>();

        getClassHierarchy(object).
                forEach( entry -> result.addAll(getDeclaredClassAttributes(entry, object).entrySet()));

        return result;
    }

    /**
     * Generiert eine Map nach dem Schema {@code Voller-Klassen-Name.Attribut-Name -> Attribut-Wert} und gibt diese zurück.
     * {@code Voller-Klassen-Name} bedeutet hier Package-Name + Klassen-Name.
     *
     * @return Eine Map, die alle deklarierten und geerbten Attribute beinhaltet.
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
