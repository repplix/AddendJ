package io.jexxa.addendj.applicationcore.object;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import io.jexxa.addendj.applicationcore.testclasses.valueobject.BaseValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.PrimitiveDataTypeValueObject;
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


    @Test
    void testGetAllAttributesFromInheritedClass()
    {
        assertEquals(2, ClassAccessor.getAllClassAttributes(new DerivedValueObject(1, 2)).get(BaseValueObject.class.getName() + ".value"));
        assertEquals(1, ClassAccessor.getAllClassAttributes(new DerivedValueObject(1, 2)).get(DerivedValueObject.class.getName() + ".derivedValue"));
    }

    /**
     * Attribute sollten nach der ebene der Vererbung sortiert werden und dann alphabetisch.
     */
    @Test
    void testGetAllAttributesSortedInheritance()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new DerivedValueObject(1, 2));
        assertEquals("derivedValue", attributes.get(0).getKey());
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

}



































