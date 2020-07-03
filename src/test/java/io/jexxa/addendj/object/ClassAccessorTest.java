package io.jexxa.addendj.object;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import io.jexxa.addendj.valueobject.BaseValueObject;
import io.jexxa.addendj.valueobject.DerivedValueObject;
import io.jexxa.addendj.valueobject.PrimitiveDataTypeValueObject;
import io.jexxa.addendj.valueobject.ThreeStringsValueObject;
import org.junit.jupiter.api.Test;


class ClassAccessorTest
{


    /**
     * Checks class hierarchy with Inheritance .
     */
    @Test
    void testClassHierarchyWithInheritance()
    {
        assertEquals(2, ClassAccessor.getClassHierarchy(new DerivedValueObject(1,2) ).size());
    }


    /**
     * Testet ob alle Attribute korrekt wiedergegeben werden, auch geerbte attribute.
     */
    @Test
    void testGetAllAttributes()
    {
        assertEquals(2, ClassAccessor.getAllClassAttributes(new DerivedValueObject(1, 2)).get(BaseValueObject.class.getName() + ".value"));
        assertEquals(1, ClassAccessor.getAllClassAttributes(new DerivedValueObject(1, 2)).get(DerivedValueObject.class.getName() + ".b"));
    }

    /**
     * Attribute sollten nach der ebene der Vererbung sortiert werden und dann alphabetisch.
     */
    @Test
    void testGetAllAttributesSortedInheritance()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new DerivedValueObject(1, 2));
        assertEquals("b", attributes.get(0).getKey());
        assertEquals(1, attributes.get(0).getValue());

        assertEquals("value", attributes.get(1).getKey());
        assertEquals(2, attributes.get(1).getValue());
    }

    @Test
    void testGetAllAttributesSorted()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(
                new PrimitiveDataTypeValueObject(1, 2L, 12.0f, 13.0, true, 'd', (byte) 12, (short) 13)
        );
        assertEquals("intValue", attributes.get(0).getKey());
        assertEquals(1, attributes.get(0).getValue());

        assertEquals("longValue", attributes.get(1).getKey());
        assertEquals(2L, attributes.get(1).getValue());

        assertEquals("floatValue", attributes.get(2).getKey());
        assertEquals(12.0f, attributes.get(2).getValue());

        assertEquals("doubleValue", attributes.get(3).getKey());
        assertEquals(13.0, attributes.get(3).getValue());

        assertEquals("booleanValue", attributes.get(4).getKey());
        assertEquals(true, attributes.get(4).getValue());

        assertEquals("charValue", attributes.get(5).getKey());
        assertEquals('d', attributes.get(5).getValue());

        assertEquals("byteValue", attributes.get(6).getKey());
        assertEquals((byte) 12, attributes.get(6).getValue());

        assertEquals("shortValue", attributes.get(7).getKey());
        assertEquals((short) 13, attributes.get(7).getValue());
    }

    /**
     * Attribute mit vererbungshierarchie, die unsortiert deklariert wurden sollten richtig sortiert zurückgegeben werden.
     */
    @Test
    void testGetAllAttributesSortedUnorderedHierarchy()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new ThreeStringsValueObject(1, "two", "three", "four"));

        assertEquals("b", attributes.get(0).getKey());
        assertEquals("two", attributes.get(0).getValue());

        assertEquals("c", attributes.get(1).getKey());
        assertEquals("three", attributes.get(1).getValue());

        assertEquals("d", attributes.get(2).getKey());
        assertEquals("four", attributes.get(2).getValue());

        assertEquals("value", attributes.get(3).getKey());
        assertEquals(1, attributes.get(3).getValue());
    }


}



































