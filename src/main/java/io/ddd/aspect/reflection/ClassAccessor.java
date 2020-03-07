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
    private final Object currentObject;
    private final Class<?> staticType;

    /**
     * Konstruktor des ClassAccessor. Er nutzt das momentane Objekt und dessen momentanen statischen Typ.
     *
     * @param currentObject Das eigentliche Objekt, auf wessen Attribut-Werte zugegriffen werden soll.
     * @param staticType    Der statische Typ, als dieser das Objekt interpretiert werden soll.
     */
    private ClassAccessor(final Object currentObject, final Class<?> staticType)
    {
        this.currentObject = currentObject;
        this.staticType = staticType;
    }

    /**
     * Konstruktor des ClassAccessor, übernimmt den Statischen typ aus der getClass funktion des currentObject.
     *
     * @param currentObject Das eigentliche Objekt, auf wessen Attribut-Werte zugegriffen werden soll.
     */
    public ClassAccessor(final Object currentObject)
    {
        this(currentObject, currentObject.getClass());
    }

    /**
     * Gibt eine sortierte Liste zurück, die alle Klassen in der Klassenhierarchie beinhaltet.
     * Diese Liste besteht aus ClassAccessor instanzen, die alle das selbe Objekt haben,
     * aber jeweils einen anderen statischen Typ aus der hierarchie besitzen.
     *
     * @return Eine sortierte Liste, die alle Klassen der Klassenhierarchie beinhaltet.
     */                         
    private List<ClassAccessor> getClassHierarchy()
    {
        Class<?> currentSuperClass = staticType;
        List<ClassAccessor> superClasses = new ArrayList<>();

        while (currentSuperClass != Object.class)
        {
            superClasses.add(new ClassAccessor(currentObject, currentSuperClass));
            currentSuperClass = currentSuperClass.getSuperclass();
        }

        return superClasses;
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

    /**
     * Generiert eine Map nach dem Schema {@code Attribut-Name -> Attribut-Wert} und gibt diese zurück.
     * Diese Map beinhaltet nur Attribute, die im statischen typ deklariert sind.
     *
     * @return Eine Map, die alle deklarierten Attribut-Namen mit deren passenden Werten beinhaltet.
     */
    public Map<String, Object> getDeclaredClassAttributes()
    {
        Map<String, Object> returnedAttributes = new HashMap<>();
        Field[] declaredAttributes = staticType.getDeclaredFields();

        for (Field attribute : declaredAttributes)
        {
            // Falls statische Attribute nicht herausgenommen werden, kann es bei Klassen, die rekursiv definiert sind und die Equals Methode aus diesem Package verwenden, ein StackOverflow vorkommen.
            if (!Modifier.isStatic(attribute.getModifiers()))
            {
                attribute.setAccessible(true);
                try
                {
                    returnedAttributes.put(attribute.getName(), attribute.get(currentObject));
                }
                catch (final IllegalAccessException e)
                {
                    throw new AttributeAccessException("Context: Aspect/Generic Hash, Equals, toString: error accessing " + attribute.toString(), e);
                }
            }
        }

        return returnedAttributes;
    }


    public static Map<String, Object> getDeclaredClassAttributes(Class<?> clazz, Object object)
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

    /**
     * Generiert eine Map nach dem Schema {@code Voller-Klassen-Name.Attribut-Name -> Attribut-Wert} und gibt diese zurück.
     * {@code Voller-Klassen-Name} bedeutet hier Package-Name + Klassen-Name.
     *
     * @return Eine Map, die alle deklarierten und geerbten Attribute beinhaltet.
     */
    public Map<String, Object> getAllClassAttributes()
    {
        final Map<String, Object> returnedAttributes = new HashMap<>();
        for (ClassAccessor currentClass : getClassHierarchy())
        {
            returnedAttributes.putAll(addClassNameToAttributeNames(
                    currentClass.getDeclaredClassAttributes(),
                    currentClass.staticType.getName()
            ));
        }
        return returnedAttributes;
    }


    /**
     * Generiert eine Map nach dem Schema {@code Voller-Klassen-Name.Attribut-Name -> Attribut-Wert} und gibt diese zurück.
     * {@code Voller-Klassen-Name} bedeutet hier Package-Name + Klassen-Name.
     *
     * @return Eine Map, die alle deklarierten und geerbten Attribute beinhaltet.
     */
    public static Map<String, Object> getAllClassAttributes(Object object)
    {
        final Map<String, Object> returnedAttributes = new HashMap<>();
        
        for (Class<?> currentClass : getClassHierarchy(object))
        {
            returnedAttributes.putAll(addClassNameToAttributeNames(
                    getDeclaredClassAttributes(currentClass, object),
                    currentClass.getName()
            ));
        }
        return returnedAttributes;
    }


    /**
     * Generiert eine Liste aus Schlüssel, Wert Paaren, die nach ihrem Auftreten in der Klassenhierarchie und danach Alphabetisch sortiert sind.
     * Der Schlüssel besteht nur Rein aus dem Attribut-Namen ohne Klassen-Name und der Wert ist der eigentliche Attribut-Wert.
     *
     * @return Eine sortierte Liste mit allen Attributen und deren Werten.
     */
    public List<Map.Entry<String, Object>> getAllClassAttributesSorted()
    {
        List<Map.Entry<String, Object>> attributes = new ArrayList<>();
        for (ClassAccessor currentClass : getClassHierarchy())
        {
            List<Map.Entry<String, Object>> currentAttributes = new ArrayList<>(currentClass.getDeclaredClassAttributes().entrySet());
            attributes.addAll(currentAttributes);
        }
        return attributes;
    }



    /**
     * Generiert eine Liste aus Schlüssel, Wert Paaren, die nach ihrem Auftreten in der Klassenhierarchie und danach Alphabetisch sortiert sind.
     * Der Schlüssel besteht nur Rein aus dem Attribut-Namen ohne Klassen-Name und der Wert ist der eigentliche Attribut-Wert.
     *
     * @return Eine sortierte Liste mit allen Attributen und deren Werten.
     */
    public static List<Map.Entry<String, Object>> getAllClassAttributesSorted(Object object)
    {
        List<Map.Entry<String, Object>> attributes = new ArrayList<>();
        for (Class<?> currentClass : getClassHierarchy(object))
        {
            List<Map.Entry<String, Object>> currentAttributes = new ArrayList<>(getDeclaredClassAttributes(currentClass, object).entrySet());
            attributes.addAll(currentAttributes);
        }
        return attributes;
    }


    static private Map<String, Object> addClassNameToAttributeNames(final Map<String, Object> attributes, final String className)
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
}
