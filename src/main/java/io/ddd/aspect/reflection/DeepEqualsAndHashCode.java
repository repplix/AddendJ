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
 *
 * NOTE: classes with arrays as attributes are not supported at the moment
 */
public class DeepEqualsAndHashCode
{

    /**
     * Generates a hash code for given Object which fulfills requirements of object.hashCode()
     *
     * @param object to create hash code
     * @return The hash code.
     */
    public static int reflectiveHashCode(final Object object)
    {
        ClassAccessor objectAccessor = new ClassAccessor(object);
        Map<String, Object> attributes = objectAccessor.getAllClassAttributes();
        Object[] attributeValues = attributes.values().toArray();
        return Arrays.deepHashCode(attributeValues);
    }

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

        if (isOneNull(object1, object2)) {
            return false;
        }
        
        if (object1.getClass() != object2.getClass()) {
            return false;
        }

        return isDeepEquals(object1, object2);
    }

    private static boolean isDeepEquals(final Object a, final Object b)
    {
        Map<String, Object> attributesA = new ClassAccessor(a).getAllClassAttributes();
        Map<String, Object> attributesB = new ClassAccessor(b).getAllClassAttributes();

        if ( attributesA.size() != attributesB.size() )    {
            return false;
        }

        for (Map.Entry<String, Object> entryA : attributesA.entrySet())
        {
            Object valA = entryA.getValue();
            Object valB = attributesB.get(entryA.getKey());
            if (!isEquals(valA, valB))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean isEquals(final Object a, final Object b)
    {
        if (isSameObject(a, b)) {
            return true;
        }
        if (isOneNull(a, b)) {
            return false;
        }

        throwIfArrayDetected(a, b);

        return a.equals(b);
    }

    private static void throwIfArrayDetected(Object a, Object b)
    {
        if (a.getClass().isArray() || b.getClass().isArray())
        {
            throw new FieldIsArrayException(
                    "Context: Aspect/Generic Hash, Equals, toString; Cannot apply equals method to class with array attributes.");
        }
    }



    private static boolean isSameObject(final Object a, final Object b)
    {
        if ( a == null && b == null ) {  // explicitly added to avoid sonarlint Null pointer warnings
            return true;
        }
        return a == b;
    }


    private static boolean isOneNull(final Object a, final Object b)
    {
        return (a == null && b != null) || (a != null && b == null);
    }


    private DeepEqualsAndHashCode()
    {
    }

}
