package io.ddd.aspect.reflection;

import java.util.List;
import java.util.Map;


/**
 * Provides a {@link Object#toString()} method which shows all members as key value pairs
 *
 * The string is formatted as {@code <name of class>{<name of attribute 1>=<value as string>, ..., <name of attribute n>=<value as string>>}}
 *
 * Note: This implementation of toString is just for debugging/logging purpose. In case you to serialize objects use some json lib 
 * Note: Since {@link DeepEqualsAndHashCode } does not support Arrays, they are also disabled in this class
 */
public class DeepToString
{

    public static String toString(final Object obj)
    {
        List<Map.Entry<String, Object>> attributes = new ClassAccessor(obj).getAllClassAttributesSorted();
        StringBuilder attributesAsString = new StringBuilder();
        for (Map.Entry<String, Object> attribute : attributes)
        {
            attributesAsString.append((attributesAsString.length() == 0) ? "" : ", ").append(objectToString(attribute.getKey(), attribute.getValue()));
        }
        return String.format("%s{%s}", obj.getClass().getSimpleName(), attributesAsString.toString());
    }

    private static String objectToString(final String name, Object object)
    {
        if (object == null)
        {
            return String.format("%s=%s", name, "null");
        }

        if (object.getClass().isArray())
        {
            throw new IllegalArgumentException(
                    "Context: Aspect/Generic Hash, Equals, toString; Cannot apply toString to Field " + name);
        }

        if (object instanceof String ||
            object instanceof Character)
        {
            return  String.format("%s=%s", name, String.format("'%s'", object));
        }

        return String.format("%s=%s", name, object.toString());
    }

    private DeepToString()
    {
    }

}
