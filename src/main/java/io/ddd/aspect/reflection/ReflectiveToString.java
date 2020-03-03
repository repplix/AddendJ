package io.ddd.aspect.reflection;

import java.util.List;
import java.util.Map;


/**
 * Eine Klasse, die eine generische Implementierung der {@link Object#toString()} Methode bietet.
 */
public class ReflectiveToString
{

    /**
     * Privater konstruktor, da diese Klasse nur statische Methoden beinhaltet-
     */
    private ReflectiveToString()
    {

    }

    /**
     * Greift auf eine Klasse zu mittels reflection und generiert einen String, der die gesamte Klasse repräsentiert.
     * Format {@code Klassen-Name{Attribut0=Wert0, Attribut1=Wert1}}
     *
     * @param obj Das Objekt, dessen String-Repräsentation generiert werden soll.
     * @return Ein String, der das Objekt repräsentiert.
     */
    public static String toString(final Object obj)
    {
        List<Map.Entry<String, Object>> attributes = new ClassAccessor(obj).getAllClassAttributesSorted();
        StringBuilder attributesAsString = new StringBuilder();
        for (Map.Entry<String, Object> attribute : attributes)
        {
            attributesAsString.append((attributesAsString.length() == 0) ? "" : ", ").append(singleAttributeAsString(attribute));
        }
        return String.format("%s{%s}", obj.getClass().getSimpleName(), attributesAsString.toString());
    }

    /**
     * Transformiert ein einzelnes Attribut einer Klasse in einen String.
     *
     * @param attribute Das zu transformierende Attribut.
     * @return Eine String-Repräsentation des Attributs.
     * @throws FieldIsArrayException Wenn ein Array übergeben wurde.
     */
    private static String singleAttributeAsString(final Map.Entry<String, Object> attribute)
    {
        String name = attribute.getKey();
        String valueString;
        Object value = attribute.getValue();

        if (value == null)
        {
            return String.format("%s=%s", name, "null");
        }

        if (value.getClass().isArray())
        {
            throw new FieldIsArrayException(
                    "Context: Aspect/Generic Hash, Equals, toString; Cannot apply toString to Field " + name);
        }

        if (value instanceof String)
        {
            valueString = String.format("'%s'", value);
        }
        else if (value instanceof Character)
        {
            valueString = String.format("'%s'", value);
        }
        else
        {
            valueString = value.toString();
        }

        return String.format("%s=%s", name, valueString);
    }


}
