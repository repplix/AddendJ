package io.jexxa.addendj.aspect.reflection;

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
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(obj);
        StringBuilder attributesAsString = new StringBuilder();

        attributes.forEach( e -> attributesAsString.append((attributesAsString.length() == 0) ? "" : ", ").
                                                    append(objectToString(e.getKey(), e.getValue())));
        
        return String.format("%s{%s}", obj.getClass().getSimpleName(), attributesAsString.toString());
    }

    private static String objectToString(final String name, Object object)
    {
        String format = "%s=%s";
        if (object == null)
        {
            return String.format(format, name, "null");
        }

        if (object.getClass().isArray())
        {
            throw new IllegalArgumentException(
                    "Context: Aspect/Generic Hash, Equals, toString; Cannot apply toString to Field " + name);
        }

        if (object instanceof String ||
            object instanceof Character)
        {
            return  String.format(format, name, String.format("'%s'", object));
        }

        return String.format(format, name, object.toString());
    }

    private DeepToString()
    {
    }

}
