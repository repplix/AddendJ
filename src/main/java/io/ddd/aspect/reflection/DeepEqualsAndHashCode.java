package io.ddd.aspect.reflection;

import java.util.Arrays;
import java.util.Map;


/**
 * This class provides generic methods to implement Java equals() and hashCode() methods according to their contracts.
 *
 * equals()
 *   reflexive: an object must equal itself
 *   symmetric: x.equals(y) must return the same result as y.equals(x)
 *   transitive: if x.equals(y) and y.equals(z) then also x.equals(z)
 *   consistent: the value of equals() should change only if a property that is contained in equals() changes (no randomness allowed)
 *
 *  hashCode()
 *    internal consistency: the value of hashCode() may only change if a property that is in equals() changes
 *    equals consistency: objects that are equal to each other must return the same hashCode
 *    collisions: unequal objects may have the same hashCode
 */
public class DeepEqualsAndHashCode
{
    /**
     * This method can be used to implement a generic equals method.
     *
     * It compares if all attributes of the two given objects are equals
     *
     * @param object1 First given object which is comapr
     * @param object2 Das Objekt, welches mit dem ersten Objekt verglichen wird.
     * @return True if all attributes of both objects are euqual
     * @throws FieldIsArrayException If one of the two objects contains an array  
     */
    public static boolean isReflectiveEqual(final Object object1, final Object object2)
    {
        if (isSameObject(object1, object2)) {
            return true;
        }

        if (!isBothNotNull(object1, object2)) {
            return false;
        }

        if (!isSameClass(object1, object2)) {
            return false;
        }

        return isDeepEquals(object1, object2);
    }

    /**
     * Hasht alle Attribute innerhalb eines Objekts und generiert einen Hashcode nach den richtlinien der {@link Object#hashCode()} Methode.
     * Implementiert durch {@link Arrays#deepHashCode(Object[])}.
     *
     * @param obj Das Objekt, wessen Hashcode generiert werden soll.
     * @return Ein Hashcode basierend auf dem übergebenen Objekt.
     */
    public static int reflectiveHashCode(final Object obj)
    {
        ClassAccessor objectAccessor = new ClassAccessor(obj);
        Map<String, Object> attributes = objectAccessor.getAllClassAttributes();
        Object[] attributeValues = attributes.values().toArray();
        return Arrays.deepHashCode(attributeValues);
    }

    private DeepEqualsAndHashCode()
    {
    }
    

    private static boolean isSameObject(final Object a, final Object b)
    {
        return a == b;
    }

    private static boolean isSameClass(final Object a, final Object b)
    {
        return a.getClass() == b.getClass();
    }

    private static boolean isSameSize(final Map<String, Object> attributesA, final Map<String, Object> attributesB)
    {
        return attributesA.size() == attributesB.size();
    }

    private static boolean isBothNotNull(final Object a, final Object b)
    {
        return a != null && b != null;
    }

    private static boolean isDeepEquals(final Object a, final Object b)
    {
        Map<String, Object> attributesA = new ClassAccessor(a).getAllClassAttributes();
        Map<String, Object> attributesB = new ClassAccessor(b).getAllClassAttributes();

        return isDeepEquals(attributesA, attributesB);
    }

    private static boolean isDeepEquals(final Map<String, Object> attributesA, final Map<String, Object> attributesB)
    {
        return (isSameSize(attributesA, attributesB) && isAttributesGenericEquals(attributesA, attributesB));
    }


    /**
     * Überprüft ob die einträge der beiden Maps jeweils gleich sind.
     *
     * @param attributesA Eine zu vergleichende Map.
     * @param attributesB Eine andere zu vergleichende Map.
     * @return true, wenn beide Maps den gleichen inhalt besitzen.
     */
    private static boolean isAttributesGenericEquals(final Map<String, Object> attributesA, final Map<String, Object> attributesB)
    {
        for (Map.Entry<String, Object> entryA : attributesA.entrySet())
        {
            Object valA = entryA.getValue();
            Object valB = attributesB.get(entryA.getKey());
            if (!isSingleAttributeGenericEquals(valA, valB))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Vergleicht 2 Attribut-Werte miteinander.
     *
     * @param a Ein Attribut-Wert.
     * @param b Ein anderer Attribut-Wert.
     * @return True, wenn beide Werte gleich sind, False wenn nicht.
     * @throws FieldIsArrayException Wenn eines der Attribute ein Array ist.
     */
    private static boolean isSingleAttributeGenericEquals(final Object a, final Object b)
    {
        return (isSameObject(a, b) || (isBothNotNull(a, b) && isArray(a, b) && a.equals(b)));
    }

    /**
     * Überprüft ob eines der beiden Objekte ein Array ist.
     *
     * @param a Ein zu vergleichendes Objekt.
     * @param b Ein anderes zu vergleichendes Objekt.
     * @return True, wenn mindestens eines der beiden Objekte ein Array ist.
     */
    private static boolean isArray(Object a, Object b)
    {
        if (a.getClass().isArray() || b.getClass().isArray())
        {
            throw new FieldIsArrayException(
                    "Context: Aspect/Generic Hash, Equals, toString; Cannot apply equals method to class with array attributes.");
        }
        return true;
    }


}
