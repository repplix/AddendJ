package io.jexxa.addendj.applicationcore.object;

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
public final class DeepEqualsAndHashCode
{

    /**
     * Generates a hash code for given Object which fulfills requirements of object.hashCode()
     *
     * @param object to create hash code
     * @return The hash code.
     */
    public static int deepHashCode(Object object)
    {
        return Arrays.deepHashCode(
                ClassAccessor
                        .getAllClassAttributes(object)
                        .values()
                        .toArray()
        );
    }


    /**
     * This method can be used to implement a generic equals method.
     *
     * It compares if all attributes of the two given objects are equals
     *
     * @param object1 which is compared with object2
     * @param object2 which is compared to object1
     * @return True if all attributes of both objects are equal, otherwise false
     * @throws IllegalArgumentException If one of the two objects contains an array
     */
    public static boolean deepEquals(final Object object1, final Object object2)
    {
        if (isSameObject(object1, object2))
        {
            return true;
        }

        if (isOneNull(object1, object2))
        {
            return false;
        }
        
        if (!isSameType(object1, object2))
        {
            return false;
        }

        return isDeepEquals(object1, object2);
    }

    private static boolean isDeepEquals(final Object object1, final Object object2)
    {
        Map<String, Object> attributesObject1 = ClassAccessor.getAllClassAttributes(object1);
        Map<String, Object> attributesObject2 = ClassAccessor.getAllClassAttributes(object2);

        if ( attributesObject1.size() != attributesObject2.size() )
        {
            return false;
        }

        return attributesObject1
                .entrySet()
                .stream()
                .allMatch(e -> isEquals(e.getValue(), attributesObject2.get(e.getKey())));
    }

    private static boolean isEquals(final Object object1, final Object object2)
    {
        if (isSameObject(object1, object2))
        {
            return true;
        }

        if (isOneNull(object1, object2))
        {
            return false;
        }

        throwIfArrayDetected(object1, object2);

        if ( object1 != null )
        {
            return object1.equals(object2);
        }

        return false;
    }

    private static void throwIfArrayDetected(Object object1, Object object2)
    {
        if ( object1 == null || object2 == null)
        {
            return;
        }

        if (object1.getClass().isArray() || object2.getClass().isArray())
        {
            throw new IllegalArgumentException(
                    "Context: Aspect/Generic Hash, Equals, toString; Cannot apply equals method to class with array attributes.");
        }
    }



    private static boolean isSameObject(final Object object1, final Object object2)
    {
        if ( object1 == null && object2 == null )
        {  // explicitly added to avoid sonarlint Null pointer warnings
            return true;
        }
        return object1 == object2;
    }


    private static boolean isOneNull(final Object object1, final Object object2)
    {
        return (object1 == null) ^ (object2 == null);  //Xor operation
    }

    private static boolean isSameType(final Object object1, final Object object2)
    {
        if ( object1 == null || object2 == null)
        {
            return false;
        }

        return object1.getClass().equals(object2.getClass());
    }

    private DeepEqualsAndHashCode()
    {
        //Empty constructor 
    }

}
