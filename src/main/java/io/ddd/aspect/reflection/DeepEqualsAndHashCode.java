package io.ddd.aspect.reflection;

import java.util.Arrays;
import java.util.Map;


/**
 * Eine Klasse, die generische implementationen der {@link Object#equals(Object)} und {@link Object#hashCode()} Methoden bietet.
 */
public class DeepEqualsAndHashCode
{

    /**
     * Privater konstruktor, da diese Klasse nur statische Methoden beinhaltet-
     */
    private DeepEqualsAndHashCode()
    {
    }


    /**
     * Vergleicht beide Objekte nach den gegebenheiten der {@link Object#equals(Object)} Methode.
     * Diese Methode arbeitet mit reflection.
     *
     * @param object1 Das erste Objekt, das verglichen wird. Üblicherweise {@code this} beim aufruf.
     * @param object2 Das Objekt, welches mit dem ersten Objekt verglichen wird.
     * @return True wenn beide Objekte gleich sind, False wenn nicht.
     * @throws FieldIsArrayException Wenn eines der Attribute ein Array ist.
     */
    public static boolean isReflectiveEqual(final Object object1, final Object object2)
    {
        if ( object1 == object2) {
            return true;
        }

        return (isBothNotNull(object1, object2) && isSameClass(object1, object2) && isDeepEquals(object1, object2));
    }

    /**
     * Prüft ob die identität der Objekte die selbe ist.
     *
     * @param a Ein zu vergleichendes Objekt.
     * @param b Ein anderes zu vergleichendes Objekt.
     * @return True wenn beide das selbe Objekt sind.
     */
    private static boolean isSame(final Object a, final Object b)
    {
        return a == b;
    }

    /**
     * Überprüft ob beide Objekte der selben Klasse entsprechen.
     *
     * @param a Ein zu vergleichendes Objekt.
     * @param b Ein anderes zu vergleichendes Objekt.
     * @return True wenn beide Objekte der selben Klasse entsprechen.
     */
    private static boolean isSameClass(final Object a, final Object b)
    {
        return a.getClass() == b.getClass();
    }

    /**
     * Vergleicht beide Objekte an hand ihrer Attribute mittels reflection. Berücksichtigt auch Oberklassen.
     *
     * @param a Ein zu vergleichendes Objekt.
     * @param b Ein anderes zu vergleichendes Objekt.
     * @return True, wenn beide Objekte die gleichen Attribute haben mit den gleichen werten.
     */
    private static boolean isDeepEquals(final Object a, final Object b)
    {
        Map<String, Object> attributesA = new ClassAccessor(a).getAllClassAttributes();
        Map<String, Object> attributesB = new ClassAccessor(b).getAllClassAttributes();

        return isDeepEquals(attributesA, attributesB);
    }


    /**
     * DeepEquals vergleich zweier Maps.
     *
     * @param attributesA Eine Map, die aus Attribut-Namen und Attribut-Werten eines Objekts besteht.
     * @param attributesB Eine Map, die aus Attribut-Namen und Attribut-Werten eines Objekts besteht.
     * @return True, wenn beide Maps gleich sind, false wenn nicht.
     */
    private static boolean isDeepEquals(final Map<String, Object> attributesA, final Map<String, Object> attributesB)
    {
        return (isSameSize(attributesA, attributesB) && isAttributesGenericEquals(attributesA, attributesB));
    }

    /**
     * Vergleicht ob beide Maps die selbe größe haben.
     *
     * @param attributesA Eine zu vergleichende Map.
     * @param attributesB Eine andere zu vergleichende Map.
     * @return True, wenn beide Maps die selbe größe haben.
     */
    private static boolean isSameSize(final Map<String, Object> attributesA, final Map<String, Object> attributesB)
    {
        return attributesA.size() == attributesB.size();
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
        return (isSame(a, b) || (isBothNotNull(a, b) && isArray(a, b) && a.equals(b)));
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

    /**
     * Prüft ob beide Objekte nicht null sind.
     *
     * @param a Ein Objekt.
     * @param b Ein anderes Objekt.
     * @return True, wenn keines der beiden Objekte null ist.
     */
    private static boolean isBothNotNull(final Object a, final Object b)
    {
        return a != null && b != null;
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
}
